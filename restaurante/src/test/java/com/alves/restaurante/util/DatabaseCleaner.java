package com.alves.restaurante.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseCleaner {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String CHAVE_TABELAS = "tabela";
    private static final String CHAVE_TABELAS_COM_ID = "tabelaComId";

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    public void clearTables() {
        try (Connection connection = dataSource.getConnection()) {
            this.connection = connection;

            checkTestDatabase();
            tryToClearTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.connection = null;
        }
    }

    private void checkTestDatabase() throws SQLException {
        String catalog = connection.getCatalog();

        if (catalog == null || !catalog.endsWith("test")) {
            throw new RuntimeException(
                    "Cannot clear database tables because '" + catalog + "' is not a test database (suffix 'test' not found).");
        }
    }

    private void tryToClearTables() throws SQLException {
        Map<String, List<String>> tableNames = getTableNames();
        clear(tableNames);
    }

    private Map<String, List<String>> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        List<String> tableNamesWithId = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});
        while (rs.next()) {
            String tabela = rs.getString("TABLE_NAME");
            tableNames.add(tabela);
        }
        tableNames.remove("flyway_schema_history");
        tableNamesWithId = findTableWithId(tableNames);

        map.put(CHAVE_TABELAS, tableNames);
        map.put(CHAVE_TABELAS_COM_ID, tableNamesWithId);

        return map;
    }

    private List<String> findTableWithId(List<String> tableNames) throws SQLException {
        List<String> table = new ArrayList<>();
        Statement stm = connection.createStatement();
        for (String tableName : tableNames) {
            ResultSet rs1 = stm.executeQuery("SELECT * FROM " + tableName + " LIMIT 0");
            ResultSetMetaData rsMetaData = rs1.getMetaData();
            for (int i = 1; i < rsMetaData.getColumnCount(); i++) {
                if (rsMetaData.getColumnName(1).equals("id")) {
                    table.add(tableName);
                }
            }
        }
        return table;
    }

    private void clear(Map<String, List<String>> map) throws SQLException {
        Statement statement = buildSqlStatement(map);

        logger.debug("Executing SQL");
        statement.executeBatch();
    }

    private Statement buildSqlStatement(Map<String, List<String>> map) throws SQLException {
        Statement statement = connection.createStatement();

        statement.addBatch(sql("SET session_replication_role = 'replica'"));
        addTruncateSatements(map.get(CHAVE_TABELAS), statement);
        statement.addBatch(sql("SET session_replication_role = 'origin'"));
        restartSequence(map.get(CHAVE_TABELAS_COM_ID), statement);

        return statement;
    }

    private void restartSequence(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                statement.addBatch(sql("ALTER SEQUENCE " + tableName + "_id_seq RESTART"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addTruncateSatements(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                statement.addBatch(sql("TRUNCATE TABLE " + tableName + " CASCADE"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String sql(String sql) {
        logger.debug("Adding SQL: {}", sql);
        return sql;
    }

}

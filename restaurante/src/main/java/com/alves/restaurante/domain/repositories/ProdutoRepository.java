package com.alves.restaurante.domain.repositories;

import com.alves.restaurante.domain.model.Produto;
import com.alves.restaurante.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("FROM Produto WHERE restaurante.id = :restaurante AND id = :produto")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId,
                               @Param("produto") Long produtoId);

    @Query("from Produto where ativo = true and restaurante = :restaurante")
    List<Produto> findByAtivoRestaurante(Restaurante restaurante);

    List<Produto> findTodosByRestaurante(Restaurante restaurante);
}

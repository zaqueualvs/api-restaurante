CREATE TABLE cozinha
(
    id   BIGSERIAL   NOT NULL,
    nome VARCHAR(60) NOT NULL,

    PRIMARY KEY (id)
);
CREATE TABLE estado
(
    id   BIGSERIAL   NOT NULL,
    nome VARCHAR(60) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE cidade
(
    id        BIGSERIAL   NOT NULL,
    nome      VARCHAR(60) NOT NULL,
    estado_id BIGINT      NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_cidade_estado
        FOREIGN KEY (estado_id) REFERENCES estado (id)
);
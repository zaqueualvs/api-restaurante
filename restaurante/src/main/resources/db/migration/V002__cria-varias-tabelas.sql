CREATE TABLE forma_pagamento
(
    id        BIGSERIAL NOT NULL,
    descricao VARCHAR(60),
    PRIMARY KEY (id)
);
CREATE TABLE grupo
(
    id   BIGSERIAL NOT NULL,
    nome VARCHAR(60),
    PRIMARY KEY (id)
);
CREATE TABLE permissao
(
    id        BIGSERIAL NOT NULL,
    descricao VARCHAR(60),
    nome      VARCHAR(60),
    PRIMARY KEY (id)
);

CREATE TABLE grupo_permissao
(
    grupo_id     BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL,
    CONSTRAINT fk_grupo_permissao_permissao
        FOREIGN KEY (permissao_id)
            REFERENCES permissao (id),
    CONSTRAINT fk_grupo_permissao_grupo
        FOREIGN KEY (grupo_id)
            REFERENCES grupo (id)
);


CREATE TABLE restaurante
(
    taxa_frete           NUMERIC(38, 2),
    cozinha_id           BIGINT,
    data_atualizacao     TIMESTAMP(0),
    data_cadastro        TIMESTAMP(0),
    endereco_cidade_id   BIGINT,
    id                   BIGSERIAL NOT NULL,
    endereco_bairro      VARCHAR(60),
    endereco_cep         VARCHAR(60),
    endereco_complemento VARCHAR(60),
    endereco_logradouro  VARCHAR(60),
    endereco_numero      VARCHAR(60),
    nome                 VARCHAR(60),
    PRIMARY KEY (id),
    CONSTRAINT fk_restaurante_cozinha
        FOREIGN KEY (cozinha_id)
            REFERENCES cozinha (id),
    CONSTRAINT fk_restaurante_cidade
        FOREIGN KEY (endereco_cidade_id)
            REFERENCES cidade (id)
);
CREATE TABLE produto
(
    ativo          BOOLEAN,
    preco          NUMERIC(38, 2),
    id             BIGSERIAL NOT NULL,
    restaurante_id BIGINT,
    descricao      VARCHAR(255),
    nome           VARCHAR(60),
    PRIMARY KEY (id),
    CONSTRAINT fk_produto_restaurante
        FOREIGN KEY (restaurante_id)
            REFERENCES restaurante (id)
);

CREATE TABLE restaurante_forma_pagamento
(
    forma_pagamento_id BIGINT NOT NULL,
    restaurante_id     BIGINT NOT NULL,
    CONSTRAINT fk_restaurante_forma_pagamento_forma_pagamento
        FOREIGN KEY (forma_pagamento_id)
            REFERENCES forma_pagamento (id),
    CONSTRAINT fk_restaurante_forma_pagamento_restaurante
        FOREIGN KEY (restaurante_id)
            REFERENCES restaurante (id)
);

CREATE TABLE restaurante_produto
(
    produto_id     BIGINT NOT NULL UNIQUE,
    restaurante_id BIGINT NOT NULL,
    CONSTRAINT fk_restaurante_produto_produto
        FOREIGN KEY (produto_id)
            REFERENCES produto (id),
    CONSTRAINT fk_restaurante_produto_restaurante
        FOREIGN KEY (restaurante_id)
            REFERENCES restaurante (id)
);

CREATE TABLE usuario
(
    data_cadastro TIMESTAMP(6),
    id            BIGSERIAL NOT NULL,
    email         VARCHAR(60),
    nome          VARCHAR(60),
    senha         VARCHAR(60),
    PRIMARY KEY (id)
);

CREATE TABLE usuario_grupo
(
    grupo_id   BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario_grupos_grupo
        FOREIGN KEY (grupo_id)
            REFERENCES grupo (id),
    CONSTRAINT fk_usuario_grupo_usuario
        FOREIGN KEY (usuario_id)
            REFERENCES usuario (id)
);
CREATE TABLE pedido
(
    id                   BIGSERIAL      NOT NULL,
    subtotal             NUMERIC(10, 2) NOT NULL,
    taxa_frete           NUMERIC(10, 2) NOT NULL,
    valor_total          NUMERIC(10, 2) NOT NULL,

    restaurante_id       BIGINT         NOT NULL,
    usuario_cliente_id   BIGINT         NOT NULL,
    forma_pagamento_id   BIGINT         NOT NULL,

    endereco_cidade_id   BIGINT         NOT NULL,
    endereco_cep         VARCHAR(9)     NOT NULL,
    endereco_logradouro  VARCHAR(100)   NOT NULL,
    endereco_numero      VARCHAR(20)    NOT NULL,
    endereco_complemento VARCHAR(60)    NULL,
    endereco_bairro      VARCHAR(60)    NOT NULL,

    status_pedido        VARCHAR(10)    NOT NULL,
    data_criacao         TIMESTAMP(0)   NOT NULL,
    data_confirmacao     TIMESTAMP(0)   NULL,
    data_cancelamento    TIMESTAMP(0)   NULL,
    data_entrega         TIMESTAMP(0)   NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_pedido_restaurante
        FOREIGN KEY (restaurante_id)
            REFERENCES restaurante (id),
    CONSTRAINT fk_pedido_usuario_cliente
        FOREIGN KEY (usuario_cliente_id)
            REFERENCES usuario (id),
    CONSTRAINT fk_pedido_forma_pagamento
        FOREIGN KEY (forma_pagamento_id)
            REFERENCES forma_pagamento (id)
);

CREATE TABLE item_pedido
(
    id             BIGSERIAL      NOT NULL,
    quantidade     SMALLINT       NOT NULL,
    preco_unitario NUMERIC(10, 2) NOT NULL,
    preco_total    NUMERIC(10, 2) NOT NULL,
    observacao     VARCHAR(255)   NULL,

    pedido_id      BIGINT         NOT NULL,
    produto_id     BIGINT         NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_item_pedido_produto
        FOREIGN KEY (produto_id)
            REFERENCES produto (id),
    CONSTRAINT fk_item_pedido_pedido
        FOREIGN KEY (pedido_id)
            REFERENCES pedido (id)
);
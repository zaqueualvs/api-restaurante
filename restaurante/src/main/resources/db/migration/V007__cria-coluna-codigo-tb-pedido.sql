ALTER TABLE pedido
    ADD COLUMN codigo UUID NOT NULL;
ALTER TABLE pedido
    ADD CONSTRAINT uk_pedido_codigo UNIQUE (codigo);
CREATE TABLE restaurante_usuario_responsavel
(
    restaurante_id BIGINT NOT NULL,
    usuario_id     BIGINT NOT NULL,
    CONSTRAINT FK_restaurante_usuario_responsavel_restaurante
        FOREIGN KEY (restaurante_id)
            references restaurante (id),
    CONSTRAINT FK_restaurante_usuario_responsavel_usuario
        FOREIGN KEY (usuario_id)
            references usuario (id)

)
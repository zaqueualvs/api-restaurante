package com.alves.restaurante.domain.exceptions;

import java.util.UUID;

public class PedidoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoEncontradoException(UUID codigo) {
        super(String.format("Não existe um pedido com código %s", codigo));
    }

}

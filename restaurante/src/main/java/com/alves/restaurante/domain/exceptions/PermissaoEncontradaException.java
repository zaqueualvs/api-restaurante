package com.alves.restaurante.domain.exceptions;

public class PermissaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de permissão com código %d", id));
    }

}

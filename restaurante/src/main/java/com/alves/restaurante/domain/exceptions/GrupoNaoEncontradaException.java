package com.alves.restaurante.domain.exceptions;

public class GrupoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradaException(String message) {
        super(message);
    }

    public GrupoNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de grupo com código %d", id));
    }

}

package com.alves.restaurante.domain.exceptions;

public abstract class EntidadeNaoEncontradaException extends NegocioException{

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}

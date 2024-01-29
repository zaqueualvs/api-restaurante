package com.alves.restaurante.domain.exceptions;


public class NegocioException extends RuntimeException{

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}

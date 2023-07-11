package com.tastecamp.api.Exception;

public class ConflitoException extends RuntimeException {
    public ConflitoException(String mensagem){
        super(mensagem);
    }
}

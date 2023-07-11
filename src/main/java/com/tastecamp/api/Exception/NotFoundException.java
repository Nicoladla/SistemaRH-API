package com.tastecamp.api.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String mensagem){
        super(mensagem);
    }
}
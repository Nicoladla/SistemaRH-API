package com.tastecamp.api.Exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String mensagem){
        super(mensagem);
    }
}

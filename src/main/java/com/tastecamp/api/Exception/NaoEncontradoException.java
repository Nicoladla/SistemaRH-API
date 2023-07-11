package com.tastecamp.api.Exception;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
package com.klima.financasspring.exception;

public class EmailNaoEncontradoException extends RuntimeException {
    public EmailNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

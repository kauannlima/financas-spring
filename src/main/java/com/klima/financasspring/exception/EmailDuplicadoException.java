package com.klima.financasspring.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String mensagem) {
        super(mensagem);
    }
}

package com.klima.financasspring.exception;

public class SaldoInsuficienteException extends  RuntimeException{
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}

package com.klima.financasspring.exception;

public class DespesaNaoEncontradaException extends  RuntimeException{
    public DespesaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

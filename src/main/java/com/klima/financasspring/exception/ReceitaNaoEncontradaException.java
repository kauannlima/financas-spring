package com.klima.financasspring.exception;

public class ReceitaNaoEncontradaException extends  RuntimeException{
    public ReceitaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

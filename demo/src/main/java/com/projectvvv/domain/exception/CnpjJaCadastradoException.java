package com.projectvvv.domain.exception;

public class CnpjJaCadastradoException extends RuntimeException {

    public CnpjJaCadastradoException(String cnpj) {
        super("CNPJ já cadastrado: " + cnpj);
    }
}
 
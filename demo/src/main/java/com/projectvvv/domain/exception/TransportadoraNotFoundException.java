package com.projectvvv.domain.exception;

public class TransportadoraNotFoundException extends RuntimeException {

    public TransportadoraNotFoundException(Long id) {
        super("Transportadora não encontrada com id: " + id);
    }
}

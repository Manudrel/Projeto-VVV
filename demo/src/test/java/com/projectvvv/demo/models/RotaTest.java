package com.projectvvv.demo.models;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Rota;

public class RotaTest {
    
    @Test
    void testRota() {
        Rota rota = new Rota();
        rota.setId(1L);
        rota.setOrigem(100);
        rota.setDestino(200);

        assert rota.getId().equals(1L);
        assert rota.getOrigem().equals(100);
        assert rota.getDestino().equals(200);
    }
}

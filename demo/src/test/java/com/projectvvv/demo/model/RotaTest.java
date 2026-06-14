package com.projectvvv.demo.model;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.model.Rota;

public class RotaTest {

    @Test
    void testRota() {

        Cidade origem = new Cidade();
        origem.setId(100L);

        Cidade destino = new Cidade();
        destino.setId(200L);

        Rota rota = new Rota();
        rota.setId(1L);
        rota.setOrigem(origem);
        rota.setDestino(destino);

        assert rota.getId().equals(1L);
        assert rota.getOrigem().getId().equals(100L);
        assert rota.getDestino().getId().equals(200L);
    }
}
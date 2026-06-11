package com.projectvvv.demo.model;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Cidade;

public class CidadeTest {
    
    @Test
    void CidadeConstructorTest(){
        Long id = 1L;
        String estado = "São Paulo";
        String cidade = "São Paulo";
        String pais = "Brasil";

        Cidade cidadeObj = new Cidade(id, estado, cidade, pais);

        assert cidadeObj.getId().equals(id);
        assert cidadeObj.getEstado().equals(estado);
        assert cidadeObj.getCidade().equals(cidade);
        assert cidadeObj.getPais().equals(pais);
    }
}

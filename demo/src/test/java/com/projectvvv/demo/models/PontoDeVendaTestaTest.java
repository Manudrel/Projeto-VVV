package com.projectvvv.demo.models;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.PontoDeVenda;

public class PontoDeVendaTestaTest {
    
    @Test
    void PontoDeVendaConstructorTest(){
        long codigoPonto = 1L;
        String nome = "Ponto A";
        String cnpj = "12345678000199";
        String endereco = "Rua Miguel Ângelo, 96";
        String telefone = "21999999999";

        PontoDeVenda pontoDeVenda = new PontoDeVenda(codigoPonto, nome, cnpj, endereco, telefone);

        assert pontoDeVenda.getCodigoPonto().equals(codigoPonto);
        assert pontoDeVenda.getNome().equals(nome);
        assert pontoDeVenda.getCnpj().equals(cnpj);
        assert pontoDeVenda.getEndereco().equals(endereco);
        assert pontoDeVenda.getTelefone().equals(telefone);
    }
}

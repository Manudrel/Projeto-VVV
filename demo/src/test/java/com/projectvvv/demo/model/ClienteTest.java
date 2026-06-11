package com.projectvvv.demo.model;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Cliente;

public class ClienteTest {
    
    @Test
    void ClienteConstructorTest(){
        long id = 1L;
        String nome = "João Silva";
        String cpf = "123.456.789-00";
        String endereco = "Rua Miguel Ângelo, 96";
        String telefone = "21987654321";
        LocalDate dataNascimento = LocalDate.of(1990, 1, 1);

        Cliente clienteObj = new Cliente(id, nome, cpf, endereco, telefone, dataNascimento);

        assert clienteObj.getId() == id;
        assert clienteObj.getNome().equals(nome);
        assert clienteObj.getCpf().equals(cpf);
        assert clienteObj.getEndereco().equals(endereco);
        assert clienteObj.getTelefone().equals(telefone);
        assert clienteObj.getDataNascimento().equals(dataNascimento);

    }
}

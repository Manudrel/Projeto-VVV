package com.projectvvv.demo.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;

public class FuncionarioTest {
    
    @Test
    void FuncionarioConstructorTest(){
        long id = 1L;
        String nome = "João Silva";
        String cpf = "123.456.789-00";
        String endereco = "Rua Miguel Ângelo, 96";
        Cargo cargo = Cargo.GERENTE;
        String telefone = "21987654321";
        LocalDate dataNascimento = LocalDate.of(1990, 1, 1);

        Funcionario funcionarioObj = new Funcionario(id, nome, cpf, endereco, cargo, telefone, dataNascimento);

        assert funcionarioObj.getId() == id;
        assert funcionarioObj.getNome().equals(nome);
        assert funcionarioObj.getCpf().equals(cpf);
        assert funcionarioObj.getEndereco().equals(endereco);
        assert funcionarioObj.getCargo() == cargo;
        assert funcionarioObj.getTelefone().equals(telefone);
        assert funcionarioObj.getDataNascimento().equals(dataNascimento);
    }
}

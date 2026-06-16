package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.repository.FuncionarioRepository;

@DataJpaTest
class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Test
    @DisplayName("existsByCpf - Deve retornar true quando o CPF já estiver cadastrado")
    void deveRetornarTrueSeCpfExistir() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Felipe");
        funcionario.setCpf("12345678901");
            
        funcionarioRepository.save(funcionario);

        boolean existe = funcionarioRepository.existsByCpf("12345678901");

        assertTrue(existe, "Deveria retornar true para um CPF cadastrado no banco");
    }

    @Test
    @DisplayName("existsByCpf - Deve retornar false quando o CPF não estiver cadastrado no banco")
    void deveRetornarFalseSeCpfNaoExistir() {
        boolean existe = funcionarioRepository.existsByCpf("00000000000");

        assertFalse(existe, "Deveria retornar false para um CPF que não existe no banco");
    }
}
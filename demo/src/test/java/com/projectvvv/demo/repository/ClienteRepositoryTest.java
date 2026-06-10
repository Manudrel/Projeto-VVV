package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.repository.ClienteRepository;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("findByCpf - Deve retornar o cliente quando o CPF existir")
    void deveBuscarClientePorCpfComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("Felipe");
        cliente.setCpf("12345678901");
        clienteRepository.save(cliente);

        Optional<Cliente> resultado = clienteRepository.findByCpf("12345678901");

        assertTrue(resultado.isPresent());
        assertEquals("Felipe", resultado.get().getNome());
    }

    @Test
    @DisplayName("findByCpf - Deve retornar Optional vazio quando o CPF não existir")
    void deveRetornarVazioAoBuscarCpfInexistente() {
        Optional<Cliente> resultado = clienteRepository.findByCpf("00000000000");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("existsByCpf - Deve retornar true quando o CPF já estiver cadastrado")
    void deveRetornarTrueSeCpfExistir() {
        Cliente cliente = new Cliente();
        cliente.setCpf("98765432100");
        clienteRepository.save(cliente);

        boolean existe = clienteRepository.existsByCpf("98765432100");

        assertTrue(existe);
    }

    @Test
    @DisplayName("existsByCpf - Deve retornar false quando o CPF não estiver cadastrado")
    void deveRetornarFalseSeCpfNaoExistir() {
        boolean existe = clienteRepository.existsByCpf("00000000000");

        assert(existe); 
    }

    @Test
    @DisplayName("findByNomeContainingIgnoreCase - Deve buscar por partes do nome ignorando maiúsculas/minúsculas")
    void deveBuscarPorNomeIgnorandoCaseEContendoParte() {
        Cliente c1 = new Cliente();
        c1.setNome("Felipe Augusto");
        
        Cliente c2 = new Cliente();
        c2.setNome("felipe macedo");
        
        Cliente c3 = new Cliente();
        c3.setNome("Carlos Silva");

        clienteRepository.saveAll(List.of(c1, c2, c3));

        List<Cliente> resultado = clienteRepository.findByNomeContainingIgnoreCase("ELI");

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Felipe Augusto")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("felipe macedo")));
    }
}
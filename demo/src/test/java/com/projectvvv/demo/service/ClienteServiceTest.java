package com.projectvvv.demo.service;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.service.ClienteService;
import com.projectvvv.domain.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("listarTodos - Deve retornar a lista de todos os clientes do banco")
    void deveListarTodosOsClientes() {
       
        List<Cliente> clientesEsperados = List.of(new Cliente(), new Cliente());
        Mockito.when(repository.findAll()).thenReturn(clientesEsperados);
       
        List<Cliente> resultado = clienteService.listarTodos();
       
        assertEquals(2, resultado.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("buscarPorId - Deve retornar o cliente quando o ID existir")
    void deveBuscarClientePorIdComSucesso() {
       
        Long id = 1L;
        Cliente clienteEsperado = new Cliente();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(clienteEsperado));
       
        Cliente resultado = clienteService.buscarPorId(id);
       
        assertNotNull(resultado);
        assertEquals(clienteEsperado, resultado);
    }

    @Test
    @DisplayName("buscarPorId - Deve lançar NoSuchElementException quando o ID não existir")
    void deveLancarErroAoBuscarIdInexistente() {
       
        Long id = 99L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
       
        assertThrows(NoSuchElementException.class, () -> {
            clienteService.buscarPorId(id);
        });
    }

    @Test
    @DisplayName("salvar - Deve persistir o cliente e retornar o objeto salvo")
    void deveSalvarClienteComSucesso() {
       
        Cliente clienteParaSalvar = new Cliente();
        Mockito.when(repository.save(clienteParaSalvar)).thenReturn(clienteParaSalvar);
       
        Cliente resultado = clienteService.salvar(clienteParaSalvar);
       
        assertNotNull(resultado);
        Mockito.verify(repository, Mockito.times(1)).save(clienteParaSalvar);
    }

    @Test
    @DisplayName("excluir - Deve chamar o método de deleção do repository")
    void deveExcluirClienteComSucesso() {
       
        Long id = 1L;
       
        clienteService.excluir(id);
       
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("atualizar - Deve mesclar apenas as propriedades preenchidas e manter as antigas")
    void deveAtualizarApenasCamposPreenchidos() {
       
        Long id = 1L;
        
        Cliente clienteAntigo = new Cliente();
        clienteAntigo.setNome("Felipe Antigo");
        clienteAntigo.setCpf("123.456.789-00");
        clienteAntigo.setDataNascimento(LocalDate.of(1990, 1, 1));

        Cliente dadosNovos = new Cliente();
        dadosNovos.setNome("Felipe Novo");
        dadosNovos.setCpf(null);         
        dadosNovos.setDataNascimento(null);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(clienteAntigo));
        Mockito.when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));
       
        Cliente resultado = clienteService.atualizar(id, dadosNovos);

        assertEquals("Felipe Novo", resultado.getNome());    
        assertEquals("123.456.789-00", resultado.getCpf());  
        assertEquals(LocalDate.of(1990, 1, 1), resultado.getDataNascimento());              
    }
}
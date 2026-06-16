package com.projectvvv.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.model.PontosDeVendaDoFuncionario;
import com.projectvvv.domain.repository.FuncionarioRepository;
import com.projectvvv.domain.repository.PontoDeVendaRepository;
import com.projectvvv.domain.service.FuncionarioService;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private PontoDeVendaRepository pontoDeVendaRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    // --- TESTES DO MÉTODO CRIAR (E VALIDAÇÕES) ---

    @Test
    @DisplayName("Criar - Deve salvar funcionário com sucesso quando dados e pontos forem válidos")
    void deveCriarFuncionarioComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678900");
        
        PontosDeVendaDoFuncionario p1 = new PontosDeVendaDoFuncionario();
        p1.setDiaTrabalho((short) 1);
        PontosDeVendaDoFuncionario p2 = new PontosDeVendaDoFuncionario();
        p2.setDiaTrabalho((short) 2);
        
        funcionario.setPontosDeVenda(List.of(p1, p2));

        Mockito.when(funcionarioRepository.existsByCpf("12345678900")).thenReturn(false);
        Mockito.when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario criado = funcionarioService.criar(funcionario);

        assertNotNull(criado);
        Mockito.verify(funcionarioRepository, Mockito.times(1)).save(funcionario);
    }

    @Test
    @DisplayName("Criar - Deve lançar exceção se o CPF já estiver cadastrado")
    void deveLancarExcecaoQuandoCpfJaExistir() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678900");

        Mockito.when(funcionarioRepository.existsByCpf("12345678900")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.criar(funcionario);
        });

        assertEquals("CPF já cadastrado.", exception.getMessage());
        Mockito.verify(funcionarioRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Criar - Deve lançar exceção se o funcionário tiver mais de 2 pontos de venda")
    void deveLancarExcecaoQuandoMaisDeDoisPontos() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678900");
        
        // Adicionando 3 pontos de venda para estourar o limite
        funcionario.setPontosDeVenda(List.of(new PontosDeVendaDoFuncionario(), 
                                             new PontosDeVendaDoFuncionario(), 
                                             new PontosDeVendaDoFuncionario()));

        Mockito.when(funcionarioRepository.existsByCpf("12345678900")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.criar(funcionario);
        });

        assertEquals("Funcionário pode trabalhar em no máximo 2 pontos.", exception.getMessage());
    }

    @Test
    @DisplayName("Criar - Deve lançar exceção se algum ponto de venda não contiver o dia de trabalho")
    void deveLancarExcecaoQuandoDiaTrabalhoForNulo() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678900");
        
        PontosDeVendaDoFuncionario ponto = new PontosDeVendaDoFuncionario();
        ponto.setDiaTrabalho(null); // Dia nulo
        funcionario.setPontosDeVenda(List.of(ponto));

        Mockito.when(funcionarioRepository.existsByCpf("12345678900")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.criar(funcionario);
        });

        assertEquals("Dia de trabalho é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Criar - Deve lançar exceção se o dia de trabalho for inválido (menor que 1 ou maior que 7)")
    void deveLancarExcecaoQuandoDiaTrabalhoForInvalido() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678900");
        
        PontosDeVendaDoFuncionario ponto = new PontosDeVendaDoFuncionario();
        ponto.setDiaTrabalho((short) 8); // Dia inválido
        funcionario.setPontosDeVenda(List.of(ponto));

        Mockito.when(funcionarioRepository.existsByCpf("12345678900")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.criar(funcionario);
        });

        assertEquals("Dia deve estar entre 1 e 7.", exception.getMessage());
    }

    @Test
    @DisplayName("Criar - Deve lançar exceção se houver pontos com dias de trabalho repetidos")
    void deveLancarExcecaoQuandoDiasForemRepetidos() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678900");
        
        PontosDeVendaDoFuncionario p1 = new PontosDeVendaDoFuncionario();
        p1.setDiaTrabalho((short) 3);
        PontosDeVendaDoFuncionario p2 = new PontosDeVendaDoFuncionario();
        p2.setDiaTrabalho((short) 3); // Repetindo o dia no mesmo funcionário
        
        funcionario.setPontosDeVenda(List.of(p1, p2));

        Mockito.when(funcionarioRepository.existsByCpf("12345678900")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.criar(funcionario);
        });

        assertEquals("Os dias devem ser diferentes.", exception.getMessage());
    }

    // --- TESTES DE BUSCA E LISTAGEM ---

    @Test
    @DisplayName("BuscarPorId - Deve retornar o funcionário caso o ID exista")
    void deveBuscarPorIdComSucesso() {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);

        Mockito.when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));

        Funcionario resultado = funcionarioService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    @DisplayName("BuscarPorId - Deve lançar exceção caso o funcionário não seja encontrado")
    void deveLancarExcecaoQuandoIdNaoExistir() {
        Long id = 99L;
        Mockito.when(funcionarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.buscarPorId(id);
        });

        assertEquals("Funcionário não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("ListarTodos - Deve invocar o findAll do repositório")
    void deveListarTodosOsFuncionarios() {
        Mockito.when(funcionarioRepository.findAll()).thenReturn(List.of(new Funcionario(), new Funcionario()));

        List<Funcionario> resultado = funcionarioService.listarTodos();

        assertEquals(2, resultado.size());
        Mockito.verify(funcionarioRepository, Mockito.times(1)).findAll();
    }

    // --- TESTES DO MÉTODO DELETAR ---

    @Test
    @DisplayName("Deletar - Deve remover o funcionário se o ID existir")
    void deveDeletarComSucesso() {
        Long id = 1L;
        Mockito.when(funcionarioRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> funcionarioService.deletar(id));

        Mockito.verify(funcionarioRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deletar - Deve lançar exceção ao tentar deletar ID inexistente")
    void deveLancarExcecaoAoDeletarIdInexistente() {
        Long id = 99L;
        Mockito.when(funcionarioRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.deletar(id);
        });

        assertEquals("Funcionário não encontrado.", exception.getMessage());
        Mockito.verify(funcionarioRepository, Mockito.never()).deleteById(any());
    }
}
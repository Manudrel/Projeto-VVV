package com.projectvvv.demo.service;

import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.model.HistoricoManutencao;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.repository.HistoricoManutencaoRepository;
import com.projectvvv.domain.repository.ModalRepository;
import com.projectvvv.domain.service.ModalService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ModalServiceTest {

    @Mock
    private ModalRepository modalRepository;

    @Mock
    private HistoricoManutencaoRepository historicoRepository;

    @InjectMocks
    private ModalService modalService;

    @Test
    @DisplayName("buscarPorId - Deve retornar o modal quando o ID existir")
    void deveBuscarModalPorIdComSucesso() {
        Long id = 1L;
        Modal modalEsperado = new Modal();
        Mockito.when(modalRepository.findById(id)).thenReturn(Optional.of(modalEsperado));

        Modal resultado = modalService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(modalEsperado, resultado);
    }

    @Test
    @DisplayName("buscarPorId - Deve lancar RuntimeException quando o ID nao existir")
    void deveLancarErroAoBuscarIdInexistente() {
        Long id = 99L;
        Mockito.when(modalRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            modalService.buscarPorId(id);
        });

        assertEquals("Modal não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("criar - Deve gerar o código MD-XXXX e definir estado ATIVO")
    void deveCriarModalComDadosCorretos() {
        Modal modalParaCriar = new Modal();
        Mockito.when(modalRepository.save(any(Modal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Modal resultado = modalService.criar(modalParaCriar, 10L, TipoModal.ONIBUS, 2026, "Scania R450", 40);

        assertNotNull(resultado.getCodigoModal());
        assertTrue(resultado.getCodigoModal().startsWith("MD-"));
        assertEquals(EstadoModal.ATIVO, resultado.getEstadoModal());
        assertEquals(10L, resultado.getIdTrasnportadora());
        assertEquals("Scania R450", resultado.getModelo());
    }

    @Test
    @DisplayName("manutencao - Deve alterar o estado do modal e salvar um registro no histórico")
    void deveColocarModalEmManutencaoEGerarHistorico() {
        Long id = 1L;
        String descricao = "Troca de pneus";
        Modal modalExistente = new Modal();
        modalExistente.setEstadoModal(EstadoModal.ATIVO);

        Mockito.when(modalRepository.findById(id)).thenReturn(Optional.of(modalExistente));
        Mockito.when(modalRepository.save(any(Modal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Modal resultado = modalService.manutencao(id, descricao);

        assertEquals(EstadoModal.MANUTENCAO, resultado.getEstadoModal());
        
        Mockito.verify(historicoRepository, Mockito.times(1)).save(any(HistoricoManutencao.class));
    }

    @Test
    @DisplayName("atualizar - Deve atualizar apenas as propriedades enviadas pelo objeto")
    void deveAtualizarApenasCamposPreenchidos() {
        Long id = 1L;
        Modal modalOriginal = new Modal();
        modalOriginal.setModelo("Antigo");
        modalOriginal.setAno(2020);

        Modal modalDadosNovos = new Modal();
        modalDadosNovos.setModelo("Novo"); 

        Mockito.when(modalRepository.findById(id)).thenReturn(Optional.of(modalOriginal));
        Mockito.when(modalRepository.save(any(Modal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Modal resultado = modalService.atualizar(id, modalDadosNovos);

        assertEquals("Novo", resultado.getModelo()); 
        assertEquals(2020, resultado.getAno());       
    }

    @Test
    @DisplayName("deletar - Deve lancar RuntimeException caso tente deletar um ID inexistente")
    void deveLancarErroAoDeletarIdInexistente() {
        Long id = 99L;
        Mockito.when(modalRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> modalService.deletar(id));
        
        Mockito.verify(modalRepository, Mockito.never()).deleteById(any());
    }
}
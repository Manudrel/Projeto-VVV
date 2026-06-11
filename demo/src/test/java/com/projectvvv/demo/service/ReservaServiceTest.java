package com.projectvvv.demo.service;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.repository.ReservaRepository;
import com.projectvvv.domain.service.ReservaService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("buscarPorId - Deve retornar a reserva quando o ID existir")
    void deveBuscarReservaPorIdComSucesso() {
        
        Long id = 1L;
        Reserva reservaEsperada = new Reserva();
        Mockito.when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaEsperada));

        Reserva resultado = reservaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(reservaEsperada, resultado);
    }

    @Test
    @DisplayName("buscarPorId - Deve lançar EntityNotFoundException quando o ID não existir")
    void deveLancarErroAoBuscarIdInexistente() {
        
        Long id = 99L;
        Mockito.when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            reservaService.buscarPorId(id);
        });

        assertEquals("Reserva não encontrada com ID: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("buscarPorStatus - Deve retornar a lista filtrada por status")
    void deveBuscarReservasPorStatus() {
        StatusReserva status = StatusReserva.CONCLUIDO;
        List<Reserva> listaEsperada = List.of(new Reserva(), new Reserva());
        Mockito.when(reservaRepository.findByStatusReserva(status)).thenReturn(listaEsperada);

        List<Reserva> resultado = reservaService.buscarPorStatus(status);
        
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("criar - Deve salvar e retornar a nova reserva")
    void deveCriarReservaComSucesso() {
        
        Reserva novaReserva = new Reserva();
        Mockito.when(reservaRepository.save(novaReserva)).thenReturn(novaReserva);
        
        Reserva resultado = reservaService.criar(novaReserva);
        
        assertNotNull(resultado);
        Mockito.verify(reservaRepository, Mockito.times(1)).save(novaReserva);
    }

    @Test
    @DisplayName("atualizar - Deve mesclar apenas os campos não nulos enviados")
    void deveAtualizarCamposEnviados() {
        
        Long id = 1L;
        Reserva reservaOriginal = new Reserva();
        reservaOriginal.setLocalizador("LOC123");
        reservaOriginal.setStatusReserva(StatusReserva.PENDENTE);

        Reserva dadosNovos = new Reserva();
        dadosNovos.setLocalizador("LOC999"); 

        Mockito.when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaOriginal));
        Mockito.when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Reserva resultado = reservaService.atualizar(id, dadosNovos);
        
        assertEquals("LOC999", resultado.getLocalizador()); 
        assertEquals(StatusReserva.PENDENTE, resultado.getStatusReserva()); 
    }

    @Test
    @DisplayName("deletar - Deve apagar com sucesso se a reserva existir")
    void deveDeletarReservaComSucesso() {
        
        Long id = 1L;
        Mockito.when(reservaRepository.existsById(id)).thenReturn(true);
        
        reservaService.deletar(id);
        
        Mockito.verify(reservaRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deletar - Deve lançar EntityNotFoundException ao tentar excluir ID inexistente")
    void deveLancarErroAoDeletarInexistente() {
        
        Long id = 99L;
        Mockito.when(reservaRepository.existsById(id)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> reservaService.deletar(id));
        Mockito.verify(reservaRepository, Mockito.never()).deleteById(any());
    }
}
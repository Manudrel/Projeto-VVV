package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.repository.ReservaRepository;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    private Reserva criarReservaExemplo(StatusReserva status, Long idModal) {
        Reserva reserva = new Reserva();
        reserva.setStatusReserva(status);
        reserva.setIdModal(idModal);
        
        reserva.setData(LocalDate.now());
        reserva.setHoraPartida(LocalTime.of(14, 0));
        reserva.setLocalizador("LOC123");
        
        return reserva;
    }

    @Test
    @DisplayName("findByStatusReserva - Deve retornar apenas as reservas com o status pesquisado")
    void deveBuscarReservasPorStatusComSucesso() {
        Reserva r1 = criarReservaExemplo(StatusReserva.PENDENTE, 1L);
        Reserva r2 = criarReservaExemplo(StatusReserva.CONCLUIDO, 1L);
        Reserva r3 = criarReservaExemplo(StatusReserva.PENDENTE, 2L);

        reservaRepository.saveAll(List.of(r1, r2, r3));

        List<Reserva> resultado = reservaRepository.findByStatusReserva(StatusReserva.PENDENTE);

        assertEquals(2, resultado.size(), "Deveria encontrar exatamente 2 reservas PENDENTES");
        assertTrue(resultado.stream().allMatch(r -> r.getStatusReserva() == StatusReserva.PENDENTE));
    }

    @Test
    @DisplayName("findByIdModal - Deve retornar apenas as reservas vinculadas ao idModal informado")
    void deveBuscarReservasPorIdModalComSucesso() {
        Reserva r1 = criarReservaExemplo(StatusReserva.CONCLUIDO, 10L);
        Reserva r2 = criarReservaExemplo(StatusReserva.PENDENTE, 10L);
        Reserva r3 = criarReservaExemplo(StatusReserva.CONCLUIDO, 20L);

        reservaRepository.saveAll(List.of(r1, r2, r3));

        List<Reserva> resultado = reservaRepository.findByIdModal(10L);

        assertEquals(2, resultado.size(), "Deveria encontrar exatamente 2 reservas para o modal 10");
        assertTrue(resultado.stream().allMatch(r -> r.getIdModal().equals(10L)));
    }

    @Test
    @DisplayName("Queries Customizadas - Deve retornar lista vazia se nenhum registro corresponder aos filtros")
    void deveRetornarVazioSeNaoEncontrarRegistros() {
        List<Reserva> porStatus = reservaRepository.findByStatusReserva(StatusReserva.CANCELADA);
        List<Reserva> porModal = reservaRepository.findByIdModal(999L);

        assertTrue(porStatus.isEmpty(), "A busca por status inexistente deve ser vazia");
        assertTrue(porModal.isEmpty(), "A busca por modal inexistente deve ser vazia");
    }
}
package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.repository.ReservaRepository;

@DataJpaTest
public class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("findByStatusReserva - Deve retornar apenas as reservas com o status pesquisado")
    void deveBuscarPorStatusReserva() {
        Reserva r1 = new Reserva();
        r1.setStatusReserva(StatusReserva.CONCLUIDO);
        
        Reserva r2 = new Reserva();
        r2.setStatusReserva(StatusReserva.PENDENTE);

        reservaRepository.save(r1);
        reservaRepository.save(r2);

        List<Reserva> resultado = reservaRepository.findByStatusReserva(StatusReserva.CONCLUIDO);

        assertEquals(1, resultado.size());
        assertEquals(StatusReserva.CONCLUIDO, resultado.get(0).getStatusReserva());
    }

    @Test
    @DisplayName("findByIdRota - Deve retornar a lista de reservas associadas a uma rota específica")
    void deveBuscarPorIdRota() {
        Reserva r1 = new Reserva();
        r1.setIdRota(100L);
        
        Reserva r2 = new Reserva();
        r2.setIdRota(200L);

        reservaRepository.save(r1);
        reservaRepository.save(r2);

        List<Reserva> resultado = reservaRepository.findByIdRota(100L);

        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getIdRota());
    }

    @Test
    @DisplayName("findByIdModal - Deve retornar a lista de reservas associadas a um modal específico")
    void deveBuscarPorIdModal() {
        Reserva r1 = new Reserva();
        r1.setIdModal(5L);
        
        Reserva r2 = new Reserva();
        r2.setIdModal(5L);
        
        Reserva r3 = new Reserva();
        r3.setIdModal(9L);

        reservaRepository.saveAll(List.of(r1, r2, r3));

        List<Reserva> resultado = reservaRepository.findByIdModal(5L);

        assertEquals(2, resultado.size());
    }
}
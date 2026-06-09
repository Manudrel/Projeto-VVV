package com.projectvvv.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByStatusReserva(StatusReserva statusReserva);

    List<Reserva> findByIdRota(Long idRota);

    List<Reserva> findByIdModal(Long idModal);
}
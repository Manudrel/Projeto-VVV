package com.projectvvv.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.repository.ReservaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + id));
    }

    public List<Reserva> buscarPorStatus(StatusReserva status) {
        return reservaRepository.findByStatusReserva(status);
    }

    public Reserva criar(Reserva reserva) {
        reserva.setStatusReserva(StatusReserva.PENDENTE);
        
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Long id, Reserva reservaAtualizada) {
        Reserva reserva = buscarPorId(id);

        if (reservaAtualizada.getData() != null) {
            reserva.setData(reservaAtualizada.getData());
        }

        if (reservaAtualizada.getHoraPartida() != null) {
            reserva.setHoraPartida(reservaAtualizada.getHoraPartida());
        }

        if (reservaAtualizada.getStatusReserva() != null) {
            reserva.setStatusReserva(reservaAtualizada.getStatusReserva());
        }

        if (reservaAtualizada.getTipoModal() != null) {
            reserva.setTipoModal(reservaAtualizada.getTipoModal());
        }

        if (reservaAtualizada.getLocalizador() != null) {
            reserva.setLocalizador(reservaAtualizada.getLocalizador());
        }

        if (reservaAtualizada.getTipoPassagem() != null) {
            reserva.setTipoPassagem(reservaAtualizada.getTipoPassagem());
        }

        if (reservaAtualizada.getIdRota() != null) {
            reserva.setIdRota(reservaAtualizada.getIdRota());
        }

        if (reservaAtualizada.getIdModal() != null) {
            reserva.setIdModal(reservaAtualizada.getIdModal());
        }

        return reservaRepository.save(reserva);
    }

    public void deletar(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("Reserva não encontrada com ID: " + id);
        }
        reservaRepository.deleteById(id);
    }
}
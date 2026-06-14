package com.projectvvv.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectvvv.domain.dto.ReservaDTO;
import com.projectvvv.domain.dto.RotaDaReservaDTO;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.model.RotaDaReserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.repository.ModalRepository;
import com.projectvvv.domain.repository.ReservaRepository;
import com.projectvvv.domain.repository.RotaDaReservaRepository;
import com.projectvvv.domain.repository.RotaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ModalRepository modalRepository;

    @Autowired
    private RotaRepository rotaRepository;

    @Autowired
    private RotaDaReservaRepository rotaDaReservaRepository;

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Reserva não encontrada com ID: " + id));
    }

    public List<Reserva> buscarPorStatus(StatusReserva status) {
        return reservaRepository.findByStatusReserva(status);
    }

    public Reserva criar(ReservaDTO dto) {

        if (!modalRepository.existsById(dto.getIdModal())) {
            throw new EntityNotFoundException(
                    "Modal não encontrado com ID: "
                            + dto.getIdModal());
        }

        Reserva reserva = new Reserva();

        reserva.setData(dto.getData());
        reserva.setHoraPartida(dto.getHoraPartida());
        reserva.setLocalizador(dto.getLocalizador());

        reserva.setTipoViagem(dto.getTipoViagem());
        reserva.setTipoModal(dto.getTipoModal());
        reserva.setTipoPassagem(dto.getTipoPassagem());

        reserva.setIdModal(dto.getIdModal());

        reserva.setStatusReserva(StatusReserva.PENDENTE);

        Reserva reservaSalva = reservaRepository.save(reserva);

        if (dto.getRotas() != null && !dto.getRotas().isEmpty()) {

            for (RotaDaReservaDTO rotaDTO : dto.getRotas()) {

                Rota rota = rotaRepository.findById(
                                rotaDTO.getRotaId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Rota não encontrada com ID: "
                                                + rotaDTO.getRotaId()));

                RotaDaReserva trecho = new RotaDaReserva();

                trecho.setReserva(reservaSalva);
                trecho.setRota(rota);
                trecho.setOrdem(rotaDTO.getOrdem());

                rotaDaReservaRepository.save(trecho);
            }
        }

        return reservaSalva;
    }

    public Reserva atualizar(Long id, ReservaDTO dto) {

        Reserva reserva = buscarPorId(id);

        if (dto.getData() != null) {
            reserva.setData(dto.getData());
        }

        if (dto.getHoraPartida() != null) {
            reserva.setHoraPartida(dto.getHoraPartida());
        }

        if (dto.getLocalizador() != null) {
            reserva.setLocalizador(dto.getLocalizador());
        }

        if (dto.getTipoViagem() != null) {
            reserva.setTipoViagem(dto.getTipoViagem());
        }

        if (dto.getTipoModal() != null) {
            reserva.setTipoModal(dto.getTipoModal());
        }

        if (dto.getTipoPassagem() != null) {
            reserva.setTipoPassagem(dto.getTipoPassagem());
        }

        if (dto.getStatusReserva() != null) {
            reserva.setStatusReserva(dto.getStatusReserva());
        }

        if (dto.getIdModal() != null) {

            if (!modalRepository.existsById(dto.getIdModal())) {
                throw new EntityNotFoundException(
                        "Modal não encontrado com ID: "
                                + dto.getIdModal());
            }

            reserva.setIdModal(dto.getIdModal());
        }

        return reservaRepository.save(reserva);
    }

    public void deletar(Long id) {

        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Reserva não encontrada com ID: " + id);
        }

        reservaRepository.deleteById(id);
    }
}
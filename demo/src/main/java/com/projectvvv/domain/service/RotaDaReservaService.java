package com.projectvvv.domain.service;

import com.projectvvv.domain.dto.RotaDaReservaDTO;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.model.RotaDaReserva;
import com.projectvvv.domain.repository.ReservaRepository;
import com.projectvvv.domain.repository.RotaRepository;
import com.projectvvv.domain.repository.RotaDaReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotaDaReservaService {

    private final RotaDaReservaRepository repository;
    private final ReservaRepository reservaRepository;
    private final RotaRepository rotaRepository;

    public RotaDaReservaService(
            RotaDaReservaRepository repository,
            ReservaRepository reservaRepository,
            RotaRepository rotaRepository) {

        this.repository = repository;
        this.reservaRepository = reservaRepository;
        this.rotaRepository = rotaRepository;
    }

    public RotaDaReserva salvar(RotaDaReservaDTO dto) {

        Reserva reserva = reservaRepository.findById(dto.getReservaId())
                .orElseThrow(() ->
                        new RuntimeException("Reserva não encontrada"));

        Rota rota = rotaRepository.findById(dto.getRotaId())
                .orElseThrow(() ->
                        new RuntimeException("Rota não encontrada"));

        RotaDaReserva entidade = new RotaDaReserva();
        entidade.setReserva(reserva);
        entidade.setRota(rota);
        entidade.setOrdem(dto.getOrdem());

        return repository.save(entidade);
    }

    public List<RotaDaReserva> listarTodas() {
        return repository.findAll();
    }

    public RotaDaReserva buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trecho não encontrado"));
    }

    public List<RotaDaReserva> buscarPorReserva(Long reservaId) {

        return repository.findByReservaId(reservaId);

    }

    public RotaDaReserva atualizar(Long id, RotaDaReservaDTO dto) {

        RotaDaReserva entidade = buscarPorId(id);

        Reserva reserva = reservaRepository.findById(dto.getReservaId())
                .orElseThrow(() ->
                        new RuntimeException("Reserva não encontrada"));

        Rota rota = rotaRepository.findById(dto.getRotaId())
                .orElseThrow(() ->
                        new RuntimeException("Rota não encontrada"));

        entidade.setReserva(reserva);
        entidade.setRota(rota);
        entidade.setOrdem(dto.getOrdem());

        return repository.save(entidade);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
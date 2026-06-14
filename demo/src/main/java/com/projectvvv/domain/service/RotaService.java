package com.projectvvv.domain.service;

import com.projectvvv.domain.dto.RotaDTO;
import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.repository.CidadeRepository;
import com.projectvvv.domain.repository.RotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotaService {

    private final RotaRepository rotaRepository;
    private final CidadeRepository cidadeRepository;

    public RotaService(
            RotaRepository rotaRepository,
            CidadeRepository cidadeRepository) {

        this.rotaRepository = rotaRepository;
        this.cidadeRepository = cidadeRepository;
    }

    public Rota salvar(RotaDTO dto) {

        Cidade origem = cidadeRepository.findById(dto.getOrigemId())
                .orElseThrow(() ->
                        new RuntimeException("Cidade de origem não encontrada"));

        Cidade destino = cidadeRepository.findById(dto.getDestinoId())
                .orElseThrow(() ->
                        new RuntimeException("Cidade de destino não encontrada"));

        Rota rota = new Rota();
        rota.setOrigem(origem);
        rota.setDestino(destino);

        return rotaRepository.save(rota);
    }

    public List<Rota> listarTodas() {
        return rotaRepository.findAll();
    }

    public Rota buscarPorId(Long id) {
        return rotaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rota não encontrada"));
    }

    public Rota atualizar(Long id, RotaDTO dto) {

        Rota rota = buscarPorId(id);

        Cidade origem = cidadeRepository.findById(dto.getOrigemId())
                .orElseThrow(() ->
                        new RuntimeException("Cidade de origem não encontrada"));

        Cidade destino = cidadeRepository.findById(dto.getDestinoId())
                .orElseThrow(() ->
                        new RuntimeException("Cidade de destino não encontrada"));

        rota.setOrigem(origem);
        rota.setDestino(destino);

        return rotaRepository.save(rota);
    }

    public void deletar(Long id) {
        rotaRepository.deleteById(id);
    }
}
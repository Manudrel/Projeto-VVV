package com.projectvvv.domain.service;

import com.projectvvv.domain.model.PontoDeVenda;
import com.projectvvv.domain.repository.PontoDeVendaRepository;
import org.springframework.stereotype.Service;
import com.projectvvv.domain.dto.PontoVendaDTO;


import java.util.List;

@Service
public class PontoDeVendaService {

    private final PontoDeVendaRepository repository;

    public PontoDeVendaService(
            PontoDeVendaRepository repository) {
        this.repository = repository;
    }

    public PontoDeVenda criar(PontoDeVenda ponto) {

        if (repository.existsByCnpj(ponto.getCnpj())) {
            throw new RuntimeException(
                "Já existe um ponto de venda com este CNPJ."
            );
        }

        return repository.save(ponto);
    }

    public List<PontoDeVenda> listarTodos() {
        return repository.findAll();
    }

    public PontoDeVenda buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Ponto de venda não encontrado."));
    }

    public PontoDeVenda atualizar(
            Long id,
            PontoDeVenda atualizado) {

        PontoDeVenda existente = buscarPorId(id);

        if (atualizado.getNome() != null) {
            existente.setNome(atualizado.getNome());
        }

        if (atualizado.getCnpj() != null) {
            existente.setCnpj(atualizado.getCnpj());
        }

        if (atualizado.getEndereco() != null) {
            existente.setEndereco(atualizado.getEndereco());
        }

        if (atualizado.getTelefone() != null) {
            existente.setTelefone(atualizado.getTelefone());
        }

        return repository.save(existente);
    }

    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException(
                "Ponto de venda não encontrado.");
        }

        repository.deleteById(id);
    }


    public PontoDeVenda salvarDoDTO(PontoVendaDTO dto) {
        PontoDeVenda ponto = new PontoDeVenda();
        ponto.setNome(dto.getNome());
        ponto.setCnpj(dto.getCnpj());
        ponto.setTelefone(dto.getTelefone());
        ponto.setEndereco(dto.getEndereco());
        return criar(ponto); // já valida CNPJ duplicado
    }
}
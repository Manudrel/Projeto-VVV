package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.repository.CidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    // Criar cidade
    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    // Buscar todas as cidades
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }

    // Buscar cidade por ID
    public Cidade buscarPorId(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada com ID: " + id));
    }

    // Atualizar cidade
    public Cidade atualizar(Long id, Cidade cidadeAtualizada) {
        Cidade cidade = buscarPorId(id);

        cidade.setCidade(cidadeAtualizada.getCidade());
        cidade.setEstado(cidadeAtualizada.getEstado());
        cidade.setPais(cidadeAtualizada.getPais());

        return cidadeRepository.save(cidade);
    }

    // Excluir cidade
    public void deletar(Long id) {
        Cidade cidade = buscarPorId(id);
        cidadeRepository.delete(cidade);
    }
}
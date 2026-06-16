package com.projectvvv.domain.service;

import com.projectvvv.domain.dto.CidadeDTO;
import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.repository.CidadeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository repository;

    public CidadeService(
            CidadeRepository repository
    ) {
        this.repository = repository;
    }

    // SALVAR
    public Cidade salvar(CidadeDTO dto) {

        Cidade cidade = new Cidade();

        cidade.setCidade(dto.getNome());

        cidade.setEstado(
                dto.getUf().toUpperCase()
        );

        cidade.setPais(dto.getPais());

        cidade.setCodigoIata(
                dto.getCodigoIata().toUpperCase()
        );

        return repository.save(cidade);
    }


    // Criar cidade
    public Cidade salvar(Cidade cidade) {
        return repository.save(cidade);
    }

    // Buscar todas as cidades
    public List<Cidade> listarTodas() {
        return repository.findAll();
    }

    // Buscar cidade por ID
    public Cidade buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada com ID: " + id));
    }

    // Atualizar cidade
    public Cidade atualizar(Long id, Cidade cidadeAtualizada) {
        Cidade cidade = buscarPorId(id);

        cidade.setCidade(cidadeAtualizada.getCidade());
        cidade.setEstado(cidadeAtualizada.getEstado());
        cidade.setPais(cidadeAtualizada.getPais());

        return repository.save(cidade);
    }

    // Excluir cidade
    public void deletar(Long id) {
        Cidade cidade = buscarPorId(id);
        repository.delete(cidade);
    }

}

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

        cidade.setEstado(dto.getNome());

        cidade.setCidade(
                dto.getUf().toUpperCase()
        );

        cidade.setPais(dto.getPais());

        cidade.setCodigoIata(
                dto.getCodigoIata().toUpperCase()
        );

        return repository.save(cidade);
    }

    // LISTAR
    public List<Cidade> listarTodas() {
        return repository.findAll();
    }
}
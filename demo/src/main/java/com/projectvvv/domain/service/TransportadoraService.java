package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.repository.TransportadoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportadoraService {

    private final TransportadoraRepository repository;

    public TransportadoraService(
            TransportadoraRepository repository
    ) {
        this.repository = repository;
    }

    public Transportadora salvar(
            Transportadora transportadora
    ) {

        if (repository.existsByCnpj(
                transportadora.getCnpj()
        )) {

            throw new RuntimeException(
                    "CNPJ já cadastrado"
            );
        }

        return repository.save(transportadora);
    }

    public List<Transportadora> listarTodas() {
        return repository.findAll();
    }

    public Transportadora buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transportadora não encontrada"
                        )
                );
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
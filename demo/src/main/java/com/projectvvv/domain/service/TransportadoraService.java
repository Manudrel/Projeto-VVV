package com.projectvvv.domain.service;

import com.projectvvv.domain.exception.CnpjJaCadastradoException;
import com.projectvvv.domain.exception.TransportadoraNotFoundException;
import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.repository.TransportadoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransportadoraService {

    private final TransportadoraRepository repository;

    public TransportadoraService(
            TransportadoraRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional
    public Transportadora salvar(
            Transportadora transportadora
    ) {

        String cnpjNormalizado = normalizarCnpj(transportadora.getCnpj());

        if (cnpjNormalizado == null || cnpjNormalizado.length() != 14) {
            throw new IllegalArgumentException(
                    "CNPJ deve conter 14 dígitos"
            );
        }

        transportadora.setCnpj(cnpjNormalizado);

        if (repository.existsByCnpj(cnpjNormalizado)) {
            throw new CnpjJaCadastradoException(cnpjNormalizado);
        }

        return repository.save(transportadora);
    }

    public List<Transportadora> listarTodas() {
        return repository.findAll();
    }

    public Transportadora buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new TransportadoraNotFoundException(id)
                );
    }

    @Transactional
    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new TransportadoraNotFoundException(id);
        }

        repository.deleteById(id);
    }

    /**
     * Remove qualquer máscara (pontos, barra, hífen) do CNPJ antes de
     * validar/persistir, evitando que "33.000.118/0001-00" e
     * "33000118000100" sejam tratados como CNPJs diferentes na checagem
     * de duplicidade.
     */
    private String normalizarCnpj(String cnpj) {
        return cnpj == null ? null : cnpj.replaceAll("\\D", "");
    }




}
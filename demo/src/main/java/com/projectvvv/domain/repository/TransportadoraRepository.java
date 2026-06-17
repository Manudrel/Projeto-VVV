package com.projectvvv.domain.repository;

import com.projectvvv.domain.exception.CnpjJaCadastradoException;
import com.projectvvv.domain.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {

    boolean existsByCnpj(String cnpj);

    default void garantirCnpjUnico(String cnpj) {
        if (existsByCnpj(cnpj)) {
            throw new CnpjJaCadastradoException(cnpj);
        }
    }
}
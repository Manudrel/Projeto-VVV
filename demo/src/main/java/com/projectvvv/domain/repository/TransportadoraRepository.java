package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportadoraRepository
        extends JpaRepository<Transportadora, Long> {

    boolean existsByCnpj(String cnpj);
}
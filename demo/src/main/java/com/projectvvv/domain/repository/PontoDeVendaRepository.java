package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.PontoDeVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoDeVendaRepository
        extends JpaRepository<PontoDeVenda, Long> {

    boolean existsByCnpj(String cnpj);
}
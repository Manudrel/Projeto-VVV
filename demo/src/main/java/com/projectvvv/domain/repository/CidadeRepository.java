package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository
        extends JpaRepository<Cidade, Long> {
}
package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectvvv.domain.model.HistoricoManutencao;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}


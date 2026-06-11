package com.projectvvv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectvvv.domain.model.HistoricoManutencao;

@Repository
public interface HistoricoManutencaoRepository extends JpaRepository<HistoricoManutencao, Long> {
}


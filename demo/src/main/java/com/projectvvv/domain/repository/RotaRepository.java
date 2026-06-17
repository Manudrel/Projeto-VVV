package com.projectvvv.domain.repository;

import java.util.Optional;

import com.projectvvv.domain.model.Rota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RotaRepository extends JpaRepository<Rota, Long> {

    Optional<Rota> findByOrigem_IdAndDestino_Id(Long origemId, Long destinoId);
}
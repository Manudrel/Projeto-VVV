package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.PontosDeVendaDoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontosDeVendaDoFuncionarioRepository
        extends JpaRepository<
                PontosDeVendaDoFuncionario,
                Long> {
}
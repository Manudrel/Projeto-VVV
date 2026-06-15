package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository
        extends JpaRepository<Funcionario, Long> {

    boolean existsByCpf(String cpf);
}
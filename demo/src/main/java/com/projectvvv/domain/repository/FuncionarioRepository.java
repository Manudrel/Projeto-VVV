package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FuncionarioRepository
        extends JpaRepository<Funcionario, Long> {

    boolean existsByCpf(String cpf);

    Optional<Funcionario> findByCpf(String cpf);

    boolean existsByCargo(Cargo cargo);
}
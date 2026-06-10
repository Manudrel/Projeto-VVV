package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository
        extends JpaRepository<Pagamento, Long> {
}
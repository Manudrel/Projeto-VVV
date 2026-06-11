package com.projectvvv.domain.repository;

import com.projectvvv.domain.model.RotaDaReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface RotaDaReservaRepository
        extends JpaRepository<RotaDaReserva, Long> {
    List<RotaDaReserva> findByReservaId(Long reservaId);

}
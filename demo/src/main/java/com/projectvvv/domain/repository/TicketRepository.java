package com.projectvvv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectvvv.domain.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
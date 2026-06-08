package com.projectvvv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectvvv.domain.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
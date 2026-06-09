package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Ticket;
import com.projectvvv.domain.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // GET /api/tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> listarTodos() {
        return ResponseEntity.ok(ticketService.listarTodos());
    }

    // GET /api/tickets/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.buscarPorId(id));
    }

    // PATCH /api/tickets/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> atualizar(
            @PathVariable Long id,
            @RequestBody Ticket ticket) {

        Ticket ticketAtualizado = ticketService.atualizar(id, ticket);

        return ResponseEntity.ok(ticketAtualizado);
    }

    // DELETE /api/tickets/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        ticketService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
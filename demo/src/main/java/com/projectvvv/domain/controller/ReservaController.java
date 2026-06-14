package com.projectvvv.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projectvvv.domain.dto.ReservaDTO;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // GET /api/reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    // GET /api/reservas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    // GET /api/reservas/status/PENDENTE
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reserva>> buscarPorStatus(
            @PathVariable StatusReserva status) {

        return ResponseEntity.ok(
                reservaService.buscarPorStatus(status));
    }

    // POST /api/reservas
    @PostMapping
    public ResponseEntity<Reserva> criar(
            @RequestBody ReservaDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservaService.criar(dto));
    }

    // PATCH /api/reservas/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(
            @PathVariable Long id,
            @RequestBody ReservaDTO dto) {

        Reserva reservaAtualizada =
                reservaService.atualizar(id, dto);

        return ResponseEntity.ok(reservaAtualizada);
    }

    // DELETE /api/reservas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        reservaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
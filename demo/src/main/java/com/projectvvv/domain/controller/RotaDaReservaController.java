package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.RotaDaReservaDTO;
import com.projectvvv.domain.model.RotaDaReserva;
import com.projectvvv.domain.service.RotaDaReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rota-da-reserva")
public class RotaDaReservaController {

    private final RotaDaReservaService service;

    public RotaDaReservaController(RotaDaReservaService service) {
        this.service = service;
    }

    @PostMapping
    public RotaDaReserva criar(
            @RequestBody RotaDaReservaDTO dto) {

        return service.salvar(dto);
    }

    @GetMapping
    public List<RotaDaReserva> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public RotaDaReserva buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    @GetMapping("/reserva/{reservaId}")
    public List<RotaDaReserva> buscarPorReserva(
            @PathVariable Long reservaId) {

        return service.buscarPorReserva(reservaId);

    }

    @PutMapping("/{id}")
    public RotaDaReserva atualizar(
            @PathVariable Long id,
            @RequestBody RotaDaReservaDTO dto) {

        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
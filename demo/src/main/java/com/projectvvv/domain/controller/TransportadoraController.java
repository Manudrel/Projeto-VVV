package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.service.TransportadoraService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transportadoras")
public class TransportadoraController {

    private final TransportadoraService service;

    public TransportadoraController(
            TransportadoraService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transportadora> criar(
            @RequestBody Transportadora transportadora
    ) {

        return ResponseEntity
                .status(201)
                .body(service.salvar(transportadora));
    }

    @GetMapping
    public ResponseEntity<List<Transportadora>> listar() {

        return ResponseEntity.ok(
                service.listarTodas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportadora> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        service.deletar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
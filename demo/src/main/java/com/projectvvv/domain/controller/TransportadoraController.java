package com.projectvvv.domain.controller;

import com.projectvvv.domain.exception.CnpjJaCadastradoException;
import com.projectvvv.domain.exception.TransportadoraNotFoundException;
import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.service.TransportadoraService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            @Valid @RequestBody Transportadora transportadora
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
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

    @ExceptionHandler(CnpjJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleCnpjDuplicado(
            CnpjJaCadastradoException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(TransportadoraNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNaoEncontrada(
            TransportadoraNotFoundException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleArgumentoInvalido(
            IllegalArgumentException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", ex.getMessage()));
    }
}
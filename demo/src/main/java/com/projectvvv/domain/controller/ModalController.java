package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.service.ModalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modais")
public class ModalController {

    @Autowired
    private ModalService modalService;

    // GET /api/modais
    @GetMapping
    public ResponseEntity<List<Modal>> listarTodos() {
        return ResponseEntity.ok(modalService.listarTodos());
    }

    // GET /api/modais/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Modal> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modalService.buscarPorId(id));
    }

    // POST /api/modais
    @PostMapping
    public ResponseEntity<Modal> criar(@RequestBody Modal modal) {
        Modal modalCriado = modalService.criar(modal, modal.getIdTrasnportadora(), modal.getTipoModal(), modal.getAno(), modal.getModelo(), modal.getCapacidade());
        return ResponseEntity.status(201).body(modalCriado);
    }

    // PATCH /api/modais/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Modal> atualizar(
            @PathVariable Long id,
            @RequestBody Modal modal) {

        Modal modalAtualizado = modalService.atualizar(id, modal);

        return ResponseEntity.ok(modalAtualizado);
    }

    // DELETE /api/modais/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        modalService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
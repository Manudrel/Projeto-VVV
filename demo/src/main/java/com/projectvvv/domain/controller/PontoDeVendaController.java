package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.PontoDeVenda;
import com.projectvvv.domain.service.PontoDeVendaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos-venda")
public class PontoDeVendaController {

    private final PontoDeVendaService service;

    public PontoDeVendaController(
            PontoDeVendaService service) {
        this.service = service;
    }
    // POST api/pontos-venda
    @PostMapping
    public ResponseEntity<PontoDeVenda> criar(
            @RequestBody PontoDeVenda ponto) {

        return ResponseEntity.ok(
                service.criar(ponto));
    }

    // GET api/pontos-venda
    @GetMapping
    public ResponseEntity<List<PontoDeVenda>> listar() {

        return ResponseEntity.ok(
                service.listarTodos());
    }

    // GET api/pontos-venda/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PontoDeVenda> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    // PUT api/pontos-venda/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PontoDeVenda> atualizar(
            @PathVariable Long id,
            @RequestBody PontoDeVenda ponto) {

        return ResponseEntity.ok(
                service.atualizar(id, ponto));
    }

    // DELETE api/pontos-venda/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
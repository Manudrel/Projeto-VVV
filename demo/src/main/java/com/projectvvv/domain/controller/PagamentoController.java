package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.service.PagamentoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(
            PagamentoService pagamentoService) {

        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/reserva/{reservaId}/cliente/{clienteId}")
    public ResponseEntity<Pagamento> realizarPagamento(

            @PathVariable Long reservaId,

            @PathVariable Long clienteId,

            @RequestBody Pagamento pagamento) {

        Pagamento pagamentoRealizado =
                pagamentoService.realizarPagamento(
                        reservaId,
                        clienteId,
                        pagamento);

        return ResponseEntity
                .status(201)
                .body(pagamentoRealizado);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Pagamento> cancelarPagamento(@PathVariable Long id) {

        Pagamento pagamentoCancelado = pagamentoService.cancelarPagamento(id);

        return ResponseEntity.ok(pagamentoCancelado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pagamentoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> listarTodos() {

        return ResponseEntity.ok(
                pagamentoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        pagamentoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
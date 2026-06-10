package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.service.PagamentoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(
            PagamentoService pagamentoService) {

        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Pagamento> realizarPagamento(

            @RequestBody Pagamento pagamento,

            @RequestParam Float valorReserva) {

        Pagamento pagamentoRealizado =
                pagamentoService.realizarPagamento(
                        pagamento,
                        valorReserva);

        return ResponseEntity
                .status(201)
                .body(pagamentoRealizado);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Pagamento> cancelarPagamento(
            @PathVariable Long id) {

        Pagamento pagamentoCancelado =
                pagamentoService.cancelarPagamento(id);

        return ResponseEntity.ok(pagamentoCancelado);
    }
}
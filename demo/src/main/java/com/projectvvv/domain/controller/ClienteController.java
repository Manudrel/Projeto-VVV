package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.service.ClienteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET /api/clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {

        return ResponseEntity.ok(
                clienteService.listarTodos()
        );
    }

    // GET /api/clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clienteService.buscarPorId(id)
        );
    }

    // POST /api/clientes
    @PostMapping
    public ResponseEntity<Cliente> criar(
            @RequestBody Cliente cliente) {

        Cliente clienteCriado =
                clienteService.salvar(cliente);

        return ResponseEntity
                .status(201)
                .body(clienteCriado);
    }

    // PATCH /api/clientes/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {

        Cliente clienteAtualizado =
                clienteService.atualizar(id, cliente);

        return ResponseEntity.ok(
                clienteAtualizado
        );
    }

    // DELETE /api/clientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        clienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
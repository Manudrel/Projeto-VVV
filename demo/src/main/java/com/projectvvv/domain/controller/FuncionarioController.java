package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // POST /api/funcionario
    // Apenas GERENTE pode usar esta rota — permite criar outro GERENTE
    // ou um FUNCIONARIO comum, conforme o Cargo enviado no corpo.
    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario) {

        return ResponseEntity
                .status(201)
                .body(funcionarioService.criar(funcionario));
    }

    // POST /api/funcionario/comum
    // Qualquer FUNCIONARIO autenticado pode usar esta rota.
    // O Cargo enviado é ignorado — o novo cadastro é sempre FUNCIONARIO.
    @PostMapping("/comum")
    public ResponseEntity<Funcionario> criarComum(@RequestBody Funcionario funcionario) {

        return ResponseEntity
                .status(201)
                .body(funcionarioService.criarFuncionarioComum(funcionario));
    }

    // GET /api/funcionario/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    // GET /api/funcionario
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    // DELETE /api/funcionario/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(
            FuncionarioService funcionarioService) {

        this.funcionarioService = funcionarioService;
    }

    // POST api/funcionarios
    @PostMapping
    public ResponseEntity<Funcionario> criar(
            @RequestBody Funcionario funcionario) {

        return ResponseEntity
                .status(201)
                .body(
                        funcionarioService
                                .criar(funcionario));
    }

    // GET api/funcionarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                funcionarioService.buscarPorId(id));
    }

    // GET api/funcionarios
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {

        return ResponseEntity.ok(
                funcionarioService.listarTodos());
    }

    // DELETE api/funcionarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        funcionarioService.deletar(id);

        return ResponseEntity
                .noContent()
                .build();
    }


//     @PostMapping("/{idFuncionario}/pontos-venda/{idPontoVenda}")
//     public ResponseEntity<?> vincular(
//             @PathVariable Long idFuncionario,
//             @PathVariable Long idPontoVenda) {

//         funcionarioService.vincular(idFuncionario, idPontoVenda);

//         return ResponseEntity.ok().build();
//     }
}
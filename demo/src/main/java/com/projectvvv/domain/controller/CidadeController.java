package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.service.CidadeService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @PostMapping
    public Cidade criar(@RequestBody Cidade cidade) {
        return cidadeService.salvar(cidade);
    }

    @GetMapping("/{id}")
    public Cidade buscarPorId(@PathVariable Long id) {
        return cidadeService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidadeAtualizada) {
        return cidadeService.atualizar(id, cidadeAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cidadeService.deletar(id);
    }
}
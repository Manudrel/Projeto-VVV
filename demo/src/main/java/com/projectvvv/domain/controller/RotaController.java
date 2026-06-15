package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.RotaDTO;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.service.RotaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rota")
public class RotaController {

    private final RotaService rotaService;

    public RotaController(RotaService rotaService) {
        this.rotaService = rotaService;
    }

    @PostMapping
    public Rota criar(@RequestBody RotaDTO dto) {
        return rotaService.salvar(dto);
    }

    @GetMapping
    public List<Rota> listarTodas() {
        return rotaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Rota buscarPorId(@PathVariable Long id) {
        return rotaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Rota atualizar(
            @PathVariable Long id,
            @RequestBody RotaDTO dto) {

        return rotaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        rotaService.deletar(id);
    }
}
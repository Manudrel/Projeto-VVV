package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.CidadeDTO;
import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.service.CidadeService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "cidadeDTO",
                new CidadeDTO()
        );

        model.addAttribute(
                "cidades",
                cidadeService.listarTodas()
        );

        return "cidades/cadastro";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("cidadeDTO") CidadeDTO dto,
            BindingResult br,
            RedirectAttributes ra,
            Model model
    ) {

        if (br.hasErrors()) {

            model.addAttribute(
                    "cidades",
                    cidadeService.listarTodas()
            );

            return "cidades/cadastro";
        }

        cidadeService.salvar(dto);

        ra.addFlashAttribute(
                "mensagem",
                "Cidade cadastrada."
        );

        return "redirect:/cidades";
    }

    @PostMapping
    public Cidade criar(@RequestBody Cidade cidade) {
        return cidadeService.salvar(cidade);
    }

    @GetMapping
    public List<Cidade> listarTodas() {
        return cidadeService.listarTodas();
    }

    @GetMapping("/{id}")
    public Cidade buscarPorId(@PathVariable Long id) {
        return cidadeService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(
            @PathVariable Long id,
            @RequestBody Cidade cidadeAtualizada) {

        return cidadeService.atualizar(id, cidadeAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cidadeService.deletar(id);
    }
}
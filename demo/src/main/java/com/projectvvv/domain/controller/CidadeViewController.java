package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.CidadeDTO;
import com.projectvvv.domain.service.CidadeService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cidades")
public class CidadeViewController {

    private final CidadeService cidadeService;

    public CidadeViewController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cidadeDTO", new CidadeDTO());
        model.addAttribute("cidades", cidadeService.listarTodas());
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
            model.addAttribute("cidades", cidadeService.listarTodas());
            return "cidades/cadastro";
        }

        cidadeService.salvar(dto);
        ra.addFlashAttribute("mensagem", "Cidade cadastrada.");
        return "redirect:/cidades";
    }
}
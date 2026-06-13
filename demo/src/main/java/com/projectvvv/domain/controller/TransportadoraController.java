package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.service.TransportadoraService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transportadoras")
public class TransportadoraController {

    private final TransportadoraService service;

    public TransportadoraController(
            TransportadoraService service
    ) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "transportadoras",
                service.listarTodas()
        );

        return "transportadoras";
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {

        model.addAttribute(
                "transportadora",
                new Transportadora()
        );

        return "transportadora-form";
    }

    @PostMapping
    public String salvar(
            @ModelAttribute Transportadora transportadora
    ) {

        service.salvar(transportadora);

        return "redirect:/transportadoras";
    }

    @GetMapping("/{id}")
    public String buscarPorId(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "transportadora",
                service.buscarPorId(id)
        );

        return "transportadora-detalhe";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(
            @PathVariable Long id
    ) {

        service.deletar(id);

        return "redirect:/transportadoras";
    }
}
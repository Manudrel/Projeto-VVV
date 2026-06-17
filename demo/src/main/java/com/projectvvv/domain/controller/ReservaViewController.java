package com.projectvvv.domain.controller;

import com.projectvvv.domain.service.CidadeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservas")
public class ReservaViewController {

    private final CidadeService cidadeService;

    public ReservaViewController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping("/consultar")
    public String consultarReservas() {
        return "reservas/consultar";
    }

    @GetMapping("/nova")
    public String novaReserva(Model model) {
        model.addAttribute("cidades", cidadeService.listarTodas());
        return "reservas/nova";
    }

    @GetMapping("/detalhe")
    public String detalheReserva() {
        return "reservas/detalhe";
    }
}
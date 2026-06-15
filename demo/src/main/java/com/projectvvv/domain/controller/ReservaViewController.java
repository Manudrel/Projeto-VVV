package com.projectvvv.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservas")
public class ReservaViewController {

    @GetMapping
    public String consultarReservas() {
        return "reservas/consultar";
    }

    @GetMapping("/nova")
    public String novaReserva() {
        return "reservas/nova";
    }

    @GetMapping("/detalhe")
    public String detalheReserva() {
        return "reservas/detalhe";
    }
}
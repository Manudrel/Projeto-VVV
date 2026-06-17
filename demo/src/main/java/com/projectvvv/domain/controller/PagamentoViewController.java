package com.projectvvv.domain.controller;


import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.service.ReservaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoViewController {

    private final ReservaService reservaService;

    public PagamentoViewController(
            ReservaService reservaService) {

        this.reservaService = reservaService;
    }

    @GetMapping("/novo")
    public String novoPagamento(Model model) {

        model.addAttribute(
                "reservas",
                reservaService.buscarPorStatus(
                        StatusReserva.PENDENTE));

        return "pagamentos/novo";
    }
}

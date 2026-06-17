package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.service.ReservaService;
import com.projectvvv.domain.service.TicketService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketViewController {

    private final ReservaService reservaService;
    private final TicketService ticketService;

    public TicketViewController(
            ReservaService reservaService,
            TicketService ticketService) {

        this.reservaService = reservaService;
        this.ticketService = ticketService;
    }

    @GetMapping("/novo")
    public String novoTicket(Model model) {

        model.addAttribute(
                "reservas",
                reservaService.buscarPorStatus(
                        StatusReserva.PENDENTE));

        return "tickets/novo";
    }

    @GetMapping("/consultar")
    public String consultarTickets(Model model) {

        model.addAttribute(
                "tickets",
                ticketService.listarTodos());

        return "tickets/consultar";
    }

    @GetMapping("/detalhe/{id}")
    public String detalheTicket(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "ticket",
                ticketService.buscarPorId(id));

        return "tickets/detalhe";
    }
}
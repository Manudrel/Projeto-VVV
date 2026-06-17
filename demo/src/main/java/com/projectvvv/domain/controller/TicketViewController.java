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

    @GetMapping("/checkout")
    public String novoTicket(Model model) {

        model.addAttribute(
                "reservas",
                reservaService.buscarPorStatus(
                        StatusReserva.PENDENTE));

        return "tickets/checkout";
    }

}
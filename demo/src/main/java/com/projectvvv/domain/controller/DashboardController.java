package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Funcionario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, Funcionario usuarioLogado) {
        model.addAttribute("usuario", usuarioLogado); // precisa de .getNome() e .getCargo()
        model.addAttribute("reservasPendentesHoje", 12);
        // model.addAttribute("ticketsEmitidosHoje", 47);
        // model.addAttribute("faturamentoDoDia", 18430.00);
        // model.addAttribute("modaisEmManutencao", 3);
        return "dashboard";
    }
}
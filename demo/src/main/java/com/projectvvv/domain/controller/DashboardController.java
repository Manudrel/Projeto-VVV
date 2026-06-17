package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;
import com.projectvvv.domain.repository.FuncionarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final FuncionarioRepository funcionarioRepository;

    public DashboardController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

        String cpf = authentication.getName();

        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        model.addAttribute("funcionarioLogado", funcionario);

        return "dashboard";
    }
}
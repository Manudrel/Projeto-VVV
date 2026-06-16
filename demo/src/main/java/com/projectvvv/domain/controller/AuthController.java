package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(FuncionarioService funcionarioService,
                          PasswordEncoder passwordEncoder) {
        this.funcionarioService = funcionarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(Funcionario funcionario,
                            BindingResult result,
                            RedirectAttributes attrs) {
        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        funcionarioService.criar(funcionario);
        return "redirect:/auth/login";
    }
}
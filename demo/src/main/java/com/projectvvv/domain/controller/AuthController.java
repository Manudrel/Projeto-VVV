package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ClienteForm;
import com.projectvvv.domain.dto.FuncionarioForm;
import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;
import com.projectvvv.domain.service.PontoDeVendaService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;
    private final PasswordEncoder passwordEncoder;
    private final PontoDeVendaService pontoDeVendaService;

    public AuthController(FuncionarioService funcionarioService,
                          PasswordEncoder passwordEncoder,
                          PontoDeVendaService pontoDeVendaService) {
        this.funcionarioService = funcionarioService;
        this.passwordEncoder = passwordEncoder;
        this.pontoDeVendaService = pontoDeVendaService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/cadastro-cliente")
    public String cadastroCliente(Model model) {
        model.addAttribute("clienteForm", new ClienteForm());
        return "auth/cadastro-cliente";
    }

    @PostMapping("/cadastro-cliente")
    public String salvarCliente(@ModelAttribute ClienteForm form) {
        // TODO: salvar cliente via service
        return "redirect:/dashboard";
    }

    @GetMapping("/cadastro-funcionario")
    public String cadastroFuncionario(Model model) {
        model.addAttribute("funcionarioForm", new FuncionarioForm());
        model.addAttribute("pontosDeVenda", pontoDeVendaService.listarTodos());
        return "auth/cadastro-funcionario";
    }

    @PostMapping("/cadastro-funcionario")
    public String salvarFuncionario(@ModelAttribute FuncionarioForm form,
                                    RedirectAttributes ra) {
        try {
            Funcionario f = new Funcionario();
            f.setNome(form.getNome());
            f.setCpf(form.getCpf());
            f.setTelefone(form.getTelefone());
            f.setDataNascimento(form.getDataNascimento());
            f.setEndereco(form.getEndereco());
            f.setCargo(Cargo.FUNCIONARIO);
            f.setSenha(passwordEncoder.encode(form.getSenha()));
            funcionarioService.criar(f);
            ra.addFlashAttribute("mensagem", "Cadastro realizado! Faça login.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/auth/cadastro-funcionario";
        }
        return "redirect:/auth/login";
    }
}
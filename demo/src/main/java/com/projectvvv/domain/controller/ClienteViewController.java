package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.service.ClienteService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcionario/clientes")
public class ClienteViewController {

    private final ClienteService clienteService;

    public ClienteViewController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET /funcionario/clientes/cadastro
    @GetMapping("/cadastro")
    public String formulario(Model model) {
        model.addAttribute("clienteForm", new Cliente());
        return "funcionario/clientes/cadastro";
    }

    // POST /funcionario/clientes/cadastro
    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute("clienteForm") Cliente cliente,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "funcionario/clientes/cadastro";
        }

        clienteService.salvar(cliente);

        redirectAttributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso.");

        return "redirect:/dashboard";
    }
}
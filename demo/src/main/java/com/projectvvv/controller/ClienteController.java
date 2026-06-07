package com.projectvvv.controller;

import com.projectvvv.entity.Cliente;
import com.projectvvv.service.ClienteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "clientes",
                service.listarTodos());

        return "clientes";
    }

    @GetMapping("/novo")
    public String novoCliente(Model model) {

        model.addAttribute(
                "cliente",
                new Cliente());

        return "cliente-form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute Cliente cliente) {

        service.salvar(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "cliente",
                service.buscarPorId(id));

        return "cliente-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return "redirect:/clientes";
    }
}
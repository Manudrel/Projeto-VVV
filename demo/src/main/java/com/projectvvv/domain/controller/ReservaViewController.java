package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.service.CidadeService;
import com.projectvvv.domain.service.ClienteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaViewController {

    private final CidadeService cidadeService;
    private final ClienteService clienteService;

    public ReservaViewController(CidadeService cidadeService, ClienteService clienteService) {
        this.cidadeService = cidadeService;
        this.clienteService = clienteService;
    }

    @GetMapping("/consultar")
    public String consultarReservas() {
        return "reservas/consultar";
    }

    @GetMapping("/nova")
    public String novaReserva(Model model) {
        model.addAttribute("cidades", cidadeService.listarTodas());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "reservas/nova";
    }

    @GetMapping("/nova/clientes")
    @ResponseBody
    public List<Cliente> buscarClientes(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) return clienteService.listarTodos();
        return clienteService.buscarPorNomeOuCpf(q);
    }

    @GetMapping("/detalhe")
    public String detalheReserva() {
        return "reservas/detalhe";
    }
}
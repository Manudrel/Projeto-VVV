package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.PontoVendaDTO;
import com.projectvvv.domain.service.PontoDeVendaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pontos-venda")
public class PontoVendaViewController {

    private final PontoDeVendaService service;

    public PontoVendaViewController(PontoDeVendaService service) {
        this.service = service;
    }

    // GET /pontos-venda
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pontosVenda", service.listarTodos());
        model.addAttribute("pontoVendaDTO", new PontoVendaDTO());
        return "pontos-venda/cadastro";
    }

    // POST /pontos-venda
    @PostMapping
    public String salvar(@Valid @ModelAttribute("pontoVendaDTO") PontoVendaDTO dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pontosVenda", service.listarTodos());
            return "pontos-venda/cadastro";
        }
        try {
            service.salvarDoDTO(dto);
            ra.addFlashAttribute("mensagem", "Ponto de venda cadastrado com sucesso!");
            ra.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            ra.addFlashAttribute("tipoMensagem", "error");
        }
        return "redirect:/pontos-venda";
    }

    // POST /pontos-venda/{id}/remover
    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deletar(id);
            ra.addFlashAttribute("mensagem", "Ponto de venda removido.");
            ra.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            ra.addFlashAttribute("tipoMensagem", "error");
        }
        return "redirect:/pontos-venda";
    }
}
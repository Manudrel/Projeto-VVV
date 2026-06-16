package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.PontoVendaDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/pontos-venda")
public class PontoVendaViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
        model.addAttribute("pontoVendaDTO", new PontoVendaDTO());
        model.addAttribute("pontosVenda", new ArrayList<PontoVendaDTO>());
        return "pontos-venda/cadastro";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("pontoVendaDTO") PontoVendaDTO dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
            model.addAttribute("pontosVenda", new ArrayList<PontoVendaDTO>());
            return "pontos-venda/cadastro";
        }
        // TODO: PontoVendaService.salvar(dto)
        ra.addFlashAttribute("mensagem", "Ponto de venda cadastrado.");
        return "redirect:/pontos-venda";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, RedirectAttributes ra) {
        // TODO: PontoVendaService.remover(id)
        ra.addFlashAttribute("mensagem", "Ponto de venda removido.");
        return "redirect:/pontos-venda";
    }
}

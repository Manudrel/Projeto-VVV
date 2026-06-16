package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.TransportadoraDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/transportadoras")
public class TransportadoraViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
        model.addAttribute("transportadoraDTO", new TransportadoraDTO());
        model.addAttribute("transportadoras", new ArrayList<TransportadoraDTO>());
        return "transportadoras/cadastro";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("transportadoraDTO") TransportadoraDTO dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
            model.addAttribute("transportadoras", new ArrayList<TransportadoraDTO>());
            return "transportadoras/cadastro";
        }
        // TODO: TransportadoraService.salvar(dto)
        ra.addFlashAttribute("mensagem", "Transportadora cadastrada.");
        return "redirect:/transportadoras";
    }
}

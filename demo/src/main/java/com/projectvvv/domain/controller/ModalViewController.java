package com.projectvvv.domain.controller;

import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.dto.ModalDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/modais")
public class ModalViewController {

    @GetMapping
    public String central(Model model) {
        model.addAttribute("modais", new ArrayList<ModalDTO>());
        return "modais/central";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("modalDTO", new ModalDTO());
        return "modais/central";
    }

    @PostMapping
    public String salvar( @ModelAttribute("modalDTO") ModalDTO dto,
                         BindingResult br,
                         RedirectAttributes ra) {
        if (br.hasErrors()) return "modais/central";
        // TODO: ModalService.salvar(dto)
        ra.addFlashAttribute("mensagem", "Modal salvo.");
        return "redirect:/modais";
    }

    @PostMapping("/{id}/estado")
    public String mudarEstado(@PathVariable Long id,
                              @RequestParam EstadoModal estado,
                              RedirectAttributes ra) {
        // TODO: ModalService.alterarEstado(id, estado)
        ra.addFlashAttribute("mensagem", "Estado atualizado para " + estado + ".");
        return "redirect:/modais";
    }
}

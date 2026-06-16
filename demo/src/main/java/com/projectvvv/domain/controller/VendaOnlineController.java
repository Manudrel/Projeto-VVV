package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.VendaOnlineDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/vendas-online")
public class VendaOnlineController {

    @GetMapping("/aprovacao")
    public String fila(Model model) {
        model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
        model.addAttribute("vendas", new ArrayList<VendaOnlineDTO>());
        return "vendas-online/aprovacao";
    }

    @PostMapping("/{id}/aprovar")
    public String aprovar(@PathVariable Long id, RedirectAttributes ra) {
        // TODO: VendaOnlineService.aprovar(id, usuarioLogado)
        ra.addFlashAttribute("mensagem", "Venda aprovada.");
        return "redirect:/vendas-online/aprovacao";
    }

    @PostMapping("/{id}/rejeitar")
    public String rejeitar(@PathVariable Long id, RedirectAttributes ra) {
        // TODO: VendaOnlineService.rejeitar(id, usuarioLogado)
        ra.addFlashAttribute("mensagem", "Venda rejeitada.");
        return "redirect:/vendas-online/aprovacao";
    }
}

package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ModalDTO;
import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.service.ModalService;
import com.projectvvv.domain.service.TransportadoraService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/modais")
public class ModalViewController {

    private final ModalService modalService;
    private final TransportadoraService transportadoraService;

    public ModalViewController(ModalService modalService,
                               TransportadoraService transportadoraService) {
        this.modalService = modalService;
        this.transportadoraService = transportadoraService;
    }

    @GetMapping
    public String central(Model model) {
        carregarAtributosComuns(model);
        model.addAttribute("modalDTO", new ModalDTO());
        return "modais/central";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("modalDTO") ModalDTO dto,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (br.hasErrors()) {
            carregarAtributosComuns(model);
            return "modais/central";
        }

        try {
            modalService.salvarDoDTO(dto);
            ra.addFlashAttribute("mensagem", "Modal registrado com sucesso!");
            ra.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", "Erro ao registrar modal: " + e.getMessage());
            ra.addFlashAttribute("tipoMensagem", "error");
        }

        return "redirect:/modais";
    }

    @PostMapping("/{id}/estado")
    public String mudarEstado(@PathVariable Long id,
                              @RequestParam EstadoModal estado,
                              @RequestParam(required = false) String descricao,
                              RedirectAttributes ra) {
        try {
            switch (estado) {
                case ATIVO      -> modalService.ativar(id);
                case INATIVO    -> modalService.inativar(id);
                case MANUTENCAO -> modalService.manutencao(id,
                        descricao != null ? descricao : "Manutenção solicitada via painel.");
            }
            ra.addFlashAttribute("mensagem", "Estado atualizado para " + estado + ".");
            ra.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            ra.addFlashAttribute("tipoMensagem", "error");
        }
        return "redirect:/modais";
    }

    private void carregarAtributosComuns(Model model) {
        List<Modal> modais = modalService.listarTodos();
        List<Transportadora> transportadoras = transportadoraService.listarTodas();

        Map<Long, String> nomesTransportadoras = transportadoras.stream()
                .collect(Collectors.toMap(Transportadora::getCodigoTransportadora, Transportadora::getNome));

        model.addAttribute("modais", modais);
        model.addAttribute("transportadoras", transportadoras);
        model.addAttribute("nomesTransportadoras", nomesTransportadoras);
        model.addAttribute("tiposModal", com.projectvvv.domain.model.TipoModal.values());
        model.addAttribute("estadosModal", EstadoModal.values());
    }
}
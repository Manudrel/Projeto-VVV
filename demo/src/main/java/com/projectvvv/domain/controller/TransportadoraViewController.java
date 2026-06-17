package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.TransportadoraDTO;
import com.projectvvv.domain.exception.CnpjJaCadastradoException;
import com.projectvvv.domain.exception.TransportadoraNotFoundException;
import com.projectvvv.domain.model.Transportadora;
import com.projectvvv.domain.service.TransportadoraService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transportadoras")
public class TransportadoraViewController {

    private final TransportadoraService service;

    public TransportadoraViewController(
            TransportadoraService service
    ) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
        model.addAttribute("transportadoraDTO", new TransportadoraDTO());
        model.addAttribute("transportadoras", carregarLista());
        return "transportadoras/cadastro";
    }

    @GetMapping("/{id}")
    public String detalhes(
            @PathVariable Long id,
            Model model,
            RedirectAttributes ra
    ) {
        try {
            Transportadora encontrada = service.buscarPorId(id);

            model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
            model.addAttribute("transportadoraDTO", new TransportadoraDTO());
            model.addAttribute("transportadoras", carregarLista());
            model.addAttribute("transportadoraSelecionada", toDto(encontrada));

            return "transportadoras/cadastro";

        } catch (TransportadoraNotFoundException ex) {
            ra.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/transportadoras";
        }
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("transportadoraDTO") TransportadoraDTO dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {

        if (!br.hasErrors()) {
            try {
                service.salvar(toEntity(dto));
            } catch (CnpjJaCadastradoException | IllegalArgumentException ex) {
                br.addError(new FieldError(
                        "transportadoraDTO", "cnpj", ex.getMessage()
                ));
            }
        }

        if (br.hasErrors()) {
            model.addAttribute("usuarioLogado", new UsuarioLogadoMock());
            model.addAttribute("transportadoras", carregarLista());
            return "transportadoras/cadastro";
        }

        ra.addFlashAttribute("mensagem", "Transportadora cadastrada.");
        return "redirect:/transportadoras";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {

        try {
            service.deletar(id);
            ra.addFlashAttribute("mensagem", "Transportadora removida.");
        } catch (TransportadoraNotFoundException ex) {
            ra.addFlashAttribute("erro", ex.getMessage());
        }

        return "redirect:/transportadoras";
    }

    private List<TransportadoraDTO> carregarLista() {
        return service.listarTodas().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TransportadoraDTO toDto(Transportadora t) {
        return new TransportadoraDTO(t.getCodigoTransportadora(), t.getNome(), t.getCnpj());
    }

    private Transportadora toEntity(TransportadoraDTO dto) {
        Transportadora t = new Transportadora();
        t.setNome(dto.getNome());
        t.setCnpj(dto.getCnpj());
        return t;
    }
}
package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ReservaDTO;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.service.CidadeService;
import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.service.ModalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaViewController {

    private final CidadeService cidadeService;
    private final ModalService modalService;

    public ReservaViewController(CidadeService cidadeService, ModalService modalService) {
        this.cidadeService = cidadeService;
        this.modalService = modalService;
    }

    @GetMapping("/consultar")
    public String consultarReservas() {
        return "reservas/consultar";
    }


    @GetMapping("/nova")
    public String novaReserva(Model model) {

        model.addAttribute("cidades", cidadeService.listarTodas());

        List<Modal> modaisAtivos = modalService.listarTodos()
                .stream()
                .filter(m -> m.getEstadoModal() == EstadoModal.ATIVO)
                .toList();

        model.addAttribute("modais", modaisAtivos);

        model.addAttribute("reservaDTO", new ReservaDTO());

        return "reservas/nova";
    }


    @GetMapping("/detalhe")
    public String detalheReserva() {
        return "reservas/detalhe";
    }


    @PostMapping
    public String salvarReserva(@ModelAttribute ReservaDTO dto) {

        System.out.println("====== RESERVA ======");
        System.out.println("Modal: " + dto.getIdModal());
        System.out.println("Valor: " + dto.getValor());
        System.out.println("Tipo viagem: " + dto.getTipoViagem());
        System.out.println("Tipo passagem: " + dto.getTipoPassagem());
        System.out.println("Data: " + dto.getData());

        // TODO: chamar service
        // reservaService.salvar(dto);

        return "redirect:/reservas/consultar";

    }
}
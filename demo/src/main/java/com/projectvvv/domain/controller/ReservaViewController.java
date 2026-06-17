package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ReservaDTO;
import com.projectvvv.domain.dto.RotaDaReservaDTO;
import com.projectvvv.domain.model.*;
import com.projectvvv.domain.repository.CidadeRepository;
import com.projectvvv.domain.repository.ModalRepository;
import com.projectvvv.domain.repository.RotaDaReservaRepository;
import com.projectvvv.domain.repository.RotaRepository;
import com.projectvvv.domain.service.CidadeService;
import com.projectvvv.domain.service.ModalService;
import com.projectvvv.domain.service.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/reservas")
public class ReservaViewController {

    private final CidadeService cidadeService;
    private final ModalService modalService;
    private final ReservaService reservaService;
    private final RotaRepository rotaRepository;
    private final CidadeRepository cidadeRepository;
    private final ModalRepository  modalRepository;
    private  final RotaDaReservaRepository rotaDaReservaRepository;


    public ReservaViewController(CidadeService cidadeService,
                                 ModalService modalService,
                                 ReservaService reservaService,
                                 RotaRepository rotaRepository,
                                 CidadeRepository cidadeRepository,
                                ModalRepository modalRepository,
                                 RotaDaReservaRepository rotaDaReservaRepository) {
        this.cidadeService = cidadeService;
        this.modalService = modalService;
        this.reservaService = reservaService;
        this.rotaRepository = rotaRepository;
        this.cidadeRepository = cidadeRepository;
        this.modalRepository = modalRepository;
        this.rotaDaReservaRepository = rotaDaReservaRepository;
    }

    @GetMapping("/consultar")
    public String consultarReservas(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String de,
            @RequestParam(required = false) String ate,
            Model model) {

        List<Reserva> reservas;

        if (status != null && !status.isEmpty()) {
            StatusReserva sr = StatusReserva.valueOf(status.toUpperCase());
            reservas = reservaService.buscarPorStatus(sr);
        } else {
            reservas = reservaService.listarTodas();
        }

        model.addAttribute("reservas", reservas);
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

        ReservaDTO dto = new ReservaDTO();
        model.addAttribute("reservaDTO", dto);

        return "reservas/nova";
    }

    @GetMapping("/detalhe")
    public String detalheReserva() {
        return "reservas/detalhe";
    }
    private String generatLocalizador() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(6);
        java.security.SecureRandom random = new java.security.SecureRandom();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @PostMapping
    public String salvarReserva(@ModelAttribute ReservaDTO dto) {

        // 1. Monta a sequência de cidades
        List<Long> pontos = new ArrayList<>();
        pontos.add(dto.getOrigemId());


        if (dto.getEscalas() != null) {
            dto.getEscalas().stream()
                    .filter(Objects::nonNull)
                    .forEach(pontos::add);
        }

        pontos.add(dto.getDestinoId());
        // Gera localizador automático (ex: "A3F9K2")
        dto.setLocalizador(generatLocalizador());

        // 2. Para cada par, busca ou cria a Rota
        List<RotaDaReservaDTO> rotas = new ArrayList<>();

        for (int i = 0; i < pontos.size() - 1; i++) {
            Long cidOrigem = pontos.get(i);
            Long cidDestino = pontos.get(i + 1);

            // Busca a rota — se não existir, cria
            Rota rota = rotaRepository
                    .findByOrigem_IdAndDestino_Id(cidOrigem, cidDestino)
                    .orElseGet(() -> {
                        Cidade origem = cidadeRepository.findById(cidOrigem)
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Cidade origem não encontrada: " + cidOrigem));
                        Cidade destino = cidadeRepository.findById(cidDestino)
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Cidade destino não encontrada: " + cidDestino));

                        Rota nova = new Rota();
                        nova.setOrigem(origem);    // ← ajuste se o campo tiver outro nome
                        nova.setDestino(destino);  // ← ajuste se o campo tiver outro nome
                        return rotaRepository.save(nova);
                    });

            RotaDaReservaDTO rotaDTO = new RotaDaReservaDTO();
            rotaDTO.setRotaId(rota.getId());
            rotaDTO.setOrdem(i + 1);
            rotas.add(rotaDTO);
        }

        dto.setRotas(rotas);

        // 3. Salva
        reservaService.criar(dto);

        return "redirect:/reservas/consultar";
    }

    @PostMapping("/{codigoReserva}/confirmar")
    public String confirmarReserva(@PathVariable Long codigoReserva) {
        Reserva reserva = reservaService.buscarPorId(codigoReserva);
        reserva.setStatusReserva(StatusReserva.CONCLUIDO);
        reservaService.atualizar(codigoReserva, mapToDTO(reserva));
        return "redirect:/reservas/consultar";
    }


    @PostMapping("/{codigoReserva}/cancelar")
    public String cancelarReserva(@PathVariable Long codigoReserva) {
        Reserva reserva = reservaService.buscarPorId(codigoReserva);
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        reservaService.atualizar(codigoReserva, mapToDTO(reserva));
        return "redirect:/reservas/consultar";
    }

    @GetMapping("/detalhe/{codigoReserva}")
    public String detalheReserva(@PathVariable Long codigoReserva, Model model) {

        Reserva reserva = reservaService.buscarPorId(codigoReserva);
        model.addAttribute("reserva", reserva);

        // Busca o Modal para mostrar modelo/transportadora
        modalRepository.findById(reserva.getIdModal())
                .ifPresent(modal -> model.addAttribute("modal", modal));

        // Busca as rotas vinculadas a esta reserva
        List<RotaDaReserva> rotas = rotaDaReservaRepository
                .findByReservaCodigoReserva(codigoReserva);
        model.addAttribute("rotas", rotas);

        return "reservas/detalhe";
    }

    private ReservaDTO mapToDTO(Reserva r) {
        ReservaDTO dto = new ReservaDTO();
        dto.setData(r.getData());
        dto.setHoraPartida(r.getHoraPartida());
        dto.setLocalizador(r.getLocalizador());
        dto.setTipoViagem(r.getTipoViagem());
        dto.setTipoModal(r.getTipoModal());
        dto.setTipoPassagem(r.getTipoPassagem());
        dto.setIdModal(r.getIdModal());
        dto.setValor(r.getValor());
        dto.setStatusReserva(r.getStatusReserva());
        return dto;
    }
}
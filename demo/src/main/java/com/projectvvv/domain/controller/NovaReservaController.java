package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.NovaReservaForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Wizard de Nova Reserva — 5 etapas sem JavaScript.
 *
 * O estado parcial do formulário é mantido na sessão HTTP via
 * NovaReservaForm, descartado apenas após confirmação ou cancelamento.
 *
 * Fluxo:
 *   GET  /reservas/nova?etapa=1  → exibe etapa 1 (Cliente)
 *   POST /reservas/nova/etapa/1  → valida e avança para etapa 2
 *   ...
 *   POST /reservas/nova/etapa/5  → confirma e emite ticket
 */
@Controller
@RequestMapping("/reservas/nova")
public class NovaReservaController {

    // ------------------------------------------------------------------ //
    //  Chave da sessão                                                     //
    // ------------------------------------------------------------------ //

    private static final String FORM_KEY = "novaReservaForm";

    // ------------------------------------------------------------------ //
    //  GET — exibe o wizard na etapa indicada                              //
    // ------------------------------------------------------------------ //

    @GetMapping
    public String wizard(
            @RequestParam(name = "etapa", defaultValue = "1") int etapa,
            HttpSession session,
            Model model) {

        // Recupera (ou cria) o rascunho salvo na sessão
        NovaReservaForm form = (NovaReservaForm) session.getAttribute(FORM_KEY);
        if (form == null) {
            form = new NovaReservaForm();
            session.setAttribute(FORM_KEY, form);
        }

        // Garante que a etapa solicitada seja acessível (não pode pular)
        int etapaPermitida = form.getUltimaEtapaLiberada();
        if (etapa > etapaPermitida) {
            return "redirect:/reservas/nova?etapa=" + etapaPermitida;
        }

        popularModelComDadosDaEtapa(etapa, model);
        model.addAttribute("form", form);
        model.addAttribute("etapaAtual", etapa);
        model.addAttribute("totalEtapas", 5);

        return "reservas/nova-reserva";
    }

    // ------------------------------------------------------------------ //
    //  POST — processa cada etapa e redireciona                           //
    // ------------------------------------------------------------------ //

    /** Etapa 1 — Seleção do cliente */
    @PostMapping("/etapa/1")
    public String processarEtapa1(
            @RequestParam(name = "clienteId", required = false) Long clienteId,
            HttpSession session,
            RedirectAttributes ra) {

        NovaReservaForm form = getOrCreate(session);

        if (clienteId == null) {
            ra.addFlashAttribute("erro", "Selecione um cliente para continuar.");
            return "redirect:/reservas/nova?etapa=1";
        }

        form.setClienteId(clienteId);
        // TODO: buscar nome do cliente pelo service e guardar no form
        // form.setClienteNome(clienteService.findById(clienteId).getNome());
        form.setUltimaEtapaLiberada(Math.max(form.getUltimaEtapaLiberada(), 2));
        session.setAttribute(FORM_KEY, form);

        return "redirect:/reservas/nova?etapa=2";
    }

    /** Etapa 2 — Trajeto */
    @PostMapping("/etapa/2")
    public String processarEtapa2(
            @RequestParam(name = "origemId",   required = false) Long origemId,
            @RequestParam(name = "destinoId",  required = false) Long destinoId,
            @RequestParam(name = "tipoViagem", required = false) String tipoViagem,
            @RequestParam(name = "data",       required = false) String data,
            HttpSession session,
            RedirectAttributes ra) {

        NovaReservaForm form = getOrCreate(session);

        if (origemId == null || destinoId == null
                || tipoViagem == null || data == null || data.isBlank()) {
            ra.addFlashAttribute("erro", "Preencha todos os campos do trajeto.");
            return "redirect:/reservas/nova?etapa=2";
        }
        if (origemId.equals(destinoId)) {
            ra.addFlashAttribute("erro", "Origem e destino não podem ser iguais.");
            return "redirect:/reservas/nova?etapa=2";
        }

        form.setOrigemId(origemId);
        form.setDestinoId(destinoId);
        form.setTipoViagem(tipoViagem);
        form.setData(data);
        form.setUltimaEtapaLiberada(Math.max(form.getUltimaEtapaLiberada(), 3));
        session.setAttribute(FORM_KEY, form);

        return "redirect:/reservas/nova?etapa=3";
    }

    /** Etapa 3 — Modal e tipo de passagem */
    @PostMapping("/etapa/3")
    public String processarEtapa3(
            @RequestParam(name = "modalId",       required = false) Long modalId,
            @RequestParam(name = "tipoPassagem",  required = false) String tipoPassagem,
            HttpSession session,
            RedirectAttributes ra) {

        NovaReservaForm form = getOrCreate(session);

        if (modalId == null || tipoPassagem == null || tipoPassagem.isBlank()) {
            ra.addFlashAttribute("erro", "Selecione o modal e o tipo de passagem.");
            return "redirect:/reservas/nova?etapa=3";
        }

        form.setModalId(modalId);
        form.setTipoPassagem(tipoPassagem);
        form.setUltimaEtapaLiberada(Math.max(form.getUltimaEtapaLiberada(), 4));
        session.setAttribute(FORM_KEY, form);

        return "redirect:/reservas/nova?etapa=4";
    }

    /** Etapa 4 — Revisão / apenas avança para pagamento */
    @PostMapping("/etapa/4")
    public String processarEtapa4(HttpSession session) {
        NovaReservaForm form = getOrCreate(session);
        form.setUltimaEtapaLiberada(Math.max(form.getUltimaEtapaLiberada(), 5));
        session.setAttribute(FORM_KEY, form);
        return "redirect:/reservas/nova?etapa=5";
    }

    /** Etapa 5 — Pagamento e confirmação final */
    @PostMapping("/etapa/5")
    public String processarEtapa5(
            @RequestParam(name = "formaPagamento", required = false) String formaPagamento,
            @RequestParam(name = "parcelas",       required = false) String parcelas,
            HttpSession session,
            RedirectAttributes ra) {

        NovaReservaForm form = getOrCreate(session);

        if (formaPagamento == null || formaPagamento.isBlank()) {
            ra.addFlashAttribute("erro", "Selecione a forma de pagamento.");
            return "redirect:/reservas/nova?etapa=5";
        }

        form.setFormaPagamento(formaPagamento);
        form.setParcelas(parcelas);

        // TODO: chamar reservaService.confirmar(form) e obter o número do ticket
        // Long ticketId = reservaService.confirmar(form);
        Long ticketId = 99L; // placeholder

        // Limpa o rascunho da sessão após confirmação
        session.removeAttribute(FORM_KEY);

        ra.addFlashAttribute("sucesso",
                "Reserva confirmada! Ticket #" + ticketId + " emitido com sucesso.");
        return "redirect:/reservas/" + ticketId;
    }

    /** Cancelar e descartar rascunho */
    @PostMapping("/cancelar")
    public String cancelar(HttpSession session) {
        session.removeAttribute(FORM_KEY);
        return "redirect:/reservas";
    }

    // ------------------------------------------------------------------ //
    //  Helpers privados                                                    //
    // ------------------------------------------------------------------ //

    private NovaReservaForm getOrCreate(HttpSession session) {
        NovaReservaForm form = (NovaReservaForm) session.getAttribute(FORM_KEY);
        if (form == null) {
            form = new NovaReservaForm();
            session.setAttribute(FORM_KEY, form);
        }
        return form;
    }

    /**
     * Popula o Model com os dados de lookup necessários para cada etapa.
     * Em produção, esses dados viriam dos respectivos Services.
     */
    private void popularModelComDadosDaEtapa(int etapa, Model model) {
        switch (etapa) {
            case 1 -> {
                // TODO: model.addAttribute("clientes", clienteService.findAll());
            }
            case 2 -> {
                // TODO: model.addAttribute("cidades", cidadeService.findAll());
            }
            case 3 -> {
                // TODO: model.addAttribute("modais", modalService.findDisponiveisPara(form));
            }
            case 4, 5 -> {
                // Dados já estão no form (sessão)
            }
        }
    }
}
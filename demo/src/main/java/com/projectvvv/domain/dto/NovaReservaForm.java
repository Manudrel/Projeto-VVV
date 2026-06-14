package com.projectvvv.domain.dto;

import java.io.Serializable;

/**
 * Rascunho da reserva mantido na sessão HTTP durante o wizard.
 * Deve ser Serializable para funcionar em sessões distribuídas.
 */
public class NovaReservaForm implements Serializable {

    // ── Etapa 1: Cliente ──────────────────────────────────────
    private Long   clienteId;
    private String clienteNome;   // preenchido pelo service após busca

    // ── Etapa 2: Trajeto ─────────────────────────────────────
    private Long   origemId;
    private String origemNome;
    private Long   destinoId;
    private String destinoNome;
    private String tipoViagem;   // "direta" | "comEscalas"
    private String data;         // ISO date: "2026-06-22"

    // ── Etapa 3: Modal e passagem ─────────────────────────────
    private Long   modalId;
    private String modalNome;
    private String tipoPassagem; // "Economica" | "Semi-Leito" | "Leito"
    private Double valorTotal;

    // ── Etapa 5: Pagamento ────────────────────────────────────
    private String formaPagamento; // "dinheiro" | "pix" | "debito" | "credito"
    private String parcelas;

    // ── Controle do wizard ────────────────────────────────────
    /** Última etapa já concluída + 1 = próxima etapa liberada */
    private int ultimaEtapaLiberada = 1;

    // ── Getters e Setters ─────────────────────────────────────

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

    public Long getOrigemId() { return origemId; }
    public void setOrigemId(Long origemId) { this.origemId = origemId; }

    public String getOrigemNome() { return origemNome; }
    public void setOrigemNome(String origemNome) { this.origemNome = origemNome; }

    public Long getDestinoId() { return destinoId; }
    public void setDestinoId(Long destinoId) { this.destinoId = destinoId; }

    public String getDestinoNome() { return destinoNome; }
    public void setDestinoNome(String destinoNome) { this.destinoNome = destinoNome; }

    public String getTipoViagem() { return tipoViagem; }
    public void setTipoViagem(String tipoViagem) { this.tipoViagem = tipoViagem; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public Long getModalId() { return modalId; }
    public void setModalId(Long modalId) { this.modalId = modalId; }

    public String getModalNome() { return modalNome; }
    public void setModalNome(String modalNome) { this.modalNome = modalNome; }

    public String getTipoPassagem() { return tipoPassagem; }
    public void setTipoPassagem(String tipoPassagem) { this.tipoPassagem = tipoPassagem; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public String getParcelas() { return parcelas; }
    public void setParcelas(String parcelas) { this.parcelas = parcelas; }

    public int getUltimaEtapaLiberada() { return ultimaEtapaLiberada; }
    public void setUltimaEtapaLiberada(int ultimaEtapaLiberada) {
        this.ultimaEtapaLiberada = ultimaEtapaLiberada;
    }
}
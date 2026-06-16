package com.projectvvv.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class VendaOnlineDTO {

    private Long id;

    @NotNull
    private Long reservaId;

    @NotNull
    private LocalDateTime solicitadaEm;

    private LocalDateTime aprovadaEm;
    private Long aprovadaPorId;

    //private StatusReserva status = StatusReserva.Pendente;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }
    public LocalDateTime getSolicitadaEm() { return solicitadaEm; }
    public void setSolicitadaEm(LocalDateTime solicitadaEm) { this.solicitadaEm = solicitadaEm; }
    public LocalDateTime getAprovadaEm() { return aprovadaEm; }
    public void setAprovadaEm(LocalDateTime aprovadaEm) { this.aprovadaEm = aprovadaEm; }
    public Long getAprovadaPorId() { return aprovadaPorId; }
    public void setAprovadaPorId(Long aprovadaPorId) { this.aprovadaPorId = aprovadaPorId; }
    //public StatusReserva getStatus() { return status; }
    //public void setStatus(StatusReserva status) { this.status = status; }
}
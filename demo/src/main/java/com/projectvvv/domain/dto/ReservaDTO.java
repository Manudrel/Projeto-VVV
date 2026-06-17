package com.projectvvv.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.model.TipoPassagem;
import com.projectvvv.domain.model.TipoViagem;

public class ReservaDTO {

    private LocalDate data;
    private LocalTime horaPartida;
    private String localizador;

    private StatusReserva statusReserva;
    private TipoViagem tipoViagem;
    private TipoModal tipoModal;
    private TipoPassagem tipoPassagem;

    private Long idModal;

    private Float valor;

    private List<RotaDaReservaDTO> rotas;

    public ReservaDTO() {
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(LocalTime horaPartida) {
        this.horaPartida = horaPartida;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public TipoViagem getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(TipoViagem tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public TipoModal getTipoModal() {
        return tipoModal;
    }

    public void setTipoModal(TipoModal tipoModal) {
        this.tipoModal = tipoModal;
    }

    public TipoPassagem getTipoPassagem() {
        return tipoPassagem;
    }

    public void setTipoPassagem(TipoPassagem tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }

    public Long getIdModal() {
        return idModal;
    }

    public void setIdModal(Long idModal) {
        this.idModal = idModal;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public List<RotaDaReservaDTO> getRotas() {
        return rotas;
    }

    public void setRotas(List<RotaDaReservaDTO> rotas) {
        this.rotas = rotas;
    }
}
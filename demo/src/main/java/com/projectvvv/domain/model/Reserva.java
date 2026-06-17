package com.projectvvv.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoReserva;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horaPartida;

    @Column(nullable = false, length = 100)
    private String localizador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva statusReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoViagem tipoViagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoModal tipoModal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPassagem tipoPassagem;

    @Column(nullable = false)
    private Long idModal;

    @Column(nullable = false)
    private Float valor;

    public Reserva() {
    }

    public Reserva(
            Long codigoReserva,
            LocalDate data,
            LocalTime horaPartida,
            StatusReserva statusReserva,
            TipoViagem tipoViagem,
            TipoModal tipoModal,
            String localizador,
            TipoPassagem tipoPassagem,
            Long idModal,
            Float valor) {

        this.codigoReserva = codigoReserva;
        this.data = data;
        this.horaPartida = horaPartida;
        this.statusReserva = statusReserva;
        this.tipoViagem = tipoViagem;
        this.tipoModal = tipoModal;
        this.localizador = localizador;
        this.tipoPassagem = tipoPassagem;
        this.idModal = idModal;
        this.valor = valor;
    }

    public Long getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(Long codigoReserva) {
        this.codigoReserva = codigoReserva;
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
}
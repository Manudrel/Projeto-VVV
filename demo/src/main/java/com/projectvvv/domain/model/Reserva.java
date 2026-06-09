package com.projectvvv.domain.model;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoReserva;

    @Column (nullable = false)
    private Date data;
     
    @Column (nullable = false, length = 100)
    private String horaPartida;

    @Column (nullable = false)
    private StatusReserva statusReserva;

    @Column (nullable = false)
    private TipoModal tipoViagem;

    @Column (nullable = false, length = 100)
    private String localizador;

    @Column (nullable = false)
    private TipoPassagem tipoPassagem;

    @Column (nullable = false)
    private Long idRota;

    @Column (nullable = false)
    private Long idModal;

    // Construtores
    public Reserva() {}


    // Getters e Setters
    public Reserva(Long codigoReserva, Date data, String horaPartida, StatusReserva statusReserva, TipoModal tipoViagem,
            String localizador, TipoPassagem tipoPassagem, Long idRota, Long idModal) {
        this.codigoReserva = codigoReserva;
        this.data = data;
        this.horaPartida = horaPartida;
        this.statusReserva = statusReserva;
        this.tipoViagem = tipoViagem;
        this.localizador = localizador;
        this.tipoPassagem = tipoPassagem;
        this.idRota = idRota;
        this.idModal = idModal;
    }


    public Long getCodigoReserva() {
        return codigoReserva;
    }


    public void setCodigoReserva(Long codigoReserva) {
        this.codigoReserva = codigoReserva;
    }


    public Date getData() {
        return data;
    }


    public void setData(Date data) {
        this.data = data;
    }


    public String getHoraPartida() {
        return horaPartida;
    }


    public void setHoraPartida(String horaPartida) {
        this.horaPartida = horaPartida;
    }


    public StatusReserva getStatusReserva() {
        return statusReserva;
    }


    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }


    public TipoModal getTipoViagem() {
        return tipoViagem;
    }


    public void setTipoViagem(TipoModal tipoViagem) {
        this.tipoViagem = tipoViagem;
    }


    public String getLocalizador() {
        return localizador;
    }


    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }


    public TipoPassagem getTipoPassagem() {
        return tipoPassagem;
    }


    public void setTipoPassagem(TipoPassagem tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }


    public Long getIdRota() {
        return idRota;
    }


    public void setIdRota(Long idRota) {
        this.idRota = idRota;
    }


    public Long getIdModal() {
        return idModal;
    }


    public void setIdModal(Long idModal) {
        this.idModal = idModal;
    }

}
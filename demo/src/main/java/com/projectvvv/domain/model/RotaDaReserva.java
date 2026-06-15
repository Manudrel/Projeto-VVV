package com.projectvvv.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rota_da_reserva")
public class RotaDaReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rota")
    private Rota rota;

    @Column(nullable = false)
    private Integer ordem;

    public RotaDaReserva() {
    }

    public RotaDaReserva(Long id, Reserva reserva, Rota rota, Integer ordem) {
        this.id = id;
        this.reserva = reserva;
        this.rota = rota;
        this.ordem = ordem;
    }

    public Long getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
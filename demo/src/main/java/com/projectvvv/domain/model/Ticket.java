package com.projectvvv.domain.model;

import jakarta.persistence.*;
import com.projectvvv.domain.enums.TipoPassagem;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String codigoTicket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPassagem tipoPassagem;

    @Column(nullable = false)
    private Long idReserva;

    @Column(nullable = false)
    private Long idCliente;

    // Construtores
    public Ticket(){}

    private Ticket(Long id, TipoPassagem tipoPassagem, String codigoTicket, Long idReserva, Long idCliente) {
        this.id = id;
        this.tipoPassagem = tipoPassagem;
        this.codigoTicket = codigoTicket;
        this.idCliente = idCliente;
        this.idReserva = idReserva;
    }

    // Getters e Setters
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public TipoPassagem getTipoPassagem() { 
        return tipoPassagem;
    }

    public void setTipoPassagem(TipoPassagem tipoPassagem) { 
        this.tipoPassagem = tipoPassagem;
    }

    public String getCodigoTicket() { return codigoTicket;}

    public void setCodigoTicket(String codigoTicket) { 
        this.codigoTicket = codigoTicket;
    }

    public Long getIdCliente() { 
        return idCliente;
    }

    public void setIdCliente(Long idCliente) { 
        this.idCliente = idCliente;
    }

    public Long getIdReserva() { 
        return idReserva;
    }

    public void setIdReserva(Long idReserva) { 
        this.idReserva = idReserva;
    }
}
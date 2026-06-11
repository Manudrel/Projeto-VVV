package com.projectvvv.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origem", nullable = false)
    private Cidade origem;

    @ManyToOne
    @JoinColumn(name = "destino", nullable = false)
    private Cidade destino;

    // Construtores
    public Rota() {
    }

    public Rota(Long id, Cidade origem, Cidade destino) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public void setOrigem(Cidade origem) {
        this.origem = origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public void setDestino(Cidade destino) {
        this.destino = destino;
    }
}
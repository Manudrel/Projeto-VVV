package com.projectvvv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String estado;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(length = 100)
    private String pais;


    // Construtores
    private Cidade(){}

    private Cidade(Long id, String estado, String cidade, String pais) {
        this.id = id;
        this.estado = estado;
        this.cidade = cidade;
        this.pais = pais;
    }

    // Getters e Setters

    private Long getId() { return id;}
    private void setId(Long id) { this.id = id;}

    private String getEstado() { return estado; }
    private void setEstado(String estado) { this.estado = estado; }

    private String getCidade() { return cidade; }
    private void setCidade(String cidade) { this.cidade = cidade; }
}
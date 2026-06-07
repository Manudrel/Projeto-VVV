package com.projectvvv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transportadora")
public class Transportadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoTransportadora;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;
     
    // Construtores
    public Transportadora() {}

    public Transportadora(Long codigoTransportadora, String nome, String cnpj) {
        this.codigoTransportadora = codigoTransportadora;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    // Getters e Setters
    public Long getCodigoTransportadora() { return codigoTransportadora; }
    public void setCodigoTransportadora(Long codigoTransportadora) { this.codigoTransportadora = codigoTransportadora; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

}
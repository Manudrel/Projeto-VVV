package com.projectvvv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private Integer origem;
    
    @Column (nullable = false, length = 100)
    private Integer destino

     
    // Construtores
    public Rota() {}

    public Rota(Long id, Integer origem, Integer destino) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getOrigem() { return origem; }
    public void setOrigem(Integer origem) { this.origem = origem; }

    public Integer getDestino() { return destino; }
    public void setDestino(Integer destino) { this.destino = destino; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

}
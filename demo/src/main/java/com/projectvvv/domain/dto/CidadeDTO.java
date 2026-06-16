package com.projectvvv.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CidadeDTO {

    private Long id;

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;

    @NotBlank
    @Size(max = 120)
    private String pais;

    @NotBlank
    @Size(min = 3, max = 3)
    private String codigoIata;

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCodigoIata() {
        return codigoIata;
    }

    public void setCodigoIata(String codigoIata) {
        this.codigoIata = codigoIata;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
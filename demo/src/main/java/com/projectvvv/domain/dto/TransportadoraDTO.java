package com.projectvvv.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TransportadoraDTO {

    private Long id;

    @NotBlank(message = "Informe o nome da transportadora")
    private String nome;

    @NotBlank(message = "Informe o CNPJ")
    @Pattern(
            regexp = "^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$",
            message = "CNPJ em formato inválido"
    )
    private String cnpj;

    public TransportadoraDTO() {
    }

    public TransportadoraDTO(Long id, String nome, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
    }

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
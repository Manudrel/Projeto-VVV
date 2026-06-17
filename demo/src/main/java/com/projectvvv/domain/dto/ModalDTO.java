package com.projectvvv.domain.dto;

import com.projectvvv.domain.model.TipoModal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ModalDTO {


    @NotNull(message = "Selecione o tipo de modal.")
    private TipoModal tipoModal;


    @NotNull(message = "Informe o ano.")
    @Min(value = 1950, message = "Ano deve ser maior ou igual a 1950.")
    private Integer ano;


    @NotBlank(message = "Informe o modelo.")
    private String modelo;


    @NotNull(message = "Informe a capacidade.")
    @Min(value = 1, message = "Capacidade deve ser maior que zero.")
    private Integer capacidade;


    @NotNull(message = "Selecione a transportadora.")
    private Long transportadoraId;



    public TipoModal getTipoModal() {
        return tipoModal;
    }

    public void setTipoModal(TipoModal tipoModal) {
        this.tipoModal = tipoModal;
    }


    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }


    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }


    public Long getTransportadoraId() {
        return transportadoraId;
    }

    public void setTransportadoraId(Long transportadoraId) {
        this.transportadoraId = transportadoraId;
    }

}
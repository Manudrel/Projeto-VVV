package com.projectvvv.domain.dto;

public class RotaDTO {

    private Long origemId;
    private Long destinoId;

    public RotaDTO() {
    }

    public Long getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Long origemId) {
        this.origemId = origemId;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }
}
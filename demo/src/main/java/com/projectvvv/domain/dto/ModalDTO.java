package com.projectvvv.domain.dto;

import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.model.TipoModal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ModalDTO {

    private Long id;

    @NotNull
    private TipoModal tipo;

    @NotBlank
    @Size(max = 60)
    private String identificacao;

    @NotNull
    private Long transportadoraId;

    @NotNull
    @Min(1)
    private Integer capacidade;

    @NotNull
    private EstadoModal estado = EstadoModal.ATIVO;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoModal getTipo() { return tipo; }
    public void setTipo(TipoModal tipo) { this.tipo = tipo; }
    public String getIdentificacao() { return identificacao; }
    public void setIdentificacao(String identificacao) { this.identificacao = identificacao; }
    public Long getTransportadoraId() { return transportadoraId; }
    public void setTransportadoraId(Long transportadoraId) { this.transportadoraId = transportadoraId; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public EstadoModal getEstado() { return estado; }
    public void setEstado(EstadoModal estado) { this.estado = estado; }
}

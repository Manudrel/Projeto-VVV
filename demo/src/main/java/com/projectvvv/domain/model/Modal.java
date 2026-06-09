package com.projectvvv.domain.model;

import jakarta.persistence.*;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.model.EstadoModal;

@Entity
@Table(name = "modal")
public class Modal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String codigoModal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private TipoModal tipo;

    @Column(nullable = false, length = 4)
    private Integer ano;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false)
    private Integer capacidade;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private EstadoModal estado;

    @Column(nullable = false)
    private Long idTrasnportadora;


    // Construtores
    public Modal() {
    }

    public Modal(Long id, String codigoModal, TipoModal tipo, Integer ano, String modelo, Integer capacidade,
                 EstadoModal estado, Long idTrasnportadora) {
        this.id = id;
        this.codigoModal = codigoModal;
        this.tipo = tipo;
        this.ano = ano;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.estado = estado;
        this.idTrasnportadora = idTrasnportadora;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoModal() {
        return codigoModal;
    }

    public void setCodigoModal(String codigoModal) {
        this.codigoModal = codigoModal;
    }

    public TipoModal getTipoEnum() {
        return tipo;
    }

    public void setTipoEnum(TipoModal tipo) {
        this.tipo = tipo;
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

    public EstadoModal getEstado() {
        return estado;
    }

    public void setEstado(EstadoModal estado) {
        this.estado = estado;
    }

    public Long getIdTrasnportadora() {
        return idTrasnportadora;
    }

    public void setIdTrasnportadora(Long idTrasnportadora) {
        this.idTrasnportadora = idTrasnportadora;
    }

}

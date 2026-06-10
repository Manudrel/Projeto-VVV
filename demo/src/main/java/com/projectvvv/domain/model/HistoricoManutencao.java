package com.projectvvv.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "historico_manutencao")
public class HistoricoManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long modalId;

    private LocalDateTime dataRegistro;

    private String descricao;

    // Construtores

    public HistoricoManutencao() {}

    public HistoricoManutencao(long id2, String descricao2, LocalDateTime dataManutencao, long idModal) {}

    public HistoricoManutencao(Long id, Long modalId, LocalDateTime dataRegistro, String descricao) {
        this.id = id;
        this.modalId = modalId;
        this.dataRegistro = dataRegistro;
        this.descricao = descricao;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModalId() {
        return modalId;
    }

    public void setModalId(Long modalId) {
        this.modalId = modalId;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
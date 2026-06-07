package com.projectvvv.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ponto_de_venda")
public class PontoDeVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoPonto;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(length = 200)
    private String endereco;

    @Column(length = 21)
    private String telefone;

     
    // Construtores
    public PontoDeVenda() {}

    public PontoDeVenda(Long codigoPonto, String nome, String cnpj, String endereco, String telefone) {
        this.codigoPonto = codigoPonto;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getCodigoPonto() {
        return codigoPonto;
    }

    public void setCodigoPonto(Long codigoPonto) {
        this.codigoPonto = codigoPonto;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
package com.projectvvv.domain.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 12)
    private String cpf;

    @Column(length = 200)
    private String endereco;

    @Column(length = 21)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column
    private Cargo cargo;

    @OneToMany(
        mappedBy = "funcionario",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<PontosDeVendaDoFuncionario> pontosDeVenda;

    @Column
    private LocalDate dataNascimento;

    // Construtores
    public Funcionario() {}

    public Funcionario(Long id, String nome, String cpf, String endereco, Cargo cargo, String telefone, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.cargo = cargo;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<PontosDeVendaDoFuncionario> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<PontosDeVendaDoFuncionario> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cargo getCargoEnum() {
        return cargo;
    }

    public void setCargoEnum(Cargo cargo) {
        this.cargo = cargo;
    }
}    
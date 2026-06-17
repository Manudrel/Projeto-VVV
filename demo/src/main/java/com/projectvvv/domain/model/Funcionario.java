package com.projectvvv.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

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

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @Column(nullable = false)
    private LocalDate dataNascimento;


    @OneToMany(
            mappedBy = "funcionario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PontosDeVendaDoFuncionario> pontosDeVenda = new ArrayList<>();


    // Construtor vazio obrigatório do JPA
    public Funcionario() {
    }


    public Funcionario(
            Long id,
            String nome,
            String cpf,
            String endereco,
            String telefone,
            String senha,
            Cargo cargo,
            LocalDate dataNascimento
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.senha = senha;
        this.cargo = cargo;
        this.dataNascimento = dataNascimento;
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


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public List<PontosDeVendaDoFuncionario> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<PontosDeVendaDoFuncionario> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }
}
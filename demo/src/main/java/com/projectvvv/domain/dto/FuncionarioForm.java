package com.projectvvv.domain.dto;

import com.projectvvv.domain.model.Cargo;

import java.time.LocalDate;

public class FuncionarioForm {

    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
    private String endereco;
    private Cargo cargo;
    private Long pontoDeVendaId;
    private String senha;
    private String confirmarSenha;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Long getPontoDeVendaId() {
        return pontoDeVendaId;
    }

    public void setPontoDeVendaId(Long pontoDeVendaId) {
        this.pontoDeVendaId = pontoDeVendaId;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
}
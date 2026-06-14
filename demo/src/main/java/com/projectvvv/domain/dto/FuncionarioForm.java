package com.projectvvv.domain.dto;

public class FuncionarioForm {

    private String nome;
    private String cpf;
    private String telefone;
    private Integer idade;
    private String endereco;
    private String cargo; // "Funcionario" ou "Gerente"
    private Long pontoDeVendaId;
    private String senha;
    private String confirmarSenha;

    public FuncionarioForm() {
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
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
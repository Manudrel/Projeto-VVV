package com.projectvvv.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FuncionarioDTO {

    private Long id;

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String email;


    @NotNull(message = "Ponto de venda é obrigatório")
    private Long pontoVendaId;

    @Size(min = 6, max = 60, message = "Senha entre 6 e 60 caracteres")
    private String senha;

    private boolean ativo = true;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; } 
    public Long getPontoVendaId() { return pontoVendaId; }
    public void setPontoVendaId(Long pontoVendaId) { this.pontoVendaId = pontoVendaId; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}

/*
    Criar enum de cargofuncionario (se existir)
*/
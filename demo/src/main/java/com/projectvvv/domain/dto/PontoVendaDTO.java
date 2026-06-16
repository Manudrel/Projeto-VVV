package com.projectvvv.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PontoVendaDTO {

    private Long id;

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}", message = "CNPJ inválido")
    private String cnpj;

    @NotBlank
    private String telefone;

    @NotBlank
    @Size(max = 200)
    private String endereco;

    private Long gerenteResponsavelId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Long getGerenteResponsavelId() { return gerenteResponsavelId; }
    public void setGerenteResponsavelId(Long gerenteResponsavelId) { this.gerenteResponsavelId = gerenteResponsavelId; }
}

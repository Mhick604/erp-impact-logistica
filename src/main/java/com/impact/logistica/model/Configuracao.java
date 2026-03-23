package com.impact.logistica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Configuracao {

	@Id
    private Long id = 1L;

    private String nomeEmpresa = "Impact Logística"; 
    private String corPrimaria = "#343a40"; 
    private String nomeLogo;
    
    private String cnpj = "00.000.000/0001-00";
    private String telefone = "(00) 00000-0000";

    // NOVO CAMPO PARA O CÁLCULO DE LUCRO
    private Double precoCombustivel = 5.89;
    
    // --- GETTERS E SETTERS ---
    public Double getPrecoCombustivel() { return precoCombustivel; }
    public void setPrecoCombustivel(Double precoCombustivel) { this.precoCombustivel = precoCombustivel; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getCorPrimaria() { return corPrimaria; }
    public void setCorPrimaria(String corPrimaria) { this.corPrimaria = corPrimaria; }

    public String getNomeLogo() { return nomeLogo; }
    public void setNomeLogo(String nomeLogo) { this.nomeLogo = nomeLogo; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
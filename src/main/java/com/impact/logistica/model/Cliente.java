package com.impact.logistica.model;

import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String razaoSocial;
    private String cnpj;
    private String telefoneWhatsapp;
    private String endereco;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getTelefoneWhatsapp() { return telefoneWhatsapp; }
    public void setTelefoneWhatsapp(String telefoneWhatsapp) { this.telefoneWhatsapp = telefoneWhatsapp; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
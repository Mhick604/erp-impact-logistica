package com.impact.logistica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String cnhNumero;
    private String cnhCategoria;
    private String cargo;
    
    // NOVO: Crucial para o sistema de alertas
    private LocalDate dataVencimentoCnh; 
    
    private String telefone;
    
    // Status: "Disponível", "Em Viagem", "Férias"
    private String status; 
    
    private Boolean ativo = true;

    // ==========================================
    // GETTERS E SETTERS
    // ==========================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCnhNumero() { return cnhNumero; }
    public void setCnhNumero(String cnhNumero) { this.cnhNumero = cnhNumero; }

    public String getCnhCategoria() { return cnhCategoria; }
    public void setCnhCategoria(String cnhCategoria) { this.cnhCategoria = cnhCategoria; }

    public LocalDate getDataVencimentoCnh() { return dataVencimentoCnh; }
    public void setDataVencimentoCnh(LocalDate dataVencimentoCnh) { this.dataVencimentoCnh = dataVencimentoCnh; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    
    public String getCargo() { return cargo; }

    public void setCargo(String cargo) { this.cargo = cargo; }
}
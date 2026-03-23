package com.impact.logistica.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String marcaModelo;
    private Integer ano;
    private Double capacidadeToneladas;
    private String renavam;
    
    private Double kmPorLitro;
    
    // Status: "Operacional" ou "Em Manutenção"
    private String status; 
    
    // ==========================================
    // CAMPOS DE MANUTENÇÃO E LICENCIAMENTO
    // ==========================================
    private Double kmProximaRevisao;
    private LocalDate dataProximaRevisao;
    private Double quilometragemAtual;
    private LocalDate dataVencimentoLicenciamento;

    // ==========================================
    // GETTERS E SETTERS
    // ==========================================
   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarcaModelo() { return marcaModelo; }
    public void setMarcaModelo(String marcaModelo) { this.marcaModelo = marcaModelo; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Double getCapacidadeToneladas() { return capacidadeToneladas; }
    public void setCapacidadeToneladas(Double capacidadeToneladas) { this.capacidadeToneladas = capacidadeToneladas; }

    public String getRenavam() { return renavam; }
    public void setRenavam(String renavam) { this.renavam = renavam; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataVencimentoLicenciamento() { return dataVencimentoLicenciamento; }
    public void setDataVencimentoLicenciamento(LocalDate dataVencimentoLicenciamento) { this.dataVencimentoLicenciamento = dataVencimentoLicenciamento; }

    public Double getQuilometragemAtual() { return quilometragemAtual; }
    public void setQuilometragemAtual(Double quilometragemAtual) { this.quilometragemAtual = quilometragemAtual; }
    
    public Double getKmProximaRevisao() { return kmProximaRevisao; }
    public void setKmProximaRevisao(Double kmProximaRevisao) { this.kmProximaRevisao = kmProximaRevisao; }
    
    public LocalDate getDataProximaRevisao() { return dataProximaRevisao; }
    public void setDataProximaRevisao(LocalDate dataProximaRevisao) { this.dataProximaRevisao = dataProximaRevisao; }
    
    public Double getKmPorLitro() { return kmPorLitro; }
    public void setKmPorLitro(Double kmPorLitro) { this.kmPorLitro = kmPorLitro; }
}
package com.impact.logistica.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: Qual caminhão está na oficina?
    @ManyToOne
    private Veiculo veiculo;

    private String tipo; // Preventiva ou Corretiva
    private String servico; // Ex: Troca de Óleo, Lona de Freio
    private String oficina; // Onde o serviço foi feito
    
    private Double quilometragem; // KM do caminhão no dia do conserto
    private Double valorTotal;
    
    private LocalDate dataManutencao;
    private String status; // Agendada, Em Andamento, Concluída

    // ==========================================
    // GETTERS E SETTERS
    // ==========================================
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Veiculo getVeiculo() {
        return veiculo;
    }
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getServico() {
        return servico;
    }
    public void setServico(String servico) {
        this.servico = servico;
    }
    public String getOficina() {
        return oficina;
    }
    public void setOficina(String oficina) {
        this.oficina = oficina;
    }
    public Double getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(Double quilometragem) {
        this.quilometragem = quilometragem;
    }
    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public LocalDate getDataManutencao() {
        return dataManutencao;
    }
    public void setDataManutencao(LocalDate dataManutencao) {
        this.dataManutencao = dataManutencao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
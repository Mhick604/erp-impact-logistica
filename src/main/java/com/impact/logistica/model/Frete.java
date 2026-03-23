package com.impact.logistica.model;

import java.time.LocalDate;
import java.util.List; // AQUI ESTÁ A IMPORTAÇÃO QUE FALTAVA!

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Frete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    // AQUI MUDOU: Removemos o singular e deixamos só a Lista Plural
    @ManyToMany
    private List<Motorista> ajudantes; 

    @ManyToOne
    private Veiculo veiculo;

    @Column(columnDefinition = "TEXT")
    private String materialTransportado;
    
    @jakarta.persistence.Transient
    private Double precoCombustivelNaViagem;
    
    private String origem;
    private String destino;
    private Double valorCombinado;
    
    // CAMPOS PARA O CÁLCULO E OS
    private Double kmEstimado = 0.0;
    private Double outrosGastos = 0.0;
    private Double custoTotalEstimado = 0.0;
    
    private LocalDate dataSaida;
    private String status;
    
    // ==========================================
    // GETTERS E SETTERS LIMPOS E ATUALIZADOS
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }
    
    public List<Motorista> getAjudantes() { return ajudantes; }
    public void setAjudantes(List<Motorista> ajudantes) { this.ajudantes = ajudantes; }
    
    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    
    public String getMaterialTransportado() { return materialTransportado; }
    public void setMaterialTransportado(String materialTransportado) { this.materialTransportado = materialTransportado; }
    
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    
    public Double getValorCombinado() { return valorCombinado; }
    public void setValorCombinado(Double valorCombinado) { this.valorCombinado = valorCombinado; }
    
    public Double getKmEstimado() { return kmEstimado; }
    public void setKmEstimado(Double kmEstimado) { this.kmEstimado = kmEstimado; }
    
    public Double getOutrosGastos() { return outrosGastos; }
    public void setOutrosGastos(Double outrosGastos) { this.outrosGastos = outrosGastos; }
    
    public Double getCustoTotalEstimado() { return custoTotalEstimado; }
    public void setCustoTotalEstimado(Double custoTotalEstimado) { this.custoTotalEstimado = custoTotalEstimado; }
    
    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Double getPrecoCombustivelNaViagem() { return precoCombustivelNaViagem; }
    public void setPrecoCombustivelNaViagem(Double precoCombustivelNaViagem) { this.precoCombustivelNaViagem = precoCombustivelNaViagem; }

}
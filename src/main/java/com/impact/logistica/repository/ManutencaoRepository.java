package com.impact.logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.impact.logistica.model.Manutencao;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    
    // Busca todo o histórico de manutenção de um caminhão específico
    @Query("SELECT m FROM Manutencao m WHERE m.veiculo.id = :veiculoId ORDER BY m.dataManutencao DESC")
    List<Manutencao> buscarPorVeiculo(@Param("veiculoId") Long veiculoId);
    
}
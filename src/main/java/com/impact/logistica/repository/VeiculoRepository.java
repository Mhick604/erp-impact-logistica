package com.impact.logistica.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.impact.logistica.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // A MÁGICA DO ALERTA: Traz os veículos que estouraram o KM ou a Data de Revisão
    @Query("SELECT v FROM Veiculo v WHERE v.quilometragemAtual >= v.kmProximaRevisao OR v.dataProximaRevisao <= :hoje")
    List<Veiculo> buscarVeiculosPrecisandoRevisao(@Param("hoje") LocalDate hoje);

}
package com.impact.logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.impact.logistica.model.Frete;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {
    
    // Pede ao banco de dados para somar a coluna valorCombinado de todos os fretes
    @Query("SELECT SUM(f.valorCombinado) FROM Frete f")
    Double somarFaturamentoTotal();
}
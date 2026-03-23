package com.impact.logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.impact.logistica.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    
    // Pede ao banco de dados para somar a coluna valor de todas as despesas
    @Query("SELECT SUM(d.valor) FROM Despesa d")
    Double somarDespesasTotais();
}
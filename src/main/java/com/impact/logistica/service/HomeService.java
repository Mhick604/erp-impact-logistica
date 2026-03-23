package com.impact.logistica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.impact.logistica.model.Despesa;
import com.impact.logistica.model.Frete;
import com.impact.logistica.repository.DespesaRepository;
import com.impact.logistica.repository.FreteRepository;

@Service
public class HomeService {

    @Autowired
    private FreteRepository freteRepo;

    @Autowired
    private DespesaRepository despesaRepo;

    public Double calcularFaturamentoTotal() {
        return freteRepo.findAll().stream()
                .filter(f -> !"CANCELADO".equals(f.getStatus()))
                .mapToDouble(Frete::getValorCombinado).sum();
    }

    public Double calcularDespesasTotais() {
        return despesaRepo.findAll().stream()
                .mapToDouble(Despesa::getValor).sum();
    }
}
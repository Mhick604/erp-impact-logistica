package com.impact.logistica.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.impact.logistica.model.Frete;
import com.impact.logistica.repository.FreteRepository;

@Controller
public class AgendaController {

    @Autowired
    private FreteRepository freteRepository;

    // 1. Abre a página HTML da Agenda
    @GetMapping("/agenda")
    public String abrirAgenda() {
        return "agenda/calendario";
    }

    // 2. MÁGICA: Fornece as viagens para o calendário desenhar na tela
    @GetMapping("/api/agenda/eventos")
    @ResponseBody
    public List<Map<String, Object>> getEventos() {
        List<Frete> fretes = freteRepository.findAll();

        return fretes.stream()
            // Filtra só viagens que tenham data de saída marcada
            .filter(f -> f.getDataSaida() != null)
            .map(f -> {
                // Inteligência de Cores: Muda a cor do bloquinho pela situação da viagem!
                String cor = "#0d6efd"; // Azul (Padrão)
                
                if ("Em Viagem".equalsIgnoreCase(f.getStatus()) || "EM_TRANSITO".equalsIgnoreCase(f.getStatus())) {
                    cor = "#198754"; // Verde (Rodando)
                } else if ("Pendente".equalsIgnoreCase(f.getStatus()) || "AGUARDANDO".equalsIgnoreCase(f.getStatus())) {
                    cor = "#ffc107"; // Amarelo (Aguardando)
                } else if ("Finalizado".equalsIgnoreCase(f.getStatus()) || "CONCLUIDA".equalsIgnoreCase(f.getStatus())) {
                    cor = "#6c757d"; // Cinza (Já acabou)
                }

                // ========================================================
                // CORREÇÃO: Usando HashMap clássico para evitar erros de 
                // compilação e problemas de compatibilidade de versão.
                // ========================================================
                Map<String, Object> evento = new HashMap<>();
                
                evento.put("id", f.getId());
                
                // Evita erro se o destino estiver em branco no banco de dados
                String destino = f.getDestino() != null ? f.getDestino() : "Destino não informado";
                evento.put("title", "OS #" + f.getId() + " - " + destino);
                
                // Trata a data para cortar o "T00:00" e preencher o dia inteiro no calendário!
                String dataSaida = f.getDataSaida().toString();
                if (dataSaida.contains("T")) {
                    dataSaida = dataSaida.split("T")[0];
                }
                evento.put("start", dataSaida);
                
                evento.put("color", cor);
                evento.put("url", "/fretes/editar/" + f.getId()); // Clicou, abriu a OS!

                return evento;
            })
            .collect(Collectors.toList());
    }
}
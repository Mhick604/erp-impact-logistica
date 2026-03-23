package com.impact.logistica.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impact.logistica.service.GeminiService;

@RestController
@RequestMapping("/ia")
public class IaController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/analisar-frete")
    public ResponseEntity<String> analisarFrete(@RequestBody Map<String, String> payload) {
        // Recebe o texto que foi colado no ecrã
        String textoCliente = payload.get("texto");
        
        // Manda para o Gemini ler e devolver o JSON formatado
        String resultadoJson = geminiService.analisarTextoWhatsApp(textoCliente);
        
        // Devolve o JSON para o Javascript preencher o formulário
        return ResponseEntity.ok(resultadoJson);
    }
}
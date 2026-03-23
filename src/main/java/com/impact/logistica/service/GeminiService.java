package com.impact.logistica.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String analisarTextoWhatsApp(String textoBaguncado) {
        try {
            // A MÁGICA ATUALIZADA: Usando o gemini-3-flash-preview da documentação oficial!
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key=" + apiKey.trim();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String prompt = "Você é um assistente de logística sênior de um ERP. Leia a mensagem do cliente (que está respondendo a um questionário padrão da transportadora) e extraia os dados para criar uma Ordem de Serviço. " +
                            "Retorne APENAS um JSON válido e limpo com as seguintes chaves exatas: " +
                            "'origem' (endereço de coleta completo), " +
                            "'destino' (endereço de entrega completo), " +
                            "'valorCombinado' (se o cliente mencionar algum valor em R$, extraia apenas os números e ponto, senão deixe em branco). " +
                            "Texto do cliente: " + textoBaguncado;

            // Empacota o texto de forma blindada
            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);

            Map<String, Object> parts = new HashMap<>();
            parts.put("parts", List.of(textPart));

            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("contents", List.of(parts));

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(requestBodyMap);

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            
            // Dispara para a nuvem
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // Extrai a resposta da IA
            JsonNode root = mapper.readTree(response.getBody());
            String textoResposta = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            return textoResposta.replace("```json", "").replace("```", "").trim();

        } catch (Exception e) {
            System.err.println("🚨 ERRO DE COMUNICAÇÃO COM O GEMINI: " + e.getMessage());
            e.printStackTrace();
            return "{}"; 
        }
    }
}
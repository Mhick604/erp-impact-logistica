package com.impact.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.impact.logistica.model.Configuracao;
import com.impact.logistica.repository.ConfiguracaoRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    // Esse método disponibiliza a variável 'configApp' para TODOS os arquivos HTML
    @ModelAttribute("configApp")
    public Configuracao getConfiguracaoGlobal() {
        // Busca a configuração no banco. Se não existir (primeiro acesso), cria uma nova com os padrões.
        return configuracaoRepository.findById(1L).orElse(new Configuracao());
    }
}
package com.impact.logistica.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.impact.logistica.model.Configuracao;
import com.impact.logistica.repository.ConfiguracaoRepository;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    // Define a pasta onde as fotos serão salvas (na raiz do seu projeto)
    private static String CAMINHO_IMAGENS = "uploads/";

    @GetMapping
    public String painel(Model model) {
        Configuracao config = configuracaoRepository.findById(1L).orElse(new Configuracao());
        model.addAttribute("configuracao", config);
        
        // Enviamos também o configApp para o Menu Lateral não perder as cores e o nome!
        model.addAttribute("configApp", config);
        return "configuracao/geral";
    }

    // Adicionamos o MultipartFile para receber o arquivo
    @PostMapping("/salvar")
    public String salvar(Configuracao configuracao, @RequestParam(value = "foto", required = false) MultipartFile arquivo) {
        configuracao.setId(1L);

        try {
            // Se o usuário selecionou uma nova foto...
            if (arquivo != null && !arquivo.isEmpty()) {
                // 1. Cria a pasta uploads/ se ela ainda não existir
                File diretorio = new File(CAMINHO_IMAGENS);
                if (!diretorio.exists()) {
                    diretorio.mkdirs();
                }

                // 2. Extrai o nome do ficheiro (ex: logo.png) e guarda na pasta
                String nomeArquivo = arquivo.getOriginalFilename();
                Path caminho = Paths.get(CAMINHO_IMAGENS + nomeArquivo);
                Files.write(caminho, arquivo.getBytes());
                
                // 3. Diz à configuração o nome da nova foto
                configuracao.setNomeLogo(nomeArquivo);
            } else {
                // Se NÃO enviou foto nova, vamos manter a que já estava na base de dados
                Configuracao configAntiga = configuracaoRepository.findById(1L).orElse(null);
                if (configAntiga != null) {
                    configuracao.setNomeLogo(configAntiga.getNomeLogo());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuracaoRepository.save(configuracao);
        return "redirect:/configuracoes";
    }
}
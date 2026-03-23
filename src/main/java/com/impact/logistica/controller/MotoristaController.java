package com.impact.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.impact.logistica.model.Motorista;
import com.impact.logistica.repository.MotoristaRepository;

@Controller
@RequestMapping("/motoristas") 
public class MotoristaController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("motorista", new Motorista());
        return "motorista/cadastro"; // <-- Singular
    }

    @PostMapping("/salvar")
    public String salvar(Motorista motorista) {
        motoristaRepository.save(motorista);
        return "redirect:/motoristas";
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("motoristas", motoristaRepository.findAll());
        return "motorista/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Motorista motorista = motoristaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado: " + id));
        model.addAttribute("motorista", motorista);
        
        // CORRIGIDO AQUI: De motoristas/cadastro para motorista/cadastro
        return "motorista/cadastro"; 
    }
}
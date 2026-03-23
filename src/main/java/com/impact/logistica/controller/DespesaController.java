package com.impact.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.impact.logistica.model.Despesa;
import com.impact.logistica.repository.DespesaRepository;
import com.impact.logistica.repository.FreteRepository;

@Controller
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private FreteRepository freteRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("despesas", despesaRepository.findAll());
        return "despesa/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("despesa", new Despesa());
        // 👇 Busca todas as viagens para aparecerem na caixinha de seleção 👇
        model.addAttribute("listaFretes", freteRepository.findAll());
        return "despesa/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Despesa despesa) {
        despesaRepository.save(despesa);
        return "redirect:/despesas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("despesa", despesaRepository.findById(id).orElse(null));
        model.addAttribute("listaFretes", freteRepository.findAll());
        return "despesa/cadastro";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        despesaRepository.deleteById(id);
        return "redirect:/despesas";
    }
}
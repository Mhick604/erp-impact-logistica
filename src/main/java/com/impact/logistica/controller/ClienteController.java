package com.impact.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.impact.logistica.model.Cliente;
import com.impact.logistica.repository.ClienteRepository;
import com.impact.logistica.repository.ConfiguracaoRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("configApp", configuracaoRepository.findById(1L).orElse(null));
        return "cliente/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("configApp", configuracaoRepository.findById(1L).orElse(null));
        return "cliente/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteRepository.findById(id).orElse(null));
        model.addAttribute("configApp", configuracaoRepository.findById(1L).orElse(null));
        return "cliente/cadastro";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
}
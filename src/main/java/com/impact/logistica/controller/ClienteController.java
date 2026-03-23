package com.impact.logistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.impact.logistica.model.Cliente;
import com.impact.logistica.repository.ClienteRepository;
import com.impact.logistica.repository.ConfiguracaoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
    try {
        // Tenta excluir o cliente
        clienteRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Cliente excluído com sucesso!");
    } catch (DataIntegrityViolationException e) {
        // Se o banco bloquear por causa de um Frete, envia esta mensagem de erro:
        attributes.addFlashAttribute("erro", "Atenção: Este cliente possui fretes (viagens) registrados e não pode ser apagado do sistema.");
    }
    return "redirect:/clientes"; // Garanta que esta é a rota certa para voltar à lista
    }
}

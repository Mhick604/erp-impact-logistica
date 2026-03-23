package com.impact.logistica.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.impact.logistica.model.Veiculo;
import com.impact.logistica.repository.VeiculoRepository;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll()); 
        
        // A MÁGICA DO ALERTA
        LocalDate hoje = LocalDate.now();
        List<Veiculo> veiculosEmAlerta = veiculoRepository.buscarVeiculosPrecisandoRevisao(hoje);
        model.addAttribute("alertasManutencao", veiculosEmAlerta);
        
        return "veiculo/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        return "veiculo/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
        return "redirect:/veiculos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("veiculo", veiculoRepository.findById(id).orElse(null));
        return "veiculo/cadastro";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        veiculoRepository.deleteById(id);
        return "redirect:/veiculos";
    }
    
    @PostMapping("/atualizar-km-rapido")
    public String atualizarKmRapido(@RequestParam("veiculoId") Long veiculoId,
                                    @RequestParam("quilometragemAtual") Double quilometragemAtual,
                                    org.springframework.web.servlet.mvc.support.RedirectAttributes attributes) {
        
        // 1. Busca o veículo no banco de dados
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElse(null);

        if (veiculo != null) {
            // 2. Atualiza a KM
            veiculo.setQuilometragemAtual(quilometragemAtual);
            
            // 3. INTELIGÊNCIA DE MANUTENÇÃO: Verifica se passou do limite
            boolean precisaRevisao = false;

            // Passou do limite de KM?
            if (veiculo.getKmProximaRevisao() != null && quilometragemAtual >= veiculo.getKmProximaRevisao()) {
                precisaRevisao = true;
            }

            // Passou da Data de Revisão?
            if (veiculo.getDataProximaRevisao() != null && 
               (java.time.LocalDate.now().isAfter(veiculo.getDataProximaRevisao()) || java.time.LocalDate.now().isEqual(veiculo.getDataProximaRevisao()))) {
                precisaRevisao = true;
            }

            // 4. Salva no banco de dados
            veiculoRepository.save(veiculo);

            // 5. Devolve a mensagem correta para o Dashboard
            if (precisaRevisao) {
                attributes.addFlashAttribute("mensagemAviso", "KM atualizada! ATENÇÃO: O veículo " + veiculo.getPlaca() + " atingiu o limite para revisão na oficina!");
            } else {
                attributes.addFlashAttribute("mensagemSucesso", "KM do veículo " + veiculo.getPlaca() + " atualizada com sucesso!");
            }

        } else {
            attributes.addFlashAttribute("mensagemErro", "Erro: Veículo não encontrado!");
        }

        // 6. Volta para o painel inicial instantaneamente
        return "redirect:/";
    }
}
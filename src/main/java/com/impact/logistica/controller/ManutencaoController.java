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

import com.impact.logistica.model.Despesa; // IMPORT NOVO
import com.impact.logistica.model.Manutencao;
import com.impact.logistica.model.Veiculo;
import com.impact.logistica.repository.DespesaRepository; // IMPORT NOVO
import com.impact.logistica.repository.ManutencaoRepository;
import com.impact.logistica.repository.VeiculoRepository;

@Controller
@RequestMapping("/manutencoes")
public class ManutencaoController {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;
    
    @Autowired
    private DespesaRepository despesaRepository; // INJEÇÃO DA DESPESA!

    // ========================================================================
    // 1. TELA DE HISTÓRICO DA OFICINA
    // ========================================================================
    @GetMapping("/veiculo/{veiculoId}")
    public String historico(@PathVariable Long veiculoId, Model model) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow();
        List<Manutencao> historico = manutencaoRepository.buscarPorVeiculo(veiculoId);
        
        model.addAttribute("veiculo", veiculo);
        model.addAttribute("manutencoes", historico);
        return "manutencao/historico";
    }

    // ========================================================================
    // 2. TELA PARA CADASTRAR NOVA MANUTENÇÃO
    // ========================================================================
    @GetMapping("/novo/{veiculoId}")
    public String novaManutencao(@PathVariable Long veiculoId, Model model) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow();
        
        Manutencao manutencao = new Manutencao();
        manutencao.setVeiculo(veiculo);
        manutencao.setDataManutencao(LocalDate.now());
        manutencao.setQuilometragem(veiculo.getQuilometragemAtual()); 
        
        model.addAttribute("manutencao", manutencao);
        model.addAttribute("veiculo", veiculo);
        return "manutencao/cadastro";
    }

    // ========================================================================
    // 3. SALVAR E APLICAR INTELIGÊNCIA DE NEGÓCIO + FINANCEIRO
    // ========================================================================
    @PostMapping("/salvar")
    public String salvar(Manutencao manutencao) {
        // Salva a nota da oficina
        manutencaoRepository.save(manutencao);
        Veiculo veiculo = veiculoRepository.findById(manutencao.getVeiculo().getId()).orElseThrow();
        
        if ("Concluída".equals(manutencao.getStatus())) {
            
            // A) Atualiza o KM do caminhão
            if(manutencao.getQuilometragem() != null && 
              (veiculo.getQuilometragemAtual() == null || manutencao.getQuilometragem() > veiculo.getQuilometragemAtual())) {
                veiculo.setQuilometragemAtual(manutencao.getQuilometragem());
            }
            
            // B) Recalcula o Alarme
            if ("Preventiva".equals(manutencao.getTipo()) || manutencao.getServico().toLowerCase().contains("óleo")) {
                 if(veiculo.getQuilometragemAtual() != null) {
                     veiculo.setKmProximaRevisao(veiculo.getQuilometragemAtual() + 10000); 
                 }
                 veiculo.setDataProximaRevisao(LocalDate.now().plusMonths(6));
            }
            veiculoRepository.save(veiculo);
            
            // C) O GATILHO FINANCEIRO MÁGICO 💸
            if(manutencao.getValorTotal() != null && manutencao.getValorTotal() > 0) {
                Despesa despesa = new Despesa();
                despesa.setTipo("Manutenção");
                despesa.setValor(manutencao.getValorTotal());
                despesa.setData(manutencao.getDataManutencao());
                
                // Grava de qual caminhão foi essa despesa
                despesa.setObservacao("Oficina: " + manutencao.getOficina() + " | Serviço: " + manutencao.getServico() + " | Veículo: " + veiculo.getPlaca());
                
                despesaRepository.save(despesa);
            }
        }
        return "redirect:/manutencoes/veiculo/" + veiculo.getId();
    }

    // ========================================================================
    // 4. EXCLUIR MANUTENÇÃO
    // ========================================================================
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow();
        Long idCaminhao = manutencao.getVeiculo().getId(); 
        manutencaoRepository.deleteById(id);
        return "redirect:/manutencoes/veiculo/" + idCaminhao;
    }
}
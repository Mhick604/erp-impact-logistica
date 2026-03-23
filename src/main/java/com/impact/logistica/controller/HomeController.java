package com.impact.logistica.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.impact.logistica.model.Motorista;
import com.impact.logistica.model.Veiculo;
import com.impact.logistica.model.Frete; // IMPORTANTE: Faltava importar o Frete!
import com.impact.logistica.repository.FreteRepository;
import com.impact.logistica.repository.MotoristaRepository;
import com.impact.logistica.repository.VeiculoRepository;
import com.impact.logistica.repository.ConfiguracaoRepository;
import com.impact.logistica.service.HomeService; // IMPORTANTE: Trazendo o serviço

@Controller
public class HomeController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private HomeService homeService; // INJETANDO O CÉREBRO FINANCEIRO

    @GetMapping("/")
    public String index(Model model) {
        // 1. Contadores Operacionais
        model.addAttribute("totalMotoristas", motoristaRepository.count());
        model.addAttribute("totalVeiculos", veiculoRepository.count());
        model.addAttribute("totalViagens", freteRepository.count());
        
        // 2. Inteligência Financeira (AGORA USANDO O SERVICE CORRETAMENTE)
        // O Service já filtra os cancelados e previne retornos nulos (NullPointerException)
        Double faturamento = homeService.calcularFaturamentoTotal();
        Double despesas = homeService.calcularDespesasTotais();
        Double lucroLiquido = faturamento - despesas;

        model.addAttribute("faturamento", faturamento);
        model.addAttribute("despesas", despesas);
        model.addAttribute("lucroLiquido", lucroLiquido);

        // 3. Inteligência de Alertas
        LocalDate limiteAviso = LocalDate.now().plusDays(15);
        
        List<Motorista> alertasCnh = motoristaRepository.findAll().stream()
            .filter(m -> m.getDataVencimentoCnh() != null && m.getDataVencimentoCnh().isBefore(limiteAviso))
            .collect(Collectors.toList());
        
        List<Veiculo> alertasVeiculos = veiculoRepository.findAll().stream()
            .filter(v -> "Em Manutenção".equalsIgnoreCase(v.getStatus()))
            .collect(Collectors.toList());

        model.addAttribute("alertasCnh", alertasCnh);
        model.addAttribute("alertasVeiculos", alertasVeiculos);
        
        // 4. Configuração do Tema
        model.addAttribute("configApp", configuracaoRepository.findById(1L).orElse(null));
        
        // ====================================================================
        // 5. VIAGENS EM TEMPO REAL (MÁGICA DO DASHBOARD)
        // ====================================================================
        List<Frete> viagensAtivas = freteRepository.findAll().stream()
            .filter(f -> "Em Viagem".equalsIgnoreCase(f.getStatus()) 
                      || "Pendente".equalsIgnoreCase(f.getStatus()) 
                      || "AGUARDANDO".equalsIgnoreCase(f.getStatus()) 
                      || "EM_TRANSITO".equalsIgnoreCase(f.getStatus()))
            .collect(Collectors.toList());
        
        model.addAttribute("viagensAtivas", viagensAtivas);
        
        model.addAttribute("veiculos", veiculoRepository.findAll());
        
        return "index";
    }
}
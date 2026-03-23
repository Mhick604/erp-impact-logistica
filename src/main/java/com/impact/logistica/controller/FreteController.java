package com.impact.logistica.controller;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.impact.logistica.model.Configuracao;
import com.impact.logistica.model.Frete;
import com.impact.logistica.repository.ClienteRepository;
import com.impact.logistica.repository.FreteRepository;
import com.impact.logistica.repository.MotoristaRepository;
import com.impact.logistica.repository.VeiculoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteRepository freteRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private com.impact.logistica.service.PdfService pdfService;
    @Autowired
    private com.impact.logistica.repository.ConfiguracaoRepository configuracaoRepository;
    
    @GetMapping("/novo")
    public String novo(Model model) {
        Frete frete = new Frete();
        
        // Buscamos o preço padrão da configuração e colocamos no objeto
        Configuracao config = configuracaoRepository.findById(1L).orElse(null);
        if (config != null && config.getPrecoCombustivel() != null) {
            frete.setPrecoCombustivelNaViagem(config.getPrecoCombustivel());
        } else {
            frete.setPrecoCombustivelNaViagem(5.80); // Se não tiver config, vai o valor reserva
        }
        
        model.addAttribute("frete", frete);
        model.addAttribute("listaClientes", clienteRepository.findAll());
        model.addAttribute("listaMotoristas", motoristaRepository.findAll());
        model.addAttribute("listaVeiculos", veiculoRepository.findAll());
        return "frete/nova-viagem"; 
    }
 
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fretes", freteRepository.findAll());
        return "frete/lista"; 
    }

    @PostMapping("/salvar")
    public String salvar(Frete frete, RedirectAttributes attrs) {
        
        // 1. O preço do diesel agora vem da TELA (que o usuário pode ter alterado)
        double precoDiesel = (frete.getPrecoCombustivelNaViagem() != null) 
                             ? frete.getPrecoCombustivelNaViagem() 
                             : 5.80; // Valor de segurança caso venha vazio

        // 2. Calcula o custo de combustível
        if (frete.getVeiculo() != null && frete.getKmEstimado() != null) {
            Double kmPorLitro = frete.getVeiculo().getKmPorLitro();
            
            if (kmPorLitro != null && kmPorLitro > 0) {
                // A matemática do consumo
                double custoCombustivel = (frete.getKmEstimado() / kmPorLitro) * precoDiesel;
                
                // Pega outros gastos da OS (pedágio, chapa, etc), se houver
                double outros = (frete.getOutrosGastos() != null) ? frete.getOutrosGastos() : 0.0;
                
                // A MÁGICA QUE TIRA O ALERTA: Salva o cálculo dentro da própria OS!
                frete.setCustoTotalEstimado(custoCombustivel + outros);
            }
        }

        // 3. Salva tudo no banco de dados (agora com o custo calculado!)
        freteRepository.save(frete);
        attrs.addFlashAttribute("mensagemSucesso", "Viagem registrada com sucesso!");
        return "redirect:/fretes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        // Busca o frete pelo ID. Se não encontrar, retorna nulo
        Frete frete = freteRepository.findById(id).orElse(null);
          
        model.addAttribute("frete", frete);
        
       
        model.addAttribute("listaClientes", clienteRepository.findAll());
        model.addAttribute("listaMotoristas", motoristaRepository.findAll());
        model.addAttribute("listaVeiculos", veiculoRepository.findAll()); 
        return "frete/nova-viagem"; 
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id) {
        freteRepository.deleteById(id);
        return "redirect:/fretes";
    }
    
 // ROTA PARA GERAR O DOWNLOAD DO PDF REAL DA ORDEM DE SERVIÇO
    @GetMapping("/imprimir/{id}")
    public org.springframework.http.ResponseEntity<byte[]> imprimirPdf(@PathVariable("id") Long id) {
        try {
           
            Frete frete = freteRepository.findById(id).orElse(null);
            
            if (frete == null) {
                return org.springframework.http.ResponseEntity.notFound().build();
            }

            java.util.Map<String, Object> variaveis = new java.util.HashMap<>();
            variaveis.put("frete", frete);
            
            com.impact.logistica.model.Configuracao config = configuracaoRepository.findById(1L).orElse(null);
            variaveis.put("configApp", config);

            
            byte[] pdfBytes = pdfService.gerarPdfDeHtml("frete/recibo-pdf", variaveis);

            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
            
          
            headers.setContentDispositionFormData("attachment", "Ordem_Servico_" + frete.getId() + ".pdf");

    
            return org.springframework.http.ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return org.springframework.http.ResponseEntity.internalServerError().build();
        }
    }
    // ==============================================================
    // ROTA PARA EXPORTAR OS DADOS PARA O CONTADOR (EXCEL / CSV)
    // ==============================================================
    @GetMapping("/exportar")
    public void exportarParaExcel(HttpServletResponse response) throws Exception {
        
        // 1. Configura o navegador para baixar um ficheiro CSV
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio_contabilidade.csv");

        PrintWriter writer = response.getWriter();
        
        // 2. MÁGICA: Adiciona o "BOM" UTF-8 para o Excel reconhecer os acentos (ç, ã, etc)
        writer.write('\ufeff');

        // 3. Escreve o Cabeçalho do Excel (Usamos Ponto e Vírgula ';' para o Excel no Brasil separar as colunas automaticamente)
        writer.println("OS;Data de Saída;Status;Origem;Destino;Placa Veículo;Motorista;Material Transportado;Valor do Frete (R$)");

        // 4. Busca todas as viagens no banco de dados
        java.util.List<Frete> fretes = freteRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 5. Preenche linha por linha no Excel
        for (Frete f : fretes) {
            String os = String.valueOf(f.getId());
            String data = f.getDataSaida() != null ? f.getDataSaida().format(formatter) : "Não informada";
            String status = f.getStatus() != null ? f.getStatus() : "";
            String origem = f.getOrigem() != null ? f.getOrigem() : "";
            String destino = f.getDestino() != null ? f.getDestino() : "";
            String placa = f.getVeiculo() != null ? f.getVeiculo().getPlaca() : "Sem Veículo";
            String motorista = f.getMotorista() != null ? f.getMotorista().getNome() : "Sem Motorista";
            String material = f.getMaterialTransportado() != null ? f.getMaterialTransportado() : "";
            
            // Formata o valor com vírgula para o contador conseguir somar no Excel
            String valor = f.getValorCombinado() != null ? String.format("%.2f", f.getValorCombinado()).replace(".", ",") : "0,00";

            // Junta tudo com ponto e vírgula e salta para a próxima linha
            writer.println(os + ";" + data + ";" + status + ";" + origem + ";" + destino + ";" + placa + ";" + motorista + ";" + material + ";" + valor);
        }
        
        writer.flush();
        writer.close();
    }        
                 
}
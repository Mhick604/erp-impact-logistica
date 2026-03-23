package com.impact.logistica.controller;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.impact.logistica.model.Despesa;
import com.impact.logistica.model.Frete;
import com.impact.logistica.repository.DespesaRepository;
import com.impact.logistica.repository.FreteRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RelatorioController {

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @GetMapping("/relatorio/fechamento-mes")
    public void exportarFechamentoMes(HttpServletResponse response) throws Exception {
        
        // 1. Configura o arquivo Excel (CSV formatado)
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=fechamento_mes_atual.csv");
        PrintWriter writer = response.getWriter();
        writer.write('\ufeff'); // Mágica para o Excel ler acentos (ç, ã) perfeitamente!

        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 2. Busca RECEITAS (Fretes do mês atual que NÃO foram cancelados)
        List<Frete> fretesDoMes = freteRepository.findAll().stream()
            .filter(f -> f.getDataSaida() != null && 
                         f.getDataSaida().getMonthValue() == mesAtual && 
                         f.getDataSaida().getYear() == anoAtual &&
                         !"Cancelado".equalsIgnoreCase(f.getStatus()))
            .collect(Collectors.toList());

        double totalReceitas = fretesDoMes.stream()
            .mapToDouble(f -> f.getValorCombinado() != null ? f.getValorCombinado() : 0.0)
            .sum();

        // 3. Busca DESPESAS (Custos do mês atual)
        List<Despesa> despesasDoMes = despesaRepository.findAll().stream()
            .filter(d -> d.getData() != null && 
                         d.getData().getMonthValue() == mesAtual && 
                         d.getData().getYear() == anoAtual)
            .collect(Collectors.toList());

        double totalDespesas = despesasDoMes.stream()
            .mapToDouble(d -> d.getValor() != null ? d.getValor() : 0.0)
            .sum();

        double lucroLiquido = totalReceitas - totalDespesas;

        // ==========================================
        // BLOCO 1: RESUMO FINANCEIRO (Topo do Excel)
        // ==========================================
        writer.println("FECHAMENTO DO MÊS;" + mesAtual + "/" + anoAtual);
        writer.println(); // Linha em branco
        writer.println("RESUMO FINANCEIRO;VALOR (R$)");
        writer.println("(+) Total de Receitas (Fretes);" + String.format("%.2f", totalReceitas).replace(".", ","));
        writer.println("(-) Total de Despesas (Custos);" + String.format("%.2f", totalDespesas).replace(".", ","));
        writer.println("(=) SALDO LÍQUIDO DO MÊS;" + String.format("%.2f", lucroLiquido).replace(".", ","));
        writer.println();
        writer.println();

        // ==========================================
        // BLOCO 2: DETALHAMENTO DAS RECEITAS
        // ==========================================
        writer.println("--- RECEITAS (FRETES REALIZADOS) ---");
        writer.println("OS;Data Saída;Cliente;Rota (Origem -> Destino);Veículo;Valor (R$)");
        for (Frete f : fretesDoMes) {
            String os = String.valueOf(f.getId());
            String data = f.getDataSaida().format(dtf);
            String cliente = f.getCliente() != null ? f.getCliente().getRazaoSocial() : "Não informado";
            String rota = f.getOrigem() + " para " + f.getDestino();
            String veiculo = f.getVeiculo() != null ? f.getVeiculo().getPlaca() : "S/ Veículo";
            String valor = f.getValorCombinado() != null ? String.format("%.2f", f.getValorCombinado()).replace(".", ",") : "0,00";
            
            writer.println(os + ";" + data + ";" + cliente + ";" + rota + ";" + veiculo + ";" + valor);
        }
        writer.println();
        writer.println();

     // ==========================================
        // BLOCO 3: DETALHAMENTO DAS DESPESAS (BLINDADO)
        // ==========================================
        writer.println("--- DESPESAS (CUSTOS OPERACIONAIS) ---");
        writer.println("ID;Data;Categoria;Descrição;Veículo Referência;Valor (R$)");
        
        for (Despesa d : despesasDoMes) {
            String id = String.valueOf(d.getId());
            String data = (d.getData() != null) ? d.getData().format(dtf) : "";
            
            // Usamos o campo 'tipo' ou 'categoria' (o que estiver preenchido)
            String cat = (d.getCategoria() != null && !d.getCategoria().isEmpty()) ? d.getCategoria() : d.getTipo();
            String categoria = (cat != null) ? cat : "Outros";
            
            // Usamos a observação como descrição se a descrição estiver nula
            String desc = (d.getDescricao() != null && !d.getDescricao().isEmpty()) ? d.getDescricao() : d.getObservacao();
            String descricao = (desc != null) ? desc : "";
            
            // --- AQUI ESTAVA O ERRO: Buscamos o veículo através do Frete ---
            String placaVeiculo = "Geral/Escritório";
            if (d.getFrete() != null && d.getFrete().getVeiculo() != null) {
                placaVeiculo = d.getFrete().getVeiculo().getPlaca();
            }
            
            String valor = (d.getValor() != null) ? String.format("%.2f", d.getValor()).replace(".", ",") : "0,00";

            writer.println(id + ";" + data + ";" + categoria + ";" + descricao + ";" + placaVeiculo + ";" + valor);
        }
    }
}
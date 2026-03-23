package com.impact.logistica.service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    // Injeta o motor do Thymeleaf para ler o nosso HTML
    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] gerarPdfDeHtml(String templateName, Map<String, Object> variaveis) throws Exception {
        
        // 1. Coloca as variáveis (dados do Frete, Motorista, etc) no Contexto
        Context context = new Context();
        context.setVariables(variaveis);
        
        // 2. Transforma o ficheiro HTML numa String gigante já com os dados preenchidos
        String html = templateEngine.process(templateName, context);

        // 3. Usa a "Impressora" (Flying Saucer) para ler esse HTML e desenhar o PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        
        // 4. Devolve o ficheiro PDF em formato de bytes para ser baixado
        return outputStream.toByteArray();
    }
}
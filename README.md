# 📦 Impact ERP - Sistema de Gestão Logística

Um sistema ERP (Enterprise Resource Planning) completo, desenvolvido no modelo SaaS (Software as a Service) e White-Label, voltado para a gestão inteligente de transportadoras.

O sistema abandona a ideia de "planilhas e cadernos" e entrega ao dono da transportadora um Centro de Controle com inteligência de negócio, cálculo de lucro líquido em tempo real e gestão proativa de alertas.

## 🚀 Principais Funcionalidades

* **🎨 Efeito Camaleão (White-Label):** O sistema se adapta à identidade visual do cliente. Através das configurações, é possível alterar o Nome da Empresa e a Cor Primária, transformando todo o layout do sistema instantaneamente.
* **📊 Dashboard Financeiro:** Visão geral da saúde financeira da transportadora, calculando automaticamente o Faturamento (Fretes) - Custos (Despesas) para exibir o Lucro Líquido.
* **🚨 Alertas Inteligentes:** Notificações automáticas no painel inicial para:
  * Motoristas com CNH próxima do vencimento (antecedência de 15 dias).
  * Veículos com status "Em Manutenção" (Frota parada).
* **👨‍✈️ Gestão de Motoristas:** Cadastro completo com validação de categorias de CNH e status de disponibilidade.
* **🚛 Gestão de Frota (Veículos):** Controle de caminhões, capacidade de carga (Toneladas) e status operacional.
* **🛣️ Gestão de Fretes (OS):** Emissão de ordens de serviço (Viagens) amarrando cliente, origem, destino, veículo e motorista responsável.
* **💸 Controle de Despesas:** Lançamento de custos de viagem (Diesel, Pedágio, Chapa) atrelados diretamente à OS para cálculo de rentabilidade.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework Backend:** Spring Boot 3.x (Spring Web, Spring Data JPA)
* **Arquitetura:** MVC (Model-View-Controller)
* **Frontend:** HTML5, Thymeleaf e Bootstrap 5
* **Banco de Dados:** * `H2 Database` (Perfil de Testes - Em memória)
  * `PostgreSQL` (Perfil de Desenvolvimento/Produção)
* **Gerenciador de Dependências:** Maven

## 👨‍💻 Desenvolvedor
Desenvolvido por **Mateus Henrique** - Estudante de Engenharia de Software e Desenvolvedor Full-Stack.
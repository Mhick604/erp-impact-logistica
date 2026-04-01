# 🚚 Impact Logística - ERP de Gestão de Frotas e Fretes

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Google Gemini](https://img.shields.io/badge/Google_Gemini_AI-8E75B2?style=for-the-badge&logo=googlebard&logoColor=white)

O **Impact Logística** é um sistema completo de gestão de transportes desenhado para automatizar a operação de transportadoras e gestores de frota. A plataforma une o controle de veículos, finanças e motoristas a recursos avançados de Inteligência Artificial e geração de documentos.

## ✨ Funcionalidades Principais

* 🧠 **Integração com Inteligência Artificial (Google Gemini):** Utilização da API do Gemini para fornecer *insights* logísticos e análises inteligentes diretamente no sistema.
* 📊 **Dashboard Financeiro e Operacional:** Visão consolidada do saldo da empresa (Receitas de Fretes abatendo Despesas e Manutenções), com indicadores de veículos em rota.
* 🚛 **Gestão de Frotas e Manutenções:** Cadastro completo de veículos e rastreamento de histórico de manutenções preventivas e corretivas.
* 📱 **Portal do Motorista:** Interface dedicada para que os motoristas façam login, visualizem suas pautas, iniciem novas viagens e acessem o mapa de rotas.
* 📄 **Geração de Recibos em PDF:** Emissão automática de recibos/comprovantes de frete formatados para impressão ou envio digital.
* 📅 **Agenda Integrada:** Controle de despachos e compromissos operacionais.
* 🛡️ **Segurança e Hierarquia (Spring Security):** Rotas protegidas com divisão clara de acessos entre Gestores (`ROLE_ADMIN`) e equipe de campo (`ROLE_MOTORISTA`).

## 🛠️ Arquitetura e Tecnologias

**Backend:**
* **Java 17**
* **Spring Boot 3.4.3** (Web, Data JPA, Security, Validation)
* **Integração REST:** Google Gemini API (`GeminiService`)
* **Bancos de Dados:** H2 Database (Dev) e PostgreSQL (Prod)
* **Maven** (Gerenciamento de dependências)

**Frontend:**
* **Thymeleaf** (Renderização Server-Side)
* **Bootstrap 5** & **Bootstrap Icons** (UI Responsiva)
* **HTML5 to PDF:** Rotina de renderização de views para impressão de recibos.

**DevOps:**
* **Docker:** Multi-stage build configurado via `Dockerfile`.
* **Spring Profiles:** Separação de propriedades (`dev`, `prod`, `test`).

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
* [JDK 17+](https://adoptium.net/) instalado.
* Obter uma API Key gratuita no [Google AI Studio](https://aistudio.google.com/) para habilitar os recursos do Gemini (Opcional para uso básico).

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SeuUsuario/erp-impact-logistica.git](https://github.com/SeuUsuario/erp-impact-logistica.git)
   cd erp-impact-logistica

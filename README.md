# 🏢 Sistema de Locação

Projeto acadêmico desenvolvido em Java, com foco em Programação Orientada a Objetos e persistência de dados em banco de dados MySQL. O sistema simula a locação de espaços comerciais, como estúdios, galpões, salas comerciais, entre outros.

## 📚 Sobre o Projeto

O **Sistema de Locação** foi criado como parte de um trabalho da faculdade, com o objetivo de aplicar conceitos de modelagem de classes, herança, composição e integração com banco de dados via JDBC. A aplicação é executada via terminal e não possui interface gráfica.

## 🚀 Funcionalidades

- Cadastro de Clientes e Locadores
- Cadastro de Espaços para Locação:
  - Estúdio
  - Galpão Industrial
  - Loja Comercial
  - Sala Comercial
  - Pavilhão de Eventos
- Registro de Locações
- Consultas de dados diretamente no banco
- Persistência em banco de dados relacional (MySQL)

## 🛠️ Tecnologias Utilizadas

- **Java**
- **MySQL**
- **JDBC**
- **NetBeans** (como IDE)

## 🧱 Estrutura do Projeto

O sistema é dividido em:

- **Modelos (Entidades):**
  - `Cliente`, `Locador`, `Locacao`
  - Classe abstrata `Espaco` e suas subclasses:
    - `Estudio`, `GalpaoIndustrial`, `LojaComercial`, `SalaComercial`, `PavilhaoEventos`

- **DAOs (Data Access Objects):**
  - `ClienteDAO`, `LocadorDAO`, `EspacoDAO`, `LocacaoDAO`

- **Banco de Dados:**
  - Conexão realizada pela classe `ConexaoBD.java`

- **Classe Principal:**
  - `SistemaLocacao.java` (simulação e execução dos fluxos principais)

## 🧪 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/wevertonrocha/SistemaLocacao_DB

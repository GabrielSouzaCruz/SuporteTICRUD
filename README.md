# SuporteTICRUD

Projeto de sistema de suporte técnico com operações CRUD, desenvolvido em Java.

## 📋 Visão Geral

Este projeto tem como objetivo desenvolver um sistema simples para gerenciar tickets de suporte (criação, consulta, atualização, exclusão) e/ou demandas de TI, utilizando Java puro (sem frameworks pesados) e banco de dados via scripts SQL.  
Ele foi estruturado para fins de aprendizado / exercício de conceitos básicos de persistência, camadas, DAO, etc.

## 🧩 Funcionalidades

- CRUD completo (Create, Read, Update, Delete) para entidades principais (ex: ticket de suporte, usuário, categoria)  
- Scripts SQL para criação de banco de dados / tabelas (pasta `sql/`)  
- Código-fonte em Java (pasta `src/`) com separação de pacotes (ex: model, dao, service, view)  
- Possível interface simples (console ou GUI leve) ou apenas via terminal  

## 🏗️ Estrutura do Projeto

```

SuporteTICRUD/
├── bin/             ← arquivos compilados/executáveis 
├── lib/             ← bibliotecas externas 
├── sql/             ← scripts SQL: criação do schema, tabelas
├── src/             ← código-fonte em Java
├── .settings/       ← configurações do IDE (Eclipse)
├── .classpath
└── .project

````

## 🚀 Pré-requisitos

Antes de compilar e executar o projeto, verifique:

- Java JDK (versão compatível, por exemplo Java 8 ou superior)  
- Um SGBD (como MySQL, PostgreSQL ou outro) configurado localmente ou remotamente  
- Configuração de conexão no código (ex: URL JDBC, usuário, senha) ajustada conforme seu ambiente  
- Executar o script SQL da pasta `sql/` para criar o banco e tabelas  

## 🔧 Instalação e Execução

1. Clone este repositório:  
   ```bash
   git clone https://github.com/GabrielSouzaCruz/SuporteTICRUD.git
    ````

2. Navegue até o diretório do projeto:

   ```bash
   cd SuporteTICRUD
   ```
3. Importe o projeto no seu IDE preferido (Eclipse, IntelliJ, NetBeans) ou compile via terminal:

   ```bash
   javac -d bin src/**/*.java
   ```
4. Ajuste arquivo de configuração de conexão (ex: `src/config/DatabaseConfig.java` ou similar) com seu host, porta, usuário, senha.
5. Execute o programa principal (por exemplo):

   ```bash
   java -cp bin com.seuprojeto.Main
   ```
6. Utilize a interface ou console para inserir, listar, editar e excluir registros.

## 👨‍💻 Código-fonte

O código-fonte está organizado em pacotes tais como:

* `model` — classes de entidade (ex: Ticket, Usuario, Categoria)
* `dao` — classes responsáveis pela persistência (ex: TicketDao, UsuarioDao)
* `service` — lógica de negócio (ex: TicketService)
* `view` ou `ui` — interface (console ou GUI)
* `config` — configuração da aplicação e do banco

## 📌 Observações e Melhorias Futuras

Possíveis extensões para este projeto incluem:

* Adicionar interface web (ex: Servlet/JSP ou Spring Boot)
* Utilizar ORM (ex: Hibernate) para facilitar persistência
* Implementar autenticação/autorização (usuários, papéis)
* Adicionar logs, tratamento de exceções mais robusto
* Melhorar design da interface (Swing, JavaFX ou Web)
* Incluir testes automatizados (JUnit)
* Deploy em ambiente servidor com Docker

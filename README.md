#  Sistema de Gerenciamento Financeiro

Um sistema desktop moderno para controle financeiro pessoal e empresarial, focado em registrar entradas (receitas) e saídas (despesas) de forma intuitiva e visual. 

Este projeto foi desenvolvido com foco na aplicação de conceitos de Programação Orientada a Objetos (POO), padrão de projeto DAO e integração com banco de dados relacional.

---

##  Funcionalidades Atuais

* **Autenticação:** Tela de login inicial para controle de acesso.
* **Dashboard Visual:** Gráfico de pizza (PieChart) interativo e dinâmico que atualiza em tempo real mostrando a proporção entre receitas e despesas.
* **Registro de Transações (Create):** Formulário ágil para adicionar novas receitas e despesas, calculando a data automaticamente.
* **Histórico e Listagem (Read):** Tabela (TableView) que exibe todas as movimentações financeiras diretamente do banco de dados.
* **Relatórios:** Tela de consolidação financeira exibindo saldos totais (Receitas - Despesas).

---

##  Tecnologias Utilizadas

* **Linguagem:** Java (JDK 17+)
* **Interface Gráfica:** JavaFX 13+
* **Banco de Dados:** PostgreSQL 
* **Gerenciador de Dependências:** Maven
* **IDE:** VS Code
* **Arquitetura:** Padrão MVC (Model-View-Controller) simplificado com Data Access Object (DAO) para persistência.

---

##  Estrutura do Projeto

A organização do código fonte segue as boas práticas de separação de responsabilidades:

* `com.shottz.model`: Classes de modelo e regras de negócio (`Transacao`, `Receita`, `Despesa`).
* `com.shottz.dao`: Classes responsáveis pela comunicação direta com o banco de dados (`TransacaoDAO`).
* `com.shottz.db`: Configuração e estabelecimento da conexão JDBC (`ConexaoDB`).
* `com.shottz`: Classe principal com a interface gráfica JavaFX (`SistemaApp`).

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
Certifique-se de ter instalado em sua máquina:
* Java Development Kit (JDK) versão 17 ou superior.
* Apache Maven.
* PostgreSQL (Local ou em Nuvem).

### 1. Configuração do Banco de Dados
Abra o seu cliente PostgreSQL (pgAdmin, DBeaver, psql) e execute o script abaixo para criar a tabela necessária:

```sql
CREATE TABLE transacoes (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    data_transacao DATE NOT NULL
);

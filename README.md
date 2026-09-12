# EcoPower Monitor

Aplicação de acompanhamento de consumo de energia elétrica residencial, desenvolvida como Atividade de Estudo Programada (AEP) do curso de Engenharia de Software da Unicesumar.

O consumidor só descobre quanto gastou de energia quando a fatura chega, no fim do mês, quando já não dá para corrigir nada. O EcoPower Monitor registra o consumo de cada aparelho da casa, deixa o usuário definir uma meta mensal e avisa quando o consumo se aproxima ou passa dessa meta — enquanto ainda dá tempo de agir.

O projeto está alinhado às ODS **7 (Energia Limpa e Acessível)** e **12 (Consumo e Produção Responsáveis)** da ONU.

---

## Integrantes

- Victor Souza
- Fernando Martinez
- Samuel Hungaro

---

## Tecnologias utilizadas

| Camada | Tecnologia |
|---|---|
| Linguagem | Java (JDK 17 ou superior) |
| Banco de dados | PostgreSQL (local) |
| Acesso a dados | JDBC + padrão DAO |
| Gerência de dependências | Maven |
| Ferramenta de banco | DBeaver |
| Interface | Console (linha de comando) |

A coleta de leituras roda em segundo plano usando **threads** do Java, com acesso concorrente ao banco controlado por exclusão mútua (`synchronized`).

---

## Requisitos Funcionais

| Código | Descrição |
|---|---|
| RF01 | Cadastrar, editar e remover aparelhos elétricos da residência, guardando nome, cômodo e potência estimada (W). |
| RF02 | Registrar em segundo plano as medições de consumo (kWh) de cada aparelho, montando o histórico ao longo do tempo. |
| RF03 | Definir uma meta mensal de consumo (kWh ou R$) e avisar quando o consumo se aproximar do limite ou passar dele. |
| RF04 | Estimar o valor da conta em reais a partir do consumo acumulado e da tarifa cadastrada. |
| RF05 | Gerar relatórios de consumo por aparelho e por período, apontando os maiores consumidores e sugerindo economia. |

---

## Cronograma da 1ª Entrega

| Semana | Período | Atividade | Responsável |
|---|---|---|---|
| Semana 1 | 04/08 a 10/08 | Levantamento do problema, escopo e requisitos | Victor Souza |
| Semana 2 | 11/08 a 15/08 | Modelagem orientada a objetos e diagrama de classes | Fernando Martinez |
| Semana 3 | 17/08 a 21/08 | Modelagem do banco de dados (DER) e script SQL | Samuel Hungaro |
| Semana 4 | 23/08 a 28/08 | Configuração do ambiente e organização do repositório | Victor Souza |
| Semana 5 | 01/09 a 11/09 | Revisão final e consolidação da 1ª entrega | Victor Souza |

---

## Estrutura do repositório

```
ecopower/
├── README.md
├── docs/         # documentação: diagrama de classes (UML) e DER
├── database/     # script SQL de criação das tabelas
└── src/          # código-fonte Java (2ª entrega)
```

---

## Como executar

> A entrega da 1ª fase é documental. As instruções abaixo já ficam prontas para a 2ª entrega, quando o código estará em `/src`.

**Pré-requisitos**
1. **JDK 17+** instalado. Confira com `java -version`.
2. **PostgreSQL** instalado e em execução na máquina.
3. **Maven** instalado (`mvn -version`), ou uso do wrapper do projeto.

**Preparar o banco**
1. Crie um banco chamado `ecopower` no PostgreSQL.
2. Rode o script de criação das tabelas:
   ```
   psql -U seu_usuario -d ecopower -f database/schema.sql
   ```
   (ou execute o `database/schema.sql` pelo DBeaver).

**Configurar a conexão**
- Ajuste usuário, senha e nome do banco na classe de conexão em `src/` (os valores padrão apontam para `localhost:5432/ecopower`).

**Rodar a aplicação** (a partir da 2ª entrega)
```
mvn compile
mvn exec:java
```

---

## Objetivos de Desenvolvimento Sustentável (ODS)

- **ODS 7 — Energia Limpa e Acessível:** incentiva o uso consciente da energia ao dar visibilidade ao consumo.
- **ODS 12 — Consumo e Produção Responsáveis:** reduz o desperdício doméstico e apoia o planejamento do consumo.

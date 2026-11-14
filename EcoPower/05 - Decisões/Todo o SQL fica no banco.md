---
tipo: decisao
data: 2026-11
status: aceita
---

# Todo o SQL fica no banco

## Decisão
O Java não tem nenhum `SELECT`/`INSERT`/`UPDATE`/`DELETE`. Os DAOs só chamam procedures e funções
pelo nome ([Funções e Procedures](../04%20-%20Banco%20de%20Dados/Fun%C3%A7%C3%B5es%20e%20Procedures.md)).

## Por quê
- Regras críticas (consumo, alertas) valem mesmo para inserções feitas direto no banco.
- Demonstra PL/SQL, triggers e views para a disciplina de banco.
- Java fica simples: [Camadas (MVC + DAO)](../02%20-%20Arquitetura/Camadas%20%28MVC%20%2B%20DAO%29.md).

## Custo
Algumas regras ficam duplicadas (Java e banco) e podem divergir — ex.: [EstimativaCusto](../03%20-%20Dom%C3%ADnio/EstimativaCusto.md).

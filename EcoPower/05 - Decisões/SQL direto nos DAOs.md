---
tipo: decisao
data: 2026-11
status: aceita
---

# SQL direto nos DAOs

## Decisão
Cada DAO escreve o próprio SQL (`SELECT`/`INSERT`/`UPDATE`/`DELETE`) com `PreparedStatement`.
Não há procedures, funções, triggers nem views no banco — só tabelas, chaves e índices.

## Por quê
- Mais simples de entender e depurar: o SQL está ao lado do código que o usa.
- Parâmetros `?` evitam SQL injection.
- O PostgreSQL resolve o essencial sozinho: `SERIAL` para ids, `RETURNING id` no `INSERT`, `ON DELETE CASCADE`.
- Detalhes em [Camadas (MVC + DAO)](../02%20-%20Arquitetura/Camadas%20%28MVC%20%2B%20DAO%29.md).

## Custo
As regras (cálculo de consumo, verificação de meta) só valem pelo app — um `INSERT` feito direto no banco não passa por elas.

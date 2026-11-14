---
tipo: banco
status: atualizado
---

# Triggers

Arquivo: `database/oracle/02_triggers_views.sql`

| Trigger | Quando | Nível | O que faz |
|---|---|---|---|
| `trg_leituras_calcula_consumo` | BEFORE INSERT/UPDATE em [leituras](Tabelas/leituras.md) | linha | `consumo_kwh = potencia / 1000 × horas_uso` (lê [dispositivos](Tabelas/dispositivos.md)) |
| `trg_leituras_verifica_meta` | AFTER INSERT/UPDATE/DELETE em [leituras](Tabelas/leituras.md) | comando | chama `sp_verificar_meta` p/ cada meta do mês |
| `trg_metas_verifica` | AFTER INSERT/UPDATE em [metas](Tabelas/metas.md) | comando | idem — avisa na hora se a nova meta já foi passada |

## Por que nível de comando?
`sp_verificar_meta` soma a própria tabela `leituras`. Num trigger `FOR EACH ROW` isso causa
**ORA-04091 (tabela mutante)**. Sem `FOR EACH ROW`, o trigger roda uma vez após o comando e pode ler a tabela.

Resultado: linhas em [alertas](Tabelas/alertas.md).

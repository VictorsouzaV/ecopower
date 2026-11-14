---
tipo: tabela
classe: Meta
status: atualizado
---

# metas

Meta mensal — **uma por usuário por mês**. Classe Java: [Meta](../../03%20-%20Dom%C3%ADnio/Meta.md).

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | NUMBER identity | PK |
| `usuario_id` | NUMBER | FK → [usuarios](usuarios.md) (cascade) |
| `mes_ano` | DATE | 1º dia do mês; UNIQUE com `usuario_id` |
| `valor_mensal` | NUMBER(10,2) | > 0 |
| `unidade` | VARCHAR2(5) | `kwh` ou `reais` |
| `pct_alerta` | NUMBER(5,2) | 0–100, padrão 80 |

Gravada por `pr_salvar_meta` (`MERGE`). Trigger `trg_metas_verifica` → [alertas](alertas.md).

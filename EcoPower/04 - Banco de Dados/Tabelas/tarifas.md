---
tipo: tabela
classe: TarifaEnergia
status: atualizado
---

# tarifas

Histórico de tarifas do usuário; a vigente é a de maior `vigente_desde`. Classe Java: [TarifaEnergia](../../03%20-%20Dom%C3%ADnio/TarifaEnergia.md).

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | NUMBER identity | PK |
| `usuario_id` | NUMBER | FK → [usuarios](usuarios.md) (cascade) |
| `tarifa_kwh` | NUMBER(10,5) | > 0 |
| `bandeira_kwh` | NUMBER(10,5) | ≥ 0 |
| `pct_encargos`, `pct_icms`, `pct_pis`, `pct_cofins` | NUMBER(6,5) | entre 0 e 1 |
| `iluminacao_publica` | NUMBER(10,2) | ≥ 0 |
| `minimo_kwh` | NUMBER(5) | > 0 |
| `vigente_desde` | TIMESTAMP | padrão `SYSTIMESTAMP` |

Usada por `fn_estimar_conta` ([Funções e Procedures](../Fun%C3%A7%C3%B5es%20e%20Procedures.md)).

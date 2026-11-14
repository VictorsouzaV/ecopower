---
tipo: tabela
classe: Leitura
status: atualizado
---

# leituras

Medições de consumo. Classe Java: [Leitura](../../03%20-%20Dom%C3%ADnio/Leitura.md).

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | NUMBER identity | PK |
| `dispositivo_id` | NUMBER | FK → [dispositivos](dispositivos.md) (cascade) |
| `data_hora` | TIMESTAMP | padrão `SYSTIMESTAMP` |
| `horas_uso` | NUMBER(6,2) | > 0 e ≤ 744 (horas de um mês) |
| `consumo_kwh` | NUMBER(12,4) | ≥ 0 — **calculado por trigger** |

Índices: `idx_leituras_dispositivo (dispositivo_id, data_hora)`, `idx_leituras_data (data_hora)`.

[Triggers](../Triggers.md): `trg_leituras_calcula_consumo` (antes), `trg_leituras_verifica_meta` (depois).

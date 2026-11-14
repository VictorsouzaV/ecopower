---
tipo: tabela
classe: Leitura
status: atualizado
---

# leituras

Medições de consumo. Classe Java: [Leitura](../../03%20-%20Dom%C3%ADnio/Leitura.md). DAO: `LeituraDAO`.

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | SERIAL | PK |
| `dispositivo_id` | INT | NOT NULL, FK → [dispositivos](dispositivos.md) (cascade) |
| `data_hora` | TIMESTAMP | NOT NULL, padrão `CURRENT_TIMESTAMP` |
| `consumo_kwh` | DECIMAL(10,4) | NOT NULL — calculado no Java (W / 1000 × horas) |

Índices: `idx_leituras_dispositivo (dispositivo_id)`, `idx_leituras_data (data_hora)`.

As horas de uso não são gravadas; ao carregar, o DAO recalcula `horas = consumo_kwh / (W / 1000)`.
A listagem faz `JOIN` com [dispositivos](dispositivos.md) filtrando pelo usuário, ordenada por `data_hora`.

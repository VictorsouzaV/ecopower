---
tipo: classe
camada: model
requisito: RF04
status: atualizado
---

# TarifaEnergia

`model/TarifaEnergia.java` — composição da tarifa da distribuidora. Persistida em [tarifas](../04%20-%20Banco%20de%20Dados/Tabelas/tarifas.md) (guarda histórico; vale a mais recente).

| Campo Java | Coluna |
|---|---|
| `tarifaPorKWh` | `tarifa_kwh` |
| `valorBandeiraPorKWh` | `bandeira_kwh` |
| `percentualEncargosSetoriais` | `pct_encargos` |
| `percentualICMS` / `PIS` / `COFINS` | `pct_icms` / `pct_pis` / `pct_cofins` |
| `iluminacaoPublica` | `iluminacao_publica` |
| `minimoKWh` | `minimo_kwh` |

Percentuais entre 0 e 1. Usada por [EstimativaCusto](EstimativaCusto.md).

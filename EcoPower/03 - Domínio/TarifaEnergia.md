---
tipo: classe
camada: model
requisito: RF04
status: atualizado
---

# TarifaEnergia

`model/TarifaEnergia.java` — composição da tarifa da distribuidora. **Só em memória** (configurada no menu 7).

| Campo Java | Descrição |
|---|---|
| `tarifaPorKWh` | R$ por kWh |
| `valorBandeiraPorKWh` | adicional da bandeira tarifária |
| `percentualEncargosSetoriais` | encargos |
| `percentualICMS` / `PIS` / `COFINS` | impostos |
| `iluminacaoPublica` | valor fixo |
| `minimoKWh` | consumo mínimo faturado |

Percentuais entre 0 e 1. Usada por [EstimativaCusto](EstimativaCusto.md).

> O schema tem a coluna `usuarios.tarifa_kwh` (só o valor do kWh), mas o app ainda não lê nem grava nela.

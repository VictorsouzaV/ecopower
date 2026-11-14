---
tipo: classe
camada: model
requisito: RF02
status: atualizado
---

# Leitura

`model/Leitura.java` — uma medição de uso de um [Aparelho](Aparelho.md). Persistida em [leituras](../04%20-%20Banco%20de%20Dados/Tabelas/leituras.md).

## Atributos
| Campo | Tipo | Regra |
|---|---|---|
| `id` | int | contador em memória (não é o `id` do banco) |
| `nomeDoAparelho` | String | |
| `dataHora` | String | `dd/MM/aaaa HH:mm` — convertida para `TIMESTAMP` por `LeituraDAO.converterData` |
| `horasDeUso` | double | > 0 |
| `aparelho` | [Aparelho](Aparelho.md) | obrigatório |

## Cálculo
`getConsumoEmKWh()` = potência (W) / 1000 × horas de uso.

O banco guarda só o `consumo_kwh` (não as horas). Ao carregar, `LeituraDAO` recalcula:
`horas = consumo_kwh / (potência / 1000)`.

## Datas aceitas
`dd/MM/aaaa HH:mm`, `dd/MM/aaaa`, `aaaa-MM-ddTHH:mm` ou `aaaa-MM-dd`. Outro formato → "Data inválida".

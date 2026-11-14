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
| `id` | int | |
| `nomeDoAparelho` | String | |
| `dataHora` | String | formato `dd/mm/aaaa hh:mm` (convertido por `fn_converter_data`) |
| `horasDeUso` | double | > 0 (no banco: ≤ 744) |
| `aparelho` | [Aparelho](Aparelho.md) | obrigatório |

## Cálculo
`getConsumoEmKWh()` = potência (W) / 1000 × horas de uso.
O banco recalcula o mesmo valor em `trg_leituras_calcula_consumo` (ver [Triggers](../04%20-%20Banco%20de%20Dados/Triggers.md)).

## Concorrência
`LeituraDAO.inserir` é `synchronized` — exclusão mútua para a coleta em segundo plano.

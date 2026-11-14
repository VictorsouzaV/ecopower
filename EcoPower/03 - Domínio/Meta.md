---
tipo: classe
camada: model
requisito: RF03
status: atualizado
---

# Meta

`model/Meta.java` — meta mensal de consumo. Persistida em [metas](../04%20-%20Banco%20de%20Dados/Tabelas/metas.md) (uma por mês).

## Atributos
| Campo | Regra |
|---|---|
| `valorMensal` | > 0 |
| `tipoUnidade` | `kwh` ou `reais` |
| `percentualAlerta` | 0–100 (padrão 80) |

## Regra `verificarConsumo(consumo)`
| Condição | Resultado |
|---|---|
| consumo > meta | "Meta mensal excedida" |
| consumo ≥ meta × % alerta | "próximo do limite" |
| senão | "dentro do limite" |

A **mesma regra** existe no banco em `sp_verificar_meta`, que grava em [alertas](../04%20-%20Banco%20de%20Dados/Tabelas/alertas.md)
(`EXCEDIDO` / `PROXIMO_LIMITE`) — disparada pelos [Triggers](../04%20-%20Banco%20de%20Dados/Triggers.md). Situação consultável em `vw_situacao_meta` ([Views](../04%20-%20Banco%20de%20Dados/Views.md)).

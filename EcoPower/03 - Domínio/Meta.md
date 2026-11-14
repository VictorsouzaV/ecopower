---
tipo: classe
camada: model
requisito: RF03
status: atualizado
---

# Meta

`model/Meta.java` — meta mensal de consumo. **Só em memória** por enquanto (some ao fechar o app).

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

## No banco
O schema já tem a coluna `usuarios.meta_kwh` e a tabela [alertas](../04%20-%20Banco%20de%20Dados/Tabelas/alertas.md),
mas o app ainda não grava em nenhuma das duas. Próximo passo: um `MetaDAO` que salve a meta e registre os avisos.

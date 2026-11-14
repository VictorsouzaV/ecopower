---
tipo: classe
camada: model
requisito: RF01
status: atualizado
---

# Aparelho

`model/Aparelho.java` — um aparelho elétrico da casa. Persistido na tabela [dispositivos](../04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md).

## Atributos
| Campo | Tipo | Coluna | Regra |
|---|---|---|---|
| `id` | int | `id` | gerado pelo banco |
| `nome` | String | `nome` | não vazio |
| `comodo` | String | `localizacao` ⚠️ nome diferente | não vazio |
| `potenciaEmWatts` | int | `potencia_watts` | > 0 |

## Usado por
- `AparelhoServices` → `DispositivoDAO` (`pr_inserir/atualizar/remover_dispositivo`)
- [Leitura](Leitura.md) referencia um aparelho; remover o aparelho apaga as leituras (`ON DELETE CASCADE`)

## Histórico
- Ganhou `id` na [2026-11 Migração para Oracle](../05%20-%20Decis%C3%B5es/2026-11%20Migra%C3%A7%C3%A3o%20para%20Oracle.md).

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
| `id` | int | `id` | gerado pelo banco (`SERIAL`); `0` no modo só-memória |
| `nome` | String | `nome` | não vazio |
| `comodo` | String | `localizacao` ⚠️ nome diferente | não vazio (se a coluna vier `NULL`, o DAO usa `"Sem cômodo"`) |
| `potenciaEmWatts` | int | `potencia_watts` | > 0 |

## Usado por
- `AparelhoServices` → `DispositivoDAO` (`listar`, `inserir`, `atualizar`, `remover`)
- [Leitura](Leitura.md) referencia um aparelho; remover o aparelho apaga as leituras (`ON DELETE CASCADE`)

## Histórico
- Ganhou `id` na [2026-11 Persistência com PostgreSQL](../05%20-%20Decis%C3%B5es/2026-11%20Persist%C3%AAncia%20com%20PostgreSQL.md).

---
tipo: tabela
status: atualizado
---

# alertas

Avisos gravados automaticamente por `sp_verificar_meta` (chamada pelos [Triggers](../Triggers.md)). Não repete o mesmo tipo no mesmo mês.

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | NUMBER identity | PK |
| `usuario_id` | NUMBER | FK → [usuarios](usuarios.md) (cascade) |
| `tipo` | VARCHAR2(20) | `PROXIMO_LIMITE` ou `EXCEDIDO` |
| `mensagem` | VARCHAR2(400) | |
| `data_hora` | TIMESTAMP | |
| `lido` | CHAR(1) | `S` / `N` (padrão `N`) |

Regra igual a `Meta.verificarConsumo()` — ver [Meta](../../03%20-%20Dom%C3%ADnio/Meta.md).

---
tipo: tabela
status: atualizado
---

# alertas

Avisos de meta para o usuário. **A tabela existe no schema, mas o app ainda não grava nela.**

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | SERIAL | PK |
| `usuario_id` | INT | NOT NULL, FK → [usuarios](usuarios.md) (cascade) |
| `tipo` | VARCHAR(30) | NOT NULL — ex.: `PROXIMO_LIMITE`, `EXCEDIDO` |
| `mensagem` | TEXT | NOT NULL |
| `data_hora` | TIMESTAMP | NOT NULL, padrão `CURRENT_TIMESTAMP` |

Ideia de uso: gravar aqui o resultado de `Meta.verificarConsumo()` — ver [Meta](../../03%20-%20Dom%C3%ADnio/Meta.md).

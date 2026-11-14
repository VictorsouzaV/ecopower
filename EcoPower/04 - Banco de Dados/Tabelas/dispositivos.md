---
tipo: tabela
classe: Aparelho
status: atualizado
---

# dispositivos

Aparelhos do usuário. Classe Java: [Aparelho](../../03%20-%20Dom%C3%ADnio/Aparelho.md). DAO: `DispositivoDAO`.

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | SERIAL | PK |
| `usuario_id` | INT | NOT NULL, FK → [usuarios](usuarios.md) (cascade) |
| `nome` | VARCHAR(100) | NOT NULL |
| `localizacao` | VARCHAR(50) | opcional — `Aparelho.comodo` |
| `potencia_watts` | DECIMAL(10,2) | NOT NULL (o Java trata como `int`) |

Índice: `idx_dispositivos_usuario (usuario_id)`.

SQL do DAO: `SELECT ... WHERE usuario_id = ?`, `INSERT ... RETURNING id`, `UPDATE ... WHERE id = ?`, `DELETE ... WHERE id = ?`.
Apagar um dispositivo apaga as [leituras](leituras.md) dele (`ON DELETE CASCADE`).

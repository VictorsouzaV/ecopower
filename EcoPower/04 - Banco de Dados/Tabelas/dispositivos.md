---
tipo: tabela
classe: Aparelho
status: atualizado
---

# dispositivos

Aparelhos do usuário. Classe Java: [Aparelho](../../03%20-%20Dom%C3%ADnio/Aparelho.md).

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | NUMBER identity | PK |
| `usuario_id` | NUMBER | FK → [usuarios](usuarios.md) (cascade) |
| `nome` | VARCHAR2(100) | NOT NULL |
| `localizacao` | VARCHAR2(50) | NOT NULL — `Aparelho.comodo` |
| `potencia_watts` | NUMBER(6) | > 0 |

Índice: `idx_dispositivos_usuario (usuario_id)`.
Procedures: `pr_inserir_dispositivo`, `pr_atualizar_dispositivo`, `pr_remover_dispositivo`, `fn_listar_dispositivos`.

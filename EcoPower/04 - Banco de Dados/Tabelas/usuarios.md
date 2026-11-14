---
tipo: tabela
status: atualizado
---

# usuarios

Cadastro do morador.

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | SERIAL | PK |
| `nome` | VARCHAR(100) | NOT NULL |
| `email` | VARCHAR(100) | NOT NULL, UNIQUE |
| `tipo_usuario` | VARCHAR(20) | NOT NULL, padrão `CONSUMIDOR` |
| `tarifa_kwh` | DECIMAL(10,4) | NOT NULL, padrão 0 — *ainda não usada pelo app* |
| `meta_kwh` | DECIMAL(10,2) | NOT NULL, padrão 0 — *ainda não usada pelo app* |

**Filhas:** [dispositivos](dispositivos.md), [alertas](alertas.md).

O app busca o usuário pelo `app.usuario.email`; se não existir, cria com `INSERT ... RETURNING id`
([Configuração e Conexão](../../02%20-%20Arquitetura/Configura%C3%A7%C3%A3o%20e%20Conex%C3%A3o.md)).

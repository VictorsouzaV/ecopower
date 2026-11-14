---
tipo: tabela
status: atualizado
---

# usuarios

Cadastro do morador.

| Coluna | Tipo | Regra |
|---|---|---|
| `id` | NUMBER identity | PK |
| `nome` | VARCHAR2(100) | NOT NULL |
| `email` | VARCHAR2(100) | NOT NULL, UNIQUE |
| `tipo_usuario` | VARCHAR2(20) | `CONSUMIDOR` (padrão) ou `ADMIN` |
| `criado_em` | TIMESTAMP | padrão `SYSTIMESTAMP` |

**Filhas:** [dispositivos](dispositivos.md), [tarifas](tarifas.md), [metas](metas.md), [alertas](alertas.md).
Obtido/criado pelo app via `pr_obter_usuario` ([Configuração e Conexão](../../02%20-%20Arquitetura/Configura%C3%A7%C3%A3o%20e%20Conex%C3%A3o.md)).

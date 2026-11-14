---
tipo: guia
status: atualizado
---

# Camadas (MVC + DAO)

```
view/       menu de console (MenuView, AparelhoView, LeituraView, ...)
  │
services/   regras e lista em memória (AparelhoServices, LeituraServices, ...)
  │
dao/        1 classe por tabela — SQL com PreparedStatement (JDBC)
  ▼
PostgreSQL  tabelas de database/schema.sql
```

| Pacote | Responsabilidade | Classes |
|---|---|---|
| `model` | Entidades + validação nos setters/construtores | [Aparelho](../03%20-%20Dom%C3%ADnio/Aparelho.md), [Leitura](../03%20-%20Dom%C3%ADnio/Leitura.md), [Meta](../03%20-%20Dom%C3%ADnio/Meta.md), [TarifaEnergia](../03%20-%20Dom%C3%ADnio/TarifaEnergia.md), [EstimativaCusto](../03%20-%20Dom%C3%ADnio/EstimativaCusto.md), [Relatorios](../03%20-%20Dom%C3%ADnio/Relatorios.md) |
| `view` | Entrada/saída no console | `MenuView`, `InputView`, uma View por tela |
| `services` | Carrega do banco ao iniciar, chama o DAO antes de alterar a memória | `AparelhoServices`, `LeituraServices` (com banco); `MetaServices`, `EstimativaCustoServices`, `RelatoriosServices` (só memória) |
| `dao` | SQL via JDBC (`PreparedStatement`) | `ConexaoBD`, `DispositivoDAO`, `LeituraDAO` |

## Menu → banco
| Menu | Java chama | SQL |
|---|---|---|
| Iniciar | `DispositivoDAO.listar()`, `LeituraDAO.listar()` | `SELECT` em [dispositivos](../04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) e [leituras](../04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) (`JOIN`) do usuário |
| 1 | `DispositivoDAO.inserir()` | `INSERT ... RETURNING id` |
| 3 | `DispositivoDAO.atualizar()` | `UPDATE` |
| 4 | `DispositivoDAO.remover()` | `DELETE` (as leituras saem junto por `ON DELETE CASCADE`) |
| 5 | `LeituraDAO.inserir()` | `INSERT` em [leituras](../04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) |
| 7, 8, 9, 10, 11 | — | nada: tarifa, meta, estimativa e relatório ficam em memória |

Por que assim: [SQL direto nos DAOs](../05%20-%20Decis%C3%B5es/SQL%20direto%20nos%20DAOs.md).

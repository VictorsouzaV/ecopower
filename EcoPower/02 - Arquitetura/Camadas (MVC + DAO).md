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
dao/        1 classe por tabela — só chama procedures pelo nome
  ▼
Oracle      procedures/funções → triggers/views → tabelas
```

| Pacote | Responsabilidade | Classes |
|---|---|---|
| `model` | Entidades + validação nos setters/construtores | [Aparelho](../03%20-%20Dom%C3%ADnio/Aparelho.md), [Leitura](../03%20-%20Dom%C3%ADnio/Leitura.md), [Meta](../03%20-%20Dom%C3%ADnio/Meta.md), [TarifaEnergia](../03%20-%20Dom%C3%ADnio/TarifaEnergia.md), [EstimativaCusto](../03%20-%20Dom%C3%ADnio/EstimativaCusto.md), [Relatorios](../03%20-%20Dom%C3%ADnio/Relatorios.md) |
| `view` | Entrada/saída no console | `MenuView`, `InputView`, uma View por tela |
| `services` | Carrega do banco ao iniciar, chama o DAO antes de alterar a memória | `AparelhoServices`, `LeituraServices`, `MetaServices`, `EstimativaCustoServices`, `RelatoriosServices` |
| `dao` | `{call pr_...}` / `{? = call fn_...}` via JDBC | `DispositivoDAO`, `LeituraDAO`, `TarifaDAO`, `MetaDAO`, `ConexaoBD` |

## Menu → banco
| Menu | Java chama | No banco |
|---|---|---|
| Iniciar | `fn_listar_dispositivos`, `fn_listar_leituras`, `fn_tarifa_atual`, `fn_meta_do_mes` | `SYS_REFCURSOR` |
| 1 / 3 / 4 | `pr_inserir/atualizar/remover_dispositivo` | [dispositivos](../04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) |
| 5 | `pr_inserir_leitura` | [leituras](../04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) + [Triggers](../04%20-%20Banco%20de%20Dados/Triggers.md) |
| 7 | `pr_salvar_tarifa` | [tarifas](../04%20-%20Banco%20de%20Dados/Tabelas/tarifas.md) (histórico) |
| 10 | `pr_salvar_meta` | `MERGE` em [metas](../04%20-%20Banco%20de%20Dados/Tabelas/metas.md) → [alertas](../04%20-%20Banco%20de%20Dados/Tabelas/alertas.md) |

Detalhes em [Funções e Procedures](../04%20-%20Banco%20de%20Dados/Fun%C3%A7%C3%B5es%20e%20Procedures.md). Por que assim: [Todo o SQL fica no banco](../05%20-%20Decis%C3%B5es/Todo%20o%20SQL%20fica%20no%20banco.md).

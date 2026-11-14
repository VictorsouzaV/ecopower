---
tipo: banco
status: atualizado
---

# Funções e Procedures

## Regras de negócio (`02_triggers_views.sql`)
| Nome | Retorno | Descrição |
|---|---|---|
| `fn_consumo_mes(usuario, ref)` | NUMBER | soma de `consumo_kwh` do mês |
| `fn_estimar_conta(usuario, kwh)` | NUMBER | conta em R$ com impostos (ver [EstimativaCusto](../03%20-%20Dom%C3%ADnio/EstimativaCusto.md)) |
| `sp_verificar_meta(usuario)` | — | grava em [alertas](Tabelas/alertas.md) |

## Chamadas pelo app (`03_procedimentos_app.sql`)
| Nome | DAO | Tabela |
|---|---|---|
| `pr_obter_usuario` | `ConexaoBD` | [usuarios](Tabelas/usuarios.md) |
| `pr_inserir_dispositivo` / `pr_atualizar_dispositivo` / `pr_remover_dispositivo` | `DispositivoDAO` | [dispositivos](Tabelas/dispositivos.md) |
| `fn_listar_dispositivos` | `DispositivoDAO` | [dispositivos](Tabelas/dispositivos.md) |
| `fn_converter_data` | — | converte `dd/mm/aaaa hh:mm` |
| `pr_inserir_leitura` / `fn_listar_leituras` | `LeituraDAO` | [leituras](Tabelas/leituras.md) |
| `pr_salvar_tarifa` / `fn_tarifa_atual` | `TarifaDAO` | [tarifas](Tabelas/tarifas.md) |
| `pr_salvar_meta` / `fn_meta_do_mes` | `MetaDAO` | [metas](Tabelas/metas.md) |

Funções de listagem devolvem `SYS_REFCURSOR`. Erros de regra usam `RAISE_APPLICATION_ERROR` e aparecem no menu.

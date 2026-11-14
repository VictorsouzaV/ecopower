---
tipo: banco
requisito: RF05
status: atualizado
---

# Views (RF05)

| View | Conteúdo |
|---|---|
| `vw_consumo_dispositivo_mes` | consumo por aparelho/mês, `RANK()` e `% do total` (`RATIO_TO_REPORT`) |
| `vw_consumo_localizacao_mes` | consumo por cômodo/mês |
| `vw_consumo_diario` | consumo total por dia |
| `vw_situacao_meta` | meta × consumo × estimativa R$, `% atingido` e status (`DENTRO DA META`, `PROXIMO DO LIMITE`, `EXCEDIDA`, `SEM TARIFA`) |

Equivalente no Java: [Relatorios](../03%20-%20Dom%C3%ADnio/Relatorios.md). Mais consultas de exemplo em `database/oracle/consultas.sql`
(JOIN, GROUP BY/HAVING, subconsulta, `RANK`, `LAG`).

---
tipo: classe
camada: model
requisito: RF04
status: atualizado
---

# EstimativaCusto

`model/EstimativaCusto.java` — valor estimado da conta. **Não é persistida** (calculada sob demanda).

## Fórmula
```
consumoFaturado = max(consumo, minimoKWh)
valorTotal      = consumoFaturado × (tarifa + bandeira) + iluminacaoPublica
```

> [!note] Impostos
> Os percentuais de encargos/ICMS/PIS/COFINS existem em [TarifaEnergia](TarifaEnergia.md), mas a fórmula ainda não os aplica.

## Services
`EstimativaCustoServices.estimarCusto()` (menu 8) e `estimarCustoFimDoMes()` (menu 9 — projeção proporcional aos dias).
Depende de [TarifaEnergia](TarifaEnergia.md) e do consumo de [Leitura](Leitura.md) (que vem do banco).

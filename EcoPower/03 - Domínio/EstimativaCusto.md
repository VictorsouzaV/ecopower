---
tipo: classe
camada: model
requisito: RF04
status: desatualizado
---

# EstimativaCusto

`model/EstimativaCusto.java` — valor estimado da conta. **Não é persistida** (calculada sob demanda).

## Fórmula no Java
```
consumoFaturado = max(consumo, minimoKWh)
valorTotal      = consumoFaturado × (tarifa + bandeira) + iluminacaoPublica
```

## Fórmula no banco (`fn_estimar_conta`)
```
max(consumo, minimo) × (tarifa + bandeira) × (1 + encargos + ICMS + PIS + COFINS) + iluminacao
```

> [!bug] Divergência
> O Java ignora encargos e impostos; o banco aplica. A estimativa do menu 8/9 e a de `vw_situacao_meta`
> dão valores diferentes. Decidir qual é a correta e alinhar.

## Services
`EstimativaCustoServices.estimarCusto()` (menu 8) e `estimarCustoFimDoMes()` (menu 9 — projeção proporcional aos dias).
Depende de [TarifaEnergia](TarifaEnergia.md) e do consumo de [Leitura](Leitura.md).

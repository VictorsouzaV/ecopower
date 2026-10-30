# Modelo de dados existente

Não há banco de dados nem mapeamentos ORM. O modelo é mantido em memória durante a execução.

## Entidades

- `Aparelho`: nome, cômodo e potência em watts.
- `Leitura`: identificador estático, aparelho, data/hora, horas de uso e consumo calculado em kWh.
- `TarifaEnergia`: tarifa base, bandeira, percentuais tributários, iluminação pública e consumo mínimo.
- `EstimativaCusto`: consumo, valores tarifários e mínimo faturado; calcula consumo faturado e total.
- `Meta`: valor mensal, unidade (`kWh` ou `reais`) e percentual de alerta.
- `Relatorios`: dias de uso, mapa de consumo por aparelho, total e maior consumidor.

## Relações

- Uma `Leitura` mantém uma referência para um `Aparelho`.
- `Relatorios` recebe uma lista de `Leitura` e agrega consumo por nome do aparelho.
- `EstimativaCustoServices` usa `LeituraServices` e `TarifaEnergia`.
- `MetaServices` mantém uma única `Meta` ativa.

## Estado e identidade

Os IDs de `Leitura` e `Relatorios` são gerados por contadores `static`, sem persistência e sem garantia de segurança para múltiplas threads. O nome do aparelho é usado como chave de agregação em relatórios.

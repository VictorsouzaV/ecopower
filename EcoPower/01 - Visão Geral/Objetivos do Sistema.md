---
tipo: guia
status: atualizado
---

# Objetivos do Sistema

## Problema
O consumidor residencial só descobre quanto gastou quando a fatura chega — quando já não dá para corrigir nada.
Faltam três coisas: informação **durante** o mês, saber **quais aparelhos** pesam mais e um **aviso** antes de estourar o orçamento.

## Solução
O EcoPower Monitor registra o consumo de cada aparelho, deixa o usuário definir uma [Meta](../03%20-%20Dom%C3%ADnio/Meta.md) mensal (kWh ou R$)
e avisa quando o consumo se aproxima ou passa dela. Cada dor virou um item em [Requisitos](Requisitos.md).

## ODS da ONU
- **ODS 7** — Energia Limpa e Acessível
- **ODS 12** — Consumo e Produção Responsáveis

## Parte interessada
Morador responsável pela conta de luz (tabela [usuarios](../04%20-%20Banco%20de%20Dados/Tabelas/usuarios.md), tipo `CONSUMIDOR`).

## Decisão de projeto inicial
Funciona **local e autônomo**: a coleta roda em segundo plano na própria máquina (threads Java),
sem depender de serviços externos.

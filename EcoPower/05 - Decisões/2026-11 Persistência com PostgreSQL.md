---
tipo: decisao
data: 2026-11
status: aceita
---

# 2026-11 — Persistência com PostgreSQL

## Contexto
A 1ª versão guardava tudo em memória: ao fechar o app, os dados sumiam.

## Decisão
Persistir em **PostgreSQL** (local ou Docker `postgres:16`) via JDBC + DAO, usando o schema
modelado na 1ª entrega (`database/schema.sql`, mesmo modelo do [DER](../04%20-%20Banco%20de%20Dados/DER.md)).

Escopo mínimo desta etapa: **aparelhos e leituras**. Tarifa e meta continuam em memória.

## Consequências
- [Aparelho](../03%20-%20Dom%C3%ADnio/Aparelho.md) ganhou `id`.
- `AparelhoServices` e `LeituraServices` carregam do banco ao iniciar e chamam o DAO antes de alterar a memória.
- Corrigidos os `break` que faltavam no `switch` do `MenuView` (sem eles, cada opção executava todas as seguintes).
- Sem banco configurado, o app continua funcionando só em memória ([Configuração e Conexão](../02%20-%20Arquitetura/Configura%C3%A7%C3%A3o%20e%20Conex%C3%A3o.md)).
- Ver também [SQL direto nos DAOs](SQL%20direto%20nos%20DAOs.md).

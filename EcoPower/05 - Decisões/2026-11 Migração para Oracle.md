---
tipo: decisao
data: 2026-11
status: aceita
---

# 2026-11 — Migração para Oracle

## Contexto
A 1ª versão guardava tudo em memória: ao fechar o app, os dados sumiam.

## Decisão
Persistir em **Oracle 23ai** (FreeSQL.com ou Docker `gvenzl/oracle-free`) via JDBC + DAO.

## Consequências
- [Aparelho](../03%20-%20Dom%C3%ADnio/Aparelho.md) ganhou `id`.
- Cada service carrega do banco ao iniciar e chama o DAO antes de alterar a memória.
- `TarifaView` passou a salvar a tarifa.
- Corrigidos os `break` que faltavam no `switch` do `MenuView`.
- Sem banco configurado, o app continua funcionando só em memória ([Configuração e Conexão](../02%20-%20Arquitetura/Configura%C3%A7%C3%A3o%20e%20Conex%C3%A3o.md)).
- Ver também [Todo o SQL fica no banco](Todo%20o%20SQL%20fica%20no%20banco.md).

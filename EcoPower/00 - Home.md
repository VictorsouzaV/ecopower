---
tipo: indice
---

# EcoPower Monitor

Aplicação de console em Java + PostgreSQL para acompanhar o consumo de energia residencial,
definir metas mensais e receber alertas antes de a fatura fechar. AEP de Engenharia de Software — Unicesumar.

## 🧭 Visão geral
- [Objetivos do Sistema](01%20-%20Vis%C3%A3o%20Geral/Objetivos%20do%20Sistema.md) — o problema, as ODS e as partes interessadas
- [Requisitos](01%20-%20Vis%C3%A3o%20Geral/Requisitos.md) — RF01 a RF05 e onde cada um está implementado
- [Entregas AEP](01%20-%20Vis%C3%A3o%20Geral/Entregas%20AEP.md) — cronograma e documento da 1ª entrega
- [Como Executar](01%20-%20Vis%C3%A3o%20Geral/Como%20Executar.md) — do zero até o app rodando com o PostgreSQL

## 🏗️ Arquitetura
- [Camadas (MVC + DAO)](02%20-%20Arquitetura/Camadas%20%28MVC%20%2B%20DAO%29.md) — view → services → dao → PostgreSQL
- [Diagrama de Classes](02%20-%20Arquitetura/Diagrama%20de%20Classes.md)
- [Configuração e Conexão](02%20-%20Arquitetura/Configura%C3%A7%C3%A3o%20e%20Conex%C3%A3o.md) — `config.local.properties`, modo só-memória

## 📦 Domínio (classes Java)
| Classe | Onde fica | Requisito |
|---|---|---|
| [Aparelho](03%20-%20Dom%C3%ADnio/Aparelho.md) | tabela [dispositivos](04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) | RF01 |
| [Leitura](03%20-%20Dom%C3%ADnio/Leitura.md) | tabela [leituras](04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) | RF02 |
| [Meta](03%20-%20Dom%C3%ADnio/Meta.md) | só em memória | RF03 |
| [TarifaEnergia](03%20-%20Dom%C3%ADnio/TarifaEnergia.md) | só em memória | RF04 |
| [EstimativaCusto](03%20-%20Dom%C3%ADnio/EstimativaCusto.md) | — (calculada) | RF04 |
| [Relatorios](03%20-%20Dom%C3%ADnio/Relatorios.md) | — (calculado no Java) | RF05 |

## 🗄️ Banco de dados (PostgreSQL)
- [DER](04%20-%20Banco%20de%20Dados/DER.md) — modelo e relacionamentos (`database/schema.sql`)
- Tabelas: [usuarios](04%20-%20Banco%20de%20Dados/Tabelas/usuarios.md) · [dispositivos](04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) · [leituras](04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) · [alertas](04%20-%20Banco%20de%20Dados/Tabelas/alertas.md)

## 🧠 Decisões
- [2026-11 Persistência com PostgreSQL](05%20-%20Decis%C3%B5es/2026-11%20Persist%C3%AAncia%20com%20PostgreSQL.md)
- [SQL direto nos DAOs](05%20-%20Decis%C3%B5es/SQL%20direto%20nos%20DAOs.md)

## ⚠️ Pendências
- [Meta](03%20-%20Dom%C3%ADnio/Meta.md) e [TarifaEnergia](03%20-%20Dom%C3%ADnio/TarifaEnergia.md) ainda não são salvas — as colunas `usuarios.meta_kwh` e `usuarios.tarifa_kwh` existem, mas o app não usa
- A tabela [alertas](04%20-%20Banco%20de%20Dados/Tabelas/alertas.md) existe, mas o app ainda não grava nela
- [Diagrama de Classes](02%20-%20Arquitetura/Diagrama%20de%20Classes.md) diverge do código atual

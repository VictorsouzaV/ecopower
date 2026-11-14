---
tipo: indice
---

# EcoPower Monitor

Aplicação de console em Java + Oracle para acompanhar o consumo de energia residencial,
definir metas mensais e receber alertas antes de a fatura fechar. AEP de Engenharia de Software — Unicesumar.

## 🧭 Visão geral
- [Objetivos do Sistema](01%20-%20Vis%C3%A3o%20Geral/Objetivos%20do%20Sistema.md) — o problema, as ODS e as partes interessadas
- [Requisitos](01%20-%20Vis%C3%A3o%20Geral/Requisitos.md) — RF01 a RF05 e onde cada um está implementado
- [Entregas AEP](01%20-%20Vis%C3%A3o%20Geral/Entregas%20AEP.md) — cronograma e documento da 1ª entrega
- [Como Executar](01%20-%20Vis%C3%A3o%20Geral/Como%20Executar.md) — do zero até o app rodando com o banco

## 🏗️ Arquitetura
- [Camadas (MVC + DAO)](02%20-%20Arquitetura/Camadas%20%28MVC%20%2B%20DAO%29.md) — view → services → dao → Oracle
- [Diagrama de Classes](02%20-%20Arquitetura/Diagrama%20de%20Classes.md)
- [Configuração e Conexão](02%20-%20Arquitetura/Configura%C3%A7%C3%A3o%20e%20Conex%C3%A3o.md) — `config.local.properties`, modo só-memória

## 📦 Domínio (classes Java)
| Classe | Tabela | Requisito |
|---|---|---|
| [Aparelho](03%20-%20Dom%C3%ADnio/Aparelho.md) | [dispositivos](04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) | RF01 |
| [Leitura](03%20-%20Dom%C3%ADnio/Leitura.md) | [leituras](04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) | RF02 |
| [Meta](03%20-%20Dom%C3%ADnio/Meta.md) | [metas](04%20-%20Banco%20de%20Dados/Tabelas/metas.md) / [alertas](04%20-%20Banco%20de%20Dados/Tabelas/alertas.md) | RF03 |
| [TarifaEnergia](03%20-%20Dom%C3%ADnio/TarifaEnergia.md) | [tarifas](04%20-%20Banco%20de%20Dados/Tabelas/tarifas.md) | RF04 |
| [EstimativaCusto](03%20-%20Dom%C3%ADnio/EstimativaCusto.md) | — (calculada) | RF04 |
| [Relatorios](03%20-%20Dom%C3%ADnio/Relatorios.md) | views | RF05 |

## 🗄️ Banco de dados
- [DER](04%20-%20Banco%20de%20Dados/DER.md) — modelo e relacionamentos
- Tabelas: [usuarios](04%20-%20Banco%20de%20Dados/Tabelas/usuarios.md) · [dispositivos](04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) · [leituras](04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) · [tarifas](04%20-%20Banco%20de%20Dados/Tabelas/tarifas.md) · [metas](04%20-%20Banco%20de%20Dados/Tabelas/metas.md) · [alertas](04%20-%20Banco%20de%20Dados/Tabelas/alertas.md)
- [Triggers](04%20-%20Banco%20de%20Dados/Triggers.md) · [Views](04%20-%20Banco%20de%20Dados/Views.md) · [Funções e Procedures](04%20-%20Banco%20de%20Dados/Fun%C3%A7%C3%B5es%20e%20Procedures.md)

## 🧠 Decisões
- [2026-11 Migração para Oracle](05%20-%20Decis%C3%B5es/2026-11%20Migra%C3%A7%C3%A3o%20para%20Oracle.md)
- [Todo o SQL fica no banco](05%20-%20Decis%C3%B5es/Todo%20o%20SQL%20fica%20no%20banco.md)

## ⚠️ Pendências
- [DER](04%20-%20Banco%20de%20Dados/DER.md) desatualizado (faltam `tarifas`, `metas`, `horas_uso`)
- [EstimativaCusto](03%20-%20Dom%C3%ADnio/EstimativaCusto.md) no Java não aplica impostos; `fn_estimar_conta` no banco aplica

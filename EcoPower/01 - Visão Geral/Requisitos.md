---
tipo: guia
status: atualizado
---

# Requisitos Funcionais

| Código | Descrição | Java | Banco | Menu |
|---|---|---|---|---|
| RF01 | Cadastrar, editar e remover aparelhos (nome, cômodo, potência W) | [Aparelho](../03%20-%20Dom%C3%ADnio/Aparelho.md) | [dispositivos](../04%20-%20Banco%20de%20Dados/Tabelas/dispositivos.md) | 1–4 |
| RF02 | Registrar medições de consumo (kWh) e montar histórico | [Leitura](../03%20-%20Dom%C3%ADnio/Leitura.md) | [leituras](../04%20-%20Banco%20de%20Dados/Tabelas/leituras.md) | 5–6 |
| RF03 | Meta mensal (kWh ou R$) com aviso perto/acima do limite | [Meta](../03%20-%20Dom%C3%ADnio/Meta.md) | [metas](../04%20-%20Banco%20de%20Dados/Tabelas/metas.md), [alertas](../04%20-%20Banco%20de%20Dados/Tabelas/alertas.md), [Triggers](../04%20-%20Banco%20de%20Dados/Triggers.md) | 10 |
| RF04 | Estimar a conta em R$ pelo consumo e tarifa | [TarifaEnergia](../03%20-%20Dom%C3%ADnio/TarifaEnergia.md), [EstimativaCusto](../03%20-%20Dom%C3%ADnio/EstimativaCusto.md) | [tarifas](../04%20-%20Banco%20de%20Dados/Tabelas/tarifas.md), `fn_estimar_conta` | 7–9 |
| RF05 | Relatórios por aparelho/período, maiores consumidores e dicas | [Relatorios](../03%20-%20Dom%C3%ADnio/Relatorios.md) | [Views](../04%20-%20Banco%20de%20Dados/Views.md) | 11 |

O roteiro para demonstrar cada requisito está em [Como Executar](Como%20Executar.md) (seção 6).

---
tipo: classe
camada: model
requisito: RF05
status: atualizado
---

# Relatorios

`model/Relatorios.java` — consolida uma lista de [Leitura](Leitura.md)s: consumo total, consumo por aparelho
e o aparelho de maior consumo. `RelatoriosServices.mostrarDicas()` sugere economia (menu 11).

No banco, os relatórios equivalentes são as [Views](../04%20-%20Banco%20de%20Dados/Views.md) (`vw_consumo_dispositivo_mes` com `RANK` e % do total).

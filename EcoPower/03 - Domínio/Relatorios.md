---
tipo: classe
camada: model
requisito: RF05
status: atualizado
---

# Relatorios

`model/Relatorios.java` — consolida uma lista de [Leitura](Leitura.md)s: consumo total, consumo por aparelho
e o aparelho de maior consumo. `RelatoriosServices.mostrarDicas()` sugere economia (menu 11).

Tudo é calculado no Java a partir das leituras carregadas do banco. Para a mesma análise direto no PostgreSQL:

```sql
SELECT d.nome, SUM(l.consumo_kwh) AS total_kwh
  FROM leituras l
  JOIN dispositivos d ON d.id = l.dispositivo_id
 GROUP BY d.nome
 ORDER BY total_kwh DESC;
```

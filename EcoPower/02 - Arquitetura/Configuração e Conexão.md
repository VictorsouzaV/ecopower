---
tipo: guia
status: atualizado
---

# Configuração e Conexão

Classe `dao/ConexaoBD.java`. Lê a conexão, em ordem:
1. `ecopower/sistema/config.local.properties` (fora do git — copiar de `config.example.properties`)
2. Variáveis de ambiente `ECOPOWER_DB_URL`, `ECOPOWER_DB_USER`, `ECOPOWER_DB_PASSWORD`
3. `-Ddb.url=...` na linha de comando

```properties
db.url=jdbc:oracle:thin:@//localhost:1521/FREEPDB1
db.user=ecopower
db.password=ecopower
app.usuario.email=morador@exemplo.com
app.usuario.nome=Morador Exemplo
```

- O usuário é obtido/criado por `pr_obter_usuario` (tabela [usuarios](../04%20-%20Banco%20de%20Dados/Tabelas/usuarios.md)).
- **Sem configuração → modo só-memória**, igual à versão original. É assim que `mvn test` roda (`ecopower.banco=off` no `pom.xml`).
- Passo a passo completo: [Como Executar](../01%20-%20Vis%C3%A3o%20Geral/Como%20Executar.md).

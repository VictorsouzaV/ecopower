---
tipo: guia
status: atualizado
---

# Configuração e Conexão

Classe `dao/ConexaoBD.java`, driver JDBC `org.postgresql:postgresql` (no `pom.xml`). Lê cada valor nesta ordem de prioridade:
1. `-Ddb.url=...` / `-Ddb.user=...` / `-Ddb.password=...` na linha de comando
2. Variáveis de ambiente `ECOPOWER_DB_URL`, `ECOPOWER_DB_USER`, `ECOPOWER_DB_PASSWORD`
3. `ecopower/sistema/config.local.properties` (fora do git — copiar de `config.example.properties`)

```properties
db.url=jdbc:postgresql://localhost:5433/ecopower
db.user=ecopower
db.password=ecopower
app.usuario.email=morador@exemplo.com
app.usuario.nome=Morador Exemplo
```

- O usuário do app é buscado pelo e-mail em [usuarios](../04%20-%20Banco%20de%20Dados/Tabelas/usuarios.md); se não existir, é criado (`INSERT ... RETURNING id`).
- Cada operação abre uma conexão nova (`DriverManager.getConnection`) e fecha no fim (try-with-resources).
- **Sem `db.url` → modo só-memória**, igual à versão original. É assim que `mvn test` roda: o `pom.xml` zera `ECOPOWER_DB_URL` e aponta `ecopower.config` para um arquivo que não existe.
- Passo a passo completo: [Como Executar](../01%20-%20Vis%C3%A3o%20Geral/Como%20Executar.md).

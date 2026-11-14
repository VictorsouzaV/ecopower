---
tipo: guia
status: atualizado
---

# Guia: como fazer o EcoPower Monitor funcionar

Este guia leva você do zero até o sistema rodando, com o banco PostgreSQL criado e o menu funcionando.
Tempo estimado: **10–20 minutos** (a maior parte é download).

---

## Visão geral

```
┌──────────────────────┐   JDBC (postgresql)  ┌──────────────────────────────┐
│  App Java (console)  │ ───────────────────▶ │  PostgreSQL                  │
│  ecopower/sistema    │                      │  Docker  OU  instalado local │
└──────────────────────┘                      └──────────────────────────────┘
        lê a conexão de                         criado pelo script
   config.local.properties                      database/schema.sql
```

Você vai:

1. Instalar as ferramentas (Java, Maven e Docker ou PostgreSQL).
2. Subir o PostgreSQL: **Docker** (recomendado) **ou** instalação local.
3. Rodar o `database/schema.sql` para criar as tabelas.
4. Configurar a conexão no `config.local.properties`.
5. Rodar o app.

---

## 1. Pré-requisitos

| Ferramenta | Versão | Para quê | Como conferir |
|---|---|---|---|
| Git | qualquer | baixar o projeto | `git --version` |
| JDK | **21** | compilar e rodar o Java | `java -version` |
| Maven | 3.9+ | baixar o driver do PostgreSQL e rodar o app | `mvn -version` |
| Docker | qualquer | *se* for usar o banco no Docker (opção A) | `docker --version` |
| PostgreSQL | 14+ | *se* for instalar o banco direto na máquina (opção B) | `psql --version` |

### Instalando

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-21-jdk maven git
# Opção A: Docker → https://docs.docker.com/engine/install/ubuntu/
# Opção B: sudo apt install postgresql
```

**Windows:**
- JDK 21: baixe o instalador do [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21).
- Maven: baixe o `.zip` em [maven.apache.org](https://maven.apache.org/download.cgi), descompacte e adicione a pasta `bin` ao `PATH`.
- Opção A: [Docker Desktop](https://www.docker.com/products/docker-desktop/). Opção B: instalador em [postgresql.org/download](https://www.postgresql.org/download/windows/).

**macOS:**
```bash
brew install openjdk@21 maven git
# Opção A: Docker Desktop   |   Opção B: brew install postgresql@16
```

---

## 2. Baixar o projeto

```bash
git clone https://github.com/VictorsouzaV/ecopower.git
cd ecopower
git checkout feature/database-implementation   # branch com a integração do banco
```

Estrutura que importa para este guia:

```
ecopower/
├── database/
│   └── schema.sql              ← cria as tabelas e insere dados de exemplo
└── ecopower/sistema/           ← projeto Java (Maven)
    ├── pom.xml
    ├── config.example.properties
    └── src/
```

---

## 3. Subir o banco de dados

Escolha **uma** das opções. O script é o mesmo nas duas.

| | Opção A — Docker | Opção B — PostgreSQL instalado |
|---|---|---|
| Instalação | só o Docker | o PostgreSQL |
| Porta usada | **5433** (não conflita com um Postgres local) | 5432 (padrão) |
| Bom para | **apresentação**, não suja a máquina | quem já tem PostgreSQL |

### Opção A — PostgreSQL no Docker

1. Suba o container (a primeira vez baixa ~150 MB):

   **Linux/macOS:**
   ```bash
   docker run -d --name ecopower-postgres -p 5433:5432 \
     -e POSTGRES_USER=ecopower -e POSTGRES_PASSWORD=ecopower -e POSTGRES_DB=ecopower \
     postgres:16
   ```
   **Windows (PowerShell, uma linha só):**
   ```powershell
   docker run -d --name ecopower-postgres -p 5433:5432 -e POSTGRES_USER=ecopower -e POSTGRES_PASSWORD=ecopower -e POSTGRES_DB=ecopower postgres:16
   ```

2. Espere alguns segundos e rode o script (a partir da raiz do projeto):

   **Linux/macOS:**
   ```bash
   docker exec -i ecopower-postgres psql -U ecopower -d ecopower < database/schema.sql
   ```
   **Windows (PowerShell):**
   ```powershell
   Get-Content database/schema.sql | docker exec -i ecopower-postgres psql -U ecopower -d ecopower
   ```
   Se aparecer `the database system is starting up`, espere mais um pouco e rode de novo.

3. No dia a dia:
   ```bash
   docker stop ecopower-postgres     # desliga (os dados ficam salvos)
   docker start ecopower-postgres    # liga de novo
   ```

### Opção B — PostgreSQL instalado na máquina

1. Crie o usuário e o banco (como superusuário `postgres`):
   ```bash
   sudo -u postgres psql -c "CREATE USER ecopower WITH PASSWORD 'ecopower';"
   sudo -u postgres psql -c "CREATE DATABASE ecopower OWNER ecopower;"
   ```
   No Windows, abra o **SQL Shell (psql)**, entre como `postgres` e rode os dois comandos sem o `sudo -u postgres psql -c`.

2. Rode o script:
   ```bash
   psql -h localhost -U ecopower -d ecopower -f database/schema.sql
   ```

3. Lembre de usar a porta **5432** no `db.url` (passo 4).

### Conferir se deu certo
```bash
# Opção A
docker exec -it ecopower-postgres psql -U ecopower -d ecopower -c "\dt"
# Opção B
psql -h localhost -U ecopower -d ecopower -c "\dt"
```
Devem aparecer **4 tabelas**: `alertas`, `dispositivos`, `leituras`, `usuarios`.

> **Dica:** para usar uma interface gráfica, conecte o DBeaver ou o pgAdmin em
> `localhost`, porta `5433` (Docker) ou `5432` (local), banco `ecopower`, usuário `ecopower`, senha `ecopower`.

---

## 4. Configurar a conexão do app

O app lê os dados do banco de um arquivo que **não vai para o git** (está no `.gitignore`), para ninguém subir senha no GitHub.

```bash
cd ecopower/sistema
cp config.example.properties config.local.properties      # Windows: copy config.example.properties config.local.properties
```

Para a **Opção A (Docker)** o arquivo já vem certo:
```properties
db.url=jdbc:postgresql://localhost:5433/ecopower
db.user=ecopower
db.password=ecopower
```

Para a **Opção B (local)**, troque só a porta:
```properties
db.url=jdbc:postgresql://localhost:5432/ecopower
```

As duas últimas linhas definem o usuário do app (se o e-mail não existir na tabela `usuarios`, o app cria):
```properties
app.usuario.email=morador@exemplo.com
app.usuario.nome=Morador Exemplo
```
Com `morador@exemplo.com`, o app abre já com os aparelhos de exemplo.

> Também dá para passar a configuração sem arquivo, por variável de ambiente
> (`ECOPOWER_DB_URL`, `ECOPOWER_DB_USER`, `ECOPOWER_DB_PASSWORD`) ou por `-Ddb.url=...` no Maven.
> Ordem de prioridade: `-D` → variável de ambiente → arquivo.

---

## 5. Rodar o app

Ainda dentro de `ecopower/sistema`:

```bash
mvn compile exec:java
```

Na primeira vez o Maven baixa o driver do PostgreSQL (`org.postgresql:postgresql`). Depois deve aparecer:

```
========================================
    EcoPower - Monitor de energia
========================================

--------------- MENU ----------------
1  - Cadastrar aparelho
...
11 - Gerar relatório e dicas
0  - Sair
```

> O comando precisa ser rodado **dentro de `ecopower/sistema`**, porque é lá que o app procura o `config.local.properties`.

### Rodar os testes

```bash
mvn test
```

Os testes **não precisam de banco**: o `pom.xml` zera `ECOPOWER_DB_URL` e aponta a configuração para um arquivo
que não existe, então o app funciona só em memória, como na versão original.

> Um teste (`estimativaCalculaConsumoFaturadoEValorTotal`) espera o valor formatado como `R$ 11,00`
> e falha em computadores com idioma em inglês. Para rodar com formato brasileiro:
> ```bash
> mvn test "-DargLine=-Duser.language=pt -Duser.country=BR"
> ```

---

## 6. Roteiro de teste / apresentação

Com os dados de exemplo carregados, siga esta sequência para mostrar cada requisito.
A parte do app usa o menu; a parte do banco é mostrada no **psql** ou no DBeaver/pgAdmin.

| Passo | Onde | O que fazer / mostrar | Requisito |
|---|---|---|---|
| 1 | menu `2` | Lista dos 3 aparelhos de exemplo (Geladeira, Ar-condicionado, Chuveiro) — vieram do banco | RF01 |
| 2 | menu `1` | Cadastrar um aparelho novo (ex.: Micro-ondas, Cozinha, 1200 W) | RF01 |
| 3 | SQL | `SELECT * FROM dispositivos;` → o Micro-ondas está lá | RF01 |
| 4 | menu `5` | Registrar uma leitura. Data no formato **`dd/mm/aaaa hh:mm`** (ex.: `12/11/2026 18:30`) | RF02 |
| 5 | menu `6` | Listar leituras + consumo acumulado | RF02 |
| 6 | SQL | `SELECT * FROM leituras;` → a leitura com `consumo_kwh` calculado | RF02 |
| 7 | menu `7`, `8`, `9` | Configurar a tarifa, estimativa da conta e projeção (em memória) | RF04 |
| 8 | menu `10` | Definir meta de **100 kWh** e ver o aviso (em memória) | RF03 |
| 9 | menu `11` | Relatório e dicas | RF05 |

Feche e abra o app de novo e use a opção `2`: o aparelho cadastrado no passo 2 continua lá — prova de que está no banco.
(Tarifa e meta precisam ser configuradas de novo: ainda não são salvas.)

Consulta extra para mostrar o lado do banco (consumo por aparelho):
```sql
SELECT d.nome, d.localizacao, SUM(l.consumo_kwh) AS total_kwh
  FROM leituras l
  JOIN dispositivos d ON d.id = l.dispositivo_id
 GROUP BY d.nome, d.localizacao
 ORDER BY total_kwh DESC;
```

---

## 7. Problemas comuns

| Mensagem | Causa | Solução |
|---|---|---|
| `Aviso: banco não configurado ... dados ficam só em memória` | Não existe `config.local.properties` ou você rodou fora de `ecopower/sistema` | Faça o passo 4 e rode o comando dentro de `ecopower/sistema` |
| `Connection to localhost:5433 refused` | Banco desligado ou porta errada | Docker: `docker start ecopower-postgres`. Local: use a porta `5432` |
| `FATAL: password authentication failed for user "ecopower"` | Usuário ou senha errados | Confira `db.user` e `db.password` |
| `FATAL: database "ecopower" does not exist` | Banco não criado | Opção A: recrie o container com `POSTGRES_DB=ecopower`. Opção B: `CREATE DATABASE ecopower` |
| `ERROR: relation "dispositivos" does not exist` | `schema.sql` não foi rodado neste banco | Rode o passo 3 de novo |
| `No suitable driver found for jdbc:...` | `db.url` não começa com `jdbc:postgresql://` | Corrija o `db.url` |
| `Data inválida. Use o formato dd/MM/aaaa HH:mm` | Data digitada em outro formato | Use `dd/mm/aaaa hh:mm`, ex.: `12/11/2026 18:30` |
| `Bind for 0.0.0.0:5433 failed: port is already allocated` | Outro serviço já usa a porta 5433 | Use `-p 5434:5432` no `docker run` e `localhost:5434` no `db.url` |
| `mvn: command not found` | Maven não instalado / fora do `PATH` | Volte ao passo 1 |
| `release version 21 not supported` | Java mais antigo que o 21 | Instale o JDK 21 e confira `java -version` |

### Recomeçar o banco do zero

O `schema.sql` já começa com `DROP TABLE IF EXISTS`, então **rodar o script de novo apaga tudo** e recria
as tabelas com os dados de exemplo (passo 3).

No Docker, também dá para apagar o container inteiro e começar de novo:
```bash
docker rm -f ecopower-postgres
```

---

## 8. Como as peças se encaixam (para explicar ao grupo)

**O SQL fica nos DAOs.** Cada DAO monta o SQL com `PreparedStatement` (parâmetros `?`) e executa via JDBC.

```
view/        (menu — igual ao original)
  │
services/    (iguais ao original + 1 linha por método chamando o DAO)
  │
dao/         ConexaoBD, DispositivoDAO, LeituraDAO
  │            SELECT / INSERT ... RETURNING id / UPDATE / DELETE
  ▼
PostgreSQL   database/schema.sql → 4 tabelas, FKs com ON DELETE CASCADE, índices
```

| Ação no menu | O Java chama | O que roda no banco |
|---|---|---|
| Iniciar o app | `DispositivoDAO.listar()`, `LeituraDAO.listar()` | `SELECT`s filtrados pelo usuário (leituras com `JOIN` em dispositivos) |
| 1 — cadastrar aparelho | `DispositivoDAO.inserir()` | `INSERT ... RETURNING id` |
| 3 — editar aparelho | `DispositivoDAO.atualizar()` | `UPDATE` |
| 4 — remover aparelho | `DispositivoDAO.remover()` | `DELETE` (as leituras saem junto por `ON DELETE CASCADE`) |
| 5 — leitura | `LeituraDAO.inserir()` | converte a data no Java e faz `INSERT` com o `consumo_kwh` já calculado |
| 7–11 | — | nada (tarifa, meta, estimativa e relatório ficam em memória) |

Outros pontos para explicar:
- **Mudanças no Java** em relação à versão original: `Aparelho` ganhou um `id`; `AparelhoServices` e `LeituraServices`
  carregam os dados do banco ao iniciar e chamam o DAO antes de alterar a lista em memória; o `switch`
  do menu ganhou os `break` que faltavam (sem eles, cada opção executava todas as seguintes).
- A classe `Aparelho` é gravada na tabela `dispositivos` (atributo `comodo` → coluna `localizacao`).
- O banco guarda o `consumo_kwh` da leitura, não as horas; ao carregar, as horas são recalculadas pela potência.
- O usuário do app é encontrado pelo e-mail (ou criado na primeira vez).
- **Sem banco configurado** o app funciona como o original (só memória) — é assim que os testes rodam.

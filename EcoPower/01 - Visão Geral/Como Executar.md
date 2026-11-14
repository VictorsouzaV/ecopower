---
tipo: guia
status: atualizado
---

# Guia: como fazer o EcoPower Monitor funcionar

Este guia leva você do zero até o sistema rodando, com o banco Oracle criado e o menu funcionando.
Tempo estimado: **15–30 minutos** (a maior parte é download).

---

## Visão geral

```
┌──────────────────────┐   JDBC (ojdbc11)   ┌──────────────────────────────┐
│  App Java (console)  │ ─────────────────▶ │  Oracle 23ai                 │
│  ecopower/sistema    │                    │  FreeSQL.com  OU  Docker     │
└──────────────────────┘                    └──────────────────────────────┘
        lê a conexão de                       criado pelos scripts de
   config.local.properties                    database/oracle/
```

Você vai:

1. Instalar as ferramentas (Java, Maven e, se quiser banco local, Docker).
2. Escolher **onde** o banco vai rodar: FreeSQL.com (nuvem) **ou** Docker (local).
3. Rodar os scripts SQL para criar as tabelas.
4. Configurar a conexão no `config.local.properties`.
5. Rodar o app.

---

## 1. Pré-requisitos

| Ferramenta | Versão | Para quê | Como conferir |
|---|---|---|---|
| Git | qualquer | baixar o projeto | `git --version` |
| JDK | **21** | compilar e rodar o Java | `java -version` |
| Maven | 3.9+ | baixar o driver do Oracle e rodar o app | `mvn -version` |
| Docker | qualquer | *só se* for usar o banco local | `docker --version` |
| Conta no FreeSQL.com | — | *só se* for usar o banco na nuvem | — |

### Instalando

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-21-jdk maven git
# Docker (opcional): https://docs.docker.com/engine/install/ubuntu/
```

**Windows:**
- JDK 21: baixe o instalador do [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21).
- Maven: baixe o `.zip` em [maven.apache.org](https://maven.apache.org/download.cgi), descompacte e adicione a pasta `bin` ao `PATH`.
- Docker (opcional): [Docker Desktop](https://www.docker.com/products/docker-desktop/).

**macOS:**
```bash
brew install openjdk@21 maven git
# Docker (opcional): Docker Desktop
```

---

## 2. Baixar o projeto

```bash
git clone https://github.com/VictorsouzaV/ecopower.git
cd ecopower
git checkout feature/banco-oracle      # branch com a integração do banco
```

Estrutura que importa para este guia:

```
ecopower/
├── database/oracle/
│   ├── 01_tabelas.sql          ← 1º: cria as tabelas
│   ├── 02_triggers_views.sql   ← 2º: funções, triggers e views
│   ├── 03_procedimentos_app.sql← 3º: procedures que o app Java chama (todo o SQL do app)
│   ├── dados_exemplo.sql       ← 4º (opcional): dados para teste
│   └── consultas.sql           ← consultas de exemplo para estudar/apresentar
└── ecopower/sistema/           ← projeto Java (Maven)
    ├── pom.xml
    ├── config.example.properties
    └── src/
```

---

## 3. Subir o banco de dados

Escolha **uma** das opções. Os scripts são os mesmos nas duas.

| | Opção A — FreeSQL.com | Opção B — Docker local |
|---|---|---|
| Precisa de internet | Sim | Só para baixar a imagem |
| Instalação | Nenhuma | Docker |
| Bom para | estudar, testar rápido | **apresentação** (não depende da rede) |

### Opção A — FreeSQL.com

1. Entre em [freesql.com](https://freesql.com) e faça login.
2. Abra o **worksheet** SQL.
3. Cole e execute **como script** (botão *Run Script*), **um arquivo por vez, nesta ordem**:
   1. `database/oracle/01_tabelas.sql`
   2. `database/oracle/02_triggers_views.sql`
   3. `database/oracle/03_procedimentos_app.sql`
   4. `database/oracle/dados_exemplo.sql` *(opcional, mas recomendado)*
4. Confira se deu certo:
   ```sql
   SELECT object_type, object_name, status
     FROM user_objects
    WHERE object_type IN ('TABLE','VIEW','TRIGGER','FUNCTION','PROCEDURE')
    ORDER BY 1, 2;
   ```
   Devem aparecer **28 objetos**, todos `VALID`.
5. No FreeSQL, abra **Connect → Java (JDBC)**. Aparece um exemplo de código Java; dele você só precisa de três valores:
   a URL (variável `host`, começa com `jdbc:oracle:thin:@(description=...`), o **usuário** (`..._SCHEMA_...`) e a **senha**.
   Você vai usar no passo 4. A conexão é TLS (porta 2484) com certificado público — **não precisa de wallet**.

### Opção B — Oracle Free no Docker

1. Suba o container (a primeira vez baixa ~1 GB):

   **Linux/macOS:**
   ```bash
   docker run -d --name ecopower-oracle -p 1521:1521 \
     -e ORACLE_PASSWORD=admin \
     -e APP_USER=ecopower -e APP_USER_PASSWORD=ecopower \
     gvenzl/oracle-free:23-slim
   ```
   **Windows (PowerShell, uma linha só):**
   ```powershell
   docker run -d --name ecopower-oracle -p 1521:1521 -e ORACLE_PASSWORD=admin -e APP_USER=ecopower -e APP_USER_PASSWORD=ecopower gvenzl/oracle-free:23-slim
   ```

2. Espere o banco ficar pronto (1–3 minutos):
   ```bash
   docker logs -f ecopower-oracle
   ```
   Quando aparecer `DATABASE IS READY TO USE!`, aperte `Ctrl+C`.

3. Rode os scripts (a partir da raiz do projeto):
   ```bash
   docker cp database/oracle ecopower-oracle:/tmp/sql
   docker exec -it ecopower-oracle sqlplus ecopower/ecopower@localhost/FREEPDB1 @/tmp/sql/01_tabelas.sql
   docker exec -it ecopower-oracle sqlplus ecopower/ecopower@localhost/FREEPDB1 @/tmp/sql/02_triggers_views.sql
   docker exec -it ecopower-oracle sqlplus ecopower/ecopower@localhost/FREEPDB1 @/tmp/sql/03_procedimentos_app.sql
   docker exec -it ecopower-oracle sqlplus ecopower/ecopower@localhost/FREEPDB1 @/tmp/sql/dados_exemplo.sql
   ```
   Em cada um, o SQL\*Plus fica aberto no final: digite `exit` e Enter.

4. No dia a dia:
   ```bash
   docker stop ecopower-oracle     # desliga (os dados ficam salvos)
   docker start ecopower-oracle    # liga de novo
   ```

> **Dica:** para usar uma interface gráfica no banco local, conecte o DBeaver ou o *SQL Developer for VS Code* em
> `localhost`, porta `1521`, service name `FREEPDB1`, usuário `ecopower`, senha `ecopower`.

---

## 4. Configurar a conexão do app

O app lê os dados do banco de um arquivo que **não vai para o git** (está no `.gitignore`), para ninguém subir senha no GitHub.

```bash
cd ecopower/sistema
cp config.example.properties config.local.properties      # Windows: copy config.example.properties config.local.properties
```

Abra o `config.local.properties` e deixe **só uma** das configurações ativa (sem `#` na frente):

**Se usou o FreeSQL (Opção A):**
```properties
db.url=jdbc:oracle:thin:@(description=(address=(protocol=tcps)(port=2484)(host=db.freesql.com))(connect_data=(service_name=SEU_SERVICE_NAME)))
db.user=SEU_USUARIO_SCHEMA
db.password=SUA_SENHA
```

**Se usou o Docker (Opção B)** — já vem preenchido assim:
```properties
db.url=jdbc:oracle:thin:@//localhost:1521/FREEPDB1
db.user=ecopower
db.password=ecopower
```

As duas últimas linhas definem o usuário do app (se o e-mail não existir na tabela `usuarios`, o app cria):
```properties
app.usuario.email=morador@exemplo.com
app.usuario.nome=Morador Exemplo
```
Com `morador@exemplo.com`, o app abre já com os dados de exemplo.

> Também dá para passar a configuração sem arquivo, por variável de ambiente
> (`ECOPOWER_DB_URL`, `ECOPOWER_DB_USER`, `ECOPOWER_DB_PASSWORD`) ou por `-Ddb.url=...` no Maven.

---

## 5. Rodar o app

Ainda dentro de `ecopower/sistema`:

```bash
mvn compile exec:java
```

Na primeira vez o Maven baixa o driver do Oracle (`ojdbc11`) — demora um pouco. Depois deve aparecer:

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

Os testes **não precisam de banco**: o `pom.xml` desliga o banco durante os testes (`ecopower.banco=off`),
e o app funciona só em memória, como na versão original.

> Um teste (`estimativaCalculaConsumoFaturadoEValorTotal`) espera o valor formatado como `R$ 11,00`
> e falha em computadores com idioma em inglês. Para rodar com formato brasileiro:
> ```bash
> mvn test "-DargLine=-Duser.language=pt -Duser.country=BR"
> ```

---

## 6. Roteiro de teste / apresentação

Com os dados de exemplo carregados, siga esta sequência para mostrar cada requisito.
A parte do app usa o menu; a parte do banco (alertas, relatórios) é mostrada no **editor SQL** (FreeSQL/DBeaver).

| Passo | Onde | O que fazer / mostrar | Requisito |
|---|---|---|---|
| 1 | menu `2` | Lista dos 5 aparelhos — vieram do banco | RF01 |
| 2 | menu `1` | Cadastrar um aparelho novo (ex.: Micro-ondas, Cozinha, 1200 W) | RF01 |
| 3 | menu `5` | Registrar uma leitura. Data no formato **`dd/mm/aaaa hh:mm`** (ex.: `12/11/2026 18:30`) | RF02 |
| 4 | menu `6` | Listar leituras + consumo acumulado | RF02 |
| 5 | menu `7`, `8`, `9` | Tarifa (já vem do banco), estimativa da conta e projeção | RF04 |
| 6 | menu `10` | Definir meta de **100 kWh** (a meta fica salva no banco) | RF03 |
| 7 | editor SQL | `SELECT * FROM alertas;` → alerta **EXCEDIDO** gravado pelo **trigger** | RF03 |
| 8 | menu `11` | Relatório e dicas do app | RF05 |
| 9 | editor SQL | `SELECT * FROM vw_consumo_dispositivo_mes;` e `SELECT * FROM vw_situacao_meta;` | RF05 |

Feche e abra o app de novo e use a opção `2`: o aparelho cadastrado no passo 2 continua lá — prova de que está no banco.

Para mostrar o lado do banco de dados, abra `database/oracle/consultas.sql` no editor e rode as consultas
(JOIN, GROUP BY/HAVING, subconsulta, `RANK`, `LAG`).

---

## 7. Problemas comuns

| Mensagem | Causa | Solução |
|---|---|---|
| `Aviso: banco não configurado ... dados ficam só em memória` | Não existe `config.local.properties` ou você rodou fora de `ecopower/sistema` | Faça o passo 4 e rode o comando dentro de `ecopower/sistema` |
| `ORA-01017: invalid username/password` | Usuário ou senha errados | Confira `db.user` e `db.password` |
| `ORA-12541` / `IO Error: The Network Adapter could not establish the connection` | Banco desligado, host/porta errados ou sem internet (FreeSQL) | Docker: `docker start ecopower-oracle`. FreeSQL: confira host e porta |
| `ORA-12514: listener does not currently know of service` | Service name errado | Docker: use `FREEPDB1`. FreeSQL: copie o service name exato da sua conta |
| `ORA-00942: table or view does not exist` | `01_tabelas.sql` não foi rodado neste usuário | Rode os scripts na ordem 01 → 02 → 03 |
| `PLS-00201: identifier 'PR_...' / 'FN_...' must be declared` | `03_procedimentos_app.sql` não foi rodado | Rode o `03_procedimentos_app.sql` |
| `ORA-00955: name is already used by an existing object` | As tabelas já existem (rodou o `01` de novo) | Veja "Recomeçar o banco do zero" abaixo |
| `ORA-04098: trigger ... is invalid` | `02` rodou antes do `01` ou deu erro | Rode `01` e depois `02` de novo |
| `Data invalida: "..."` | Data digitada em outro formato | Use `dd/mm/aaaa hh:mm`, ex.: `12/11/2026 18:30` |
| `Bind for 0.0.0.0:1521 failed: port is already allocated` | Outro Oracle já usa a porta 1521 | Use `-p 1522:1521` no `docker run` e `localhost:1522` no `db.url` |
| `mvn: command not found` | Maven não instalado / fora do `PATH` | Volte ao passo 1 |
| `release version 21 not supported` | Java mais antigo que o 21 | Instale o JDK 21 e confira `java -version` |

### Recomeçar o banco do zero

O `01_tabelas.sql` não apaga nada antes de criar. Para recomeçar, rode este bloco no editor SQL
(**apaga todas as tabelas do seu usuário**) e depois os scripts 01 → 02 → 03 → dados_exemplo:

```sql
DROP TABLE IF EXISTS alertas      CASCADE CONSTRAINTS PURGE;
DROP TABLE IF EXISTS metas        CASCADE CONSTRAINTS PURGE;
DROP TABLE IF EXISTS tarifas      CASCADE CONSTRAINTS PURGE;
DROP TABLE IF EXISTS leituras     CASCADE CONSTRAINTS PURGE;
DROP TABLE IF EXISTS dispositivos CASCADE CONSTRAINTS PURGE;
DROP TABLE IF EXISTS usuarios     CASCADE CONSTRAINTS PURGE;
```

---

## 8. Como as peças se encaixam (para explicar ao grupo)

**Todo o SQL fica no banco.** O Java não tem nenhum `SELECT`, `INSERT`, `UPDATE` ou `DELETE`: ele só chama
procedures e funções pelo nome.

```
view/        (menu — igual ao original)
  │
services/    (iguais ao original + 1 linha por método chamando o DAO)
  │
dao/         (1 classe por tabela: DispositivoDAO, LeituraDAO, TarifaDAO, MetaDAO)
  │            só fazem  {call pr_inserir_dispositivo(?, ?, ?, ?, ?)}  e afins
  ▼
Oracle       03_procedimentos_app.sql → procedures/funções com o SQL de verdade
             02_triggers_views.sql    → triggers, views e funções de cálculo
             01_tabelas.sql           → tabelas
```

| Ação no menu | O Java chama | O que roda no banco |
|---|---|---|
| Iniciar o app | `fn_listar_dispositivos`, `fn_listar_leituras`, `fn_tarifa_atual`, `fn_meta_do_mes` | `SELECT`s devolvidos como `SYS_REFCURSOR` |
| 1 / 3 / 4 — aparelho | `pr_inserir_dispositivo` / `pr_atualizar_dispositivo` / `pr_remover_dispositivo` | `INSERT ... RETURNING id`, `UPDATE`, `DELETE` (leituras saem junto por `ON DELETE CASCADE`) |
| 5 — leitura | `pr_inserir_leitura` | converte a data (`fn_converter_data`), `INSERT`; o trigger calcula `consumo_kwh` e confere a meta |
| 7 — tarifa | `pr_salvar_tarifa` | `INSERT` (guarda histórico) |
| 10 — meta | `pr_salvar_meta` | `MERGE` (uma meta por mês); o trigger grava o alerta se passou |

Outros pontos para explicar:
- **Mudanças no Java** em relação à versão original: `Aparelho` ganhou um `id`; cada service carrega os dados do
  banco ao iniciar e chama o DAO antes de alterar a lista em memória; a `TarifaView` salva a tarifa; o `switch`
  do menu ganhou os `break` que faltavam (sem eles, cada opção executava todas as seguintes).
- A classe `Aparelho` é gravada na tabela `dispositivos` (atributo `comodo` → coluna `localizacao`).
- **Triggers de comando** (`trg_leituras_verifica_meta`, `trg_metas_verifica`, sem `FOR EACH ROW`): precisam somar
  a própria tabela `leituras`; num trigger de linha isso dá o erro **ORA-04091 (tabela mutante)**.
- **Erros de regra** são lançados no banco com `RAISE_APPLICATION_ERROR` (ex.: data inválida) e aparecem no menu.
- **Sem banco configurado** o app funciona como o original (só memória) — é assim que os testes rodam.

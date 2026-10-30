# Estrutura do projeto

## Classificação

Aplicação Java de linha de comando (CLI), monomódulo Maven, sem camada web, banco de dados ou persistência externa identificada.

## Módulo e entrada

- `sistema/`: módulo Maven principal.
- `src/main/java/br/com/ecopower/Main.java`: entrada da aplicação.
- `br.com.ecopower.view.MenuView`: inicia o fluxo interativo do terminal.

## Camadas encontradas

- `model/`: seis classes de domínio: `Aparelho`, `EstimativaCusto`, `Leitura`, `Meta`, `Relatorios` e `TarifaEnergia`.
- `services/`: cinco serviços que mantêm coleções em memória, aplicam operações e calculam resultados.
- `view/`: oito classes de interação com o terminal, incluindo menu, entrada e views por domínio.
- `Main.java`: bootstrap mínimo.
- `src/test/java/`: uma classe de testes com cobertura dos modelos e serviços, mas não das views.

## Áreas funcionais

1. Cadastro, edição, listagem e remoção de aparelhos.
2. Registro e consulta de leituras de consumo.
3. Configuração de tarifa energética.
4. Estimativa do custo atual e projeção mensal.
5. Definição e verificação de metas.
6. Geração de relatórios e dicas de economia.

## Observações arquiteturais

A decomposição por pacotes está coerente com uma aplicação didática em camadas, mas os serviços ainda escrevem no console e expõem coleções internas. Isso cria acoplamento entre domínio, serviço e interface. O estado é efêmero: toda a informação é perdida ao encerrar o processo.

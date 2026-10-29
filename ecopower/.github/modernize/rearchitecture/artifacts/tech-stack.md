# Stack tecnológica

- Linguagem: Java.
- Compilação: Java 21, conforme `maven.compiler.source` e `maven.compiler.target` no `pom.xml`.
- Build: Maven, módulo `sistema`.
- Dependência de produção: nenhuma biblioteca externa declarada.
- Dependência de teste: JUnit Jupiter `6.1.3`.
- Interface: terminal via `java.util.Scanner` e `System.out`.
- Persistência: nenhuma; `ArrayList`, `HashMap` e estado em memória.
- Framework web, ORM, mensageria, autenticação e configuração externa: não identificados.

## Restrições observadas

- O ambiente atual não disponibilizou o comando `mvn`; a compilação direta com `javac -encoding UTF-8` foi possível.
- Os arquivos exibem textos com sinais de inconsistência de codificação, como `Ã§` e `Ã£`, indicando que a codificação dos fontes/terminal precisa ser padronizada.
- O código usa `var` e switch expression com seta, portanto depende de Java 21 (ou de versão compatível com esses recursos).

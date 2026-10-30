# Índice da análise arquitetural

Este índice não é o contrato completo. Não implemente a partir deste arquivo sozinho; siga os caminhos dos artefatos abaixo.

## Implementation Guide

### Artefatos globais

- `unit_graph.yaml`: unidade CLI, entrada, assinaturas e dependências.
- `project-structure.md`: camadas, domínios e classificação do projeto.
- `tech-stack.md`: Java 21, Maven, JUnit e restrições observadas.
- `data-model.md`: entidades, relações e estado em memória.
- `wire_contracts.yaml`: deve ser lido por mudanças na interface externa; atualmente não há contratos externos.
- `shared_modules.yaml`: deve ser filtrado por unidade; atualmente não há módulos compartilhados entre unidades.
- `cross_unit_state.yaml`: deve ser lido por mudanças de estado entre unidades; atualmente não há múltiplas unidades.

### Unidade: `console-application`

- Trigger: execução de `br.com.ecopower.Main` e interação pelo menu textual.
- `units/console-application/behavior.yaml`: preservar operações, mensagens, branches e caminhos de erro.
- `units/console-application/bindings.yaml`: confirmar ausência de wiring de framework e configuração externa.
- `units/console-application/unit_decomposition.yaml`: candidatos de divisão são sugestões; `commit: false` significa que não são decisões estruturais.
- Antes de concluir qualquer mudança, relatar artefatos lidos, comportamentos preservados, pendências e evidência de compilação/teste/runtime.

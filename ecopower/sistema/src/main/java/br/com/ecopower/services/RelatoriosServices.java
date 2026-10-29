package br.com.ecopower.services;

import java.util.HashMap;
import java.util.Map;

import br.com.ecopower.model.Relatorios;

public class RelatoriosServices {
    private final Map<Integer, Relatorios> relatorios = new HashMap<>();

    public void adicionarRelatorio(Relatorios relatorio) {
        verificarRelatorio(relatorio);

        relatorios.put(relatorio.getId(), relatorio);
            System.out.println("Relatório criado e adicionado com sucesso!");
    }

    public void mostrarAparelhoDeMaiorConsumo(Relatorios relatorio) {
        verificarRelatorio(relatorio);

        System.out.println("Aparelho de maior consumo: "
                + relatorio.getNomeMaiorConsumo()
                + " | Consumo: " + relatorio.getMaiorConsumo() + " kWh");
    }

    public void mostrarRelatorios() {
        if (relatorios.isEmpty()) {
            throw new IllegalArgumentException("Erro!, nenhum relatório cadastrado");
        }

        for (Relatorios relatorio : relatorios.values()) {
            System.out.println("========================================");
            System.out.println("RELATÓRIO DE CONSUMO DE ENERGIA");
            System.out.println("ID: " + relatorio.getId());
            System.out.println("Dias de uso: " + relatorio.getDiasDeUso());

            System.out.println("\nConsumo por aparelho:");
            relatorio.getAparelhos().forEach((nomeAparelho, consumo) ->
                    System.out.println("- " + nomeAparelho + ": " + consumo + " kWh"));

            System.out.println("\nConsumo total da casa: "
                    + relatorio.getConsumoTotal() + " kWh");
            System.out.println("Aparelho que mais consumiu energia: "
                    + relatorio.getNomeMaiorConsumo());
            System.out.println("Maior consumo: "
                    + relatorio.getMaiorConsumo() + " kWh");
            System.out.println("========================================");
        }
    }

    public void mostrarDicas(Relatorios relatorio) {
        verificarRelatorio(relatorio);

        System.out.println("--- DICAS ---");
        System.out.println("1 - O aparelho que mais consumiu foi "
                + relatorio.getNomeMaiorConsumo()
                + ". Reveja seu uso diário para identificar formas de economizar energia.");
        System.out.println();
        System.out.println("2 - Analise o consumo dos demais aparelhos para identificar oportunidades de economia.");
        System.out.println();
        System.out.println("3 - Verifique se algum aparelho ou instalação elétrica apresenta defeito ou consumo anormal.");
        System.out.println();
        System.out.println("4 - Utilize aparelhos com boa eficiência energética e evite deixá-los ligados sem necessidade.");
    }

    private void verificarRelatorio(Relatorios relatorio) {
        if (relatorio == null) {
            throw new IllegalArgumentException("Erro!, relatório não pode ser nulo");
        }
    }
}

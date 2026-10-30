package br.com.ecopower.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Relatorios {
    private final String nome;
    private final String nomeMaiorConsumo;
    private final double consumoPorAparelho;
    private final double maiorConsumo;
    private final double consumoTotal;
    private final Map<String, Double> aparelhos;
    private final int id;
    private static int proximo = 0;
    private int diasDeUso;

    public Relatorios(int diasDeUso, List<Leitura> leituras) {
        verificarDiasDeUso(diasDeUso);
        verificarLeituras(leituras);

        Map<String, Double> consumoPorNome = new LinkedHashMap<>();
        for (Leitura leitura : leituras) {
            consumoPorNome.merge(
                    leitura.getNomeDoAparelho().trim(),
                    leitura.getConsumoEmKWh(),
                    Double::sum);
        }

        Map.Entry<String, Double> maior = consumoPorNome.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalArgumentException("Erro!, lista de leituras não pode ser vazia"));

        this.aparelhos = Collections.unmodifiableMap(new LinkedHashMap<>(consumoPorNome));
        this.consumoTotal = consumoPorNome.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        this.nomeMaiorConsumo = maior.getKey();
        this.maiorConsumo = maior.getValue();
        this.nome = maior.getKey();
        this.consumoPorAparelho = maior.getValue();
        this.diasDeUso = diasDeUso;
        this.id = proximo++;
    }

    public String getNome() {
        return this.nome;
    }

    public double getConsumoPorAparelho() {
        return this.consumoPorAparelho;
    }

    public double getMaiorConsumo() {
        return this.maiorConsumo;
    }

    public double getConsumoTotal() {
        return this.consumoTotal;
    }

    public Map<String, Double> getAparelhos() {
        return this.aparelhos;
    }

    public String getNomeMaiorConsumo() {
        return this.nomeMaiorConsumo;
    }

    public int getDiasDeUso() {
        return this.diasDeUso;
    }

    public void setAlterarDiasDeUso(int diasDeUso) {
        verificarDiasDeUso(diasDeUso);
        this.diasDeUso = diasDeUso;
    }

    public int getId() {
        return this.id;
    }

    private void verificarDiasDeUso(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Erro!, dias de uso não pode ser zero ou negativo!");
        }
    }

    private void verificarLeituras(List<Leitura> leituras) {
        if (leituras == null || leituras.isEmpty()) {
            throw new IllegalArgumentException(
                    "Erro!, lista de leituras não pode ser nula ou vazia");
        }
    }

    @Override
    public String toString() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório de consumo de energia\n")
                .append("ID: ").append(getId()).append('\n')
                .append("Dias de uso: ").append(getDiasDeUso()).append('\n')
                .append("\nConsumo por aparelho:\n");

        aparelhos.forEach((nomeAparelho, consumo) ->
                relatorio.append("- ").append(nomeAparelho)
                        .append(": ").append(consumo).append(" kWh\n"));

        relatorio.append("\nConsumo total da casa: ").append(getConsumoTotal()).append(" kWh\n")
                .append("Aparelho que mais consumiu: ").append(getNomeMaiorConsumo())
                .append(" (").append(getMaiorConsumo()).append(" kWh)");

        return relatorio.toString();
    }
}

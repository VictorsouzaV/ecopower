package br.com.ecopower.model;

public class TarifaEnergia {
    public static final double MIN_TARIFA_POR_KWH = 0.01;
    public static final double MAX_TARIFA_POR_KWH = 10.00;
    public static final double MAX_BANDEIRA_POR_KWH = 5.00;
    public static final double MAX_PERCENTUAL_INDIVIDUAL = 1.00;
    public static final double MAX_PERCENTUAIS_ACUMULADOS = 1.00;
    public static final double MAX_ILUMINACAO_PUBLICA = 1000.00;
    public static final int MAX_MINIMO_KWH = 1000;

    private final double tarifaPorKWh;
    private final double valorBandeiraPorKWh;
    private final double percentualEncargosSetoriais;
    private final double percentualICMS;
    private final double percentualPIS;
    private final double percentualCOFINS;
    private final double iluminacaoPublica;
    private final int minimoKWh;

    public TarifaEnergia(double tarifaPorKWh, double valorBandeiraPorKWh,
            double percentualEncargosSetoriais, double percentualICMS,
            double percentualPIS, double percentualCOFINS,
            double iluminacaoPublica, int minimoKWh) {
        verificarFaixa(tarifaPorKWh, MIN_TARIFA_POR_KWH, MAX_TARIFA_POR_KWH, "tarifa por kWh");
        verificarFaixa(valorBandeiraPorKWh, 0, MAX_BANDEIRA_POR_KWH, "bandeira por kWh");
        verificarPercentual(percentualEncargosSetoriais, "encargos setoriais");
        verificarPercentual(percentualICMS, "ICMS");
        verificarPercentual(percentualPIS, "PIS");
        verificarPercentual(percentualCOFINS, "COFINS");
        double totalPercentuais = percentualEncargosSetoriais + percentualICMS + percentualPIS + percentualCOFINS;
        if (!Double.isFinite(totalPercentuais) || totalPercentuais > MAX_PERCENTUAIS_ACUMULADOS) {
            throw new IllegalArgumentException("A soma dos percentuais de encargos, ICMS, PIS e COFINS deve ser no máximo 100%.");
        }
        verificarFaixa(iluminacaoPublica, 0, MAX_ILUMINACAO_PUBLICA, "iluminacao publica");
        if (minimoKWh < 1 || minimoKWh > MAX_MINIMO_KWH) {
            throw new IllegalArgumentException("O consumo minimo faturado deve estar entre 1 e " + MAX_MINIMO_KWH + " kWh.");
        }

        this.tarifaPorKWh = tarifaPorKWh;
        this.valorBandeiraPorKWh = valorBandeiraPorKWh;
        this.percentualEncargosSetoriais = percentualEncargosSetoriais;
        this.percentualICMS = percentualICMS;
        this.percentualPIS = percentualPIS;
        this.percentualCOFINS = percentualCOFINS;
        this.iluminacaoPublica = iluminacaoPublica;
        this.minimoKWh = minimoKWh;
    }

    public double getTarifaPorKWh() { return tarifaPorKWh; }
    public double getValorBandeiraPorKWh() { return valorBandeiraPorKWh; }
    public double getPercentualEncargosSetoriais() { return percentualEncargosSetoriais; }
    public double getPercentualICMS() { return percentualICMS; }
    public double getPercentualPIS() { return percentualPIS; }
    public double getPercentualCOFINS() { return percentualCOFINS; }
    public double getIluminacaoPublica() { return iluminacaoPublica; }
    public double getCustoFixo() { return iluminacaoPublica; }
    public int getMinimoKWh() { return minimoKWh; }

    private void verificarFaixa(double valor, double minimo, double maximo, String campo) {
        if (!Double.isFinite(valor) || valor < minimo || valor > maximo) {
            throw new IllegalArgumentException("Valor de " + campo + " fora do limite permitido: " + minimo + " a " + maximo + ".");
        }
    }

    private void verificarPercentual(double percentual, String campo) {
        if (!Double.isFinite(percentual) || percentual < 0 || percentual > MAX_PERCENTUAL_INDIVIDUAL) {
            throw new IllegalArgumentException("Percentual de " + campo + " deve estar entre 0% e 100%.");
        }
    }

    @Override
    public String toString() {
        return "TarifaEnergia{" +
                "tarifaPorKWh=" + tarifaPorKWh +
                ", valorBandeiraPorKWh=" + valorBandeiraPorKWh +
                ", percentualEncargosSetoriais=" + percentualEncargosSetoriais +
                ", percentualICMS=" + percentualICMS +
                ", percentualPIS=" + percentualPIS +
                ", percentualCOFINS=" + percentualCOFINS +
                ", iluminacaoPublica=" + iluminacaoPublica +
                ", minimoKWh=" + minimoKWh +
                '}';
    }
}

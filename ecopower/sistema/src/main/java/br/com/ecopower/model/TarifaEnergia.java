package br.com.ecopower.model;

public class TarifaEnergia {
    private final double tarifaPorKWh;
    private final double valorBandeiraPorKWh;
    private final double percentualEncargosSetoriais;
    private final double percentualICMS;
    private final double percentualPIS;
    private final double percentualCOFINS;
    private final double iluminacaoPublica;
    private final int minimoKWh;

    public TarifaEnergia(double tarifaPorKWh, double valorBandeiraPorKWh, double percentualEncargosSetoriais, double percentualICMS, double percentualPIS, double percentualCOFINS, double iluminacaoPublica, int minimoKWh) {
        verificarTarifa(tarifaPorKWh, "tarifa por kWh");
        verificarBandeira(valorBandeiraPorKWh, "valor da bandeira por kWh");
        verificarPercentualEncargos(percentualEncargosSetoriais, "encargos setoriais");
        verificarPercentualIcms(percentualICMS, "ICMS");
        verificarPercentualPis(percentualPIS, "PIS");
        verificarPercentualCofins(percentualCOFINS, "COFINS");
        verificarIluminacaoPublica(iluminacaoPublica, "iluminação pública");
        verificarMinimoKWh(minimoKWh, "mínimo de kWh");

            this.tarifaPorKWh = tarifaPorKWh;
            this.valorBandeiraPorKWh = valorBandeiraPorKWh;
            this.percentualEncargosSetoriais = percentualEncargosSetoriais;
            this.percentualICMS = percentualICMS;
            this.percentualPIS = percentualPIS;
            this.percentualCOFINS = percentualCOFINS;
            this.iluminacaoPublica = iluminacaoPublica;
            this.minimoKWh = minimoKWh;
    }

    public double getTarifaPorKWh() {
        return this.tarifaPorKWh;
    }

    public double getValorBandeiraPorKWh() {
        return this.valorBandeiraPorKWh;
    }

    public double getPercentualEncargosSetoriais() {
        return this.percentualEncargosSetoriais;
    }

    public double getPercentualICMS() {
        return this.percentualICMS;
    }

    public double getPercentualPIS() {
        return this.percentualPIS;
    }

    public double getPercentualCOFINS() {
        return this.percentualCOFINS;
    }

    public double getIluminacaoPublica() {
        return this.iluminacaoPublica;
    }

    public double getCustoFixo() {
        return this.iluminacaoPublica;
    }

    public int getMinimoKWh() {
        return this.minimoKWh;
    }

    private void verificarTarifa(double tarifaPorKWh, String nome) {
        if (tarifaPorKWh <= 0) {
            throw new IllegalArgumentException("Erro!, " + nome + " não pode ser menor ou igual a zero!");
        }
    }

    private void verificarBandeira(double valorBandeiraPorKWh, String nome) {
        if (valorBandeiraPorKWh <= 0) {
            throw new IllegalArgumentException("Erro!, " + nome + " não pode ser menor ou igual a zero!");
        }
    }

    private void verificarPercentualEncargos(double percentualEncargos, String nome) {
        if (percentualEncargos <= 0) {
            throw new IllegalArgumentException("Erro!, percentual de " + nome + " não pode ser menor ou igual a zero!");
        }
    }

    private void verificarPercentualIcms(double percentualIcms, String nome) {
        if (percentualIcms <= 0) {
            throw new IllegalArgumentException("Erro!, percentual de " + nome + " não pode ser menor ou igual a zero!");
        }
    }

    private void verificarIluminacaoPublica(double iluminacaoPublica, String nome) {
        if (iluminacaoPublica <= 0) {
            throw new IllegalArgumentException("Erro!, valor de " + nome + " não pode ser menor ou igual a zero!");
        }
    }


    private void verificarPercentualPis(double percentualPis, String nome) {
        if (percentualPis <= 0) {
            throw new IllegalArgumentException("Erro!, percentual de " + nome + " não pode ser menor ou igual a zero!");
        }
    }


    private void verificarPercentualCofins(double percentualCofins, String nome) {
        if (percentualCofins <= 0) {
            throw new IllegalArgumentException("Erro!, percentual de " + nome + " não pode ser menor ou igual a zero!");
        }
    }

    private void verificarMinimoKWh(int minimoKWh, String nome) {
        if (minimoKWh <= 0) {
            throw new IllegalArgumentException("Erro!, valor mínimo de " + nome + " não pode ser menor ou igual a zero!");
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

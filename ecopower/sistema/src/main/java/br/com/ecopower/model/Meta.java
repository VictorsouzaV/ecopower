package br.com.ecopower.model;

public class Meta {
    private double valorMensal;
    private String tipoUnidade;
    private double percentualAlerta = 80.0;

    public Meta(double valorMensal, String unidade) {
        verificarValorMensal(valorMensal);
        verificarUnidade(unidade);

            this.valorMensal = valorMensal;
            this.tipoUnidade = unidade.toLowerCase();
    }

    public double getValorMensal() {
        return this.valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        verificarValorMensal(valorMensal);
        this.valorMensal = valorMensal;
    }

    public String getTipoUnidade() {
        return this.tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        verificarUnidade(tipoUnidade);
        this.tipoUnidade = tipoUnidade.toLowerCase();
    }

    public double getPercentualAlerta() {
        return this.percentualAlerta;
    }

    public void setPercentualAlerta(double percentualAlerta) {
        if (percentualAlerta <= 0 || percentualAlerta > 100) {
            throw new IllegalArgumentException("Erro, percentual de alerta deve estar entre 0 e 100!");
        }
        this.percentualAlerta = percentualAlerta;
    }

    public String verificarConsumo(double consumoMensal) {
        if (consumoMensal <= 0) {
            throw new IllegalArgumentException("Erro, consumo mensal não pode ser zero ou negativo!");
        }

        if (consumoMensal > valorMensal) {
            return "Meta mensal excedida";
        }

        if (consumoMensal >= valorMensal * percentualAlerta / 100) {
            return "proximo do limite mensal!";
        }

        return "dentro do limite mensal";
    }

    private void verificarValorMensal(double valorMensal) {
        if (valorMensal <= 0) {
            throw new IllegalArgumentException("Erro, valor da meta deve ser maior que zero!");
        }
    }

    private void verificarUnidade(String tipoUnidade) {
        if (tipoUnidade == null || tipoUnidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro, unidade da meta não pode ser nula ou vazia!");
        }

        if (!tipoUnidade.equalsIgnoreCase("kWh") && !tipoUnidade.equalsIgnoreCase("reais")) {
            throw new IllegalArgumentException("Erro, unidade deve ser kWh ou reais!");
        }
    }

    @Override
    public String toString() {
        return "Meta{" +
                "valorMensal=" + valorMensal +
                ", tipoUnidade='" + tipoUnidade + '\'' +
                ", percentualAlerta=" + percentualAlerta +
                '}';
    }
}

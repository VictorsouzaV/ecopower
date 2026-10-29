package br.com.ecopower.model;

public class EstimativaCusto {
    private final double consumoKWh;
    private final double tarifaPorKWh;      
    private final double valorDaBandeira; 
    private final double iluminacaoPublica;
    private final int minimoKWh;          

    public EstimativaCusto(double consumoKWh, double tarifaPorKWh, double valorDaBandeira, double iluminacaoPublica, int minimoKWh) {
        verificarConsumo(consumoKWh);
        verificarTarifa(tarifaPorKWh);
        verificarAdicionalBandeira(valorDaBandeira);
        verificarIluminacaoPublica(iluminacaoPublica);
        verificarMinimoKWh(minimoKWh);

            this.consumoKWh = consumoKWh;
            this.tarifaPorKWh = tarifaPorKWh;
            this.valorDaBandeira = valorDaBandeira;
            this.iluminacaoPublica = iluminacaoPublica;
            this.minimoKWh = minimoKWh;
    }

    public double getConsumoFaturado() {
        return Math.max(consumoKWh, minimoKWh);
    }

    public double getValorTotal() {
        return getConsumoFaturado() * (tarifaPorKWh + valorDaBandeira) + iluminacaoPublica;
    }

    private void verificarConsumo(double consumoKWh) {
        if (consumoKWh <= 0) {
            throw new IllegalArgumentException("Erro, consumo em kWh não pode ser menor ou igual a zero!");
        }
    }

    private void verificarTarifa(double tarifaPorKWh) {
        if (tarifaPorKWh <= 0) {
            throw new IllegalArgumentException("Erro, tarifa por kWh não pode ser menor ou igual a zero!");
        }
    }

    private void verificarAdicionalBandeira(double adicionalBandeira) {
        if (adicionalBandeira <= 0) {
            throw new IllegalArgumentException("Erro, adicional de bandeira não pode ser menor ou igual a zero!");
        }
    }

    private void verificarIluminacaoPublica(double iluminacaoPublica) {
        if (iluminacaoPublica <= 0) {
            throw new IllegalArgumentException("Erro, valor de iluminação pública não pode ser menor ou igual a zero!");
        }
    }

    private void verificarMinimoKWh(int minimoKWh) {
        if (minimoKWh <= 0) {
            throw new IllegalArgumentException("Erro, valor mínimo de kWh não pode ser menor ou igual a zero!");
        }
    }

    @Override
    public String toString() {
        return String.format("Estimativa de conta: R$ %.2f (consumo: %.2f kWh)", getValorTotal(), consumoKWh);
    }
}

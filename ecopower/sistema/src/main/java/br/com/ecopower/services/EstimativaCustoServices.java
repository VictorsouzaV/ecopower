package br.com.ecopower.services;

import br.com.ecopower.model.EstimativaCusto;
import br.com.ecopower.model.TarifaEnergia;

public class EstimativaCustoServices {

    private EstimativaCusto estimar(double consumoAcumuladoEmKWh, TarifaEnergia tarifa) {
        validarConsumo(consumoAcumuladoEmKWh);
        
        if (tarifa == null) {
            throw new IllegalArgumentException("Erro, tarifa de energia não pode ser nula!");
        }

        var fatorTributacao = 1
                + tarifa.getPercentualEncargosSetoriais()
                + tarifa.getPercentualICMS()
                + tarifa.getPercentualPIS()
                + tarifa.getPercentualCOFINS();

        return new EstimativaCusto(
                consumoAcumuladoEmKWh,
                tarifa.getTarifaPorKWh() * fatorTributacao,
                tarifa.getValorBandeiraPorKWh() * fatorTributacao,
                tarifa.getIluminacaoPublica(),
                tarifa.getMinimoKWh());
    }

    public EstimativaCusto estimarCusto(LeituraServices leituraServices, TarifaEnergia tarifa) {
        if (leituraServices == null) {
            throw new IllegalArgumentException("Erro, serviço de leituras não pode ser nulo!");
        }
        return estimar(leituraServices.getConsumoAcumuladoEmKWh(), tarifa);
    }

    private EstimativaCusto estimarFimDoMes(double consumoAcumuladoEmKWh, int diasDecorridos, int diasNoMes, TarifaEnergia tarifa) {
        if (diasNoMes <= 0 || diasDecorridos <= 0 || diasDecorridos > diasNoMes) {
            throw new IllegalArgumentException(
                    "Erro, dias decorridos deve estar entre 1 e o total de dias do mês não pode ser zero ou negativo!");
        }

        validarConsumo(consumoAcumuladoEmKWh);

        double consumoProjetado = consumoAcumuladoEmKWh * diasNoMes / diasDecorridos;
            return estimar(consumoProjetado, tarifa);
    }

    public EstimativaCusto estimarCustoFimDoMes(LeituraServices leituraServices, int diasDecorridos, int diasNoMes, TarifaEnergia tarifa) {
        if (leituraServices == null) {
            throw new IllegalArgumentException("Erro, serviço de leituras não pode ser nulo!");
        }
        
        return estimarFimDoMes(leituraServices.getConsumoAcumuladoEmKWh(), diasDecorridos, diasNoMes, tarifa);
    }

    private void validarConsumo(double consumoAcumuladoEmKWh) {
        if (!Double.isFinite(consumoAcumuladoEmKWh) || consumoAcumuladoEmKWh <= 0) {
            throw new IllegalArgumentException("Erro, consumo acumulado não pode ser menor ou igual a zero!");
        }
    }
}

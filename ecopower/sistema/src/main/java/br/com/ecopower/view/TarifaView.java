package br.com.ecopower.view;

import br.com.ecopower.model.TarifaEnergia;

public class TarifaView {
    private TarifaEnergia tarifaAtual;

    public void configurar() {
        System.out.println("Percentuais devem ser informados como 18 para 18%.");

        tarifaAtual = new TarifaEnergia(
                InputView.lerDecimal("Tarifa base por kWh (R$): "),
                InputView.lerDecimal("Bandeira por kWh (R$): "),
                InputView.lerPercentual("Encargos setoriais (%): "),
                InputView.lerPercentual("ICMS (%): "),
                InputView.lerPercentual("PIS (%): "),
                InputView.lerPercentual("COFINS (%): "),
                InputView.lerDecimal("Iluminação pública (R$): "),
                InputView.lerInteiro("Consumo mínimo faturado (kWh): "));
        System.out.println("Tarifa configurada com sucesso.");
    }

    public TarifaEnergia getTarifaAtual() { 
        return tarifaAtual; 
    }
}

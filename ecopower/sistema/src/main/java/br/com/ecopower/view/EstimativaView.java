package br.com.ecopower.view;

import br.com.ecopower.model.EstimativaCusto;
import br.com.ecopower.model.TarifaEnergia;
import br.com.ecopower.services.EstimativaCustoServices;
import br.com.ecopower.services.LeituraServices;

public class EstimativaView {
    private final LeituraServices leituraServices;
    private final EstimativaCustoServices services;

    public EstimativaView(LeituraServices leituraServices, EstimativaCustoServices services) {
        this.leituraServices = leituraServices; 
        this.services = services;
    }

    public void exibir(TarifaEnergia tarifa) {
        exigirTarifa(tarifa);
            System.out.println(services.estimarCusto(leituraServices, tarifa));
    }

    public void exibirProjecao(TarifaEnergia tarifa) {
        exigirTarifa(tarifa);

        int diasDecorridos = InputView.lerInteiro("Dias já decorridos no mês: ");
        int diasNoMes = InputView.lerInteiro("Quantidade de dias do mês: ");

        EstimativaCusto estimativa = services.estimarCustoFimDoMes(leituraServices, diasDecorridos, diasNoMes, tarifa);
            System.out.println("Projeção: " + estimativa);
    }

    private void exigirTarifa(TarifaEnergia tarifa) {
        if (tarifa == null) {
            throw new IllegalStateException("Configure a tarifa antes de calcular custos.");
        }
    }
}

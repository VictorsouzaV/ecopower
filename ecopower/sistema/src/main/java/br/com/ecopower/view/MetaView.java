package br.com.ecopower.view;

import br.com.ecopower.services.MetaServices;

public class MetaView {
    private final MetaServices services;

    public MetaView(MetaServices services) { this.services = services; }

    public void definir() {
        double valor = InputView.lerDecimal("Valor limite da meta: ");
        String unidade = InputView.lerTexto("Unidade (kWh ou reais): ");

        services.definirMeta(valor, unidade);
            System.out.println(services.gerarAlerta(InputView.lerDecimal("Consumo atual na unidade escolhida: ")));
    }
}

package br.com.ecopower.view;

import br.com.ecopower.model.TarifaEnergia;

public class TarifaView {
    // Limites amplos para proteger a estimativa de entradas fora do padrao.
    private static final double MIN_TARIFA_POR_KWH = 0.01;
    private static final double MAX_TARIFA_POR_KWH = 10.00;
    private static final double MAX_BANDEIRA_POR_KWH = 5.00;
    private static final double MAX_ILUMINACAO_PUBLICA = 1000.00;
    private static final int MAX_MINIMO_KWH = 1000;

    private TarifaEnergia tarifaAtual;

    public void configurar() {
        System.out.println("Informe valores dentro dos limites para evitar tarifas fora do padrão.");
        double tarifaBase = InputView.lerDecimalEntre("Tarifa base por kWh (R$ 0,01 a R$ 10,00): ",
                MIN_TARIFA_POR_KWH, MAX_TARIFA_POR_KWH);
        double bandeira = InputView.lerDecimalEntre("Bandeira por kWh (R$ 0,00 a R$ 5,00): ",
                0, MAX_BANDEIRA_POR_KWH);

        double encargos = InputView.lerPercentualEntre("Encargos setoriais (0 a 100%): ", 100);
        double icms = InputView.lerPercentualEntre("ICMS (0 a 100%): ", (1 - encargos) * 100);
        double pis = InputView.lerPercentualEntre("PIS (0 a 100%): ", (1 - encargos - icms) * 100);
        double cofins = InputView.lerPercentualEntre("COFINS (0 a 100%): ", (1 - encargos - icms - pis) * 100);
        double iluminacao = InputView.lerDecimalEntre("Iluminação pública (R$ 0,00 a R$ 1.000,00): ",
                0, MAX_ILUMINACAO_PUBLICA);
        int minimo = InputView.lerInteiroEntre("Consumo mínimo faturado (1 a 1.000 kWh): ",
                1, MAX_MINIMO_KWH);

        tarifaAtual = new TarifaEnergia(tarifaBase, bandeira, encargos, icms, pis, cofins, iluminacao, minimo);
        System.out.println("Tarifa configurada com sucesso.");
    }

    public TarifaEnergia getTarifaAtual() {
        return tarifaAtual;
    }
}

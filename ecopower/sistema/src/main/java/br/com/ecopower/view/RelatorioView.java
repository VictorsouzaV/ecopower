package br.com.ecopower.view;

import br.com.ecopower.model.Leitura;
import br.com.ecopower.model.Relatorios;
import br.com.ecopower.services.LeituraServices;
import br.com.ecopower.services.RelatoriosServices;
import java.util.ArrayList;
import java.util.List;

public class RelatorioView {
    private final LeituraServices leituraServices;
    private final RelatoriosServices services;

    public RelatorioView(LeituraServices leituraServices, RelatoriosServices services) {
        this.leituraServices = leituraServices; this.services = services;
    }

    public void exibir() {
        int dias = InputView.lerInteiro("Quantidade de dias representados no relatório: ");
        
        List<Leitura> leituras = new ArrayList<>(leituraServices.listarLeituras().values());
        if (leituras.isEmpty()) {
            System.out.println("RELATORIO DE CONSUMO DE ENERGIA");
            System.out.println("Nenhuma leitura registrada. Consumo total: 0.0 kWh");
            System.out.println("--- DICAS ---");
            System.out.println("Registre leituras para visualizar o consumo por aparelho e receber dicas personalizadas.");
            return;
        }
        Relatorios relatorio = new Relatorios(dias, leituras);
        services.adicionarRelatorio(relatorio);
        services.mostrarDicas(relatorio);
        services.mostrarRelatorios();
    }
}

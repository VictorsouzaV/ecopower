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
        Relatorios relatorio = new Relatorios(dias, leituras);
        services.adicionarRelatorio(relatorio);
        services.mostrarAparelhoDeMaiorConsumo(relatorio);
        services.mostrarDicas(relatorio);
        services.mostrarRelatorios();
    }
}

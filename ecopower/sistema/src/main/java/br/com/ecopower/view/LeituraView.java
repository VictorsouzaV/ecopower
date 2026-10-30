package br.com.ecopower.view;

import br.com.ecopower.model.Aparelho;
import br.com.ecopower.model.Leitura;
import br.com.ecopower.services.AparelhoServices;
import br.com.ecopower.services.LeituraServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LeituraView {
    private final AparelhoServices aparelhoServices;
    private final LeituraServices services;

    public LeituraView(AparelhoServices aparelhoServices, LeituraServices services) {
        this.aparelhoServices = aparelhoServices; this.services = services;
    }

    public void cadastrar() {
        Aparelho aparelho = selecionarAparelho();

        String dataHora = InputView.lerTexto("Data e hora da leitura: ");
        double horas = InputView.lerDecimal("Horas de uso: ");

        services.cadastrarLeitura(new Leitura(aparelho.getNome(), dataHora, horas, aparelho));
            System.out.printf(Locale.US, "Consumo registrado: %.3f kWh%n", (aparelho.getPotenciaEmWatts() / 1000.0) * horas);
    }

    public void listar() {
        List<Leitura> leituras = new ArrayList<>(services.listarLeituras().values());
        if (leituras.isEmpty()) { 
            System.out.println("Nenhuma leitura cadastrada."); 
                return; 
        }

        leituras.forEach(System.out::println);
            System.out.printf(Locale.US, "Consumo acumulado: %.3f kWh%n", services.getConsumoAcumuladoEmKWh());
    }

    private Aparelho selecionarAparelho() {
        List<Aparelho> aparelhos = aparelhoServices.listarAparelhos();
        if (aparelhos.isEmpty()) {
            throw new IllegalStateException("Cadastre um aparelho antes de registrar uma leitura.");
        }

        for (int i = 0; i < aparelhos.size(); i++) {
            System.out.println((i + 1) + " - " + aparelhos.get(i));
        }

        int indice = InputView.lerInteiro("Número do aparelho: ") - 1;
        if (indice < 0 || indice >= aparelhos.size()) {
            throw new IllegalArgumentException("Aparelho selecionado não existe.");
        }
        return aparelhos.get(indice);
    }
}

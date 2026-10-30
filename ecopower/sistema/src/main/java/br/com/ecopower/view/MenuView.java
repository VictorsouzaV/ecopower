package br.com.ecopower.view;

import br.com.ecopower.model.TarifaEnergia;
import br.com.ecopower.services.AparelhoServices;
import br.com.ecopower.services.EstimativaCustoServices;
import br.com.ecopower.services.LeituraServices;
import br.com.ecopower.services.MetaServices;
import br.com.ecopower.services.RelatoriosServices;

import java.util.Locale;

public class MenuView {
    private final AparelhoView aparelhoView;
    private final LeituraView leituraView;
    private final TarifaView tarifaView;
    private final EstimativaView estimativaView;
    private final MetaView metaView;
    private final RelatorioView relatorioView;

    public MenuView() {
        AparelhoServices aparelhoServices = new AparelhoServices();
        LeituraServices leituraServices = new LeituraServices();

        aparelhoView = new AparelhoView(aparelhoServices);
        leituraView = new LeituraView(aparelhoServices, leituraServices);
        tarifaView = new TarifaView();
        estimativaView = new EstimativaView(leituraServices, new EstimativaCustoServices());
        metaView = new MetaView(new MetaServices());
        relatorioView = new RelatorioView(leituraServices, new RelatoriosServices());
    }

    public void iniciar() {
        Locale.setDefault(Locale.US);
        System.out.println("========================================");
        System.out.println("    EcoPower - Monitor de energia");
        System.out.println("========================================");

        boolean executando = true;
        while (executando) {
            exibirMenu();
                int opcao = InputView.lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        aparelhoView.cadastrar();
                    case 2:
                        aparelhoView.listar();
                    case 3:
                        aparelhoView.editar();
                    case 4:
                        aparelhoView.remover();
                    case 5:
                        leituraView.cadastrar();
                    case 6:
                        leituraView.listar();
                    case 7:
                        tarifaView.configurar();
                    case 8:
                        estimativaView.exibir(tarifaView.getTarifaAtual());
                    case 9:
                        estimativaView.exibirProjecao(tarifaView.getTarifaAtual());
                    case 10:
                        metaView.definir();
                    case 11:
                        relatorioView.exibir();
                    case 0:
                        executando = false;
                    default:
                        System.out.println("Opção inválida, escolha uma opção válida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Não foi possível concluir a operação: " + e.getMessage());
            }
        }
        System.out.println("Sistema encerrado. Até mais!");
    }

    private void exibirMenu() {
        System.out.println("\n--------------- MENU ----------------");
            System.out.println("1  - Cadastrar aparelho");
            System.out.println("2  - Listar aparelhos");
            System.out.println("3  - Editar aparelho");
            System.out.println("4  - Remover aparelho");
            System.out.println("5  - Registrar leitura de consumo");
            System.out.println("6  - Listar leituras");
            System.out.println("7  - Configurar tarifa de energia");
            System.out.println("8  - Estimar custo atual");
            System.out.println("9  - Projetar custo até o fim do mês");
            System.out.println("10 - Definir meta mensal");
            System.out.println("11 - Gerar relatório e dicas");
            System.out.println("0  - Sair");
        System.out.println("-------------------------------------");
    }
}
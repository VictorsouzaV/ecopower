package br.com.ecopower.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.ecopower.services.AparelhoServices;
import br.com.ecopower.services.EstimativaCustoServices;
import br.com.ecopower.services.LeituraServices;
import br.com.ecopower.services.MetaServices;
import br.com.ecopower.services.RelatoriosServices;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class EcoPowerTest {

    @Test
    void aparelhoExpõeDadosEPermiteAlteracoesValidas() {
        Aparelho aparelho = new Aparelho(" Geladeira ", "Cozinha", 500);

        assertEquals(" Geladeira ", aparelho.getNome());
        assertEquals("Cozinha", aparelho.getComodo());
        assertEquals(500, aparelho.getPotenciaEmWatts());

        aparelho.setNome("Freezer");
        aparelho.setComodo("Área externa");
        aparelho.setPotenciaEmWatts(700);

        assertEquals("Freezer", aparelho.getNome());
        assertEquals("Área externa", aparelho.getComodo());
        assertEquals(700, aparelho.getPotenciaEmWatts());
        assertTrue(aparelho.toString().contains("Freezer"));
    }

    @Test
    void aparelhoRejeitaDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Aparelho(null, "Sala", 100));
        assertThrows(IllegalArgumentException.class, () -> new Aparelho("TV", " ", 100));
        assertThrows(IllegalArgumentException.class, () -> new Aparelho("TV", "Sala", 0));

        Aparelho aparelho = new Aparelho("TV", "Sala", 100);
        assertThrows(IllegalArgumentException.class, () -> aparelho.setNome(""));
        assertThrows(IllegalArgumentException.class, () -> aparelho.setComodo(null));
        assertThrows(IllegalArgumentException.class, () -> aparelho.setPotenciaEmWatts(-1));
    }

    @Test
    void leituraCalculaConsumoEPermiteAlterarDataEHoras() {
        Aparelho aparelho = new Aparelho("Ventilador", "Sala", 200);
        Leitura leitura = new Leitura("nome ignorado", "2026-10-01T10:00", 2.5, aparelho);

        assertNotNull(leitura.getId());
        assertEquals("Ventilador", leitura.getNomeDoAparelho());
        assertEquals("2026-10-01T10:00", leitura.getDataHora());
        assertEquals(2.5, leitura.getHorasDeUso());
        assertEquals(0.5, leitura.getConsumoEmKWh(), 0.000001);
        assertSame(aparelho, leitura.getAparelho());

        leitura.setAlterarDataHora("2026-10-02T11:00");
        leitura.setAlterarHorasDeUso(3);
        assertEquals("2026-10-02T11:00", leitura.getDataHora());
        assertEquals(3, leitura.getHorasDeUso());
        assertTrue(leitura.toString().contains("Ventilador"));
    }

    @Test
    void leituraRejeitaDadosInvalidos() {
        Aparelho aparelho = new Aparelho("TV", "Sala", 100);
        assertThrows(IllegalArgumentException.class, () -> new Leitura("TV", "", 1, aparelho));
        assertThrows(IllegalArgumentException.class, () -> new Leitura("TV", "2026", 0, aparelho));
        assertThrows(IllegalArgumentException.class, () -> new Leitura("TV", "2026", 1, null));

        Leitura leitura = new Leitura("TV", "2026", 1, aparelho);
        assertThrows(IllegalArgumentException.class, () -> leitura.setAlterarDataHora(null));
        assertThrows(IllegalArgumentException.class, () -> leitura.setAlterarHorasDeUso(-2));
    }

    @Test
    void estimativaCalculaConsumoFaturadoEValorTotal() {
        EstimativaCusto estimativa = new EstimativaCusto(8, 0.5, 0.1, 5, 10);

        assertEquals(10, estimativa.getConsumoFaturado(), 0.000001);
        assertEquals(11, estimativa.getValorTotal(), 0.000001);
        assertTrue(estimativa.toString().contains("R$ 11,00"));

        EstimativaCusto acimaDoMinimo = new EstimativaCusto(20, 0.5, 0.1, 5, 10);
        assertEquals(20, acimaDoMinimo.getConsumoFaturado(), 0.000001);
        assertEquals(17, acimaDoMinimo.getValorTotal(), 0.000001);
    }

    @Test
    void estimativaRejeitaParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new EstimativaCusto(0, 1, 1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new EstimativaCusto(1, 0, 1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new EstimativaCusto(1, 1, 0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new EstimativaCusto(1, 1, 1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new EstimativaCusto(1, 1, 1, 1, 0));
    }

    @Test
    void metaNormalizaUnidadeEClassificaConsumo() {
        Meta meta = new Meta(100, "KWH");
        assertEquals("kwh", meta.getTipoUnidade());
        assertEquals(80, meta.getPercentualAlerta(), 0.000001);
        assertEquals("dentro do limite mensal", meta.verificarConsumo(50));
        assertEquals("proximo do limite mensal!", meta.verificarConsumo(80));
        assertEquals("Meta mensal excedida", meta.verificarConsumo(101));

        meta.setValorMensal(200);
        meta.setTipoUnidade("REais");
        meta.setPercentualAlerta(90);
        assertEquals(200, meta.getValorMensal(), 0.000001);
        assertEquals("reais", meta.getTipoUnidade());
        assertEquals(90, meta.getPercentualAlerta(), 0.000001);
        assertTrue(meta.toString().contains("reais"));
    }

    @Test
    void metaRejeitaValoresInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Meta(0, "kWh"));
        assertThrows(IllegalArgumentException.class, () -> new Meta(10, "litros"));
        Meta meta = new Meta(10, "kWh");
        assertThrows(IllegalArgumentException.class, () -> meta.setValorMensal(-1));
        assertThrows(IllegalArgumentException.class, () -> meta.setTipoUnidade(""));
        assertThrows(IllegalArgumentException.class, () -> meta.setPercentualAlerta(0));
        assertThrows(IllegalArgumentException.class, () -> meta.setPercentualAlerta(101));
        assertThrows(IllegalArgumentException.class, () -> meta.verificarConsumo(0));
    }

    @Test
    void tarifaExpõeTodosOsValores() {
        TarifaEnergia tarifa = new TarifaEnergia(0.5, 0.1, 0.02, 0.18, 0.01, 0.03, 7, 30);

        assertEquals(0.5, tarifa.getTarifaPorKWh());
        assertEquals(0.1, tarifa.getValorBandeiraPorKWh());
        assertEquals(0.02, tarifa.getPercentualEncargosSetoriais());
        assertEquals(0.18, tarifa.getPercentualICMS());
        assertEquals(0.01, tarifa.getPercentualPIS());
        assertEquals(0.03, tarifa.getPercentualCOFINS());
        assertEquals(7, tarifa.getIluminacaoPublica());
        assertEquals(7, tarifa.getCustoFixo());
        assertEquals(30, tarifa.getMinimoKWh());
        assertTrue(tarifa.toString().contains("tarifaPorKWh=0.5"));
    }

    @Test
    void tarifaRejeitaCadaParametroNaoPositivo() {
        double[] valores = {0, -1};
        for (double valor : valores) {
            assertThrows(IllegalArgumentException.class, () -> tarifa(valor, 0.1, 0.1, 0.1, 0.1, 0.1, 1, 1));
            assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, valor, 0.1, 0.1, 0.1, 0.1, 1, 1));
            assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, 0.1, valor, 0.1, 0.1, 0.1, 1, 1));
            assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, 0.1, 0.1, valor, 0.1, 0.1, 1, 1));
            assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, 0.1, 0.1, 0.1, valor, 0.1, 1, 1));
            assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, 0.1, 0.1, 0.1, 0.1, valor, 1, 1));
            assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, valor, 1));
        }
        assertThrows(IllegalArgumentException.class, () -> tarifa(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 1, 0));
    }

    @Test
    void relatorioAgregaConsumosEProtegeSeuMapa() {
        Aparelho tv = new Aparelho("TV", "Sala", 100);
        Aparelho forno = new Aparelho("Forno", "Cozinha", 2000);
        Relatorios relatorio = new Relatorios(30, List.of(
                new Leitura("TV", "2026-10-01", 2, tv),
                new Leitura("Forno", "2026-10-01", 1, forno),
                new Leitura("TV", "2026-10-02", 3, tv)));

        assertEquals(0.5, relatorio.getAparelhos().get("TV"), 0.000001);
        assertEquals(2, relatorio.getAparelhos().get("Forno"), 0.000001);
        assertEquals(2.5, relatorio.getConsumoTotal(), 0.000001);
        assertEquals("Forno", relatorio.getNome());
        assertEquals(30, relatorio.getDiasDeUso());
        assertThrows(UnsupportedOperationException.class, () -> relatorio.getAparelhos().put("X", 1.0));

        relatorio.setAlterarDiasDeUso(31);
        assertEquals(31, relatorio.getDiasDeUso());
        assertTrue(relatorio.toString().contains("Forno"));
        assertThrows(IllegalArgumentException.class, () -> new Relatorios(0, List.of(new Leitura("TV", "2026", 1, tv))));
        assertThrows(IllegalArgumentException.class, () -> new Relatorios(1, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Relatorios(1, null));
    }

    @Test
    void aparelhoServiceCadastraEditaListaERemove() {
        AparelhoServices service = new AparelhoServices();
        Aparelho aparelho = new Aparelho("TV", "Sala", 100);

        service.cadastrarAparelho(aparelho);
        assertEquals(1, service.listarAparelhos().size());
        service.editarAparelho(aparelho, "Smart TV", "Quarto", 120);
        assertEquals("Smart TV", service.listarAparelhos().get(0).getNome());
        service.removerAparelho(aparelho);
        assertTrue(service.listarAparelhos().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.editarAparelho(null, "x", "y", 1));
        assertThrows(IllegalArgumentException.class, () -> service.removerAparelho(aparelho));
    }

    @Test
    void leituraServiceAcumulaEDevolveCopiaDaLista() {
        Aparelho aparelho = new Aparelho("Lâmpada", "Sala", 100);
        Leitura primeira = new Leitura("Lâmpada", "2026-10-01", 2, aparelho);
        Leitura segunda = new Leitura("Lâmpada", "2026-10-02", 3, aparelho);
        LeituraServices service = new LeituraServices();

        assertThrows(IllegalArgumentException.class, () -> service.cadastrarLeitura(null));
        service.cadastrarLeitura(primeira);
        service.cadastrarLeitura(segunda);
        assertEquals(0.5, service.getConsumoAcumuladoEmKWh(), 0.000001);
        Map<Integer, Leitura> copia = service.listarLeituras();
        copia.clear();
        assertEquals(2, service.listarLeituras().size());
    }

    @Test
    void estimativaServiceCalculaContaEProjecao() {
        Aparelho aparelho = new Aparelho("TV", "Sala", 1000);
        LeituraServices leituras = new LeituraServices();
        leituras.cadastrarLeitura(new Leitura("TV", "2026", 10, aparelho));
        TarifaEnergia tarifa = tarifa(2, 0.5, 0.1, 0.05, 0.02, 0.03, 5, 5);
        EstimativaCustoServices service = new EstimativaCustoServices();

        EstimativaCusto conta = service.estimarCusto(leituras, tarifa);
        assertEquals(10, conta.getConsumoFaturado(), 0.000001);
        assertEquals(35, conta.getValorTotal(), 0.000001);
        assertEquals(20, service.estimarCustoFimDoMes(leituras, 15, 30, tarifa).getConsumoFaturado(), 0.000001);
        assertThrows(IllegalArgumentException.class, () -> service.estimarCusto(null, tarifa));
        assertThrows(IllegalArgumentException.class, () -> service.estimarCusto(leituras, null));
        assertThrows(IllegalArgumentException.class, () -> service.estimarCustoFimDoMes(leituras, 0, 30, tarifa));
        assertThrows(IllegalArgumentException.class, () -> service.estimarCustoFimDoMes(leituras, 31, 30, tarifa));
    }

    @Test
    void metaServiceGeraOsTresTiposDeAlerta() {
        MetaServices service = new MetaServices();
        assertThrows(IllegalStateException.class, () -> service.gerarAlerta(1));
        service.definirMeta(100, "reais");
        assertEquals("Consumo mensal está dentro do limite definido.", service.gerarAlerta(50));
        assertEquals("Alerta: o consumo mensal está próximo do limite definido.", service.gerarAlerta(80));
        assertEquals("Alerta: o limite mensal de consumo foi excedido.", service.gerarAlerta(101));
    }

    @Test
    void relatoriosServiceValidaRelatorioEImprimeInformacoes() {
        Aparelho aparelho = new Aparelho("TV", "Sala", 100);
        Relatorios relatorio = new Relatorios(30, List.of(new Leitura("TV", "2026", 1, aparelho)));
        RelatoriosServices service = new RelatoriosServices();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(saida));
            assertThrows(IllegalArgumentException.class, () -> service.mostrarRelatorios());
            assertThrows(IllegalArgumentException.class, () -> service.adicionarRelatorio(null));
            service.adicionarRelatorio(relatorio);
            service.mostrarAparelhoDeMaiorConsumo(relatorio);
            service.mostrarDicas(relatorio);
            service.mostrarRelatorios();
        } finally {
            System.setOut(original);
        }

        assertTrue(saida.toString().contains("TV"));
        assertFalse(saida.toString().isBlank());
        assertThrows(IllegalArgumentException.class, () -> service.mostrarDicas(null));
    }

    private static TarifaEnergia tarifa(double tarifa, double bandeira, double encargos,
            double icms, double pis, double cofins, double iluminacao, int minimo) {
        return new TarifaEnergia(tarifa, bandeira, encargos, icms, pis, cofins, iluminacao, minimo);
    }
}

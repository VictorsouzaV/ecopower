package br.com.ecopower.services;

import br.com.ecopower.dao.LeituraDAO;
import br.com.ecopower.model.Leitura;

import java.util.HashMap;
import java.util.Map;

public class LeituraServices {
    private Map<Integer, Leitura> leituras = new HashMap<>();

    public LeituraServices() {
        for (Leitura leitura : LeituraDAO.listar()) {
            leituras.put(leitura.getId(), leitura);
        }
    }

    public void cadastrarLeitura(Leitura leitura) {
        if (leitura == null) {
            throw new IllegalArgumentException("Erro, leitura não pode ser nula!");
        }

        LeituraDAO.inserir(leitura);
        leituras.put(leitura.getId(), leitura);
            System.out.println("Leitura cadastrada: " + leitura.getId());
    }

    public double getConsumoAcumuladoEmKWh() {
        return leituras.values().stream()
                .mapToDouble(Leitura::getConsumoEmKWh)
                .sum();
    }

    public Map<Integer, Leitura> listarLeituras() {
        return new HashMap<>(leituras);
    }
}
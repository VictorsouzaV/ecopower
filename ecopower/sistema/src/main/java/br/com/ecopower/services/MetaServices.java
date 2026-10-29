package br.com.ecopower.services;

import br.com.ecopower.model.Meta;

public class MetaServices {
    private Meta meta;

    public void definirMeta(double valorMensal, String unidade) {
        meta = new Meta(valorMensal, unidade);
    }

    public String gerarAlerta(double consumoMensal) {
        if (meta == null) {
            throw new IllegalStateException("Erro, nenhuma meta mensal foi definida!");
        }

        String status = meta.verificarConsumo(consumoMensal);

        if (status.equals("Meta mensal excedida")) {
            return "Alerta: o limite mensal de consumo foi excedido.";
        }

        if (status.equals("proximo do limite mensal!")) {
            return "Alerta: o consumo mensal está próximo do limite definido.";
        }

        return "Consumo mensal está dentro do limite definido.";
    }
}

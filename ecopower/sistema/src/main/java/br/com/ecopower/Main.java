package br.com.ecopower;

import br.com.ecopower.dao.ConexaoBD;
import br.com.ecopower.view.MenuView;

public class Main {
    public static void main(String[] args) {
        if (!ConexaoBD.ativo()) {
            System.out.println("Aviso: banco não configurado (config.local.properties). Os dados ficam só em memória.");
        }

        try {
            new MenuView().iniciar();
        } catch (IllegalStateException e) {
            System.out.println("Não foi possível iniciar o EcoPower: " + e.getMessage());
        }
    }
}

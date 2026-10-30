package br.com.ecopower.view;

import br.com.ecopower.model.Aparelho;
import br.com.ecopower.services.AparelhoServices;
import java.util.List;

public class AparelhoView {
    private final AparelhoServices services;

    public AparelhoView(AparelhoServices services) { this.services = services; }

    public void cadastrar() {
        String nome = InputView.lerTexto("Nome do aparelho: ");
        String comodo = InputView.lerTexto("Cômodo: ");
        int potencia = InputView.lerInteiro("Potência em watts: ");
        System.out.println("Aparelho cadastrado com sucesso!");

        services.cadastrarAparelho(new Aparelho(nome, comodo, potencia));
    }

    public void listar() {
        List<Aparelho> aparelhos = services.listarAparelhos();

        if (aparelhos.isEmpty()) { 
            System.out.println("Nenhum aparelho cadastrado."); 
                return; 
        }

        for (int i = 0; i < aparelhos.size(); i++) {
            System.out.println((i + 1) + " - " + aparelhos.get(i));
        }
    }

    public void editar() {
        Aparelho aparelho = selecionar();
        services.editarAparelho(aparelho, InputView.lerTexto("Novo nome: "),
                InputView.lerTexto("Novo cômodo: "), InputView.lerInteiro("Nova potência em watts: "));

        System.out.println("Aparelho editado com sucesso!");
    }

    public void remover() { 
        Aparelho removido = selecionar();
        services.removerAparelho(removido);
            System.out.println("Aparelho " + removido.getNome() + " removido com sucesso!");
    }

    public Aparelho selecionar() {
        listar();

        int indice = InputView.lerInteiro("Número do aparelho: ") - 1;
        List<Aparelho> aparelhos = services.listarAparelhos();
        
        if (indice < 0 || indice >= aparelhos.size()) {
            throw new IllegalArgumentException("Aparelho selecionado não existe.");
        }
        return aparelhos.get(indice);
    }
}

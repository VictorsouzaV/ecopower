package br.com.ecopower.services;

import br.com.ecopower.model.Aparelho;
import java.util.ArrayList;
import java.util.List;

public class AparelhoServices{
    private List<Aparelho> aparelhos = new ArrayList<>();

    public void cadastrarAparelho(Aparelho aparelho) {
        aparelhos.add(aparelho);
            System.out.println("Aparelho cadastrado: " + aparelho.getNome());
    }

    public void editarAparelho(Aparelho aparelho, String novoNome, String novoComodo, int novoPotenciaEmWatts) {
        if (aparelho == null) {
            throw new IllegalArgumentException("Erro, aparelho não pode ser nulo!");
        }

        if (!aparelhos.contains(aparelho)) {
            throw new IllegalArgumentException("Erro, aparelho não encontrado na lista!");
        }

        aparelho.setNome(novoNome);
        aparelho.setComodo(novoComodo);
        aparelho.setPotenciaEmWatts(novoPotenciaEmWatts);
            System.out.println("Aparelho editado: " + aparelho.getNome());
    }

    public void removerAparelho(Aparelho aparelho) {
        if (aparelho == null) {
            throw new IllegalArgumentException("Erro, aparelho não pode ser nulo!");
        }

        if (!aparelhos.contains(aparelho)) {
            throw new IllegalArgumentException("Erro, aparelho não encontrado na lista!");
        }

        aparelhos.remove(aparelho);
    }
    
    public List<Aparelho> listarAparelhos() {
        return aparelhos;
    }
}
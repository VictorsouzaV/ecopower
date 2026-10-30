package br.com.ecopower.model;

public class Aparelho {
    private String nome;
    private String comodo;
    private int potenciaEmWatts;

    public Aparelho(String nome, String comodo, int potenciaEmWatts) {
        verificarNome(nome);
        verificarComodo(comodo);
        verificarPotencia(potenciaEmWatts);

            this.nome = nome;
            this.comodo = comodo;
            this.potenciaEmWatts = potenciaEmWatts;
    }

    public String getNome() {
        return this.nome;
    }

    public String getComodo() {
        return this.comodo;
    }

    public int getPotenciaEmWatts() {
        return this.potenciaEmWatts;
    }

    public void setNome(String nome) {
        verificarNome(nome);
            this.nome = nome;
    }

    public void setComodo(String comodo) {
        verificarComodo(comodo);
            this.comodo = comodo;
    }

    public void setPotenciaEmWatts(int potenciaEmWatts) {
        verificarPotencia(potenciaEmWatts);
            this.potenciaEmWatts = potenciaEmWatts;
    }

     private void verificarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro, nome de aparelho não poder ser nulo ou vazio!");
        }
    }

    private void verificarComodo(String comodo) {
        if (comodo == null || comodo.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro, nome do cômodo não poder ser nulo ou vazio!");
        }
    }

    private void verificarPotencia(int potenciaEmWatts) {
        if (potenciaEmWatts <= 0) {
            throw new IllegalArgumentException("Erro, potência em watts não pode ser zero ou negativa!");
        }
    }

    @Override 
    public String toString() {
        return "Cadastro{" +
                "nome='" + nome + '\'' +
                ", comodo='" + comodo + '\'' +
                ", potenciaEmWatts=" + potenciaEmWatts +
                '}';
    }
}
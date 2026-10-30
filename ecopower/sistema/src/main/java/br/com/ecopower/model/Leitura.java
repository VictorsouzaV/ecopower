package br.com.ecopower.model;

public class Leitura {
    private static int proximoId = 0;
    private final int id;
    private String nomeDoAparelho;
    private String dataHora;
    private double horasDeUso = 0.0;
    private double consumoEmKWh = 0.0;
    private Aparelho aparelho;

    public Leitura(String nomeDoAparelho, String dataHora, double horasDeUso, Aparelho aparelho) {
        verificarDataHora(dataHora);
        verificarHorasDeUso(horasDeUso);
        verificarAparelho(aparelho);
        consumoEmKWh = (aparelho.getPotenciaEmWatts() / 1000.0) * horasDeUso;
        
            this.id = proximoId++;
            this.nomeDoAparelho = aparelho.getNome().trim();
            this.dataHora = dataHora;
            this.horasDeUso = horasDeUso;
            this.aparelho = aparelho;
    }

    public int getId() {
        return this.id;
    }

    public String getNomeDoAparelho() {
        return this.nomeDoAparelho;
    }

    public String getDataHora() {
        return this.dataHora;
    }

    public void setAlterarDataHora(String dataHora) {
        verificarDataHora(dataHora);
        this.dataHora = dataHora;
    }

    public double getHorasDeUso() {
        return this.horasDeUso;
    }

    public void setAlterarHorasDeUso(double horasDeUso) {
        verificarHorasDeUso(horasDeUso);
        this.horasDeUso = horasDeUso;
    }

    public double getConsumoEmKWh() {
        return this.consumoEmKWh;
    }

    public Aparelho getAparelho() {
        return this.aparelho;
    }

    private void verificarAparelho(Aparelho aparelho) {
        if (aparelho == null) {
            throw new IllegalArgumentException("Erro, aparelho não pode ser nulo!");
        }
    }

    private void verificarHorasDeUso(double horasDeUso) {
        if (horasDeUso <= 0) {
            throw new IllegalArgumentException("Erro, horas de uso não podem ser zero ou negativa!");
        }
    }

    private void verificarDataHora(String dataHora) {
        if (dataHora == null || dataHora.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro, data e hora não podem ser nula ou vazia!");
        }
    }

    @Override 
    public String toString() {
        return "Leitura{" +
                "id= " + id +
                ", nome do aparelho= " + getNomeDoAparelho() +
                ", dataHora= " + getDataHora() + '\'' +
                ", horas de uso= " + getHorasDeUso() +
                ", consumoEmKWh= " + getConsumoEmKWh() +
                '}';
    }
}
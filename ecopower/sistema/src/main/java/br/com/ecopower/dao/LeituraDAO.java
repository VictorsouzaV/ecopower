package br.com.ecopower.dao;

import br.com.ecopower.model.Aparelho;
import br.com.ecopower.model.Leitura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabela LEITURAS. O banco guarda consumo_kwh (nao as horas de uso);
 * ao carregar, as horas sao recalculadas: horas = kWh / (W / 1000).
 */
public final class LeituraDAO {
    private static final DateTimeFormatter BR_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter BR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LeituraDAO() {
    }

    public static List<Leitura> listar() {
        List<Leitura> leituras = new ArrayList<>();
        if (!ConexaoBD.ativo()) {
            return leituras;
        }

        String sql = "SELECT l.data_hora, l.consumo_kwh, d.id, d.nome, d.localizacao, d.potencia_watts "
                + "FROM leituras l JOIN dispositivos d ON d.id = l.dispositivo_id "
                + "WHERE d.usuario_id = ? ORDER BY l.data_hora, l.id";
        try (Connection conexao = ConexaoBD.abrir();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, ConexaoBD.usuarioId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Aparelho aparelho = DispositivoDAO.montar(rs.getInt("id"), rs.getString("nome"),
                            rs.getString("localizacao"), rs.getInt("potencia_watts"));
                    double horas = rs.getDouble("consumo_kwh") / (aparelho.getPotenciaEmWatts() / 1000.0);
                    String dataHora = rs.getTimestamp("data_hora").toLocalDateTime().format(BR_DATA_HORA);
                    leituras.add(new Leitura(aparelho.getNome(), dataHora, horas, aparelho));
                }
            }
        } catch (SQLException e) {
            throw ConexaoBD.erro("listar as leituras", e);
        }
        return leituras;
    }

    public static void inserir(Leitura leitura) {
        if (!ConexaoBD.ativo()) {
            return;
        }

        String sql = "INSERT INTO leituras (dispositivo_id, data_hora, consumo_kwh) VALUES (?, ?, ?)";
        try (Connection conexao = ConexaoBD.abrir();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, leitura.getAparelho().getId());
            ps.setTimestamp(2, Timestamp.valueOf(converterData(leitura.getDataHora())));
            ps.setDouble(3, leitura.getConsumoEmKWh());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw ConexaoBD.erro("registrar a leitura", e);
        }
    }

    /** Aceita "dd/MM/yyyy HH:mm", "dd/MM/yyyy", "yyyy-MM-ddTHH:mm" ou "yyyy-MM-dd". */
    static LocalDateTime converterData(String texto) {
        String valor = texto.trim();
        try {
            return LocalDateTime.parse(valor, BR_DATA_HORA);
        } catch (DateTimeParseException ignorada) { }
        try {
            return LocalDate.parse(valor, BR_DATA).atStartOfDay();
        } catch (DateTimeParseException ignorada) { }
        try {
            return LocalDateTime.parse(valor.replace(' ', 'T'));
        } catch (DateTimeParseException ignorada) { }
        try {
            return LocalDate.parse(valor).atStartOfDay();
        } catch (DateTimeParseException ignorada) { }
        throw new IllegalArgumentException("Data inválida. Use o formato dd/MM/aaaa HH:mm (ex.: 12/11/2026 14:30).");
    }
}

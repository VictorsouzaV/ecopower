package br.com.ecopower.dao;

import br.com.ecopower.model.Aparelho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Tabela DISPOSITIVOS (classe Aparelho; comodo -> coluna localizacao). */
public final class DispositivoDAO {
    private DispositivoDAO() {
    }

    public static List<Aparelho> listar() {
        List<Aparelho> aparelhos = new ArrayList<>();
        if (!ConexaoBD.ativo()) {
            return aparelhos;
        }

        String sql = "SELECT id, nome, localizacao, potencia_watts FROM dispositivos WHERE usuario_id = ? ORDER BY id";
        try (Connection conexao = ConexaoBD.abrir();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, ConexaoBD.usuarioId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    aparelhos.add(montar(rs.getInt("id"), rs.getString("nome"),
                            rs.getString("localizacao"), rs.getInt("potencia_watts")));
                }
            }
        } catch (SQLException e) {
            throw ConexaoBD.erro("listar os aparelhos", e);
        }
        return aparelhos;
    }

    public static void inserir(Aparelho aparelho) {
        if (!ConexaoBD.ativo()) {
            return;
        }

        String sql = "INSERT INTO dispositivos (usuario_id, nome, localizacao, potencia_watts) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conexao = ConexaoBD.abrir();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, ConexaoBD.usuarioId());
            ps.setString(2, aparelho.getNome());
            ps.setString(3, aparelho.getComodo());
            ps.setInt(4, aparelho.getPotenciaEmWatts());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                aparelho.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw ConexaoBD.erro("cadastrar o aparelho", e);
        }
    }

    public static void atualizar(Aparelho aparelho) {
        if (!ConexaoBD.ativo()) {
            return;
        }

        String sql = "UPDATE dispositivos SET nome = ?, localizacao = ?, potencia_watts = ? WHERE id = ?";
        try (Connection conexao = ConexaoBD.abrir();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, aparelho.getNome());
            ps.setString(2, aparelho.getComodo());
            ps.setInt(3, aparelho.getPotenciaEmWatts());
            ps.setInt(4, aparelho.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw ConexaoBD.erro("editar o aparelho", e);
        }
    }

    /** As leituras do aparelho sao apagadas junto (ON DELETE CASCADE). */
    public static void remover(Aparelho aparelho) {
        if (!ConexaoBD.ativo()) {
            return;
        }

        try (Connection conexao = ConexaoBD.abrir();
             PreparedStatement ps = conexao.prepareStatement("DELETE FROM dispositivos WHERE id = ?")) {
            ps.setInt(1, aparelho.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw ConexaoBD.erro("remover o aparelho", e);
        }
    }

    static Aparelho montar(int id, String nome, String localizacao, int potencia) {
        // localizacao pode ser NULL no banco, mas Aparelho exige um comodo
        String comodo = (localizacao == null || localizacao.isBlank()) ? "Sem cômodo" : localizacao;
        Aparelho aparelho = new Aparelho(nome, comodo, potencia);
        aparelho.setId(id);
        return aparelho;
    }
}

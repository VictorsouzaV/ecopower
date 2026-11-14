package br.com.ecopower.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Conexao com o PostgreSQL (database/schema.sql).
 *
 * Configuracao, em ordem de prioridade:
 *   1. -Ddb.url=... -Ddb.user=... -Ddb.password=...
 *   2. variaveis de ambiente ECOPOWER_DB_URL, ECOPOWER_DB_USER, ECOPOWER_DB_PASSWORD
 *   3. arquivo config.local.properties (fora do git)
 *
 * Sem db.url configurado o app funciona so em memoria (como nos testes).
 */
public final class ConexaoBD {
    private static final Properties ARQUIVO = carregarArquivo();
    private static Integer usuarioId;

    private ConexaoBD() {
    }

    public static boolean ativo() {
        return config("db.url", null) != null;
    }

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(config("db.url", null), config("db.user", null), config("db.password", null));
    }

    /** Id do usuario do app: busca pelo e-mail ou cadastra (tabela USUARIOS). */
    public static synchronized int usuarioId() {
        if (usuarioId != null) {
            return usuarioId;
        }

        String email = config("app.usuario.email", "morador@exemplo.com");
        String nome = config("app.usuario.nome", "Morador Exemplo");
        try (Connection conexao = abrir()) {
            try (PreparedStatement ps = conexao.prepareStatement("SELECT id FROM usuarios WHERE email = ?")) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        usuarioId = rs.getInt(1);
                        return usuarioId;
                    }
                }
            }
            try (PreparedStatement ps = conexao.prepareStatement(
                    "INSERT INTO usuarios (nome, email) VALUES (?, ?) RETURNING id")) {
                ps.setString(1, nome);
                ps.setString(2, email);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    usuarioId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw erro("conectar ao banco", e);
        }
        return usuarioId;
    }

    public static IllegalStateException erro(String acao, SQLException e) {
        String mensagem = e.getMessage() == null ? "" : e.getMessage().split("\n")[0];
        return new IllegalStateException("Erro ao " + acao + ": " + mensagem, e);
    }

    static String config(String chave, String padrao) {
        String valor = System.getProperty(chave);
        if (valor == null || valor.isBlank()) {
            valor = System.getenv("ECOPOWER_" + chave.toUpperCase().replace('.', '_'));
        }
        if (valor == null || valor.isBlank()) {
            valor = ARQUIVO.getProperty(chave);
        }
        return (valor == null || valor.isBlank()) ? padrao : valor.trim();
    }

    private static Properties carregarArquivo() {
        Properties propriedades = new Properties();
        Path caminho = Path.of(System.getProperty("ecopower.config", "config.local.properties"));
        if (Files.exists(caminho)) {
            try (InputStream entrada = Files.newInputStream(caminho)) {
                propriedades.load(entrada);
            } catch (IOException e) {
                throw new IllegalStateException("Erro ao ler " + caminho.toAbsolutePath(), e);
            }
        }
        return propriedades;
    }
}

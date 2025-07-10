package sistemalocacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocadorDAO {

    public void salvar(Locador locador) throws SQLException {
        String sql = "INSERT INTO locadores (nome, email, idade, endereco, cpf, telefone, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, locador.getNome());
            pstmt.setString(2, locador.getEmail());
            pstmt.setInt(3, locador.getIdade());
            pstmt.setString(4, locador.getEndereco());
            pstmt.setLong(5, locador.getCpf());
            pstmt.setLong(6, locador.getTelefone());
            pstmt.setString(7, locador.getSenha());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar locador: " + e.getMessage());
            throw e;
        }
    }

    public List<Locador> listar() throws SQLException {
        List<Locador> locadores = new ArrayList<>();
        String sql = "SELECT * FROM locadores";
        try (Connection conn = ConexaoBD.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Locador locador = new Locador();
                locador.setId(rs.getInt("id"));
                locador.setNome(rs.getString("nome"));
                locador.setEmail(rs.getString("email"));
                locador.setIdade(rs.getInt("idade"));
                locador.setEndereco(rs.getString("endereco"));
                locador.setCpf(rs.getLong("cpf"));
                locador.setTelefone(rs.getLong("telefone"));
                locador.setSenha(rs.getString("senha"));
                locadores.add(locador);
            }
        }
        return locadores;
    }

    /**
     * Atualiza um locador existente no banco de dados.
     */
    public void atualizar(Locador locador) throws SQLException {
        String sql = "UPDATE locadores SET nome = ?, email = ?, idade = ?, endereco = ?, telefone = ?, senha = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, locador.getNome());
            pstmt.setString(2, locador.getEmail());
            pstmt.setInt(3, locador.getIdade());
            pstmt.setString(4, locador.getEndereco());
            pstmt.setLong(5, locador.getTelefone());
            pstmt.setString(6, locador.getSenha());
            pstmt.setInt(7, locador.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Exclui um locador do banco de dados pelo ID.
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM locadores WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Busca um locador pelo seu ID.
     */
    public Optional<Locador> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM locadores WHERE id = ?";
        Locador locador = null;
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    locador = new Locador();
                    locador.setId(rs.getInt("id"));
                    locador.setNome(rs.getString("nome"));
                    locador.setEmail(rs.getString("email"));
                    locador.setIdade(rs.getInt("idade"));
                    locador.setEndereco(rs.getString("endereco"));
                    locador.setCpf(rs.getLong("cpf"));
                    locador.setTelefone(rs.getLong("telefone"));
                    locador.setSenha(rs.getString("senha"));
                }
            }
        }
        return Optional.ofNullable(locador);
    }
}

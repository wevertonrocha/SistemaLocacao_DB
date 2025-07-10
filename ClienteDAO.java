package sistemalocacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    public void salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, email, idade, endereco, cpf, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setInt(3, cliente.getIdade());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.setLong(5, cliente.getCpf());
            pstmt.setLong(6, cliente.getTelefone());
            pstmt.executeUpdate();
        }
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, email = ?, idade = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setInt(3, cliente.getIdade());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.setLong(5, cliente.getTelefone());
            pstmt.setInt(6, cliente.getId());
            pstmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = ConexaoBD.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setIdade(rs.getInt("idade"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCpf(rs.getLong("cpf"));
                cliente.setTelefone(rs.getLong("telefone"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public Optional<Cliente> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setIdade(rs.getInt("idade"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setCpf(rs.getLong("cpf"));
                    cliente.setTelefone(rs.getLong("telefone"));
                }
            }
        }
        return Optional.ofNullable(cliente);
    }
}

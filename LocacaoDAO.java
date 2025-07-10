package sistemalocacao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDAO {

    public void salvar(Locacao locacao) throws SQLException {
        // A data_fim não é inserida aqui, pois será NULL por padrão no banco.
        String updateEspacoSql = "UPDATE espacos SET disponivel = false WHERE id = ?";
        String insertLocacaoSql = "INSERT INTO locacoes (cliente_id, espaco_id, data_inicio) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexaoBD.getConexao();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateEspacoSql)) {
                pstmtUpdate.setInt(1, locacao.getEspaco().getId());
                pstmtUpdate.executeUpdate();
            }

            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertLocacaoSql)) {
                pstmtInsert.setInt(1, locacao.getCliente().getId());
                pstmtInsert.setInt(2, locacao.getEspaco().getId());
                pstmtInsert.setDate(3, Date.valueOf(locacao.getDataInicio()));
                pstmtInsert.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            throw new SQLException("Erro ao salvar locação: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Encerra uma locação, definindo a data_fim e liberando o
     * espaço.
     */
    public void encerrar(int locacaoId) throws SQLException {
        String getEspacoIdSql = "SELECT espaco_id FROM locacoes WHERE id = ? AND data_fim IS NULL";
        String updateLocacaoSql = "UPDATE locacoes SET data_fim = ? WHERE id = ?";
        String updateEspacoSql = "UPDATE espacos SET disponivel = true WHERE id = ?";

        Connection conn = null;
        try {
            conn = ConexaoBD.getConexao();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Obter o ID do espaço para poder liberá-lo
            int espacoId = -1;
            try (PreparedStatement pstmtGet = conn.prepareStatement(getEspacoIdSql)) {
                pstmtGet.setInt(1, locacaoId);
                try (ResultSet rs = pstmtGet.executeQuery()) {
                    if (rs.next()) {
                        espacoId = rs.getInt("espaco_id");
                    } else {
                        throw new SQLException("Locação ativa com ID " + locacaoId + " não encontrada.");
                    }
                }
            }

            // 2. Atualizar a locação com a data de fim
            try (PreparedStatement pstmtUpdateLocacao = conn.prepareStatement(updateLocacaoSql)) {
                pstmtUpdateLocacao.setDate(1, Date.valueOf(LocalDate.now()));
                pstmtUpdateLocacao.setInt(2, locacaoId);
                pstmtUpdateLocacao.executeUpdate();
            }

            // 3. Liberar o espaço
            try (PreparedStatement pstmtUpdateEspaco = conn.prepareStatement(updateEspacoSql)) {
                pstmtUpdateEspaco.setInt(1, espacoId);
                pstmtUpdateEspaco.executeUpdate();
            }

            conn.commit(); // Confirma a transação

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Erro ao encerrar locação: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<Locacao> listarAtivas() throws SQLException {
        return buscarLocacoesPorCondicao("WHERE l.data_fim IS NULL");
    }

    public List<Locacao> listarEncerradas() throws SQLException {
        return buscarLocacoesPorCondicao("WHERE l.data_fim IS NOT NULL");
    }

    private List<Locacao> buscarLocacoesPorCondicao(String condicao) throws SQLException {
        List<Locacao> locacoes = new ArrayList<>();
        String sql = "SELECT "
                + "l.id as locacao_id, l.data_inicio, l.data_fim, "
                + "c.id as cliente_id, c.nome as cliente_nome, c.cpf as cliente_cpf, "
                + "e.id as espaco_id, e.endereco as espaco_endereco, e.tipo_espaco, e.valor as espaco_valor, e.disponivel as espaco_disponivel "
                + "FROM locacoes l "
                + "JOIN clientes c ON l.cliente_id = c.id "
                + "JOIN espacos e ON l.espaco_id = e.id " + condicao;

        try (Connection conn = ConexaoBD.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                cliente.setCpf(rs.getLong("cliente_cpf"));

                String tipoEspaco = rs.getString("tipo_espaco");
                Espaco espaco = criarEspacoPeloTipo(tipoEspaco);
                if (espaco == null) {
                    continue;
                }

                espaco.setId(rs.getInt("espaco_id"));
                espaco.setEndereco(rs.getString("espaco_endereco"));
                espaco.setValor(rs.getDouble("espaco_valor"));
                espaco.setDisponivel(rs.getBoolean("espaco_disponivel"));

                Locacao locacao = new Locacao();
                locacao.setCliente(cliente);
                locacao.setEspaco(espaco);
                locacao.setId(rs.getInt("locacao_id"));
                locacao.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                Date dataFimSql = rs.getDate("data_fim");
                if (dataFimSql != null) {
                    locacao.setDataFim(dataFimSql.toLocalDate());
                }

                locacoes.add(locacao);
            }
        }
        return locacoes;
    }

    private Espaco criarEspacoPeloTipo(String tipoEspaco) {
        switch (tipoEspaco) {
            case "SalaComercial":
                return new SalaComercial();
            case "GalpaoIndustrial":
                return new GalpaoIndustrial();
            case "LojaComercial":
                return new LojaComercial();
            case "Estudio":
                return new Estudio();
            case "PavilhaoEventos":
                return new PavilhaoEventos();
            default:
                return null;
        }
    }
}

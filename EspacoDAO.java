package sistemalocacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A classe EspacoDAO, responsável por toda a comunicação com o banco de dados
 * referente a espaços.
 */
public class EspacoDAO {

    /**
     * Salva um novo espaço no banco de dados, tratando os diferentes tipos de
     * subclasses.
     */
    public void salvar(Espaco espaco) throws SQLException {
        String sql = "INSERT INTO espacos (locador_id, tipo_espaco, endereco, valor, disponivel, area_total, altura, "
                + "numero_de_portas, area_estudio, numero_janela, sistema_seguranca, area_evento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, espaco.getLocador().getId());
            pstmt.setString(2, espaco.getClass().getSimpleName());
            pstmt.setString(3, espaco.getEndereco());
            pstmt.setDouble(4, espaco.getValor());
            pstmt.setBoolean(5, espaco.isDisponivel());

            // Preenche os campos específicos de cada subclasse
            if (espaco instanceof GalpaoIndustrial) {
                GalpaoIndustrial g = (GalpaoIndustrial) espaco;
                pstmt.setDouble(6, g.getAreaTotal());
                pstmt.setDouble(7, g.getAltura());
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.INTEGER);
                pstmt.setNull(11, Types.VARCHAR);
                pstmt.setNull(12, Types.DOUBLE);
            } else if (espaco instanceof LojaComercial) {
                LojaComercial l = (LojaComercial) espaco;
                pstmt.setDouble(6, l.getAreaTotal());
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.INTEGER);
                pstmt.setNull(11, Types.VARCHAR);
                pstmt.setNull(12, Types.DOUBLE);
            } else if (espaco instanceof SalaComercial) {
                SalaComercial s = (SalaComercial) espaco;
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setInt(8, s.getNumeroDePortas());
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setInt(10, s.getNumeroDeJanelas());
                pstmt.setString(11, s.getSistemaSeguranca());
                pstmt.setNull(12, Types.DOUBLE);
            } else if (espaco instanceof Estudio) {
                Estudio e = (Estudio) espaco;
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setDouble(9, e.getAreaEstudio());
                pstmt.setNull(10, Types.INTEGER);
                pstmt.setNull(11, Types.VARCHAR);
                pstmt.setNull(12, Types.DOUBLE);
            } else if (espaco instanceof PavilhaoEventos) {
                PavilhaoEventos p = (PavilhaoEventos) espaco;
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.INTEGER);
                pstmt.setNull(11, Types.VARCHAR);
                pstmt.setDouble(12, p.getAreaEvento());
            } else {
                for (int i = 6; i <= 12; i++) {
                    pstmt.setNull(i, Types.NULL);
                }
            }
            pstmt.executeUpdate();
        }
    }

    /**
     * Atualiza os dados de um espaço existente no banco de dados.
     */
    public void atualizar(Espaco espaco) throws SQLException {
        // SQL para atualizar colunas comuns e específicas
        String sql = "UPDATE espacos SET endereco=?, valor=?, disponivel=?, "
                + "area_total=?, altura=?, numero_de_portas=?, area_estudio=?, "
                + "numero_janela=?, sistema_seguranca=?, area_evento=? "
                + "WHERE id=?";

        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Seta as propriedades comuns
            pstmt.setString(1, espaco.getEndereco());
            pstmt.setDouble(2, espaco.getValor());
            pstmt.setBoolean(3, espaco.isDisponivel());

            // Seta as propriedades específicas (e NULAS para os outros tipos)
            if (espaco instanceof GalpaoIndustrial) {
                GalpaoIndustrial g = (GalpaoIndustrial) espaco;
                pstmt.setDouble(4, g.getAreaTotal());
                pstmt.setDouble(5, g.getAltura());
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.DOUBLE);
            } else if (espaco instanceof LojaComercial) {
                LojaComercial l = (LojaComercial) espaco;
                pstmt.setDouble(4, l.getAreaTotal());
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.DOUBLE);
            } else if (espaco instanceof SalaComercial) {
                SalaComercial s = (SalaComercial) espaco;
                pstmt.setNull(4, Types.DOUBLE);
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setInt(6, s.getNumeroDePortas());
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setInt(8, s.getNumeroDeJanelas());
                pstmt.setString(9, s.getSistemaSeguranca());
                pstmt.setNull(10, Types.DOUBLE);
            } else if (espaco instanceof Estudio) {
                Estudio e = (Estudio) espaco;
                pstmt.setNull(4, Types.DOUBLE);
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setDouble(7, e.getAreaEstudio());
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.DOUBLE);
            } else if (espaco instanceof PavilhaoEventos) {
                PavilhaoEventos p = (PavilhaoEventos) espaco;
                pstmt.setNull(4, Types.DOUBLE);
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setDouble(10, p.getAreaEvento());
            } else {
                for (int i = 4; i <= 10; i++) {
                    pstmt.setNull(i, Types.NULL);
                }
            }
            // Seta o ID para a cláusula WHERE
            pstmt.setInt(11, espaco.getId());

            pstmt.executeUpdate();
        }
    }

    /**
     * Exclui um espaço do banco de dados com base no ID.
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM espacos WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Lista todos os espaços cadastrados no banco de dados.
     */
    public List<Espaco> listarTodos() throws SQLException {
        return buscarEspacosPorCondicao(null);
    }

    /**
     * Lista apenas os espaços disponíveis, corrigindo o erro de
     * UnsupportedOperationException.
     */
    public List<Espaco> listarDisponiveis() throws SQLException {
        return buscarEspacosPorCondicao("WHERE e.disponivel = true");
    }

    /**
     * Busca um único espaço pelo seu ID.
     */
    public Optional<Espaco> buscarPorId(int id) throws SQLException {
        List<Espaco> resultado = buscarEspacosPorCondicao("WHERE e.id = " + id);
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    private List<Espaco> buscarEspacosPorCondicao(String condicaoWhere) throws SQLException {
        List<Espaco> espacos = new ArrayList<>();
        String sql = "SELECT e.*, l.nome as locador_nome FROM espacos e JOIN locadores l ON e.locador_id = l.id";

        if (condicaoWhere != null && !condicaoWhere.isEmpty()) {
            sql += " " + condicaoWhere;
        }

        try (Connection conn = ConexaoBD.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Espaco espaco = criarEspacoDeResultSet(rs);
                if (espaco != null) {
                    espacos.add(espaco);
                }
            }
        }
        return espacos;
    }

    private Espaco criarEspacoDeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo_espaco");
        Espaco espaco;

        switch (tipo) {
            case "SalaComercial":
                espaco = new SalaComercial();
                ((SalaComercial) espaco).setSistemaSeguranca(rs.getString("sistema_seguranca"));
                ((SalaComercial) espaco).setNumeroDeJanelas(rs.getInt("numero_janela"));
                ((SalaComercial) espaco).setNumeroDePortas(rs.getInt("numero_de_portas"));
                break;
            case "GalpaoIndustrial":
                espaco = new GalpaoIndustrial();
                ((GalpaoIndustrial) espaco).setAreaTotal(rs.getDouble("area_total"));
                ((GalpaoIndustrial) espaco).setAltura(rs.getDouble("altura"));
                break;
            case "LojaComercial":
                espaco = new LojaComercial();
                ((LojaComercial) espaco).setAreaTotal(rs.getDouble("area_total"));
                break;
            case "Estudio":
                espaco = new Estudio();
                ((Estudio) espaco).setAreaEstudio(rs.getDouble("area_estudio"));
                break;
            case "PavilhaoEventos":
                espaco = new PavilhaoEventos();
                ((PavilhaoEventos) espaco).setAreaEvento(rs.getDouble("area_evento"));
                break;
            default:
                System.err.println("Tipo de espaço desconhecido no banco de dados: " + tipo);
                return null;
        }

        espaco.setId(rs.getInt("id"));
        espaco.setEndereco(rs.getString("endereco"));
        espaco.setValor(rs.getDouble("valor"));
        espaco.setDisponivel(rs.getBoolean("disponivel"));

        Locador locador = new Locador();
        locador.setId(rs.getInt("locador_id"));
        locador.setNome(rs.getString("locador_nome"));
        espaco.setLocador(locador);

        return espaco;
    }
}

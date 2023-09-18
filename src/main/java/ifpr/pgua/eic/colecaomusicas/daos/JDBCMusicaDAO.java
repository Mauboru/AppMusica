package ifpr.pgua.eic.colecaomusicas.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.Musica;

public class JDBCMusicaDAO implements MusicaDAO {
    private static final String INSERTSQL = "INSERT INTO OOIIMusicas(nome,duracao,anoLancamento,artistaId,generoId) VALUES (?,?,?,?,?)";
    private static final String SELECTSQL = "SELECT * FROM OOIIMusicas";
    private FabricaConexoes fabrica;

    public JDBCMusicaDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Musica musica) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, musica.getNome());
            pstm.setInt(2, musica.getDuracao());
            pstm.setInt(3, musica.getAnoLancamento());
            pstm.setInt(4, musica.getArtista().getId());
            pstm.setInt(5, musica.getGenero().getId());
            int ret = pstm.executeUpdate();
            pstm.close();
            con.close();
            if (ret == 1) {
                // ResultSet rs = pstm.getGeneratedKeys();
                // rs.next();
                // int id = rs.getInt(1);
                // int id = DBUtils.getLastId(pstm);
                // musica.setId(id);   
                return Resultado.sucesso("Música cadastrada", musica);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(SELECTSQL);
            ResultSet rs = pstm.executeQuery();
            ArrayList<Musica> lista = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int duracao = rs.getInt("duracao");
                int anoLancamento = rs.getInt("anoLancamento");
                // iremos buscar artista e genero através do repositório
                Musica musica = new Musica(id, nome, anoLancamento, duracao, null, null);
                lista.add(musica);
            }
            return Resultado.sucesso("Musicas listadas", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public List<Musica> buscarMusicasPlaylist(int playlistId) {
        try (Connection con = fabrica.getConnection()) {
            String sql = "SELECT m.* FROM OOIIMusicas m " +
                    "INNER JOIN OOIIPlaylistMusicas pm ON m.id = pm.musicaId " +
                    "WHERE pm.playlistId = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, playlistId);
            ResultSet rs = pstm.executeQuery();
            ArrayList<Musica> lista = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int duracao = rs.getInt("duracao");
                int anoLancamento = rs.getInt("anoLancamento");
                Musica musica = new Musica(id, nome, anoLancamento, duracao, null, null);
                lista.add(musica);
            }
            return lista;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Resultado atualizar(int id, Musica nova) {
        return null;
    }

    @Override
    public Resultado deletar(int id) {
        return null;
    }
}
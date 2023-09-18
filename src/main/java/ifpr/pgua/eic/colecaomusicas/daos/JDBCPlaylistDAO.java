package ifpr.pgua.eic.colecaomusicas.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.*;
import ifpr.pgua.eic.colecaomusicas.utils.DBUtils;

public class JDBCPlaylistDAO implements PlaylistDAO {
    private static final String INSERTSQL = "INSERT INTO OOIIPlaylist(nome) VALUES (?)";
    private static final String SELECTSQL = "SELECT * FROM OOIIPlaylist";
    private FabricaConexoes fabrica;

    public JDBCPlaylistDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Playlist playlist) {
        try {
            Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, playlist.getNome());
            int ret = pstm.executeUpdate();
           
            if (ret == 1) {
                int id = DBUtils.getLastId(pstm);
                playlist.setId(id);
                cadastrarMusicas(playlist.getId(), playlist.getMusicas());
                pstm.close();
                con.close();
                return Resultado.sucesso("Playlist cadastrada!", playlist);
            }            
            return Resultado.erro("Erro não identificado!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    private Resultado cadastrarMusicas(int id, List<Musica> musicas) {
        try {
            Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(
                    "INSERT INTO OOIIPlaylistMusicas(playlistId, musicaId) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (Musica musica : musicas) {
                pstm.setInt(1, id);
                pstm.setInt(2, musica.getId());
                int ret = pstm.executeUpdate();
                if (ret == 1) {
                    // ResultSet rs = pstm.getGeneratedKeys();
                    // rs.next();
                    // count++;
                    int ids = DBUtils.getLastId(pstm);
                    musica.setId(ids);
                }
            }
            // pstm.close();
            // con.close();
            return Resultado.erro("Erro não identificado!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {        
        try (Connection con = fabrica.getConnection()){
             PreparedStatement ps = con.prepareStatement(SELECTSQL);
             ResultSet rs = ps.executeQuery();
             List<Playlist> playlists = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Playlist playlist = new Playlist(id, nome, null);
                playlists.add(playlist);
            }
            return Resultado.sucesso("Playlist listadas", playlists);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado atualizar(int id, Playlist novo) {
        return null;
    }

    @Override
    public Resultado deletar(int id) {
        return null;
    }
}
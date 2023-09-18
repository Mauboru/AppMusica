package ifpr.pgua.eic.colecaomusicas.daos;

import java.sql.*;
import java.util.ArrayList;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.*;

public class JDBCArtistaDAO implements ArtistaDAO {
    private FabricaConexoes fabrica;

    public JDBCArtistaDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Artista artista) {
        try {
            Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement("INSERT INTO OOIIArtistas(nome, contato) VALUES (?, ?)");
            pstm.setString(1, artista.getNome());
            pstm.setString(2, artista.getContato());
            int ret = pstm.executeUpdate();
            pstm.close();
            con.close();
            if (ret == 1) {
                return Resultado.sucesso("Artista cadastrado!", artista);
            }
            return Resultado.erro("Erro não identificado!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {
        try {
            Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM OOIIArtistas");
            ResultSet rs = pstm.executeQuery();
            ArrayList<Artista> artistas = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String contato = rs.getString("contato");
                Artista artista = new Artista(id, nome, contato);
                artistas.add(artista);
            }
            rs.close();
            pstm.close();
            con.close();
            return Resultado.sucesso("Artistas listados!", artistas);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado getById(int id){
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM OOIIArtistas WHERE id=?");
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String nome = rs.getString("nome");
                String contato = rs.getString("contato");
                Artista artista = new Artista(id,nome, contato);
                return Resultado.sucesso("Artista encontrado", artista);
            }else{
                return Resultado.erro("Artista não encontrado!");
            }
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscarArtistaMusica(int musicaId) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT artistaId FROM OOIIMusicas WHERE id=?");
            pstm.setInt(1, musicaId);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            int artistaId = rs.getInt("artistaId");
            return getById(artistaId);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado atualizar(int id, Artista novo) {
        try(Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement("UPDATE OOIIArtistas SET nome=?, contato=? WHERE id=?");
            pstm.setString(1, novo.getNome());
            pstm.setString(2, novo.getContato());
            pstm.setInt(3, id);

            int ret = pstm.executeUpdate();

            if(ret == 1){
                novo.setId(id);
                return Resultado.sucesso("Artista editado!", novo);
            }
            return Resultado.erro("Ja era fi!");
        }catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado deletar(int id) {
        return null;
    }
}
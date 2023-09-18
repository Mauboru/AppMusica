package ifpr.pgua.eic.colecaomusicas.daos;

import java.sql.*;
import java.util.ArrayList;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.*;

public class JDBCGeneroDAO implements GeneroDAO {
    private FabricaConexoes fabrica;

    public JDBCGeneroDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Genero genero) {
        try {
            Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement("INSERT INTO OOIIGeneros(nome) VALUES (?)");
            pstm.setString(1, genero.getNome());
            int ret = pstm.executeUpdate();
            pstm.close();
            con.close();
            if (ret == 1) {
                return Resultado.sucesso("Gênero cadastrado!", genero);
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

            // Preparar o comando SQL para selecionar todos os gêneros
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM OOIIGeneros");
            ResultSet rs = pstm.executeQuery();
            ArrayList<Genero> generos = new ArrayList<>();
            // Iterar pelos resultados e adicionar os gêneros à lista
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Genero genero = new Genero(id, nome);
                generos.add(genero);
            }
            rs.close();
            pstm.close();
            con.close();

            return Resultado.sucesso("Gêneros listados!", generos);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscarGeneroMusica(int musicaId) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT generoId FROM OOIIMusicas WHERE id=?");
            pstm.setInt(1, musicaId);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            int generoId = rs.getInt("generoId");
            return getById(generoId);
        } catch (SQLException e) {  
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado getById(int id) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM OOIIGeneros WHERE id=?");
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String nome = rs.getString("nome");
                Genero genero = new Genero(id, nome);
                return Resultado.sucesso("Gênero encontrado!", genero);
            }
            return Resultado.erro("Gênero não encontrado!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado atualizar(int id, Genero novo) {
        try(Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement("UPDATE OOIIGeneros SET nome=? WHERE id=?");
            pstm.setString(1, novo.getNome());
            pstm.setInt(2, id);

            int ret = pstm.executeUpdate();

            if(ret == 1){
                novo.setId(id);
                return Resultado.sucesso("Gênero editado!", novo);
            }
            return Resultado.erro("Ja era fi!");
        }catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado delete(int id) {
        return null;
    }
}
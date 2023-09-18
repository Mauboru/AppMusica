package ifpr.pgua.eic.colecaomusicas.repositories;

import java.util.*;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.daos.ArtistaDAO;
import ifpr.pgua.eic.colecaomusicas.models.*;

public class RepositorioArtistas {
    private ArrayList<Artista> artistas;
    private ArtistaDAO dao;

    public RepositorioArtistas(ArtistaDAO dao) {
        artistas = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cadastrarArtista(String nome, String contato){
        if(nome.isEmpty() || nome.isBlank()){
            return Resultado.erro("Nome inválido!");
        }

        if(contato.isBlank() || contato.isEmpty()){
            return Resultado.erro("Contato inválido!");
        }

        Artista artista = new Artista(nome, contato);
        return dao.criar(artista);
    }

    public Resultado listarArtistas(){
        return dao.listar();
    }

    public Resultado atualizarArtista(int id, String nome, String contato) {
        Artista artista = new Artista(nome, contato);
        return dao.atualizar(id, artista);
    }
}
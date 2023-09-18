package ifpr.pgua.eic.colecaomusicas.repositories;

import java.util.*;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.daos.GeneroDAO;
import ifpr.pgua.eic.colecaomusicas.models.*;

public class RepositorioGeneros {
    private ArrayList<Genero> generos;
    private GeneroDAO dao;

    public RepositorioGeneros(GeneroDAO dao) {
        generos = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cadastrarGenero(String nome){
        if(nome.isEmpty() || nome.isBlank()){
            return Resultado.erro("Nome inválido!");
        }
        Genero genero = new Genero(nome);
        return dao.criar(genero);
    }

    public Resultado listarGeneros(){
        return dao.listar();
    }

    public Resultado atualizarGenero(int id, String nome) {
        Genero genero = new Genero(nome);
        return dao.atualizar(id, genero);
    }
}
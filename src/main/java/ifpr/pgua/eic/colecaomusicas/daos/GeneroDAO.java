package ifpr.pgua.eic.colecaomusicas.daos;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.Genero;

public interface GeneroDAO {
    Resultado criar(Genero genero);
    Resultado listar();
    Resultado getById(int id);
    Resultado buscarGeneroMusica(int musicaId);
    Resultado atualizar(int id, Genero novo);
    Resultado delete(int id);
}
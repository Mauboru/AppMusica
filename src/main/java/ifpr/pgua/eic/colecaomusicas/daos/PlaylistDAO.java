package ifpr.pgua.eic.colecaomusicas.daos;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.Playlist;

public interface PlaylistDAO {
    Resultado criar(Playlist playlist);
    Resultado listar();
    Resultado atualizar(int id, Playlist novo);
    Resultado deletar(int id);
}
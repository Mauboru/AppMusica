package ifpr.pgua.eic.colecaomusicas.daos;

import java.util.List;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.models.Musica;

public interface MusicaDAO {
    Resultado criar(Musica musica);
    Resultado listar();
    Resultado atualizar(int id, Musica nova);
    Resultado deletar(int id);
    List<Musica> buscarMusicasPlaylist(int playlistId);
}
package ifpr.pgua.eic.colecaomusicas;

import ifpr.pgua.eic.colecaomusicas.controllers.*;
import ifpr.pgua.eic.colecaomusicas.daos.*;
import ifpr.pgua.eic.colecaomusicas.repositories.*;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

public class App extends BaseAppNavigator {
    private ArtistaDAO artistaDAO = new JDBCArtistaDAO(FabricaConexoes.getInstance());
    private RepositorioArtistas repositorioArtistas = new RepositorioArtistas(artistaDAO);
    
    private GeneroDAO generoDAO = new JDBCGeneroDAO(FabricaConexoes.getInstance());
    private RepositorioGeneros repositorioGeneros = new RepositorioGeneros(generoDAO);

    private MusicaDAO musicaDAO = new JDBCMusicaDAO(FabricaConexoes.getInstance());
    private RepositorioMusicas repositorioMusicas = new RepositorioMusicas(musicaDAO, artistaDAO, generoDAO);

    private PlaylistDAO playlistDAO = new JDBCPlaylistDAO(FabricaConexoes.getInstance());
    private RepositorioPlaylist repositorioPlaylist = new RepositorioPlaylist(playlistDAO);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getHome() {
        return "PRINCIPAL";
    }

    @Override
    public String getAppTitle() {
        return "Coleção de Músicas";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", 
                new ScreenRegistryFXML(App.class, 
                        "principal.fxml", 
                        o -> new Principal()));
        registraTela("CADASTROGENERO",
                new ScreenRegistryFXML(App.class,
                        "cadastrar_generos.fxml",
                        o -> new CadastroGenero(repositorioGeneros)));
        registraTela("CADASTROARTISTA",
                new ScreenRegistryFXML(App.class,
                        "cadastrar_artistas.fxml",
                        o -> new CadastroArtista(repositorioArtistas)));
        registraTela("LISTAGENERO",
                new ScreenRegistryFXML(App.class,
                        "listar_genero.fxml",
                        o -> new ListarGenero(repositorioGeneros)));
        registraTela("LISTAARTISTA",
                new ScreenRegistryFXML(App.class,
                        "listar_artista.fxml",
                        o -> new ListarArtista(repositorioArtistas)));   
        registraTela("CADASTRARMUSICA",
                new ScreenRegistryFXML(App.class, 
                        "cadastrar_musicas.fxml", 
                        o->new CadastroMusica(repositorioMusicas,repositorioGeneros,repositorioArtistas)));
        registraTela("LISTARMUSICAS",
                new ScreenRegistryFXML(App.class, 
                        "listar_musicas.fxml", 
                        o->new ListarMusicas(repositorioMusicas)));
        registraTela("CADASTRARPLAYLIST",
                new ScreenRegistryFXML(App.class, 
                        "cadastrar_playlist.fxml", 
                        o->new CadastroPlaylist(repositorioPlaylist, repositorioMusicas)));
        registraTela("LISTARPLAYLIST",
                new ScreenRegistryFXML(App.class, 
                        "listar_playlist.fxml", 
                        o->new ListarPlaylist(repositorioPlaylist, repositorioMusicas)));
    }
}
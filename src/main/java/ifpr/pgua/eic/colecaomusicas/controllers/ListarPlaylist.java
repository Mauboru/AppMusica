package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.*;
import ifpr.pgua.eic.colecaomusicas.repositories.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;

public class ListarPlaylist implements Initializable {
    private RepositorioPlaylist repositorioPlaylist;
    private RepositorioMusicas repositorioMusicas;

    @FXML
    private ListView<Musica> lstMusica;

    @FXML
    private ListView<Playlist> lstPlaylist;

    public ListarPlaylist(RepositorioPlaylist repositorioPlaylist, RepositorioMusicas repositorioMusicas) {
        this.repositorioPlaylist = repositorioPlaylist;
        this.repositorioMusicas = repositorioMusicas;
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    @FXML
    private void carregarMusicasDaPlaylist(MouseEvent event) {
        Playlist playlistSelecionada = lstPlaylist.getSelectionModel().getSelectedItem();
        if (playlistSelecionada != null) {
            List<Musica> resultado = repositorioMusicas.buscarMusicasPlaylist(playlistSelecionada.getId());
            if (resultado != null) {
                lstMusica.getItems().clear();
                lstMusica.getItems().addAll(resultado);
            } else {
                Alert alerta = new Alert(AlertType.ERROR, "Deu ruim!");
                alerta.show();
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lstPlaylist.getItems().clear();
        Resultado resultado = repositorioPlaylist.listarPlaylist();

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, "Erro: " + resultado.getMsg());
            alert.showAndWait();
        } else {
            List<Playlist> lista = (List<Playlist>) resultado.comoSucesso().getObj();
            lstPlaylist.getItems().addAll(lista);
        }
    }
}
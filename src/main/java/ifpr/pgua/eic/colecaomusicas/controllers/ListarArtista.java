package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Artista;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioArtistas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

public class ListarArtista implements Initializable{
    @FXML
    private ListView<Artista> lstArtista;

    private RepositorioArtistas repositorio;

    public ListarArtista(RepositorioArtistas repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lstArtista.getItems().clear();
        Resultado resultado = repositorio.listarArtistas();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }else{
            List lista = (List)resultado.comoSucesso().getObj();
            lstArtista.getItems().addAll(lista);
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    @FXML
    private void editar(){
        Artista artista = lstArtista.getSelectionModel().getSelectedItem();

        if(artista != null){
            App.pushScreen("CADASTROARTISTA", o-> new CadastroArtista(repositorio, artista));
        }
    }
}
package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Genero;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioGeneros;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;

public class ListarGenero implements Initializable{
    @FXML
    private ListView<Genero> lstGenero;
    private RepositorioGeneros repositorio;

    public ListarGenero(RepositorioGeneros repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lstGenero.getItems().clear();
        Resultado resultado = repositorio.listarGeneros();

        if(resultado.foiSucesso()){
            List<Genero> lista = (List)resultado.comoSucesso().getObj();
            lstGenero.getItems().addAll(lista);
        }else{
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    @FXML
    private void editar(){
        Genero genero = lstGenero.getSelectionModel().getSelectedItem();

        if(genero != null){
            App.pushScreen("CADASTROGENERO", o-> new CadastroGenero(repositorio, genero));
        }
    }
}
package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Genero;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioGeneros;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class CadastroGenero implements Initializable{

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    private RepositorioGeneros repositorio;
    private Genero antigo;

    public CadastroGenero(RepositorioGeneros repositorio) {
        this.repositorio = repositorio;
    }

    //Construtor para editar
    public CadastroGenero(RepositorioGeneros repositorio, Genero antigo){
        this.repositorio = repositorio;
        this.antigo = antigo;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(antigo != null){
            tfNome.setText(antigo.getNome());
        }
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String nome = tfNome.getText();
        Alert alert;
        Resultado resultado;

        if(antigo == null) resultado = repositorio.cadastrarGenero(nome);
        else resultado = repositorio.atualizarGenero(antigo.getId(), nome);
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }
        alert.showAndWait();
        tfNome.setText(null);
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
}
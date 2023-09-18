package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Artista;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioArtistas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class CadastroArtista implements Initializable{

    @FXML
    private TextField tfContato;

    @FXML
    private TextField tfNome;

    private RepositorioArtistas repositorio;
    private Artista antigo;

    //Construtor para cadastrar
    public CadastroArtista(RepositorioArtistas repositorio){
        this.repositorio = repositorio;
    }

    //Construtor para editar
    public CadastroArtista(RepositorioArtistas repositorio, Artista antigo){
        this.repositorio = repositorio;
        this.antigo = antigo;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(antigo != null){
            tfNome.setText(antigo.getNome());
            tfContato.setText(antigo.getContato());
        }
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String nome = tfNome.getText();
        String contato = tfContato.getText();
        Alert alert;
        Resultado resultado;

        if(antigo == null) resultado = repositorio.cadastrarArtista(nome, contato);
        else resultado = repositorio.atualizarArtista(antigo.getId(), nome, contato);
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }
        alert.showAndWait();
        tfNome.setText(null);
        tfContato.setText(null);
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
}
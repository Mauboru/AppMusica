package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.*;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.*;
import ifpr.pgua.eic.colecaomusicas.repositories.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;;

public class CadastroPlaylist implements Initializable{
    @FXML
    private TextField tfNome;

    @FXML
    private ListView<Musica> lstMusica;

    private RepositorioMusicas repositorioMusicas;
    private RepositorioPlaylist repositorioPlaylist;

    public CadastroPlaylist(RepositorioPlaylist repositorioPlaylist, RepositorioMusicas repositorioMusicas){
        this.repositorioPlaylist = repositorioPlaylist;
        this.repositorioMusicas = repositorioMusicas;
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String nome = tfNome.getText();
        List<Musica> selecionados = lstMusica.getSelectionModel().getSelectedItems();
        Resultado rs = repositorioPlaylist.cadastrarPlaylist(nome, selecionados);
        String msg="";
        Alert alert;
        
        msg = rs.getMsg();

        if(rs.foiErro()){
            alert = new Alert(AlertType.ERROR,msg);
        }else{
            alert = new Alert(AlertType.INFORMATION,msg);   
        }
        alert.showAndWait();
    }

    @FXML
    void cancelar(ActionEvent event) {
        App.popScreen();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        atualizarTela();
    }

    public void atualizarTela(){
        lstMusica.getItems().clear();
        lstMusica.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Resultado r = repositorioMusicas.listarMusicas();

        if(r.foiSucesso()){
            List<Musica> lista = (List)r.comoSucesso().getObj();
            lstMusica.getItems().addAll(lista);
        }else{
            Alert alert = new Alert(AlertType.ERROR, r.getMsg());
            alert.showAndWait();
        }
    }
}
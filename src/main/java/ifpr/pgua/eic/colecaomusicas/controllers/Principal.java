package ifpr.pgua.eic.colecaomusicas.controllers;

import ifpr.pgua.eic.colecaomusicas.App;
import javafx.fxml.FXML;

public class Principal {

    @FXML
    private void cadastrarGenero(){
        App.pushScreen("CADASTROGENERO");
    }

    @FXML
    private void cadastrarArtista(){
        App.pushScreen("CADASTROARTISTA");
    }

    @FXML
    private void listarArtista(){
        App.pushScreen("LISTAARTISTA");
    }

    @FXML
    private void listarGenero(){
        App.pushScreen("LISTAGENERO");
    }

    @FXML
    private void cadastrarMusica(){
        App.pushScreen("CADASTRARMUSICA");
    }

    @FXML
    private void listarMusicas(){
        App.pushScreen("LISTARMUSICAS");
    }

    @FXML
    private void cadastrarPlaylist(){
        App.pushScreen("CADASTRARPLAYLIST");
    }

    @FXML
    private void listarPlaylist(){
        App.pushScreen("LISTARPLAYLIST");
    }
}
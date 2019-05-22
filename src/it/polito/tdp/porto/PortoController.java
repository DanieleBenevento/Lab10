package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;

import it.polito.tdp.porto.db.PortoDAO;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	private Model model= new Model();
	private PortoDAO dao= new PortoDAO();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private Button btnTrovaCoautori;

    @FXML
    private Button btnSequenzaArticoli;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {

    	Author scelta=boxPrimo.getValue();
    	model.creaGrafo();
    	for(Author a:Graphs.neighborListOf(model.getGrafo(), scelta) ) {
    	txtResult.appendText(""+a.getFirstname()+" "+a.getLastname()+"\n");
    	}
    }

    @FXML
    void handleSequenza(ActionEvent event) {
        model.creaGrafo();
    	if(boxPrimo.getValue().equals(boxSecondo.getValue())) {
    		txtResult.appendText("ERRORE: I valori dei menù a tendina devono essere differenti\n");
    	}
    	else {
    		
    	}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnTrovaCoautori != null : "fx:id=\"btnTrovaCoautori\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnSequenzaArticoli != null : "fx:id=\"btnSequenzaArticoli\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	boxPrimo.getItems().addAll(dao.getListaAutori(model.getIdMap()));
    	boxSecondo.getItems().addAll(dao.getListaAutori(model.getIdMap()));
    }
}

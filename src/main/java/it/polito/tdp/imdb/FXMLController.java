/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {

    	this.txtResult.clear();
    	
    	for (Actor a:model.collegati(this.boxAttore.getValue()))
    		this.txtResult.appendText(a.toString() + "\n");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String s = this.boxGenere.getValue();
    	model.creaGrafo(s);
    	
    	this.boxAttore.getItems().addAll(model.vertici());
    	
    	this.txtResult.setText("Grafo creato!\nNumero vertici: " + model.vertici().size() +"\nNumero archi: " + model.numeroArchi());
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	
    	this.txtResult.clear();

    	
    	try {
    		int giorni = Integer.parseInt(txtGiorni.getText());
    		model.runSim(giorni);
    		
    		for (Actor a:model.intervistati())
        		this.txtResult.appendText(a.toString() + "\n");
    		
    		this.txtResult.appendText("Giorni pausa: " + model.giorniPausa());
    		
    		
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Errore: il valore inserito non è un intero\n");
    		return;
    	}


    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGenere.getItems().addAll(model.genresList());
    }
}

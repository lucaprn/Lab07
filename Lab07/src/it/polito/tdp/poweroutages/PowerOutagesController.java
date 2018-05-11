/**
 * Sample Skeleton for 'PowerOutages.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Outage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	Model model;
	

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="cmbNerc"
    private ComboBox<String> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML
    void doReset(ActionEvent event) {
    	this.reset();
    	
    }

    @FXML
    void doRun(ActionEvent event) {
    
    	
    	try {
    	String value = this.cmbNerc.getValue();
    	if(value==null) {
    		this.txtResult.appendText("\nSeleziona un nerc");
    		return;
    	}
    	int maxAnni = this.model.intervalloValidoAnni(value);
    	long maxOre = this.model.intervalloValidoOre(value);
    	int anni = Integer.parseInt(this.txtYears.getText());
    	int ore= Integer.parseInt(this.txtHours.getText());
    	if(anni>maxAnni || ore>maxOre) {
    		this.txtResult.appendText("\nInserisci anni/ore valide\n");
    		return;
    	}
    	this.txtResult.appendText(String.format("%nSto calcolando il worst case per il nerc : %s%nNumero massimo anni : %d%nNumero massimo ore : %d%n", value, anni,ore));
    	this.model.worstCase(value, 0, anni, ore);
    	int numeroClienti = this.model.numeroClientiBest();
    	long numeroOre = this.model.numeroOreBest();
    	this.txtResult.appendText(String.format("%nClienti : %d",numeroClienti));
    	this.txtResult.appendText(String.format("%nOre : %d%n",numeroOre));
    	this.txtResult.appendText(this.model.toStringBest());
    	}
    	catch (NumberFormatException e) {
   
    		this.txtResult.appendText("\nInserisci Valori Numerici!");
    	}
    	
    	
    }
    void reset() {
    	this.txtHours.clear();
    	this.txtResult.clear();
    	this.txtYears.clear();
    	this.model.reset();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		List<String> tmpString = new ArrayList<>();
		List<Nerc> tmp = new ArrayList<>(model.getNercList());
		for(Nerc nerc : tmp) {
			tmpString.add(nerc.getValue());
		}
		this.cmbNerc.getItems().addAll(tmpString);
	}
    
  
}
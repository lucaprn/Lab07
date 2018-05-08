package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutageEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea txtResult;

	@FXML
	private ComboBox<Nerc> cmbNerc;

	@FXML
	private TextField txtYears;

	@FXML
	private TextField txtHours;

	private Model model;

	@FXML
	void doRun(ActionEvent event) {

		txtResult.clear();

		try {
			Nerc selectedNerc = cmbNerc.getSelectionModel().getSelectedItem();
			if (selectedNerc == null) {
				txtResult.setText("Select a NERC (area identifier)");
				return;
			}

			int maxY = Integer.parseInt(txtYears.getText());
			int yearListSize = model.getYearList().size();
			if (maxY <= 0 || maxY > yearListSize) {
				txtResult.setText("Select a number of years in range [1, " + yearListSize + "]");
				return;
			}

			int maxH = Integer.parseInt(txtHours.getText());
			if (maxH <= 0) {
				txtResult.setText("Select a number of hours greater than 0");
				return;
			}

			txtResult.setText(
					String.format("Computing the worst case analysis... for %d hours and %d years", maxH, maxY));
			List<PowerOutageEvent> worstCase = model.getWorstCase(maxY, maxH, selectedNerc);

			txtResult.clear();
			txtResult.appendText("Tot people affected: " + model.sumAffectedPeople(worstCase) + "\n");
			txtResult.appendText("Tot hours of outage: " + model.sumOutageHours(worstCase) + "\n");

			for (PowerOutageEvent ee : worstCase) {
				txtResult.appendText(String.format("%d %s %s %d %d", ee.getYear(), ee.getOutageStart(),
						ee.getOutageEnd(), ee.getOutageDuration(), ee.getAffectedPeople()));
				txtResult.appendText("\n");
			}

		} catch (NumberFormatException e) {
			txtResult.setText("Insert a valid number of years and of hours");
		}
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";

		// Utilizzare questo font per incolonnare correttamente i dati
		txtResult.setStyle("-fx-font-family: monospace");
	}

	public void setModel(Model model) {

		this.model = model;
		List<Nerc> nercList = model.getNercList();

		cmbNerc.getItems().addAll(nercList);
	}
}
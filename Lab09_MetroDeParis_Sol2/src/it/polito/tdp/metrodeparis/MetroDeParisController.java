package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import it.polito.tdp.metrodeparis.model.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

	Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea txtRisultato;

	@FXML
	private ComboBox<Fermata> comboBoxPartenza;

	@FXML
	private ComboBox<Fermata> comboBoxArrivo;

	@FXML
	private Button btnCalcola;

	void setModel(Model model) {

		this.model = model;

		try {
			model.creaGrafo();

			List<Fermata> stazioni = model.getStazioni();
			comboBoxPartenza.getItems().addAll(stazioni);
			comboBoxArrivo.getItems().addAll(stazioni);

		} catch (RuntimeException e) {
			txtRisultato.setText(e.getMessage());
		}
	}

	@FXML
	void calcolaPercorso(ActionEvent event) {

		Fermata stazioneDiPartenza = comboBoxPartenza.getValue();
		Fermata stazioneDiArrivo = comboBoxArrivo.getValue();

		if (stazioneDiPartenza == null || stazioneDiArrivo == null) {
			txtRisultato.setText("Inserire una stazione di arrivo ed una di partenza.");
			return;
		}

		if (stazioneDiPartenza.equals(stazioneDiArrivo)) {
			txtRisultato.setText("Inserire una stazione di arrivo diversa da quella di partenza.");
			return;
		}

		try {
			// Calcolo il percorso tra le due stazioni
			model.calcolaPercorso(stazioneDiPartenza, stazioneDiArrivo);

			// Ottengo il tempo di percorrenza
			String timeString = Util.timeConverter((int) model.getPercorsoTempoTotale());

			StringBuilder risultato = new StringBuilder();
			// Ottengo il percorso
			risultato.append(model.getPercorsoEdgeList());
			risultato.append("\n\nTempo di percorrenza stimato: " + timeString + "\n");

			// Aggiorno la TextArea
			txtRisultato.setText(risultato.toString());

		} catch (RuntimeException e) {
			txtRisultato.setText(e.getMessage());
		}
	}

	@FXML
	void initialize() {

		assert txtRisultato != null : "fx:id=\"txtElencoStazioni\" was not injected: check your FXML file 'gui.fxml'.";
		assert comboBoxPartenza != null : "fx:id=\"comboBoxPartenza\" was not injected: check your FXML file 'gui.fxml'.";
		assert comboBoxArrivo != null : "fx:id=\"comboBoxArrivo\" was not injected: check your FXML file 'gui.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'gui.fxml'.";
	}
}
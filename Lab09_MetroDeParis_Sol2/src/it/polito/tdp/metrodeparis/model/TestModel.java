package it.polito.tdp.metrodeparis.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo();

		List<Fermata> fermate = model.getStazioni();
		if (fermate.size() > 1) {
			Fermata fermataPartenza = fermate.get(0);
			Fermata fermataArrivo = fermate.get(fermate.size() - 1);

			System.out.println("Calcolo percorso tra: " + fermataPartenza + " e: " + fermataArrivo);
			model.calcolaPercorso(fermataPartenza, fermataArrivo);
			System.out.println(model.getPercorsoEdgeList());

			// Ottengo il tempo di percorrenza
			System.out.println("Tempo del percorso: " + Util.timeConverter((int) model.getPercorsoTempoTotale()));
		}

	}

}

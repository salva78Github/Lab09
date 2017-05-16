package it.polito.tdp.metrodeparis.db;

import java.util.List;
import java.util.Map;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataSuLinea;
import it.polito.tdp.metrodeparis.model.Linea;

public class TestDAO {

	public static void main(String[] args) {
		
		MetroDAO metroDAO = new MetroDAO();
		
		System.out.println("Lista fermate");
		Map<Integer, Fermata> fermate = metroDAO.getAllFermate();
		System.out.println(fermate);
		
		System.out.println("Lista linee");
		Map<Integer, Linea> linee = metroDAO.getAllLinee();
		System.out.println(linee);
		
		System.out.println("Lista connessioni");
		List<Connessione> connessioni = metroDAO.getAllConnessioni(fermate, linee);
		System.out.println(connessioni);
		
		System.out.println("Lista fermate su linea");
		List<FermataSuLinea> fermateSuLinea = metroDAO.getAllFermateSuLinea(fermate, linee);
		System.out.println(fermateSuLinea);
	}
}

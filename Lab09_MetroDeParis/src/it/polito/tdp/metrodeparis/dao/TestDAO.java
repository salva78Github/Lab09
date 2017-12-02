package it.polito.tdp.metrodeparis.dao;

import java.util.List;

import it.polito.tdp.metrodeparis.exception.MetroDeParisException;
import it.polito.tdp.metrodeparis.model.Fermata;

public class TestDAO {

	public static void main(String[] args) {
		
		MetroDAO metroDAO = new MetroDAO();
		
		System.out.println("Lista fermate");
		List<Fermata> fermate;
		try {
			fermate = metroDAO.getAllFermate();
			System.out.println(fermate);
		} catch (MetroDeParisException e) {
			e.printStackTrace();
		}
	}

}

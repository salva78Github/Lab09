package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.exception.MetroDeParisException;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println("TODO: write a Model class and test it!");
		
		try {
			model.printGraphStats();
			Percorso p = model.getCamminoMinino(new Fermata(15, "Argentine",new LatLng(2.29011,48.87537)), new Fermata(20,"Athis Mons", new LatLng(2.40332,48.71245)));
			System.out.println(p);
			
			
			
		} catch (MetroDeParisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

package it.polito.tdp.metrodeparis.model;

import java.util.List;

import it.polito.tdp.metrodeparis.util.TimeHelper;

public class Percorso {
	private final List<Link> collegamenti;
	private final double tempoPercorrenza;

	/**
	 * @param colleganenti
	 * @param tempoPercorrenza
	 */
	public Percorso(List<Link> colleganenti, double tempoPercorrenza) {
		this.collegamenti = colleganenti;
		this.tempoPercorrenza = tempoPercorrenza;
	}

	/**
	 * @return the collegamenti
	 */
	public List<Link> getCollegamenti() {
		return collegamenti;
	}

	/**
	 * @return the tempoPercorrenza
	 */
	public double getTempoPercorrenza() {
		return tempoPercorrenza;
	}

	/**
	 * @return the tempoPercorrenza
	 */
	public String getTempoPercorrenzaToString() {
		return TimeHelper.timeConverter((int)getTempoPercorrenza());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Percorso [collegamenti=" + collegamenti + ", tempoPercorrenza=" + getTempoPercorrenzaToString() + "]";
	}

}

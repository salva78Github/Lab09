package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Link extends DefaultWeightedEdge {
	private static final long serialVersionUID = 3658204949203717727L;

	private final Linea linea;

	/**
	 * @param linea
	 */
	public Link(Linea linea) {
		super();
		this.linea = linea;
	}

	/**
	 * @return the linea
	 */
	public Linea getLinea() {
		return linea;
	}
	
	
}

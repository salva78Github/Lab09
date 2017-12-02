package it.polito.tdp.metrodeparis.model;

public class Linea {
	private final long id;
	private final String name;
	private final double velocita;
	private final double intervallo;
	private final String colore;
	/**
	 * @param id
	 * @param name
	 * @param velocita
	 * @param intervallo
	 * @param colore
	 */
	public Linea(long id, String name, double velocita, double intervallo, String colore) {
		this.id = id;
		this.name = name;
		this.velocita = velocita;
		this.intervallo = intervallo;
		this.colore = colore;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the velocita
	 */
	public double getVelocita() {
		return velocita;
	}
	/**
	 * @return the intervallo
	 */
	public double getIntervallo() {
		return intervallo;
	}
	/**
	 * @return the colore
	 */
	public String getColore() {
		return colore;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		if (id != other.id)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Linea [id=" + id + ", name=" + name + ", velocita=" + velocita + ", intervallo=" + intervallo
				+ ", colore=" + colore + "]";
	}

	
	
	
}

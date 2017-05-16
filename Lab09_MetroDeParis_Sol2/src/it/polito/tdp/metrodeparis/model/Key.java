package it.polito.tdp.metrodeparis.model;

public class Key {

	int idFermata;
	int idLinea;
	
	public Key(int idFermata, int idLinea) {
		super();
		this.idFermata = idFermata;
		this.idLinea = idLinea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFermata;
		result = prime * result + idLinea;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (idFermata != other.idFermata)
			return false;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}

}

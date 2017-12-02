package it.polito.tdp.metrodeparis.model;

public class FermataPair {
	private final Fermata f1;
	private final Fermata f2;
	private final Linea linea;

	/**
	 * @param f1
	 * @param f2
	 * @param linea
	 */
	public FermataPair(Fermata f1, Fermata f2, Linea linea) {
		this.f1 = f1;
		this.f2 = f2;
		this.linea = linea;
	}

	/**
	 * @return the f1
	 */
	public Fermata getF1() {
		return f1;
	}

	/**
	 * @return the f2
	 */
	public Fermata getF2() {
		return f2;
	}

	/**
	 * @return the linea
	 */
	public Linea getLinea() {
		return linea;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f1 == null) ? 0 : f1.hashCode());
		result = prime * result + ((f2 == null) ? 0 : f2.hashCode());
		result = prime * result + ((linea == null) ? 0 : linea.hashCode());
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
		FermataPair other = (FermataPair) obj;
		if (f1 == null) {
			if (other.f1 != null)
				return false;
		} else if (!f1.equals(other.f1))
			return false;
		if (f2 == null) {
			if (other.f2 != null)
				return false;
		} else if (!f2.equals(other.f2))
			return false;
		if (linea == null) {
			if (other.linea != null)
				return false;
		} else if (!linea.equals(other.linea))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FermataPair [f1=" + f1 + ", f2=" + f2 + ", linea=" + linea + "]";
	}

	
	
	
	
}

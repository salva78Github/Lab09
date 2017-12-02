package it.polito.tdp.metrodeparis.exception;

public class MetroDeParisException extends Exception {

	private static final long serialVersionUID = 2116775450092796528L;

	public MetroDeParisException(String message) {
		super(message);
	}

	public MetroDeParisException(String message, Exception e) {
		super(message,e);
	}
}

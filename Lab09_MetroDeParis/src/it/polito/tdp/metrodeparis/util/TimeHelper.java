package it.polito.tdp.metrodeparis.util;

public class TimeHelper {

	public static String timeConverter(int tempoInSecondi) {

		int ore = tempoInSecondi / 3600;
		int minuti = (tempoInSecondi % 3600) / 60;
		int secondi = tempoInSecondi % 60;

		return String.format("%02d:%02d:%02d", ore, minuti, secondi);
	}
}

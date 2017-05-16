package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataSuLinea;
import it.polito.tdp.metrodeparis.model.Key;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public Map<Integer, Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		Map<Integer, Fermata> fermate = new HashMap<Integer, Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int idFermata = rs.getInt("id_Fermata");
				Fermata fermata = new Fermata(idFermata, rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.put(idFermata, fermata);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public Map<Integer, Linea> getAllLinee() {

		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";
		Map<Integer, Linea> linee = new HashMap<Integer, Linea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int idLinea = rs.getInt("id_linea");
				Linea linea = new Linea(idLinea, rs.getString("nome"), rs.getDouble("velocita"), rs.getDouble("intervallo"));
				linee.put(idLinea, linea);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

	public List<Connessione> getAllConnessioni(Map<Integer, Fermata> fermate, Map<Integer, Linea> linee) {

		final String sql = "select id_connessione, id_linea, id_stazP, id_stazA from connessione";
		List<Connessione> connessioni = new ArrayList<Connessione>();

		try {

			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int idLinea = rs.getInt("id_linea");
				int idStazP = rs.getInt("id_stazP");
				int idStazA = rs.getInt("id_stazA");

				Linea myLinea = linee.get(idLinea);
				Fermata myStazP = fermate.get(idStazP);
				Fermata myStazA = fermate.get(idStazA);
				
				Connessione cnx = new Connessione(rs.getInt("id_connessione"), myLinea, myStazP, myStazA);
				connessioni.add(cnx);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return connessioni;
	}

	public List<FermataSuLinea> getAllFermateSuLinea(Map<Integer, Fermata> fermate, Map<Integer, Linea> linee) {

		final String sql = "SELECT DISTINCT fermata.id_fermata, linea.id_linea FROM fermata, linea, connessione WHERE (fermata.id_fermata = connessione.id_stazP OR fermata.id_fermata = connessione.id_stazA) AND connessione.id_linea = linea.id_linea";
		List<FermataSuLinea> fermateSuLinea = new ArrayList<FermataSuLinea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int idLinea = rs.getInt("id_linea");
				int idFermata = rs.getInt("id_fermata");

				Linea linea = linee.get(idLinea);
				Fermata fermata = fermate.get(idFermata);

				FermataSuLinea fermataSuLinea = new FermataSuLinea(fermata, linea);
				fermata.addFermataSuLinea(new Key(idFermata, idLinea), fermataSuLinea);
				fermateSuLinea.add(fermataSuLinea);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermateSuLinea;
	}

}

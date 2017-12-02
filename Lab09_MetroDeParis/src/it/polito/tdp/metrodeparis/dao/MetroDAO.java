package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.exception.MetroDeParisException;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataPair;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {
	private final static DBConnect dbInstance = DBConnect.getInstance();

	public List<Fermata> getAllFermate() throws MetroDeParisException {

		final String query = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = dbInstance.getConnection();
			st = conn.prepareStatement(query);
			rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}


		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new MetroDeParisException("Errore SQL nel metodo che recupera la lista di tutte le fermate.",sqle);
		} finally{
			dbInstance.closeResources(conn, st, rs);
		}

		return fermate;
	}
	
	public List<FermataPair> listCoppieFermateAdiacenti() throws MetroDeParisException{
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FermataPair> fpl = new ArrayList<FermataPair>();
		String query = "SELECT f1.id_fermata AS f1_id, f1.nome AS f1_nome, f1.coordX AS f1_x, f1.coordY AS f1_y, " +
					   "f2.id_fermata AS f2_id, f2.nome AS f2_nome, f2.coordX AS f2_x, f2.coordY AS f2_y, " +
					   "l.id_linea AS l_id, l.nome AS l_nome, l.velocita AS l_v, l.intervallo AS l_int, l.colore AS l_col " +
					   "FROM connessione c, fermata f1, fermata f2, linea l " +
					   "WHERE c.id_stazA = f1.id_fermata " +
					   "AND c.id_stazP = f2.id_fermata " +
					   "AND c.id_linea = l.id_linea";
		
		try{
			c = dbInstance.getConnection();
			ps = c.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next()){
				Fermata f1 = new Fermata(rs.getLong("f1_id"), rs.getString("f1_nome"), new LatLng(rs.getDouble("f1_x"), rs.getDouble("f1_y")));
				Fermata f2 = new Fermata(rs.getLong("f2_id"), rs.getString("f2_nome"), new LatLng(rs.getDouble("f2_x"), rs.getDouble("f2_y")));
				Linea l = new Linea(rs.getLong("l_id"), rs.getString("l_nome"), rs.getDouble("l_v"), rs.getDouble("l_int"), rs.getString("l_col"));
				FermataPair fp = new FermataPair(f1, f2, l);
				fpl.add(fp);
			}

			return fpl;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new MetroDeParisException("Errore SQL nel metodo che recupera la lista di tutte le coppie di fermate.",sqle);
		} finally{
			dbInstance.closeResources(c, ps, rs);
		}
		
		
	}
	
	
}

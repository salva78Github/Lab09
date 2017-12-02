package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.polito.tdp.metrodeparis.exception.MetroDeParisException;

public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/metroparis?user=root&password=salva_root";
	static private DBConnect instance = null;

	private DBConnect() {
		instance = this;
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else {
			return instance;
		}
	}

	public Connection getConnection() throws MetroDeParisException {
		try {
			
			Connection conn = DriverManager.getConnection(jdbcUrl);
			return conn;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new MetroDeParisException("Errore di connessione al database");
		}
	}
	
	public void closeResources(Connection c, Statement s, ResultSet rs){
		try {
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		try {
			s.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		try {
			c.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
}

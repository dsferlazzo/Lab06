package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.LocalitaEAVGUmidita;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	 /**
	  * metodo per cercare tutti i rilevamenti nel database
	  * @return lista di tutti i rilevamenti
	  */
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * metodo per trovare tutti i rilevamenti di un determinato mese e localita
	 * @param mese
	 * @param localita
	 * @return lista di rilevamenti
	 */
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT localita, Data, umidita "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA)=? AND localita=? "
				+ "ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, mese);
			st.setString(2, localita);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
	}


	}
	/**
	 * ottiene una lista di localita con la loro umidita nel mese inserito
	 * @param mese
	 * @return
	 */
	public List<LocalitaEAVGUmidita> getCittaUmidita(int mese){
		final String sql = "SELECT localita, AVG(umidita) as n "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA)=? "
				+ "GROUP BY localita";
		
		List<LocalitaEAVGUmidita> localitaUmidita = new ArrayList<LocalitaEAVGUmidita>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, mese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				LocalitaEAVGUmidita lu = new LocalitaEAVGUmidita(rs.getString("localita"), rs.getFloat("n"));
				localitaUmidita.add(lu);
			}

			conn.close();
			return localitaUmidita;

			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

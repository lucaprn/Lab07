package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Outage;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	public List<Outage> getOutageList(int idNerc) {

		String sql = "select id, nerc_id, customers_affected, date_event_began, date_event_finished from PowerOutages where nerc_id=?";
		List<Outage> outagesList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idNerc);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Outage o = new Outage(res.getInt("id"),res.getInt("nerc_id"),res.getInt("customers_affected"),
						res.getTimestamp("date_event_began").toLocalDateTime(), 
						res.getTimestamp("date_event_finished").toLocalDateTime());
				outagesList.add(o);
			
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return outagesList;
	}

}


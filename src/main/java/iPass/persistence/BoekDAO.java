package iPass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import iPass.model.Boek;

public class BoekDAO extends BaseDAO {
	public List<Boek> Boek(String query) {
		List<Boek> results = new ArrayList<Boek>();
		
		try (Connection con = super.getConnection()) {
			
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while (dbResultSet.next()) {
				int ID = dbResultSet.getInt("id");
				String boeknm = dbResultSet.getString("naam");
				String boekpath = dbResultSet.getString("path");

				results.add(new Boek(ID,boeknm,boekpath));				
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return results;
	}
	
	public List<Boek> findAll() {
		return Boek("select * from boek");
	}
	
	public void insertBoek(Boek boek) {
		try (Connection con = super.getConnection()) {
			String query = "insert into boek values(default,'" + boek.getBoekNaam() + "','" + boek.getBoekPath() + "');";
			
			Statement stmt = con.createStatement();
			stmt.execute(query);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}

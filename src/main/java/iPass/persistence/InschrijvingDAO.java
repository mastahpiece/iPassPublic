package iPass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import iPass.model.Inschrijving;

public class InschrijvingDAO extends BaseDAO {
	public List<Inschrijving> Inschrijving(String query) {

		List<Inschrijving> results = new ArrayList<Inschrijving>();
		
		try (Connection con = super.getConnection()) {
			
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while (dbResultSet.next()) {
				int iid = dbResultSet.getInt("id_inschrijving");
				int bid = dbResultSet.getInt("id_bijeenkomst");
				int aid = dbResultSet.getInt("id_account");
				
				results.add(new Inschrijving(iid,bid,aid));				
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return results;
}
	
	public List<Inschrijving> findAll() {
		return Inschrijving("SELECT * FROM inschrijvingen;");
		}
	
	public List<Inschrijving> findByBijeenkomstid(int bid){
		try{
		List<Inschrijving> lijstje = Inschrijving("select * from inschrijvingen where id_bijeenkomst=" + bid + ";");
		return lijstje;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public List<Inschrijving> findByAccountid(int aid){
		try{
		List<Inschrijving> lijstje = Inschrijving("select * from inschrijvingen where id_account=" + aid + ";");
		return lijstje;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public List<Inschrijving> findByInschrijvingid(int iid){
		try{
		List<Inschrijving> lijstje = Inschrijving("select * from inschrijvingen where id_inschrijving=" + iid + ";");
		return lijstje;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
		
	public void insertInschrijving(Inschrijving i){
		try (Connection con = super.getConnection()) {
				String query = "INSERT INTO inschrijvingen values(default," + i.getBijeenkomstId() + "," +
						i.getAccountId() + ");";
				
				Statement stmt = con.createStatement();
				
				stmt.execute(query);
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
}

package iPass.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import iPass.model.Bijeenkomst;

public class BijeenkomstDAO extends BaseDAO{
	public List<Bijeenkomst> Bijeenkomst(String query) {
		List<Bijeenkomst> results = new ArrayList<Bijeenkomst>();
		
		try (Connection con = super.getConnection()) {
			
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while (dbResultSet.next()) {
				int ID = dbResultSet.getInt("id");
				String Beschr = dbResultSet.getString("beschr");
				String toegang = dbResultSet.getString("toegang");
				String datum = dbResultSet.getString("datum");
				
				results.add(new Bijeenkomst(ID,Beschr,toegang,datum));				
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return results;
	}
	
	public List<Bijeenkomst> findAll() {
		return Bijeenkomst("SELECT * FROM bijeenkomsten;");
		}
	
	public Bijeenkomst findById(int id){
		try{
		List<Bijeenkomst> lijstje = Bijeenkomst("select * from bijeenkomsten where id=" + id + ";");
		Bijeenkomst bij = lijstje.get(0);
		return bij;
		}
		catch (Exception e){
			System.out.println("WOOPSIE DIE BIJEENKOMST BESTAAT NIET");
			return null;
		}
	}
	
	public void insertBijeenkomst(Bijeenkomst bij){
	try (Connection con = super.getConnection()) {
			String query = "INSERT INTO bijeenkomsten values(default,'" + bij.getBeschrijving() + "','" + bij.getToegang() + "','" +
					bij.getDatum() + "');";
			
			Statement stmt = con.createStatement();
			
			if (stmt.execute(query)){
				System.out.println(query);
				System.out.println("insert gelukt!");
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void verwijderBijeenkomst(Bijeenkomst bij){
		try (Connection con = super.getConnection()) {
				String query = "delete from bijeenkomsten where ID=" + bij.getID() + ";";
				
				Statement stmt = con.createStatement();
				stmt.execute(query);
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
	}
}

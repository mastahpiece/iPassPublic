package iPass.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import iPass.model.Account;


public class AccountDAO extends BaseDAO {
	public List<Account> Account(String query) {
		List<Account> results = new ArrayList<Account>();
		
		try (Connection con = super.getConnection()) {
			
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while (dbResultSet.next()) {
				int id = dbResultSet.getInt("id");
				String gbnm = dbResultSet.getString("gbnaam");
				String vnm = dbResultSet.getString("vnaam");
				String anm = dbResultSet.getString("anaam");
				String ww = dbResultSet.getString("ww");
				String mail = dbResultSet.getString("mail");
				String rol = dbResultSet.getString("rol");
				
				results.add(new Account(id,gbnm,vnm,anm,ww,mail,rol));				
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return results;
	}
	
	public List<Account> findAll() {
		return Account("SELECT * FROM accounts;");
		}

	public List<Account> findByGbnaam(String gbnaam){
		return Account("SELECT * FROM accounts WHERE gbnaam =" + "'" + gbnaam + "'" + ";");
	}
	
	public List<Account> findByGbId(int id){
		return Account("SELECT * FROM accounts WHERE id=" + id + ";");
	}
	
	public String findRol(String username, String password){
		String rol = null;
		String query = ("SELECT rol FROM accounts WHERE gbnaam= ? AND ww= ?");
		
		try (Connection con = super.getConnection()) {

			 PreparedStatement pstmt = con.prepareStatement(query);
			 pstmt.setString(1, username);
			 pstmt.setString(2, password);

			 ResultSet rs = pstmt.executeQuery();
			 if (rs.next())
			 rol = rs.getString("rol");

			 } catch (SQLException sqle) {
			 sqle.printStackTrace();
			 }

			 return rol;
			 }
	
	public void insertAccount(Account ac){
		try (Connection con = super.getConnection()) {
				String query = "INSERT INTO accounts values(default,'" + ac.getGebruikersnaam() + "','" + ac.getVoornaam() + "','" +
						ac.getAchternaam() + "','" + ac.getWachtwoord() + "','" + ac.getMail()+ "','" + ac.getRol() + "');";
				
				Statement stmt = con.createStatement();
				
				stmt.execute(query);
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
}

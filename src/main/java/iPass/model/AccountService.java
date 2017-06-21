package iPass.model;

import java.util.List;

import iPass.persistence.AccountDAO;

public class AccountService {
	private AccountDAO account;
	
	public AccountService(){
		account = new AccountDAO();
	}
	
	public List<Account> getAllAccounts() {
		return account.findAll();
	}
	
	public Account GetByGbnaam(String gbnm){
		try 
		{	
		List<Account> lijstje = account.findByGbnaam(gbnm);
		Account acc = lijstje.get(0);
		return acc;
		}
		
		catch(Exception a) {
			System.out.println(a + " ongeldige invoer");
		}
		return null;
	}
	
	public Account GetByGbID(int id){
		try 
		{	
		List<Account> lijstje = account.findByGbId(id);
		Account acc = lijstje.get(0);
		return acc;
		}
		
		catch(Exception a) {
			System.out.println(a + " ongeldige invoer");
		}
		return null;
	}
	
	public void insertAcc(Account ac){
		account.insertAccount(ac);
	}
}

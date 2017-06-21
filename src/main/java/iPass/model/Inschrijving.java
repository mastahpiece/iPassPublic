package iPass.model;


public class Inschrijving {
	int inschrId;
	int bijeenkomstId;
	int accountId;
	public Inschrijving(int iid, int bid, int aid){
		inschrId = iid;
		bijeenkomstId = bid;
		accountId = aid;
	}
	
	public Inschrijving( int bid, int aid){
		bijeenkomstId = bid;
		accountId = aid;
	}

	public int getInschrId(){
		return inschrId;
	}
	
	public void setBijeenkomstId(int bijeenkomstid){
		bijeenkomstId = bijeenkomstid;
	}
	
	public int getBijeenkomstId(){
		return bijeenkomstId;
	}
	
	public void setAccountId(int accId){
		accountId = accId;
	}
	
	public int getAccountId(){
		return accountId;
	}
}

package iPass.model;

import java.util.List;
import iPass.persistence.InschrijvingDAO;

public class InschrijvingService {
	private InschrijvingDAO indao;
	
	public InschrijvingService(){
		indao = new InschrijvingDAO();
	}
	
	public List<Inschrijving> getAllInschrijvingen(){
		return indao.findAll();
	}
	
	public List<Inschrijving> getInschrijvingByInschrijvingId(int id){
		return indao.findByInschrijvingid(id);
	}
	
	public List<Inschrijving> getInschrijvingByBijeenkomstId(int id){
		return indao.findByBijeenkomstid(id);
	}
	
	public List<Inschrijving> getInschrijvingByAccountId(int id){
		return indao.findByAccountid(id);
	}
	
	public void insert(Inschrijving i){
		indao.insertInschrijving(i);
	}
	
	
}

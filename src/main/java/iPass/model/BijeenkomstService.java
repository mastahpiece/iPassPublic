package iPass.model;

import java.util.List;
import iPass.persistence.BijeenkomstDAO;

public class BijeenkomstService {
	private BijeenkomstDAO bijeenkomst;
	
	public BijeenkomstService(){
		bijeenkomst = new BijeenkomstDAO();
	}
	
	public List<Bijeenkomst> getAllBijeenkomsten() {
		return bijeenkomst.findAll();
	}
	
	public Bijeenkomst getById(int id){
		return bijeenkomst.findById(id);
	}
	
	public void insert(Bijeenkomst bij){
		bijeenkomst.insertBijeenkomst(bij);
	}
	
	public void delete(Bijeenkomst bij){
		bijeenkomst.verwijderBijeenkomst(bij);
	}
}

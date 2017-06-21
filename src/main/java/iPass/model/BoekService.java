package iPass.model;

import java.util.List;

import iPass.persistence.BoekDAO;

public class BoekService {
	private BoekDAO bdao;
	
	public BoekService(){
		bdao = new BoekDAO();
	}
	
	public List<Boek> getAlleBoeken(){
		return bdao.findAll();
	}
	
	public void insert(Boek boek){
		bdao.insertBoek(boek);
	}
}

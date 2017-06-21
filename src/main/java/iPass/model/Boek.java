package iPass.model;

public class Boek {
	private int id;
	private String boekNaam;
	private String boekPath;
	
	public Boek(int bid, String bnm, String bpath){
		id = bid;
		boekNaam = bnm;
		boekPath = bpath;
	}
	
public Boek(String bnm, String bpath){
		boekNaam = bnm;
		boekPath = bpath;
	}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getBoekNaam() {
	return boekNaam;
}

public void setBoekNaam(String boekNaam) {
	this.boekNaam = boekNaam;
}

public String getBoekPath() {
	return boekPath;
}

public void setBoekPath(String boekPath) {
	this.boekPath = boekPath;
}

@Override
public String toString() {
	return "Boek [id=" + id + ", boekNaam=" + boekNaam + ", boekPath=" + boekPath + "]";
}


}

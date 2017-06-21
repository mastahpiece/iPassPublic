package iPass.model;

public class Bijeenkomst {
	int ID;
	String Beschrijving;
	String Toegang;
	String Datum;
	
	public Bijeenkomst(int id, String besch, String tgng, String dtm){
		ID = id;
		Beschrijving = besch;
		Toegang = tgng;
		Datum = dtm;
	}
	
	public Bijeenkomst(String besch, String tgng, String dtm){
		Beschrijving = besch;
		Toegang = tgng;
		Datum = dtm;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getBeschrijving() {
		return Beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		Beschrijving = beschrijving;
	}

	public String getToegang() {
		return Toegang;
	}

	public void setToegang(String toegang) {
		Toegang = toegang;
	}

	public String getDatum() {
		return Datum;
	}

	public void setDatum(String datum) {
		Datum = datum;
	}

	@Override
	public String toString() {
		return "Bijeenkomst [ID=" + ID + ", Beschrijving=" + Beschrijving + ", Toegang=" + Toegang + ", Datum=" + Datum
				+ "]";
	}
	
	
}

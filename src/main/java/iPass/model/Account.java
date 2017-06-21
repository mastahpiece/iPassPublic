package iPass.model;

public class Account {
	@Override
	public String toString() {
		return "Account [Gebruikersnaam=" + Gebruikersnaam + ", voornaam=" + voornaam + ", achternaam=" + achternaam
				+ ", wachtwoord=" + wachtwoord + ", mail=" + mail + ", rol=" + rol + "]";
	}
	
	private int id;
	private String Gebruikersnaam;
	private String voornaam;
	private String achternaam;
	private String wachtwoord;
	private String mail;
	private String rol;
	
	public Account(int id2, String gbnm, String vnm, String anm, String ww, String ml, String rl){
		id = id2;
		Gebruikersnaam = gbnm;
		voornaam = vnm;
		achternaam = anm;
		wachtwoord = ww;
		mail = ml;
		rol = rl;
	}
	
	public Account(String gbnm, String vnm, String anm, String ww, String ml, String rl){
		Gebruikersnaam = gbnm;
		voornaam = vnm;
		achternaam = anm;
		wachtwoord = ww;
		mail = ml;
		rol = rl;
	}
	
	public int getId(){
		return id;
	}

	public String getGebruikersnaam() {
		return Gebruikersnaam;
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		Gebruikersnaam = gebruikersnaam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}

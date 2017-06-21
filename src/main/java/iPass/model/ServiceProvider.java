package iPass.model;

public class ServiceProvider {
	private static AccountService accountService = new AccountService();
	private static BijeenkomstService bijeenkomstService = new BijeenkomstService();
	private static InschrijvingService inschrijvingService = new InschrijvingService();
	private static BoekService boekService = new BoekService();
	
	public static AccountService getAccountService() {
		return accountService;
	}
	
	public static BijeenkomstService getBijeenkomstService() {
		return bijeenkomstService;
	}
	
	public static InschrijvingService getInschrijvingService() {
		return inschrijvingService;
	}
	
	public static BoekService getBoekService() {
		return boekService;
	}
}

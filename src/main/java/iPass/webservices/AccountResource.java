package iPass.webservices;

import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import iPass.model.Account;
import iPass.model.AccountService;
import iPass.model.ServiceProvider;

@Path("/accounts")
public class AccountResource {
	AccountService service = ServiceProvider.getAccountService();
	
	@GET
	//@RolesAllowed("{user,admin}")
	@Produces("application/json")
	public String getAccounts(){
		JsonArrayBuilder allAccounts = Json.createArrayBuilder();
		
		for (Account a : service.getAllAccounts()) {
			  JsonObjectBuilder job = Json.createObjectBuilder();
			  job.add("id", a.getId());
			  job.add("gbnaam", a.getGebruikersnaam());
			  job.add("vnaam", a.getVoornaam());
			  job.add("anaam", a.getAchternaam());
			  job.add("ww", a.getWachtwoord());
			  job.add("mail", a.getMail());
			  job.add("rol", a.getRol());
			  System.out.println(a.toString());
			  allAccounts.add(job);
		}
		JsonArray arrayAllcountries = allAccounts.build();
		return arrayAllcountries.toString();
	}
	
	@GET
//	@RolesAllowed("admin")
	@Path("{gbnaam}")
	@Produces("application/json")
	public String getAccountByGb(@PathParam("gbnaam") String gbnaam) {
		try {
		Account a = service.GetByGbnaam(gbnaam);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("id", a.getId());
		job.add("gbnaam", a.getGebruikersnaam());
		job.add("vnaam", a.getVoornaam());
		job.add("anaam", a.getAchternaam());
		job.add("ww", a.getWachtwoord());
		job.add("mail", a.getMail());
		job.add("rol", a.getRol());
		System.out.println(a.toString());
		
		return job.build().toString();
		}
		catch(Exception e) {
			System.out.println(e + " ongeldige invoer");
			return null;
		}
	}
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public String getAccountByID(@PathParam("id") int id){
		AccountService service = ServiceProvider.getAccountService() ;
		JsonArrayBuilder accountByID = Json.createArrayBuilder();
		
			JsonObjectBuilder job4 = Json.createObjectBuilder();
			
			Account a = service.GetByGbID(id);
			job4.add("id", a.getId());
			job4.add("gbnaam", a.getGebruikersnaam());
			job4.add("vnaam", a.getVoornaam());
			job4.add("anaam", a.getAchternaam());
			job4.add("ww", a.getWachtwoord());
			job4.add("mail", a.getMail());
			job4.add("rol", a.getRol());
		
			accountByID.add(job4);
	
			JsonArray arrayByID = accountByID.build();
			return arrayByID.toString();
	}
	
	@POST
	@Produces("application/json")
	public String createAccount(InputStream is) {
	    JsonObject object = Json.createReader(is).readObject();
	    System.out.println(object);
	    String user = object.getString("gbnaam");
	    String vnaam = object.getString("vnaam");
	    String anaam = object.getString("anaam");
	    String ww = object.getString("ww");
	    String mail = object.getString("mail");
	    String rol = object.getString("rol");
	    
	    Account newAccount = new Account(user,vnaam,anaam,ww,mail,rol);
	    service.insertAcc(newAccount);
	    return customerToJson(newAccount).build().toString();
	  }
	
	  private JsonObjectBuilder customerToJson(Account a) {
		  JsonObjectBuilder job = Json.createObjectBuilder();
		  job.add("gbnaam", a.getGebruikersnaam());
		  job.add("vnaam", a.getVoornaam());
		  job.add("anaam", a.getAchternaam());
		  job.add("ww", a.getWachtwoord());
		  job.add("mail", a.getMail());
		  job.add("rol", a.getRol());
		  return job;
	  }
}

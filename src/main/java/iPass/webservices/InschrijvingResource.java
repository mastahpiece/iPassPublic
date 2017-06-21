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
import iPass.model.Inschrijving;
import iPass.model.InschrijvingService;
import iPass.model.ServiceProvider;

@Path("/inschrijvingen")
public class InschrijvingResource {
InschrijvingService service = ServiceProvider.getInschrijvingService();
	
	@GET
	@Produces("application/json")
	public String getAccounts(){
		JsonArrayBuilder allInschrijvingen = Json.createArrayBuilder();
		
		for (Inschrijving i : service.getAllInschrijvingen()) {
			  JsonObjectBuilder job = Json.createObjectBuilder();
			  job.add("iid", i.getInschrId());
			  job.add("bid", i.getBijeenkomstId());
			  job.add("aid", i.getAccountId());
			  allInschrijvingen.add(job);
		}
		JsonArray arrayAllcountries = allInschrijvingen.build();
		return arrayAllcountries.toString();
	}
	
	@GET
	@Path("/bid/{id}")
	@Produces("application/json")
	public String getInschrijvingByBid(@PathParam("id") int id){
		try {	
			JsonArrayBuilder bijByID = Json.createArrayBuilder();
			
			for (Inschrijving i : service.getInschrijvingByBijeenkomstId(id)){ 
				JsonObjectBuilder job = Json.createObjectBuilder();
				job.add("iid", i.getInschrId());
			    job.add("bid", i.getBijeenkomstId());
			    job.add("aid", i.getAccountId());
				bijByID.add(job);
				
				}
				JsonArray arrayByID = bijByID.build();
				return arrayByID.toString();
				}
			catch (Exception e){
				System.out.println("foutmelding door verkeerde bijeenkomstid");
				return null;
			}
	}
	
	@GET
	@Path("/aid/{id}")
	@Produces("application/json")
	public String getInschrijvingByAid(@PathParam("id") int id){
		try {
		JsonArrayBuilder bijByID = Json.createArrayBuilder();	
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		for (Inschrijving i : service.getInschrijvingByAccountId(id)){ 
	    job.add("iid", i.getInschrId());
	    job.add("bid", i.getBijeenkomstId());
	    job.add("aid", i.getAccountId());
		bijByID.add(job);
		
		}
		JsonArray arrayByID = bijByID.build();
		return arrayByID.toString();
		}
		catch (Exception e){
			System.out.println("foutmelding door verkeerde bijeenkomstid");
			return null;
		}
	}
	
	@POST
	@Produces("application/json")
	public String createInschrijving(InputStream is) {
	    JsonObject object = Json.createReader(is).readObject();
	    System.out.println(object);
	    int bid = object.getInt("bid");
	    int aid = object.getInt("aid");
	    
	    Inschrijving newInschrijving = new Inschrijving(bid, aid);
	    service.insert(newInschrijving);
	    return InschrijvingToJson(newInschrijving).build().toString();
	  }
	
	  private JsonObjectBuilder InschrijvingToJson(Inschrijving i) {
		  JsonObjectBuilder job = Json.createObjectBuilder();
		  job.add("iid", i.getInschrId());
		  job.add("bid", i.getBijeenkomstId());
		  job.add("aid", i.getAccountId());
		   return job;
	  }
}

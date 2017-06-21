package iPass.webservices;

import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import iPass.model.Bijeenkomst;
import iPass.model.BijeenkomstService;
import iPass.model.ServiceProvider;

@Path("/bijeenkomsten")
public class BijeenkomstResource {
	BijeenkomstService service = ServiceProvider.getBijeenkomstService();
	
	@GET
	@Produces("application/json")
	public String getBijeenkomsten(){
		JsonArrayBuilder allAccounts = Json.createArrayBuilder();
		
		for (Bijeenkomst b : service.getAllBijeenkomsten()) {
			  JsonObjectBuilder job = Json.createObjectBuilder();
			  job.add("id", b.getID());
			  job.add("beschr", b.getBeschrijving());
			  job.add("toegang", b.getToegang());
			  job.add("datum", b.getDatum());
			  allAccounts.add(job);
		}
		JsonArray arrayAllcountries = allAccounts.build();
		return arrayAllcountries.toString();
	}
		

	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getBijeenkomstByID(@PathParam("id") int id){
		try {
		JsonArrayBuilder bijByID = Json.createArrayBuilder();	
		JsonObjectBuilder job4 = Json.createObjectBuilder();
			
		Bijeenkomst b = service.getById(id);
		job4.add("id", b.getID());
		job4.add("beschr", b.getBeschrijving());
		job4.add("toegang", b.getToegang());
		job4.add("datum", b.getDatum());
		
		bijByID.add(job4);
	
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
	public String createBijeenkomst(InputStream is) {
	    JsonObject object = Json.createReader(is).readObject();
	    String name = object.getString("beschr");
	    String address = object.getString("toegang");
	    String datum = object.getString("datum");
	    
	    Bijeenkomst newCustomer = new Bijeenkomst(name, address, datum);
	    service.insert(newCustomer);
	    return customerToJson(newCustomer).build().toString();
	  }
	
	  private JsonObjectBuilder customerToJson(Bijeenkomst b) {
		  JsonObjectBuilder job = Json.createObjectBuilder();
		    job.add("beschr", b.getBeschrijving());
		    job.add("toegang", b.getToegang());
		    job.add("address", b.getDatum());
		    return job;
	  }
  
  @DELETE
  @Path("{id}")
  public Response deleteBijeenkomst(@PathParam("id") int id) {
	  Bijeenkomst found = null;
	  for (Bijeenkomst b : service.getAllBijeenkomsten()) {
		  if (b.getID() == id) {
			  found = b; break;
		  }
	  }

	  if (found == null) {
		  return Response.status(Response.Status.NOT_FOUND).build(); }
	  else {
		  service.delete(found);
		  return Response.ok().build();
	  }
  }


}

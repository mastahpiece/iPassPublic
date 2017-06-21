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
import javax.ws.rs.Produces;
import iPass.model.Boek;
import iPass.model.BoekService;
import iPass.model.ServiceProvider;

@Path("/boeken")
public class BoekResource {
	BoekService service = ServiceProvider.getBoekService();
	
	@GET
	@Produces("application/json")
	public String getAccounts(){
		JsonArrayBuilder allBoek = Json.createArrayBuilder();
		
		for (Boek b : service.getAlleBoeken()) {
			  JsonObjectBuilder job = Json.createObjectBuilder();
			  job.add("id", b.getId());
			  job.add("naam", b.getBoekNaam());
			  job.add("path", b.getBoekPath());
			  allBoek.add(job);
		}
		JsonArray arrayAllcountries = allBoek.build();
		return arrayAllcountries.toString();
	}
	
	@POST
	@Produces("application/json")
	public String createBoek(InputStream is) {
	    JsonObject object = Json.createReader(is).readObject();
	    System.out.println(object);
	    String naam = object.getString("naam");
	    String path = object.getString("path");
	    
	    Boek newBoek = new Boek(naam, path);
	    service.insert(newBoek);
	    return BoekToJson(newBoek).build().toString();
	  }
	
	  private JsonObjectBuilder BoekToJson(Boek b) {
		  JsonObjectBuilder job = Json.createObjectBuilder();
		  job.add("naam", b.getBoekNaam());
		  job.add("path", b.getBoekPath());
		   return job;
	  }
}

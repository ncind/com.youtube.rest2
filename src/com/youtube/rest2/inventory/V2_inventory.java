package com.youtube.rest2.inventory;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.SchemaVMDB;

@Path("/v2/inventory")
public class V2_inventory {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception{
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			
			if(brand == null){
				return Response.status(400).entity("Error : Please specify brand for this search").build();
			}
			
			SchemaVMDB dao = new SchemaVMDB();
			json = dao.queryReturnBrandParts(brand);
			returnString = json.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
		
	}
	
	
	// Method if the above default GET is not present
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnBrand() throws Exception{
		return Response.status(400).entity("Error : Please specify brand for this search").build();
	}*/
	
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(
				@PathParam("brand") String brand)
							throws Exception{
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			SchemaVMDB dao = new SchemaVMDB();
			
			json = dao.queryReturnBrandParts(brand);
			returnString = json.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
		
	}
	
	
	@Path("/{brand}/{item_number}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem(@PathParam("brand") String brand,
											@PathParam("item_number") int item_number) 
											throws Exception{
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			SchemaVMDB dao = new SchemaVMDB();
			
			json = dao.queryReturnBrandItemNumber(brand, item_number);
			returnString = json.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

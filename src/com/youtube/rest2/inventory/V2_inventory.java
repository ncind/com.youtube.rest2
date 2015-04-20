package com.youtube.rest2.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
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
	
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		SchemaVMDB dao = new SchemaVMDB();
		
		try {
			System.out.println("incomingData: " + incomingData);
			
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int http_code = dao.insertIntoPC_Parts(itemEntry.PC_PARTS_TITLE, 
					itemEntry.PC_PARTS_CODE, 
					itemEntry.PC_PARTS_MAKER,
					itemEntry.PC_PARTS_AVAIL, 
					itemEntry.PC_PARTS_DESC);
			
			if(http_code == 200){
				//returnString = jsonArray.toString();
				returnString = "Item inserted";
				
			}else {
				return Response.status(500).entity("Unable to process item").build();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
		
		
	}

}


class ItemEntry{
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
	
}


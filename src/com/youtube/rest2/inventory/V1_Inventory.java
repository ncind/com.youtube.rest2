package com.youtube.rest2.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.OracleVMDB;
import com.youtube.utilities.ToJSON;

@Path("/v1/inventory")
public class V1_Inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPCParts() throws Exception{
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		
		try {
			
			// Create a connection
			conn = OracleVMDB.OracleVMConn().getConnection();
			query = conn.prepareStatement("select * " + "from PC_PARTS");
			
			// Execute SQL
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close(); //close the connection
			
			returnString = json.toString();
			//Build the response object
			rb = Response.ok(returnString).build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(conn != null){conn.close();}
		}
		
		return rb;
	}
	
}

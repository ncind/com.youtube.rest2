package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.utilities.*;

public class SchemaVMDB extends OracleVMDB{
	
	
	public JSONArray queryReturnBrandParts(String brand) throws Exception{
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
			
		try {
			conn = oraclePcPartsConnection();
			
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +
								 "from PC_PARTS " +
								 "where UPPER(PC_PARTS_MAKER) = ?" 
					);
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); // close connection
			
			
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		}
		finally{
			if(conn != null) conn.close();
		}
		
		
		return json;
		
	}
	
	

}

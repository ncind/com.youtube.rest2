package com.youtube.dao;

import javax.naming.*;
import javax.sql.*;

public class OracleVMDB {

	private static DataSource OracleVM = null;
	private static Context context = null;

	public static DataSource OracleVMConn() throws Exception {

		if(OracleVM != null){
			return OracleVM;
		}
		
		try {
			
			if(context == null){
				context = new InitialContext();
			}
			
			OracleVM = (DataSource) context.lookup("VMOracleNik");

		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return OracleVM;
	}

}

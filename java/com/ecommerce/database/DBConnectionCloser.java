package com.ecommerce.database;

import java.sql.SQLException;

public class DBConnectionCloser {

	public void dbConClose(java.sql.Connection connection){
		
		if(null!=connection) {
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		
	}
}

package com.neosoft.jdbc.controller;


import java.sql.*;

public class JDBCConnection {
	static Connection con = null;

	public static Connection openConnection() {
		try {
			JDBCProperty.loadProperties();
			Class.forName(JDBCProperty.driverClass);
			con = DriverManager.getConnection(JDBCProperty.url, JDBCProperty.username, JDBCProperty.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeConnection() {
		try {
			if(con!=null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}


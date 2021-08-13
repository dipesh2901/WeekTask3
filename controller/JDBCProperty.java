package com.neosoft.jdbc.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class JDBCProperty {

	public static String driverClass = null;
	public static String url = null;
	public static String username = null;
	public static String password = null;

	public static void loadProperties() throws Exception {
		Properties prop = new Properties();
		InputStream in = new FileInputStream("resources/database.properties");
		prop.load(in);
		driverClass = prop.getProperty("MYSQLJDBC.driver");
		url = prop.getProperty("MYSQLJDBC.url");
		username = prop.getProperty("MYSQLJDBC.username");
		password = prop.getProperty("MYSQLJDBC.password");
		in.close();
		prop.clear();
	}

	public static void main(String[] args) throws Exception {
		JDBCProperty.loadProperties();
		System.out.println(JDBCProperty.driverClass);
		System.out.println(JDBCProperty.url);
		System.out.println(JDBCProperty.username);
		System.out.println(JDBCProperty.password);
	}

}


package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.util.SysConfig;

public class DBUtil {
	
	public static DBUtil getInstance(){
		return new DBUtil();
	}
	
	private DBUtil(){
		try{
			Class.forName(SysConfig.JDBC_DRIVER);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public Connection getConnection(){
		Connection con=null;
		try{
		    con = DriverManager.getConnection(SysConfig.JDBC_URL, SysConfig.JDBC_USER, SysConfig.JDBC_PASSWORD);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}

}

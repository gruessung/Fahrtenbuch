package Klassen;

import java.sql.*;

public class Datenbank {

	private Connection cn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private ResultSet rs2 = null;
	
	
	public Datenbank(String datenbank, String User, String Password)
	{
		try
	    {
	        Class.forName( "org.sqlite.JDBC" );
	        this.cn = DriverManager.getConnection( "jdbc:sqlite:" + datenbank, User, Password);
	        this.st = cn.createStatement();
	        System.out.println("DB Connection established...");
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}


	public ResultSet getRs() {
		return rs;
	}


	public void setRs(ResultSet rs) {
		this.rs = rs;
	}


	public ResultSet getRs2() {
		return rs2;
	}


	public void setRs2(ResultSet rs2) {
		this.rs2 = rs2;
	}
	
}

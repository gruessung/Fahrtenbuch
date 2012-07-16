package Klassen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Datenbank {

	private Connection cn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private ResultSet rs2 = null;
	
	private String db = "";
	private String user = "";
	private String pw = "";
	
	
	public Datenbank(String datenbank, String User, String Password)
	{
		this.db = datenbank;
		this.user = User;
		this.pw = Password;
		setupConnection();
	}

	public Connection getCn() {
		return cn;
	}

	public void setCn(Connection cn) {
		this.cn = cn;
	}

	public Statement getSt() {
		return st;
	}

	public void setSt(Statement st) {
		this.st = st;
	}

	public void setupConnection()
	{
		try
	    {
	        Class.forName( "org.sqlite.JDBC" );
	        this.cn = DriverManager.getConnection( "jdbc:sqlite:" + this.db, this.user, this.pw);
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

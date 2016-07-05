package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

	protected Connection conn;
	protected Statement stmt;
    
    public DbConnection(){
    	
		try {
			String connectionUrl = "jdbc:sqlserver://172.17.200.1:1433;" +  
		   	         "databaseName=MicroGrid;user=Longo;password=Admin.Longo";
	    	
			conn = DriverManager.getConnection(connectionUrl);
			stmt = conn.createStatement();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public void connClose(){
        try{
            conn.close();
        }catch(SQLException e){
			e.printStackTrace();
        }
    }
}

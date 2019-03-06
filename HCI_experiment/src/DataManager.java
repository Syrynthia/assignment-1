import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.DriverManager;

public class DataManager {
    private String JDBC_DRIVER; // "com.mysql.jdbc.Driver" 
    private String DB_URL; // "jdbc:mysql://localhost:3306/world?useSSL=false"
    private String USER; // "root"
    private String PASS; // "950918"
    Connection conn = null;
    Statement stmt = null;
    
    public DataManager(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
    	this.JDBC_DRIVER = JDBC_DRIVER;
    	this.DB_URL = DB_URL;
    	this.USER = USER;
    	this.PASS = PASS;	
    }
    
    public void connectMySQL() throws ClassNotFoundException, SQLException {
    	Class.forName("com.mysql.jdbc.Driver");
    	conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
    }
    
    public void deconnectMySQL() throws SQLException {
    	stmt.close();
        conn.close();
    }
    
    // (time - position) table
    public void createTable1() throws SQLException {
    	DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "time_position", null);
   	 	if (tables.next()) {
   	 		//System.out.println("The table does exist");
   	 	}
   	 	else {
   	   // Table does not exist
            String sql = "CREATE TABLE time_position(id int(11) NOT NULL AUTO_INCREMENT,"
            		+ "position1 varchar(20) NOT NULL,"
            		+ "position2 varchar(20) NOT NULL,"
            		+ "position3 varchar(20) NOT NULL,"
            		+ "position4 varchar(20) NOT NULL,"
            		+ "position5 varchar(20) NOT NULL,"
            		+ "position6 varchar(20) NOT NULL,"
            		+ "position7 varchar(20) NOT NULL,"
            		+ "position8 varchar(20) NOT NULL,"
            		+ "position9 varchar(20) NOT NULL,"
            		+ "PRIMARY KEY (id))";
            stmt.executeUpdate(sql);
   	 	}
    }
    
    // (time - task) table
    public void createTable2() throws SQLException {
    	DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "time_task", null);
   	 	if (tables.next()) {
   	 		//System.out.println("The table does exist");
   	 	}
   	 	else {
   	   // Table does not exist
            String sql = "CREATE TABLE time_task(id int(11) NOT NULL AUTO_INCREMENT,"
            		+ "task1 varchar(20) NOT NULL,"
            		+ "task2 varchar(20) NOT NULL,"
            		+ "task3 varchar(20) NOT NULL,"
            		+ "task4 varchar(20) NOT NULL,"
            		+ "task5 varchar(20) NOT NULL,"
            		+ "task6 varchar(20) NOT NULL,"
            		+ "task7 varchar(20) NOT NULL,"
            		+ "task8 varchar(20) NOT NULL,"
            		+ "task9 varchar(20) NOT NULL,"
            		+ "PRIMARY KEY (id))";
            stmt.executeUpdate(sql);
   	 	}
    }

    public void insertData(List<String> list, String table) throws SQLException {
   		String sql = "INSERT INTO "+ table +" values (null," + list.get(0) + ","
   				+ list.get(1) + ","
   				+ list.get(2) + ","
   				+ list.get(3) + ","
   				+ list.get(4) + ","
   				+ list.get(5) + ","
   				+ list.get(6) + ","
   				+ list.get(7) + ","
   				+ list.get(8) + ")";
            stmt.executeUpdate(sql);
    }


}

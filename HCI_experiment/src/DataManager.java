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
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver" ; // "com.mysql.jdbc.Driver" 
    private String USER = "root"; // "root"
    private String PASS = "950918"; // "950918"
    private String DB = "world";
    private String DB_URL = "jdbc:mysql://localhost:3306/"+ DB +"?useSSL=false"; // "jdbc:mysql://localhost:3306/world?useSSL=false"
    Connection conn = null;
    Statement stmt = null;
    
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
    
    public void saveDataInFile(String filename, String data) {
    	try
        {   
          File file = new File("./"+filename);
      	  if (!file.exists()) {
      		  file.createNewFile();
      	  }
          FileWriter fileWriter = new FileWriter(file, true); 
          PrintWriter pw = new PrintWriter(fileWriter); 
          pw.println(data); 
          pw.flush(); 
          fileWriter.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    }
    public void saveData(List<String> list, boolean saveIntoDatabase, boolean saveIntoFile, String filename) {
    	if(saveIntoDatabase) {
    		try {
				this.connectMySQL();
				this.createTable1();
				this.createTable2();
				this.insertData(list, filename);
				this.deconnectMySQL();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(saveIntoFile) {
    		String listString = String.join(", ", list);
    		saveDataInFile(filename+".txt", listString);
    	}
    }
}

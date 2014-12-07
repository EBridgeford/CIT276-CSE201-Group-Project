package devicetrackerpro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import com.mysql.jdbc.DatabaseMetaData;


public class DBConnection 

{
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://blueridge.it.muohio.edu/rfiddatabase";

	   //  Database credentials
	   static final String USER = "javaaccess";
	   static final String PASS = "Password99";
	   
	   
	 public DBConnection()
	 {
		 
		 
		 
	 }
	   
	   //Test connection - testing only
	   //Ignore this method 
	   public void Connect()
{
		   Connection conn = null;
		   Statement stmt = null;
		   //java.sql.DatabaseMetaData md;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
		      System.out.println("Connected!  ");
		      
		      
		   }
		   catch(SQLException se)
		   {
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }
		   catch(Exception e)
		   {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   finally
		   {
		      //finally block used to close resources
		      try
		      {
		         if(stmt!=null)
		            stmt.close();
		      }
		      catch(SQLException se2)
		      {
		      }// nothing we can do
		      try
		      {
		         if(conn!=null)
		            conn.close();
		      }
		      catch(SQLException se)
		      {
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
}
	   
	  //Show contents of table - testing only 
	  //Ignore this method 
	public void showTables()
	{
		
			Connection conn = null;
			Statement stmt = null;
		   try
		   {
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
		      
		      String query = "SELECT * FROM testtable"; 
		      
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(query);
		      while (rs.next())
		      {
		    	  //System.out.println("Output here?");
		    	  String rfid = rs.getString("rfid");
		    	  String po = rs.getString("po");
		    	  
		    	  System.out.println("PO: " +  po + " rfid: " + rfid);
		      }
		    	  	
		   }
		   catch(SQLException se)
		   {
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }
		   catch(Exception e)
		   {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   finally
		   {
		      //finally block used to close resources
		      try
		      {
		         if(stmt!=null)
		         {
		        	 //System.out.println("No connection");
		            stmt.close();
		         }
		      }
		      catch(SQLException se2)
		      {
		      }// nothing we can do
		      try
		      {
		         if(conn!=null)
		         {
		        	 //System.out.println("No connection");
		        	 conn.close();
		         }
		      }
		      catch(SQLException se)
		      {
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Disconnected");
		
		}
	
		//Not implemented for rfiddatabase
		//Ignore for now
	public void addRecord()
	{
		
			Connection conn = null;
			Statement stmt = null;
		   try
		   {
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
		      
		      System.out.println("Inserting record into table"); 
		      stmt = conn.createStatement();
		      
		      String sql = "INSERT INTO assest " + "VALUES ('5343795456', '4563325')";
		      stmt.executeUpdate(sql);
		    	  	
		   }
		   catch(SQLException se)
		   {
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }
		   catch(Exception e)
		   {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   finally
		   {
		      //finally block used to close resources
		      try
		      {
		         if(stmt!=null)
		         {
		        	 //System.out.println("No connection");
		            stmt.close();
		         }
		      }
		      catch(SQLException se2)
		      {
		      }// nothing we can do
		      try
		      {
		         if(conn!=null)
		         {
		        	 //System.out.println("No connection");
		        	 conn.close();
		         }
		      }
		      catch(SQLException se)
		      {
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Disconnected");
		
		}
	
	//Not implemented for rfiddatabase 
	//Ignore for now
	public void removeRecord() 
	{
				Connection conn = null;
				Statement stmt = null;
			   try
			   {
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			      
			      System.out.println("Deleting record from table"); 
			      stmt = conn.createStatement();
			      
			      String sql = "DELETE  FROM assest " + "WHERE po = 4563325 ";
			      stmt.executeUpdate(sql);
			    	  	
			   }
			   catch(SQLException se)
			   {
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }
			   catch(Exception e)
			   {
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }
			   finally
			   {
			      //finally block used to close resources
			      try
			      {
			         if(stmt!=null)
			         {
			        	 //System.out.println("No connection");
			            stmt.close();
			         }
			      }
			      catch(SQLException se2)
			      {
			      }// nothing we can do
			      try
			      {
			         if(conn!=null)
			         {
			        	 //System.out.println("No connection");
			        	 conn.close();
			         }
			      }
			      catch(SQLException se)
			      {
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			   System.out.println("Disconnected");
			
			}	
	
	//Method to send an inventory scan to testDataBase
	//Needs to be tweaked
	//ignore for now
	public void sendIntialScan (ArrayList<RecordObj> testList)
	{
		Connection conn = null;
		Statement stmt = null;
	   try
	   {
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      
	      System.out.println("Sending Scans...."); 
	      stmt = conn.createStatement();
	      
	      for(int i = 0; i < testList.size(); i++)
	      {
	      String sql = "INSERT INTO simpleinventory (RFID, Campus, Building, Room, Comments) " + 
	      "Values('"+testList.get(i).getRfid()+"','"+testList.get(i).getCampus()+"', '"+testList.get(i).getBldg()+"', '"+testList.get(i).getRm()+"','"+testList.get(i).getComments()+"');";
	      stmt.executeUpdate(sql);
	      }  
	      
	      System.out.println("File sent!"); 
	   }
	   catch(SQLException se)
	   {
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }
	   catch(Exception e)
	   {
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	   finally
	   {
	      //finally block used to close resources
	      try
	      {
	         if(stmt!=null)
	         {
	        	 //System.out.println("No connection");
	            stmt.close();
	         }
	      }
	      catch(SQLException se2)
	      {
	      }// nothing we can do
	      try
	      {
	         if(conn!=null)
	         {
	        	 //System.out.println("No connection");
	        	 conn.close();
	         }
	      }
	      catch(SQLException se)
	      {
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Disconnected");
		
		
		
	}
	
// GeneralQuery. This will be the main work horse of the application. Accepts campus, building, room and po as parameters 
// Or any combination of the parameters (with the exception of just room)  	
public void generalQuery (String campus, String building, String room, String PO)
{
	RecordObjListBuilder listBuilder = new RecordObjListBuilder(); //Object to add results into list
	
	Connection conn = null;
	Statement stmt = null;
   try
   {
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
      
      String query = "call GetComputerInformation('"+campus+"','"+building+"','"+room+"','"+PO+"');"; 
      System.out.println(query);
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if(rs == null)
    	  System.out.println("Failed");
      else
      {
    	 
    	  while(rs.next())
    	 {
    		 //Get data from result
    		 //This sets the variables according to the table column from which they are derived.  
    		 String rfid = rs.getString("RFID");
    		 String sN = rs.getString("SerialNumber");
    		 String rName = rs.getString("RoomName");
    		 String bName = rs.getString("BuildingName");
    		 String cName = rs.getString("CampusName");
    		 String sName = rs.getString("ScannerName");
    		 String notes = rs.getString("Notes");
    		 String poNumber = rs.getString("PONumber");
    		 String sTag = rs.getString("ServiceTag");
    		 String sDate = rs.getString("ScannedDate");
    		 
    		 //Print output for testing
    		 System.out.println( rfid + " " + sN + " " + rName + " " + bName + " "+ cName + " "+  
    				 sName + " " + notes + " " + poNumber + " " + sTag + " " + sDate);
    		 
    		 //Not needed - create record objects
//    		 RecordObj record = new RecordObj(rfid, "", "10/27/2014 19:20" , notes);
//             //Pass in optional values
//             record.setLocation(cName+"/"+bName+"/"+rName);
//             record.setPurchaseOrder(poNumber);
//             record.setServiceTag(sN);
             
    		 //Send information to listBuilder to add the record
             listBuilder.addRecord(rfid, poNumber, sN, sDate,"10/27/2014 19:20", notes, rName, bName, cName);
             
    	 }
      }
     
   }
   catch(SQLException se)
   {
      //Handle errors for JDBC
      se.printStackTrace();
   }
   catch(Exception e)
   {
      //Handle errors for Class.forName
      e.printStackTrace();
   }
   finally
   {
      //finally block used to close resources
      try
      {
         if(stmt!=null)
         {
        	 //System.out.println("No connection");
            stmt.close();
         }
      }
      catch(SQLException se2)
      {
      }// nothing we can do
      try
      {
         if(conn!=null)
         {
        	 //System.out.println("No connection");
        	 conn.close();
         }
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Disconnected");
	
}

//Waiting to determine parameters for the update record method
//public void updateRecord()
//{
//	
//}

public static void main(String[] args)
{
	DBConnection dB = new DBConnection(); 
	
	dB.generalQuery("", "", "", "");
	
//	String column = "room";
//	String searchItem = "300";
//	System.out.println("SELECT * FROM simpleinventory WHERE '"+column+"' LIKE '"+searchItem+"&';");
	
//	ArrayList <RecordObj> query =  dB.query("campus", "ham");
//	System.out.println(query.size());
//	for(int i = 0; i < query.size(); i++)
//		System.out.println(query.get(i).toString());
		
	//dB.Connect();
	//dB.showTables();
	//dB.addRecord(); 
	//dB.removeRecord(); 
	//dB.sendScan();
}

}
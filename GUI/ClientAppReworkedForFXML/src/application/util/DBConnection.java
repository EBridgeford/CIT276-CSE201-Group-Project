package application.util;

//William Wallace 
//CSE 201
//The purpose of this class is to establish a connection with a MySQL RFID database. 


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnection 

{
	//Private constant class variables

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://blueridge.it.muohio.edu/rfiddatabase";

	//Database credentials
	private static final String USER = "javaaccess";
	private static final String PASS = "Password99";

	//JDBC connection
	private static Connection conn = null; 

	//Constructor for DBConnection 
	public DBConnection() throws ClassNotFoundException, SQLException
	{
		Connect(); 
	}

	//Create JDBC Connection for the class
	public void Connect() throws ClassNotFoundException, SQLException
	{
		//Register JDBC driver
		Class.forName(JDBC_DRIVER);

		//Open a connection
		//System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS); 
		//System.out.println("Connected!  ");
	}


	// GeneralQuery. This will be the main work horse of the application. Accepts campus, building, room and po as parameters 
	// Or any combination of the parameters (with the exception of just room)  	
	public RecordObjListBuilder generalQuery (String campus, String building, String room, String PO) throws SQLException
	{
		RecordObjListBuilder listBuilder = new RecordObjListBuilder(); //Object to add results into list

		Statement stmt = null;

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
				String sDate = rs.getString("ScannedDate");

				//Print output for testing
				//    		 System.out.println( rfid + " " + sN + " " + rName + " " + bName + " "+ cName + " "+  
				//    				 sName + " " + notes + " " + poNumber + " " + sTag + " " + sDate);

				//Send information to listBuilder to add the record
				listBuilder.addRecord(rfid, poNumber, sN, sDate,"10/27/2014 19:20", notes, rName, bName, cName);
			}
		}
		return listBuilder;
	}

	//Method to build the lists for the three drop down menues
	public LocationObservableListBuilder getLocationData() throws SQLException
	{
		//Location lists 
		LocationObservableListBuilder lList = new LocationObservableListBuilder();
		lList.buildLists();

		Statement stmt = null;

		//Call stored procedure
		String query = "call GetLocation('');"; 
		//System.out.println(query);
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(rs == null)
			System.out.println("Empty Result");
		else
		{

			while(rs.next())
			{
				//Get data from result
				//This sets the variables according to the table column from which they are derived.  
				String campus = rs.getString("CampusName");
				String building = rs.getString("BuildingName");
				String room = rs.getString("RoomName");


				//Send information to listBuilder to add the record
				lList.addToAllLocationsList(campus,building, room);
			}
			lList.buildLists();
		}

		return lList;
	}

	//Testing
	private void isConnected() throws SQLException
	{
		System.out.println("Is the connection closed: " + conn.isClosed());
	}

	//Close connection with rfiddatabaseconnection
	public void closeConnection() throws SQLException 
	{
		conn.close();
		//System.out.println("Disconnected from rfiddatabase");
	}

	public static void main(String[] args) throws SQLException
	{
		RecordObjListBuilder r;
		LocationObservableListBuilder l = new LocationObservableListBuilder(); 

		try
		{

			DBConnection dB = new DBConnection(); 
			r = dB.generalQuery("", "", "", "");
			r.printObservableLists();
			l = dB.getLocationData();
			l.printObservableLists();
			dB.closeConnection();

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
			//System.out.println("End program execution");
		}
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
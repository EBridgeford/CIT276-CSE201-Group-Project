//William Wallace
//CSE 201
//The purpose of this class is to establish a connection with a MySQL RFID database. All exceptions are thrown
//to the controller of the GUI. See Github documentation for details.
//Awaiting stored procedures from DB team for methods update & add record. Expected implementation 11/24/14

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;

/**
 * DBConnection class establishes a connection with the database. Class contains methods
 * to query the database, add record assets and pull location from the database.
 * @author William Wallace
 * @version 1.0
 */
public class DBConnection

{
    //Private constant class variables
    
    // JDBC driver name and database URL - DB is behind university firewall. Utilize VPN to access DB
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://blueridge.it.muohio.edu/rfiddatabase";
    
    //Database credentials - Credentials will change for final release
    private static final String USER = "javaaccess";
    private static final String PASS = "Password99";
    
    //JDBC connection - The connection as a global variable allows the connection to remain open while during the gui
    // execution
    private static Connection conn = null;
	   
    /**
     * Constructor establishes connections to database via the connect() method
     *  @author William Wallace
     */
    public DBConnection() throws ClassNotFoundException, SQLException
    {
        Connect();
    }
	   
    /**
     * Establishes the connect with database use JDBC driver
     * Credentials for database are global variables
     *  @author William Wallace
     */
    public void Connect() throws ClassNotFoundException, SQLException
	   {
           //Register JDBC driver
           Class.forName(JDBC_DRIVER);
           
           //Open a connection
           //System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           //System.out.println("Connected!  ");
       }
    /**
     * This method adds an intial scan to the database
     * @param serialNumber - SerialNumber of asset
     * @param rfid - rfid of asset
     * @param scanUser - Scanner user
     * @param date - Date scanned
     * @param model - SerialNumber of asset
     * @param equipmentType
     * @param poNumber - Purchase order Number
     *  @author William Wallace
     */
    void addPairingRecord(String serialNumber, String rfid, String scanUser, String date, String model, String equipmentType, String poNumber) throws SQLException
    {
        Statement stmt = null;
        
        //Create SQL command
        String query = "call InsertComputerInformation('"+serialNumber+"','"+rfid+"','"+scanUser+"','"+date+"','"+model+"','"+equipmentType+"','"+poNumber+"');";
        System.out.println(query);
        stmt = conn.createStatement();
        stmt.executeQuery(query);
        
    }
    
    /**
     * This adds location of record
     * @param room - Room Number
     * @param campus - Name of campus
     * @param Building - Name of buildin
     * @param rfid - rfid number
     * @param poNumber - Purchase Order Number
     * @param Service Tag - Service tag of asset
     * @param notes - Notes
     * @param scanUser - Scanner user
     * @param date - Date scanned
     *  @author William Wallace
     */
    void addRecordLocation(String room, String campus, String building, String rfid, String poNumber, String serviceTag, String notes, String scannerName, String date) throws SQLException
    {
        Statement stmt = null;
        
        //Create SQL command
        String query = "call InsertComputerLocation('"+room+"','"+campus+"','"+building+"','"+rfid+"','"+poNumber+"','"+serviceTag+"','"+notes+"','"+scannerName+"','"+date+"');";
        System.out.println(query);
        stmt = conn.createStatement();
        stmt.executeQuery(query);
        
    }
    
    /**
     * @author William Wallace
     * Query the Database
     * @param room - Room Number
     * @param campus - Name of campus
     * @param Building - Name of buildin
     * @param rfid - rfid number
     *
     * @return listBuilder - Listbuilder with result of query
     */
    public RecordObjListBuilder generalQuery (String campus, String building, String room, String PO) throws SQLException
    {
        RecordObjListBuilder listBuilder = new RecordObjListBuilder(); //Object to add results into list
        
        Statement stmt = null;
        
        //Create SQL command
        String query = "call GetComputerInformation('"+campus+"','"+building+"','"+room+"','"+PO+"');";
        System.out.println(query);
        
        stmt = conn.createStatement();
        
        //Execute Query
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
                String serialNumber = rs.getString("SerialNumber");
                String roomName = rs.getString("RoomName");
                String buildingName = rs.getString("BuildingName");
                String campusName = rs.getString("CampusName");
                String scannerName = rs.getString("ScannerName");//Awaiting changes to recordOBJ
                String notes = rs.getString("Notes");
                String poNumber = rs.getString("PONumber");
                String scanDate = rs.getString("ScannedDate"); //Format of date returning needs to be verified
                //Send information to listBuilder to add the record
                listBuilder.addRecord(rfid, poNumber, serialNumber, scanDate,"10/27/2014 19:20", notes, roomName, buildingName, campusName);
            }
        }
        return listBuilder;
    }
    
    /**
     *  @author William Wallace
     * Query the Database for all locations in database
     * @param room - Room Number
     * @param campus - Name of campus
     * @param Building - Name of buildin
     * @param rfid - rfid number
     *
     * @return lBuilder - LocationObservableListBuilder with Campus, Building and Rooms
     */
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
    
    /**
     * Close the connection to the database
     * @author William Wallace
     */
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
            //dB.addPairingRecord("", "", "", " ", "", "", "");
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

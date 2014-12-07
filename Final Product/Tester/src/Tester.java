/*
 * Corey Allen
 * CSE 201 Client Group - Java
 * December 4, 2014
 * 
 * Tester class, incomplete overall.
 * Null and empty string inputs should be thrown out 
 * in later versions of this software by all classes.
 * Other than that, it's a pretty bare-bones tester.
 * 
 */

import devicetrackerpro.BLDGObj;
import devicetrackerpro.CampusObj;
import devicetrackerpro.RoomObj;



public class Tester {
	
	public Tester()
	{
		//Test
	}
	
	public void testLocListBuild()
	{
		//Incomplete
	}
	
	public void testRecordObj()
	{
		//Incomplete
	}
	
	public void testRecListBuild()
	{
		//Incomplete
	}
	
	
	//testCampusObj
	//Tests the campus object and prints out end results
	public void testCampusObj()
	{
		//From the void main of CampusObj
		//Initialization test
        CampusObj room = new CampusObj("HAM");
        System.err.println("Testing CampusObj: ");
        //Test getters
        System.out.print("Location : "+room.getCampus()+"\n");
        
        //Test setters
        room.setCampus("MUM");
        
        //Test getters again
        System.out.print("Location : "+room.getCampus()+"\n");
        System.err.println("Optimal case testing successful");
        
        //Bad initialization test - empty string
        try {
        	CampusObj roomTest = new CampusObj("");
        	System.out.print("Location : "+roomTest.getCampus()+"\n");
        	System.err.println("Bad init test failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("bad init test passed");
        }
        
      //Bad initialization test - null
        try {
        	CampusObj roomTest = new CampusObj(null);
        	System.out.print("Location : "+roomTest.getCampus()+"\n");
        	System.err.println("Null init test failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Null init test has succeeded");
        }
        
        //Bad set test - empty string
        try {
        	room.setCampus("");
        	System.out.print("Location : "+room.getCampus()+"\n");
        	System.err.println("Empty string set failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Empty string was thrown out successfully");
        }
        
        //Bad set test - null
        try {
         	room.setCampus(null);
        	System.out.print("Location : "+room.getCampus()+"\n");
        	System.err.println("Null string set failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Null string was thrown out successfully");
        }
        
	}
	//testBLDGObj
	//Method that tests the BLDGObj and prints the end results of each test to the command line.
	public void testBLDGObj()
	{
		//From the void main of BLDGObj
		//Initialization test
        BLDGObj room = new BLDGObj("MOS");
        System.err.println("BLDGObj testing: ");
        
        //Test getters
        System.out.print("Location : "+room.getBld()+"\n");
        
        //Test setters
        room.setBld("JST");
        
        //Test getters again
        System.out.print("Location : "+room.getBld()+"\n");
        System.err.println("Optimal case testing successful");
        
        //Bad initialization test - empty string
        try {
        	BLDGObj roomTest = new BLDGObj("");
        	System.out.print("Location : "+roomTest.getBld()+"\n");
        	System.err.println("Bad init test failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("bad init test passed");
        }
        
      //Bad initialization test - null
        try {
        	BLDGObj roomTest = new BLDGObj(null);
        	System.out.print("Location : "+roomTest.getBld()+"\n");
        	System.err.println("Null init test failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Null init test has succeeded");
        }
        
        //Bad set test - empty string
        try {
        	room.setBld("");
        	System.out.print("Location : "+room.getBld()+"\n");
        	System.err.println("Empty string set failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Empty string was thrown out successfully");
        }
        
        //Bad set test - null
        try {
         	room.setBld(null);
        	System.out.print("Location : "+room.getBld()+"\n");
        	System.err.println("Null string set failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Null string was thrown out successfully");
        }
        
	}
	
	
	//testRoomObj
	//Method that tests the roomObj and prints the end results of each test to the command line.
	public void testRoomObj()
	{
		//Code from original RoomObj class
		//Initialization test
        System.err.println("RoomObj testing: ");
		RoomObj room = new RoomObj("300");
        
        //Test getters
        System.out.print("Location : "+room.getRm()+"\n");
        System.err.println("Optimal init case successful");
        
        //Test setters
        room.setRm("100");
        
        //Test getters again
        System.out.print("Location : "+room.getRm()+"\n");
        System.err.println("Optimal set case successful");
      //Bad initialization test - empty string
        try {
        	RoomObj roomTest = new RoomObj("");
        	System.out.print("Location : "+roomTest.getRm()+"\n");
        	System.err.println("Bad init test failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("bad init test passed");
        }
        
      //Bad initialization test - null
        try {
        	RoomObj roomTest = new RoomObj(null);
        	System.out.print("Location : "+roomTest.getRm()+"\n");
        	System.err.println("Null init test failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Null init test has succeeded");
        }
        
        //Bad set test - empty string
        try {
        	room.setRm("");
        	System.out.print("Location : "+room.getRm()+"\n");
        	System.err.println("Empty string set failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Empty string was thrown out successfully");
        }
        
        //Bad set test - null
        try {
         	room.setRm(null);
        	System.out.print("Location : "+room.getRm()+"\n");
        	System.err.println("Null string set failed");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.err.println("Null string was thrown out successfully");
        }
	}

	//public static void main: tests the program, takes no arguments
	public static void main(String[] args) {
		
		Tester tester = new Tester();
		tester.testRoomObj();
		tester.testBLDGObj();
		tester.testCampusObj();
		
		//Only thing I can say is that setters and constructors for these
		//should throw out empty strings and null inputs and give a good
		//error message if a null or empty string is put in the constructor/setter
		//argument
	}

}

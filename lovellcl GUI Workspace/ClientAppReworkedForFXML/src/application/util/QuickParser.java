package application.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import application.model.RecordObj;

public class QuickParser
{

	private LinkedList<String> csv = new LinkedList<String>();
	private ArrayList<RecordObj> testList = new ArrayList<RecordObj>();
	private File filename;

	public void parse(File filename, int type) 
	{
		this.filename = filename;
		csv = new LinkedList<String>();
		fileToArray();
		parse(type);
		//return testList; 
		//change void Parse to ArrayList<RecordObj> Parse
	}

	//dumps the csv file to a linkedList
	private void fileToArray()
	{
		try
		{
			Scanner in = new Scanner(filename);

			while (in.hasNextLine())
			{
				String tempString = in.nextLine();  

				if (tempString.length() != 0)
				{
					csv.add(tempString);
				}

			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		}
	}

	private void parse(int type)
	{
		int line = 0;
		while (csv.size() > line)
		{
			//based on inventory.csv
			String[] tempArray = csv.get(line).split(",");   
			String strRFID = "", strCampus = "", strBldg = "", strLastScanedBy = "", strComments = "", strSerial = "", strHardware = "";
			int intRm = 0, intScanYear = 0, intScanMonth = 0, intScanDay = 0, intScanHour = 0, intScanMinute = 0;
			if (type == 1)
			{
				strRFID = tempArray[0];
				strCampus = tempArray[1];
				strBldg = tempArray[2];
				intRm = Integer.parseInt(tempArray[3]);

				strLastScanedBy = ""; //not available yet

				intScanYear = Integer.parseInt(tempArray[5].substring(6, 10));
				intScanMonth = Integer.parseInt(tempArray[5].substring(0, 2));
				intScanDay = Integer.parseInt(tempArray[5].substring(3, 5));
				intScanHour = Integer.parseInt(tempArray[5].substring(11, 13));
				intScanMinute = Integer.parseInt(tempArray[5].substring(14, 16));                        

				if (tempArray.length == 7)
				{
					strComments = tempArray[6];
				}
			}

			if (type == 0)
			{
				strRFID = tempArray[0];
				strSerial = tempArray[1];
				strHardware = tempArray[2];

				strLastScanedBy = ""; //not available yet

				intScanYear = Integer.parseInt(tempArray[3].substring(6, 10));
				intScanMonth = Integer.parseInt(tempArray[3].substring(0, 2));
				intScanDay = Integer.parseInt(tempArray[3].substring(3, 5));
				intScanHour = Integer.parseInt(tempArray[3].substring(11, 13));
				intScanMinute = Integer.parseInt(tempArray[3].substring(14, 16));                        


				if (tempArray.length == 5)
				{
					strComments = tempArray[4];
				}
			}

			//testList.add(new RecordObj(strRFID, strCampus, strBldg, intRm, strLastScanedBy, intScanYear, intScanMonth, intScanDay, intScanHour, intScanMinute, strComments));                              
			if (type == 1)
			{
				System.out.println("RFID Tag");
				System.out.println("RFID: "+strRFID);  
				System.out.println(); 
				System.out.println("Location Information"); 
				System.out.println("Campus: "+strCampus); 
				System.out.println("Building: "+strBldg); 
				System.out.println("Room: "+intRm); 

				System.out.println(); 
				System.out.println("Scan information"); 
				System.out.println("Year: "+intScanYear); 
				System.out.println("Month: "+intScanMonth); 
				System.out.println("Day: "+intScanDay); 
				System.out.println("Hour: "+intScanHour); 
				System.out.println("Minute: "+intScanMinute); 
				System.out.println("Comments: "+strComments); 
				System.out.println("End line "+line);
				System.out.println();
			}

			if (type == 0)
			{
				System.out.println("RFID Tag");
				System.out.println("RFID: "+strRFID);  
				System.out.println(); 
				System.out.println("Hardware info"); 
				System.out.println("Serial Number: "+strSerial); 
				System.out.println("Model: "+strHardware); 

				System.out.println(); 
				System.out.println("Scan information"); 
				System.out.println("Year: "+intScanYear); 
				System.out.println("Month: "+intScanMonth); 
				System.out.println("Day: "+intScanDay); 
				System.out.println("Hour: "+intScanHour); 
				System.out.println("Minute: "+intScanMinute); 
				System.out.println("Comments: "+strComments); 
				System.out.println("End line "+line);
				System.out.println();
			}
			
			line++;
		
		}

	}

	public static void main (String [] args)
	{
		QuickParser test = new QuickParser();
		test.parse(new File("initial.csv"), 0);
	}
	
}

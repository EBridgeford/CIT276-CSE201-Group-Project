package application.util;

import java.io.*;
import java.util.*;

import application.model.RecordObj;
import application.view.RootLayout;
import javafx.collections.*;

public class QuickParser
{

	private LinkedList<String> csv = new LinkedList<String>();
	private ArrayList<RecordObj> testList = new ArrayList<RecordObj>();
	private File csvFile;
	RootLayout.FileType fileType = RootLayout.FileType.UNASSIGNED;



	//current returns ArrayList but can easily be converted to ObservableList
	public ObservableList Parse(File csvFile, RootLayout.FileType test ) 
	{
		this.csvFile = csvFile;

		fileType = test;
		csv = new LinkedList<String>();
		fileToArray(csv);
		parse(csv);

		ObservableList<RecordObj> observTestList = FXCollections.observableArrayList(testList);
		return observTestList;

	}



	//dumps the csv file to a linkedList of strings
	private void fileToArray(LinkedList<String> tempList)
	{

		try
		{
			Scanner in = new Scanner(csvFile);




			while (in.hasNextLine() == true)
			{
				String tempString = in.nextLine();  

				if (tempString.length() > 8)
				{
					tempList.add(tempString);
				}

			}
			in.close();
		}

		catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
	}


	private void parse(LinkedList<String> tempList)
	{
		int line = 0;
		while (tempList.size() > line)
		{

			String temp = csvFile.getName().substring(0,4); //puts the first four letters of the filename into a string for identification

			String[] tempArray = tempList.get(line).split(",");   
			String strRFID = "", strLocation = "", strLastScanedBy = "", comments = "", strSerial = "", strPO = "", timeStamp = "", strPurchaseOrder = "", strServiceTag ="";




			strRFID = tempArray[0]; //always the first

			//identifies the kind of file passed in and parses it

			if (fileType == RootLayout.FileType.INVENTORY)
			{

				strLocation = tempArray[1] + " " + tempArray[2] + " " + tempArray[3];
				strLastScanedBy = tempArray[4];   
				timeStamp = tempArray[5];  

				if (tempArray.length == 7)
				{
					comments = tempArray[6];
				}
			}


			if (fileType == RootLayout.FileType.INITIAL)
			{

				strSerial = tempArray[1];
				strLastScanedBy = tempArray[2];  
				timeStamp = tempArray[3];  

				if (tempArray.length == 5)
				{
					comments = tempArray[4];
				}

			}

			RecordObj tempRecordObj = new RecordObj(strRFID,  strLastScanedBy, timeStamp, comments);

			if (fileType == RootLayout.FileType.INVENTORY)
			{
				tempRecordObj.setLocation(strLocation);
			}

			if (fileType == RootLayout.FileType.INITIAL)
			{

				tempRecordObj.setServiceTag(strSerial);

			}


			testList.add(tempRecordObj);                                 
			line++;

		}




	}


	public static void main (String [] args)
	{

		String str = "initial.csv";

		File csvFile = new File(str);

		RootLayout.FileType testEnum  = RootLayout.FileType.INITIAL;

		QuickParser test = new QuickParser();
		ObservableList<RecordObj> testList = test.Parse(csvFile,testEnum);

		if (testEnum == RootLayout.FileType.INVENTORY)
		{

			for (int i = 0; testList.size() > i; i++)
			{
				System.out.println(testList.get(i).getRfid());
				System.out.println(testList.get(i).getCampus());
				System.out.println(testList.get(i).getBldg());
				System.out.println(testList.get(i).getRm());
				System.out.println(testList.get(i).getScanMonth()+"/"+testList.get(i).getScanDay()+"/"+testList.get(i).getScanYear());
				System.out.println(testList.get(i).getLastScanedBy());
				System.out.println(testList.get(i).getComments());
				System.out.println();
			}
		}

		if (testEnum == RootLayout.FileType.INITIAL)
		{

			for (int i = 0; testList.size() > i; i++)
			{
				System.out.println(testList.get(i).getRfid());
				System.out.println(testList.get(i).getServiceTag());
				System.out.println(testList.get(i).getPurchaseOrder());
				System.out.println(testList.get(i).getLastScanedBy());
				System.out.println(testList.get(i).getScanMonth()+"/"+testList.get(i).getScanDay()+"/"+testList.get(i).getScanYear());
				System.out.println(testList.get(i).getComments());
				System.out.println();
			}
		}




	}

}
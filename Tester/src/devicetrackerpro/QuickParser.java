/*
 * *******************************************************************************************************
 * ********  **           ***      ******   ******** **           ***      ***     *** ******** ***    *** 
 * **    *** **          **  *     **   **  ***      **          **  *      ***   ***  ***      ****   ***
 * ********  **         **    *    **    ** ******** **         **    *      *** ***   ******** ** **  *** 
 * ********  **        *********   **    ** ******** **        *********      *****    ******** **  ** *** 
 * **    *** *******  ***********  **   **  ***      *******  ***********      ***     ***      **   ***** 
 * ********  ******* **         ** ******   ******** ******* **         **      *      ******** **    **** 
 * *******************************************************************************************************
 * *********************************************** BLADELAVEN STUDIOS ************************************

 */
package devicetrackerpro;
import java.io.*;
import java.util.*;

import javafx.collections.*;

public class QuickParser
{

private LinkedList<String> csv = new LinkedList<String>();
private ArrayList<RecordObj> testList = new ArrayList<RecordObj>();
private File csvFile;



//current returns ArrayList but can easily be converted to ObservableList
public ObservableList Parse(File csvFile) 
{
  this.csvFile = csvFile;
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

    timeStamp = "14:13:12 09:20:20:20"; //fake date, update when we get a newer csv
    strLastScanedBy = "nobody"; //fake person, update when we get a newer csv
    
    strRFID = tempArray[0]; //always the first
    
    //identifies the kind of file passed in and parses it
   
    if (temp.equals("inve") == true)
    {
      
      strLocation = tempArray[1] + " " + tempArray[2] + " " + tempArray[3];                           
          
      if (tempArray.length == 7)
      {
        comments = tempArray[6];
      }
    }
    
    
    if (temp.equals("init") == true)
    {

      strSerial = tempArray[1];
      strPO = tempArray[2];                  
        
      if (tempArray.length == 5)
      {
        comments = tempArray[4];
      }
      
    }
    
    RecordObj tempRecordObj = new RecordObj(strRFID,  strLastScanedBy, timeStamp, comments);
    
    if (temp.equals("inve") == true)
    {
        tempRecordObj.setLocation(strLocation);
    }
    
    if (temp.equals("init") == true)
    {

    tempRecordObj.setServiceTag(strSerial);
    tempRecordObj.setPurchaseOrder(strPO);
      
    }
     
    
    testList.add(tempRecordObj);                                 
    line++;
    
  }
  
  
  
  
}


public static void main (String [] args)
{

  String str = "initial.csv";

  File csvFile = new File(str);



QuickParser test = new QuickParser();
ObservableList<RecordObj> testList = test.Parse(csvFile);

if (str.equals("inventory.csv"))
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

if (str.equals("initial.csv"))
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

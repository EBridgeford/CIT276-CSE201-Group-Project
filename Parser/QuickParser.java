import java.io.*;
import java.util.*;

public class QuickParser
{

private LinkedList<String> csv = new LinkedList<String>();
private ArrayList<RecordObj> testList = new ArrayList<RecordObj>();
private File csvFile;


//current returns ArrayList but can easily be converted to ObservableList
public ArrayList Parse(File csvFile) 
{
  this.csvFile = csvFile;
  csv = new LinkedList<String>();
  fileToArray(csv);
  parse(csv);

  return testList;

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
    String strRFID = "", strCampus = "", strBldg = "", strLastScanedBy = "", strComments = "", strSerial = "", strHardware = "", timeStamp = "", strPurchaseOrder = "", strServiceTag ="";
    int intRm = 0;
    timeStamp = "14:13:12 09:20:20:20"; //fake date, replace with real date later 
    
    //identifies the kind of file passed in and parses it
    if (temp.equals("inve") == true)
    {
      strRFID = tempArray[0];
      strCampus = tempArray[1];
      strBldg = tempArray[2];
      intRm = Integer.parseInt(tempArray[3]);                              
      strLastScanedBy = ""; //not available yet
      
      
      if (tempArray.length == 7)
      {
        strComments = tempArray[6];
      }
    }
    
    
    if (temp.equals("init") == true)
    {
      strRFID = tempArray[0];
      strSerial = tempArray[1];
      strHardware = tempArray[2];                  
      strLastScanedBy = ""; //not available yet?
      
      
      
      if (tempArray.length == 5)
      {
        strComments = tempArray[4];
      }
      
    }
    
    
    testList.add(new RecordObj(strRFID, strCampus, strBldg, intRm, strLastScanedBy, timeStamp, strComments, strPurchaseOrder, strServiceTag));                              
    
    line++;
    
  }
  
  
  
  
}


public static void main (String [] args)
{

  String str = "inventory.csv";

  File csvFile = new File(str);



QuickParser test = new QuickParser();
ArrayList<RecordObj> testList = test.Parse(csvFile);

for (int i = 0; testList.size() > i; i++)
{
System.out.println(testList.get(i).getRfid());
System.out.println(testList.get(i).getCampus());
System.out.println(testList.get(i).getBldg());
System.out.println(testList.get(i).getRm());
System.out.println(testList.get(i).getComments());
System.out.println();
}
 
}
}

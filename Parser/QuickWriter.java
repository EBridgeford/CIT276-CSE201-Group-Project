import java.io.*;
import java.util.*;

//import QuickParser.FileType;
import javafx.collections.ObservableList;

public class QuickWriter 
{
	private ObservableList<RecordObj> testList;
	
	
	public QuickWriter(ObservableList<RecordObj> list)
	{
	testList = list;
	print();
	}
	
	private void print()
	{
		try
		{
		PrintWriter writer = new PrintWriter("List.txt");
		for (int i = 0; testList.size() > i; i++)
		{
			writer.println("RFID: "+ testList.get(i).getRfid());
			writer.println("Service tag: "+ testList.get(i).getServiceTag());
			writer.println("Purchase Order: "+ testList.get(i).getPurchaseOrder());
			writer.println("Last scanned by:" + testList.get(i).getLastScanedBy());
			writer.println("Last scanned: "+testList.get(i).getScanMonth()+"/"+testList.get(i).getScanDay()+"/"+testList.get(i).getScanYear());
			writer.println("Campus: "+ testList.get(i).getCampus());
			writer.println("Building: "+ testList.get(i).getBldg());
			writer.println("Room: "+testList.get(i).getRm());			
			writer.println("Comments: "+testList.get(i).getComments());
			writer.println("----------------------------------------------------------------------------");
			writer.println("");
		}
	writer.close();
	}
	
	catch (FileNotFoundException e)
		{
		System.out.println(e);
		}
		}
	public static void main(String[] args) 
	{
		
		
		

	}

}

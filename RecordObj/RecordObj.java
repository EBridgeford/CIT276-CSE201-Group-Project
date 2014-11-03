package inventory.tracker.pro;

//import javafx.beans.property.*;                           //Generic for All Beans Properties
//import javafx.beans.property.DoubleProperty;              //Not Needed Yet
//import javafx.beans.property.SimpleDoubleProperty;        //Not Needed Yet
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.binding.NumberBinding;                //Not Needed Yet
//import java.io.*;         
//import java.util.*; 
//import java.util.*;
import javax.swing.*;
//import java.util.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

public class RecordObj{
     // These are variable Values wrapped up in a wrapper which adds some extra functionality
     private final StringProperty RFID;
     private StringProperty campus, bldg, lastScanedBy, comments, purchaseOrder, serviceTag, strTimeStamp, rm; 
     private IntegerProperty  scanYear, scanDay, scanMonth, scanHour, scanMinute;
     
     //Initializes the Obj with the values passed in
     public RecordObj(String strRFID,  String strLastScanedBy, String timeStamp){
          this.RFID = new SimpleStringProperty(strRFID);
          this.lastScanedBy = new SimpleStringProperty(strLastScanedBy);
          this.setTimeStamp(timeStamp);
     }//END Initialization 
     
     //Getters and Setters for TimeStamp********************************************************************************
     public final String getTimeStamp(){return this.strTimeStamp.get();}// Returns the properties value                                              
     public final void setTimeStamp(String timeStamp){
         try{
            this.strTimeStamp = new SimpleStringProperty(timeStamp);
            int mo = Integer.parseInt(timeStamp.substring(0, 2));
            int day = Integer.parseInt(timeStamp.substring(3, 5));
            int yr = Integer.parseInt(timeStamp.substring(6, 8));
            int hr = Integer.parseInt(timeStamp.substring(9, 11));
            int min = Integer.parseInt(timeStamp.substring(12, 14));
            scanYear = new SimpleIntegerProperty(yr);
            scanMonth = new SimpleIntegerProperty(mo);
            scanDay = new SimpleIntegerProperty(day);
            scanHour = new SimpleIntegerProperty(hr);
            scanMinute = new SimpleIntegerProperty(min);
         }catch(NumberFormatException e){
             JOptionPane.showMessageDialog(null,e.getMessage(),
                     "Invalid Date Format! Unable to update TimeStamp in RecordObj! \n Need a format of \"MM/dd/yy HH:mm\" found this crap: "+timeStamp,
                     JOptionPane.ERROR_MESSAGE);
         }//End Try/Catch
     }                                                                         // Sets the properties value
     public StringProperty strTimeStampProperty(){return this.strTimeStamp;}   // Returns the property itself

     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for RFID
     public final String getRfid(){return this.RFID.get();}                    // Returns the properties value                                              
     public final void setRfid(String str){/*this.RFID.set(str);*/}            // Sets the properties value which should be invalid
     public StringProperty rfidProperty(){return this.RFID;}                   // Returns the property itself
     
     //Getters and Setters for campus
     public final String getCampus(){return this.campus.get();}                // Returns the properties value                                              
     public final void setCampus(String str){
         this.campus = new SimpleStringProperty(str);}                         // Sets the properties value
     public StringProperty campusProperty(){return this.campus;}               // Returns the property itself
     
     //Getters and Setters for bldg
     public final String getBldg(){return this.bldg.get();}                    // Returns the properties value                                              
     public final void setBldg(String str){
         this.bldg = new SimpleStringProperty(str);}                           // Sets the properties value
     public StringProperty bldgProperty(){return this.bldg;}                   // Returns the property itself
     
     //Getters and Setters for rm
     public final String getRm(){return this.rm.get();}                        // Returns the properties value                                              
     public final void setRm(String str){
         this.rm = new SimpleStringProperty(str);}                             // Sets the properties value
     public StringProperty rmProperty(){return this.rm;}                       // Returns the property itself
     
     //Getters and Setters for lastScanedBy
     public final String getLastScanedBy(){return this.lastScanedBy.get();}    // Returns the properties value                                              
     public final void setLastScanedBy(String str){
         this.lastScanedBy = new SimpleStringProperty(str);}                   // Sets the properties value
     public StringProperty lastScanedByProperty(){return this.lastScanedBy;}   // Returns the property itself
     
     //Getters and Setters for Comments
     public final String getComments(){return this.comments.get();}            // Returns the properties value                                              
     public final void setComments(String str){
         this.comments = new SimpleStringProperty(str);}                       // Sets the properties value
     public StringProperty commentsProperty(){return this.comments;}           // Returns the property itself
     
     //Getters and Setters for purchaseOrder
     public final String getPurchaseOrder(){return this.purchaseOrder.get();}  // Returns the properties value                                              
     public final void setPurchaseOrder(String str){
         this.purchaseOrder = new SimpleStringProperty(str);}                  // Sets the properties value
     public StringProperty purchaseOrderProperty(){return this.purchaseOrder;} // Returns the property itself
     
     //Getters and Setters for serviceTag
     public final String getServiceTag(){return this.serviceTag.get();}        // Returns the properties value                                              
     public final void setServiceTag(String str){
         this.serviceTag = new SimpleStringProperty(str);}                     // Sets the properties value
     public StringProperty serviceTagProperty(){return this.serviceTag;}       // Returns the property itself
     
     //INT SETTERS/GETTERS*******************************************************************************
     //Getters and Setters for scanYear
     public final int getScanYear(){return this.scanYear.get();}               // Returns the properties value                                              
     public final void setScanYear(int x){this.scanYear.set(x);}               // Sets the properties value
     public IntegerProperty scanYearProperty(){return this.scanYear;}          // Returns the property itself
     
     //Getters and Setters for scanMonth
     public final int getScanMonth(){return this.scanMonth.get();}             // Returns the properties value                                              
     public final void setScanMonth(int x){this.scanMonth.set(x);}             // Sets the properties value
     public IntegerProperty scanMonthProperty(){return this.scanMonth;}        // Returns the property itself
     
     //Getters and Setters for scanDay
     public final int getScanDay(){return this.scanDay.get();}                 // Returns the properties value                                              
     public final void setScanDay(int x){this.scanDay.set(x);}                 // Sets the properties value
     public IntegerProperty scanDayProperty(){return this.scanDay;}            // Returns the property itself
     
     //Getters and Setters for scanHour
     public final int getScanHour(){return this.scanHour.get();}               // Returns the properties value                                              
     public final void setScanHour(int x){this.scanHour.set(x);}               // Sets the properties value
     public IntegerProperty scanHourProperty(){return this.scanHour;}          // Returns the property itself
     
     //Getters and Setters for scanHour
     public final int getScanMinute(){return this.scanMinute.get();}           // Returns the properties value                                              
     public final void setScanMinute(int x){this.scanMinute.set(x);}           // Sets the properties value
     public IntegerProperty scanMinuteProperty(){return this.scanMinute;}      // Returns the property itself
     
     //Main Method for testing
     public static void main(String[] args)
     {    
          //Initialization Test with required values
          RecordObj record = new RecordObj("111111111", "hadleyps       ", "10/27/14 09:20");
          
          //Pass in optional value
          record.setCampus("MUH"); 
          record.setBldg("MOS"); 
          record.setComments("Old!"); 
          record.setPurchaseOrder("kjhkjhhki4321");
          record.setServiceTag("KY678UJ98");
          record.setRm("100"); 
          
          //Print column header
          System.out.print("Status     | RFID#     |  Campus |  BLDG | RM  | Comments |   Last Scanned on   | Last Scanned by | PurchaseOrder | ServiceTag# |\n");
          
          //Test getters
          System.out.print("Retrieving | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //Test setters
          record.setRfid("222222222"); 
          record.setCampus("MUM"); 
          record.setBldg("JST"); 
          record.setLastScanedBy("YourMom        "); 
          record.setComments("New!"); 
          record.setPurchaseOrder("kjhkjhhki1234");
          record.setServiceTag("kjhgfdsaw");
          record.setRm("T20"); 
          record.setTimeStamp("11/02/14 10:25");
          
          //Test Getters again
          System.out.print("Updated To | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //test getMinute, getHour, getDay, getMonth, and getYear
          System.out.print("Individual time settings = "+record.getScanYear()+"/"+record.getScanMonth()+"/"+record.getScanDay()+" at "+record.getScanHour()+":"+record.getScanMinute()+"\n");
           
     }//end Main Method   
}//End Class

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.swing.*;

public class RecordObj{
     // These are variable Values wrapped up in a wrapper which adds some extra functionality
     private final StringProperty RFID;
     private StringProperty campus, bldg, lastScanedBy, comments, purchaseOrder, serviceTag, strTimeStamp, rm, location; 
     private IntegerProperty  scanYear, scanDay, scanMonth, scanHour, scanMinute;
     
     //Initializes the Obj with the values passed in
     public RecordObj(String strRFID,  String strLastScanedBy, String timeStamp, String comments){
          this.RFID = new SimpleStringProperty(strRFID);
          this.lastScanedBy = new SimpleStringProperty(strLastScanedBy);
          this.setTimeStamp(timeStamp);
          this.setComments(comments);
     }//END Initialization 
     
     //Getters and Setters for TimeStamp********************************************************************************
     public final String getTimeStamp(){return this.strTimeStamp.get();}// Returns the properties value                                              
     public final void setTimeStamp(String timeStamp){
         try{
            this.strTimeStamp = new SimpleStringProperty(timeStamp);
            this.scanYear = new SimpleIntegerProperty(Integer.parseInt(timeStamp.substring(6, 8)));
            this.scanMonth = new SimpleIntegerProperty(Integer.parseInt(timeStamp.substring(0, 2)));
            this.scanDay = new SimpleIntegerProperty(Integer.parseInt(timeStamp.substring(3, 5)));
            this.scanHour = new SimpleIntegerProperty(Integer.parseInt(timeStamp.substring(9, 11)));
            this.scanMinute = new SimpleIntegerProperty(Integer.parseInt(timeStamp.substring(12, 14)));
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
     private final void setCampus(String str){
         this.campus = new SimpleStringProperty(str);}                         // Sets the properties value
     public StringProperty campusProperty(){return this.campus;}               // Returns the property itself
     
     //Getters and Setters for bldg
     public final String getBldg(){return this.bldg.get();}                    // Returns the properties value                                              
     private final void setBldg(String str){
         this.bldg = new SimpleStringProperty(str);}                           // Sets the properties value
     public StringProperty bldgProperty(){return this.bldg;}                   // Returns the property itself
     
     //Getters and Setters for rm
     public final String getRm(){return this.rm.get();}                        // Returns the properties value                                              
     private final void setRm(String str){
         this.rm = new SimpleStringProperty(str);}                             // Sets the properties value
     public StringProperty rmProperty(){return this.rm;}                       // Returns the property itself
     
     //Getters and Setters for Location
     public final String getLocation(){return this.location.get();}            // Returns the properties value                                              
     public final void setlocation(String str){
         this.location = new SimpleStringProperty(str);
         this.setCampus(str.substring(0, 3));
         this.setBldg(str.substring(4, 7));
         this.setRm(str.substring(8, 11));}                                    // Sets all of the location property values
     public StringProperty locationProperty(){return this.location;}           // Returns the property itself
     
     //Getters and Setters for lastScanedBy
     public final String getLastScanedBy(){return this.lastScanedBy.get();}    // Returns the properties value                                              
     private final void setLastScanedBy(String str){
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
     private final void setScanYear(int x){this.scanYear.set(x);}              // Sets the properties value
     public IntegerProperty scanYearProperty(){return this.scanYear;}          // Returns the property itself
     
     //Getters and Setters for scanMonth
     public final int getScanMonth(){return this.scanMonth.get();}             // Returns the properties value                                              
     private final void setScanMonth(int x){this.scanMonth.set(x);}            // Sets the properties value
     public IntegerProperty scanMonthProperty(){return this.scanMonth;}        // Returns the property itself
     
     //Getters and Setters for scanDay
     public final int getScanDay(){return this.scanDay.get();}                 // Returns the properties value                                              
     private final void setScanDay(int x){this.scanDay.set(x);}                // Sets the properties value
     public IntegerProperty scanDayProperty(){return this.scanDay;}            // Returns the property itself
     
     //Getters and Setters for scanHour
     public final int getScanHour(){return this.scanHour.get();}               // Returns the properties value                                              
     private final void setScanHour(int x){this.scanHour.set(x);}              // Sets the properties value
     public IntegerProperty scanHourProperty(){return this.scanHour;}          // Returns the property itself
     
     //Getters and Setters for scanHour
     public final int getScanMinute(){return this.scanMinute.get();}           // Returns the properties value                                              
     private final void setScanMinute(int x){this.scanMinute.set(x);}          // Sets the properties value
     public IntegerProperty scanMinuteProperty(){return this.scanMinute;}      // Returns the property itself
     
     //Main Method for testing
     public static void main(String[] args)
     {    
          //Initialization Test with required values
          RecordObj record = new RecordObj("111111111", "hadleyps       ", "10/27/14 09:20", "Old!");
          //Pass in optional values
          record.setlocation("MUH/MOS/100");
          record.setPurchaseOrder("kjhkjhhki4321");
          record.setServiceTag("KY678UJ98");
          
          
          //Print column header
          System.out.print("Status     | RFID#     |  Campus |  BLDG | RM  | Comments |   Last Scanned on   | Last Scanned by | PurchaseOrder | ServiceTag# |\n");

          //Test getters
          System.out.print("Retrieving | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //Test setters
          record.setRfid("222222222"); 
          record.setlocation("MUM/JST/T20");
          record.setLastScanedBy("YourMom        "); 
          record.setComments("New!"); 
          record.setPurchaseOrder("kjhkjhhki1234");
          record.setServiceTag("kjhgfdsaw");
          record.setTimeStamp("11/02/14 10:25");
          
          //Test Getters again to validate setters
          System.out.print("Updated To | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //test getMinute, getHour, getDay, getMonth, and getYear
          System.out.print("Individual time settings = "+record.getScanYear()+"/"+record.getScanMonth()+"/"+record.getScanDay()+" at "+record.getScanHour()+":"+record.getScanMinute()+"\n");
          //test getLocation
          System.out.print("Location: "+record.getLocation()+"\n");
           
     }//end Main Method   
}//End Class

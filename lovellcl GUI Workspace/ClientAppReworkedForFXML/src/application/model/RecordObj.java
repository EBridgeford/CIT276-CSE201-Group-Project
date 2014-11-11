package application.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.swing.*;

public class RecordObj{
     // These are variable Values wrapped up in a wrapper which adds some extra functionality
     private StringProperty rfid, campus, bldg, lastScanedBy, comments, purchaseOrder, serviceTag, strTimeStamp; 
     private IntegerProperty rm, scanYear, scanDay, scanMonth, scanHour, scanMinute;
     
     //Initializes the Obj with the values passed in
     public RecordObj(String strRFID, String strCampus, String strBldg, int intRm, String strLastScanedBy, 
            String timeStamp, String strComments, String strPurchaseOrder, String strServiceTag){
          this.rfid = new SimpleStringProperty(strRFID);
          this.campus = new SimpleStringProperty(strCampus);
          this.bldg = new SimpleStringProperty(strBldg);
          this.rm = new SimpleIntegerProperty(intRm);
          this.lastScanedBy = new SimpleStringProperty(strLastScanedBy);
          this.strTimeStamp = new SimpleStringProperty();
          this.setStrTimeStamp(timeStamp);
          this.comments = new SimpleStringProperty(strComments);
          this.purchaseOrder = new SimpleStringProperty(strPurchaseOrder);
          this.serviceTag = new SimpleStringProperty(strServiceTag); 
     }//END Initialization 
     
     //Getters and Setters for TimeStamp********************************************************************************
     public final String getStrTimeStamp(){return this.strTimeStamp.get();}// Returns the properties value                                              
     public final void setStrTimeStamp(String timeStamp){
         try{
            this.strTimeStamp.set(timeStamp);
            int yr = Integer.parseInt(timeStamp.substring(0, 2));
            int mo = Integer.parseInt(timeStamp.substring(3, 5));
            int day = Integer.parseInt(timeStamp.substring(6, 8));
            int hr = Integer.parseInt(timeStamp.substring(9, 11));
            int min = Integer.parseInt(timeStamp.substring(12, 14));
            scanYear = new SimpleIntegerProperty(yr);
            scanMonth = new SimpleIntegerProperty(mo);
            scanDay = new SimpleIntegerProperty(day);
            scanHour = new SimpleIntegerProperty(hr);
            scanMinute = new SimpleIntegerProperty(min);
         }catch(NumberFormatException e){
             JOptionPane.showMessageDialog(null,e.getMessage(),
                     "Invalid Date Format! Unable to update TimeStamp in RecordObj! \n Need a format of \"YY/MM/dd HH:mm:ss:mm\" found this crap: "+timeStamp,
                     JOptionPane.ERROR_MESSAGE);
         }//End Try/Catch
     }                                                                         // Sets the properties value
     public StringProperty strTimeStampProperty(){return this.strTimeStamp;}   // Returns the property itself

     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for RFID
     public final String getRfid(){return this.rfid.get();}                    // Returns the properties value                                              
     public final void setRfid(String str){this.rfid.set(str);}                // Sets the properties value
     public StringProperty rfidProperty(){return this.rfid;}                   // Returns the property itself
     
     //Getters and Setters for campus
     public final String getCampus(){return this.campus.get();}                // Returns the properties value                                              
     public final void setCampus(String str){this.campus.set(str);}            // Sets the properties value
     public StringProperty campusProperty(){return this.campus;}               // Returns the property itself
     
     //Getters and Setters for bldg
     public final String getBldg(){return this.bldg.get();}                    // Returns the properties value                                              
     public final void setBldg(String str){this.bldg.set(str);}                // Sets the properties value
     public StringProperty bldgProperty(){return this.bldg;}                   // Returns the property itself
     
     //Getters and Setters for lastScanedBy
     public final String getLastScanedBy(){return this.lastScanedBy.get();}    // Returns the properties value                                              
     public final void setLastScanedBy(String str){this.lastScanedBy.set(str);}// Sets the properties value
     public StringProperty lastScanedByProperty(){return this.lastScanedBy;}   // Returns the property itself
     
     //Getters and Setters for lastScanedBy
     public final String getComments(){return this.comments.get();}            // Returns the properties value                                              
     public final void setComments(String str){this.comments.set(str);}        // Sets the properties value
     public StringProperty commentsProperty(){return this.comments;}           // Returns the property itself
     
     //Getters and Setters for purchaseOrder
     public final String getPurchaseOrder(){return this.purchaseOrder.get();}  // Returns the properties value                                              
     public final void setPurchaseOrder(String str){this.purchaseOrder.set(str);}// Sets the properties value
     public StringProperty purchaseOrderProperty(){return this.purchaseOrder;} // Returns the property itself
     
     //Getters and Setters for serviceTag
     public final String getServiceTag(){return this.serviceTag.get();}        // Returns the properties value                                              
     public final void setServiceTag(String str){this.serviceTag.set(str);}    // Sets the properties value
     public StringProperty serviceTagProperty(){return this.serviceTag;}       // Returns the property itself
     
     //INT SETTERS/GETTERS*******************************************************************************
     //Getters and Setters for rm
     public final int getRm(){return this.rm.get();}                           // Returns the properties value                                              
     public final void setRm(int x){this.rm.set(x);}                           // Sets the properties value
     public IntegerProperty rmProperty(){return this.rm;}                      // Returns the property itself
     
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
          //Initialization Test
          RecordObj record = new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "None", "w52as00003215", "zjkfjasfk");
          
          //Print column header
          System.out.print("Status     | RFID#     |  Campus |  BLDG | RM  | Comments |      Last Scanned on      | Last Scanned by | PurchaseOrder | ServiceTag# |\n");
          
          //Test getters
          System.out.print("Retrieving | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getStrTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //Test setters
          record.setRfid("222222222"); 
          record.setCampus("MUM"); 
          record.setBldg("JST"); 
          record.setLastScanedBy("YourMom        "); 
          record.setComments("New!"); 
          record.setPurchaseOrder("kjhkjhhki1234");
          record.setServiceTag("kjhgfdsaw");
          record.setRm(100); 
          record.setStrTimeStamp("15/11/28 10:25:35:20");
          
          //Test Getters again
          System.out.print("Updated To | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getStrTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //test getMinute, getHour, getDay, getMonth, and getYear
          System.out.print("Individual time settings = "+record.getScanYear()+"/"+record.getScanMonth()+"/"+record.getScanDay()+" at "+record.getScanHour()+":"+record.getScanMinute()+"\n");
           
     } // End Main Method   
} // End Class

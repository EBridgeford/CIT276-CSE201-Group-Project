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
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.swing.*;


/**
 *
 * @author Bladelaven
 */
public class RecordObj{
     // These are variable Values wrapped up in a wrapper which adds some extra functionality
     private StringProperty RFID, campus, bldg, lastScanedBy, comments, purchaseOrder, serviceTag, strTimeStamp, rm, location; 
     private IntegerProperty  scanYear, scanDay, scanMonth, scanHour, scanMinute;
     
     //Initializes the Obj with the values passed in
     public RecordObj(String strRFID,  String strLastScanedBy, String timeStamp, String comments){
          this.RFID = new SimpleStringProperty(strRFID);
          this.lastScanedBy = new SimpleStringProperty(strLastScanedBy);
          this.setTimeStamp(timeStamp);
          this.setComments(comments);
          this.setLocation("___/___/___");
          this.setPurchaseOrder("_");
          this.setServiceTag("_");
          
     }//END Initialization 
     
     //Getters and Setters for TimeStamp********************************************************************************
     public final String getTimeStamp(){return this.strTimeStamp.get();}       // Returns the properties value                                              
     private final void setTimeStamp(String timeStamp){
         try{
            this.setScanYear(Integer.parseInt(timeStamp.substring(6, 10))); 
            this.setScanMonth(Integer.parseInt(timeStamp.substring(0, 2)));
            this.setScanDay(Integer.parseInt(timeStamp.substring(3, 5)));
            this.setScanHour(Integer.parseInt(timeStamp.substring(11, 13))); 
            this.setScanMinute(Integer.parseInt(timeStamp.substring(14, 16)));
            this.strTimeStamp = new SimpleStringProperty(String.format("%02d",this.getScanMonth())+
                    "/"+String.format("%02d",this.getScanDay())+"/"+String.format("%04d",this.getScanYear())+" "+
                    String.format("%02d",this.getScanHour())+":"+String.format("%02d",this.getScanMinute()));              
         }catch(NullPointerException e){
             JOptionPane.showMessageDialog(null,  
                     "Invalid Date Formate:\n Needed Format: \"MM/DD/YYYY HH:MM\"\n Found: "+
                     timeStamp+"\n"+e.getMessage(),"Null Pointer Exception", JOptionPane.ERROR_MESSAGE);
         }//End Try/Catch
     }                                                                         // Sets the properties value
     public StringProperty strTimeStampProperty(){return this.strTimeStamp;}   // Returns the property itself

     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for RFID
     public String getRfid(){return this.RFID.get();}                          // Returns the properties value                                              
     public void setRfid(String str){this.RFID.set(str);}                      // Sets the properties value which should be invalid
     public StringProperty rfidProperty(){return this.RFID;}                   // Returns the property itself
     
     //Getters and Setters for campus
     public String getCampus(){return this.campus.get();}                      // Returns the properties value                                              
     private void setCampus(String str){
         this.campus = new SimpleStringProperty(str);}                         // Sets the properties value
     public StringProperty campusProperty(){return this.campus;}               // Returns the property itself
     
     //Getters and Setters for bldg
     public String getBldg(){return this.bldg.get();}                          // Returns the properties value                                              
     private void setBldg(String str){
         this.bldg = new SimpleStringProperty(str);}                           // Sets the properties value
     public StringProperty bldgProperty(){return this.bldg;}                   // Returns the property itself
     
     //Getters and Setters for rm
     public String getRm(){return this.rm.get();}                              // Returns the properties value                                              
     private void setRm(String str){
         this.rm = new SimpleStringProperty(str);}                             // Sets the properties value
     public StringProperty rmProperty(){return this.rm;}                       // Returns the property itself
     
     //Getters and Setters for Location
     public String getLocation(){return this.location.get();}                  // Returns the properties value                                              
     public void setLocation(String str){
         try{
            this.setCampus(str.substring(0, 3));                               // Parse str to set the campus value
            this.setBldg(str.substring(4, 7));                                 // Parse str to set the bldg value
            this.setRm(str.substring(8, 11));                                  // Parse str to set the rm value
            this.location= new SimpleStringProperty(this.getCampus()+"/"+
                 this.getBldg()+"/"+this.getRm());                             // Set the location value
         }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,
                     "Invalid Location Format:\n Needed Format: \"SSS/SSS/SSS\"\n Found: "+
                     str+"\n"+e.getMessage(),  "Null Pointer Exception", JOptionPane.ERROR_MESSAGE);
     }}
     public StringProperty locationProperty(){return this.location;}           // Returns the property itself
     
     //Getters and Setters for lastScanedBy
     public String getLastScanedBy(){return this.lastScanedBy.get();}          // Returns the properties value                                              
     private void setLastScanedBy(String str){
         this.lastScanedBy = new SimpleStringProperty(str);}                   // Sets the properties value
     public StringProperty lastScanedByProperty(){return this.lastScanedBy;}   // Returns the property itself
     
     //Getters and Setters for Comments
     public String getComments(){return this.comments.get();}                  // Returns the properties value                                              
     public void setComments(String str){
         this.comments = new SimpleStringProperty(str);}                       // Sets the properties value
     public StringProperty commentsProperty(){return this.comments;}           // Returns the property itself
     
     //Getters and Setters for purchaseOrder
     public String getPurchaseOrder(){return this.purchaseOrder.get();}        // Returns the properties value                                              
     public void setPurchaseOrder(String str){
         this.purchaseOrder = new SimpleStringProperty(str);}                  // Sets the properties value
     public StringProperty purchaseOrderProperty(){return this.purchaseOrder;} // Returns the property itself
     
     //Getters and Setters for serviceTag
     public String getServiceTag(){return this.serviceTag.get();}              // Returns the properties value                                              
     public void setServiceTag(String str){
         this.serviceTag = new SimpleStringProperty(str);}                     // Sets the properties value
     public StringProperty serviceTagProperty(){return this.serviceTag;}       // Returns the property itself
     
     //INT SETTERS/GETTERS*******************************************************************************
     //Getters and Setters for scanYear
     public int getScanYear(){return this.scanYear.get();}                     // Returns the properties value                                              
     private void setScanYear(int x){
         this.scanYear = new SimpleIntegerProperty(x);}                        // Sets the properties value
     public IntegerProperty scanYearProperty(){return this.scanYear;}          // Returns the property itself
     
     //Getters and Setters for scanMonth
     public int getScanMonth(){return this.scanMonth.get();}                   // Returns the properties value                                              
     private void setScanMonth(int x){
         this.scanMonth = new SimpleIntegerProperty(x);}                       // Sets the properties value
     public IntegerProperty scanMonthProperty(){return this.scanMonth;}        // Returns the property itself
     
     //Getters and Setters for scanDay
     public int getScanDay(){return this.scanDay.get();}                       // Returns the properties value                                              
     private void setScanDay(int x){
         this.scanDay = new SimpleIntegerProperty(x);}                         // Sets the properties value
     public IntegerProperty scanDayProperty(){return this.scanDay;}            // Returns the property itself
     
     //Getters and Setters for scanHour
     public int getScanHour(){return this.scanHour.get();}                     // Returns the properties value                                              
     private void setScanHour(int x){
         this.scanHour = new SimpleIntegerProperty(x);}                        // Sets the properties value
     public IntegerProperty scanHourProperty(){return this.scanHour;}          // Returns the property itself
     
     //Getters and Setters for scanHour
     public int getScanMinute(){return this.scanMinute.get();}                 // Returns the properties value                                              
     private void setScanMinute(int x){
         this.scanMinute = new SimpleIntegerProperty(x);}                      // Sets the properties value
     public IntegerProperty scanMinuteProperty(){return this.scanMinute;}      // Returns the property itself
     
     //Main Method for testing
     public static void main(String[] args)
     {    
          //Initialization Test with required values
          RecordObj record = new RecordObj("111111111", "hadleyps       ", "10/27/2014 19:20", "Old!");
          //Pass in optional values
          record.setLocation("MUH/MOS/100");
          record.setPurchaseOrder("kjhkjhhki4321");
          record.setServiceTag("KY678UJ98");
          
          
          //Print column header
          System.out.print("Status     | RFID#     |  Campus |  BLDG | RM  | Comments |   Last Scanned on   | Last Scanned by | PurchaseOrder | ServiceTag# |\n");

          //Test getters
          System.out.print("Retrieving | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //Test setters
          record.setRfid("222222222"); 
          record.setLocation("MUM/JST/T20");
          record.setLastScanedBy("YourMom        "); 
          record.setComments("New!"); 
          record.setPurchaseOrder("kjhkjhhki1234");
          record.setServiceTag("kjhgfdsaw");
          record.setTimeStamp("11/02/2014 10:25");
          
          //Test Getters again to validate setters
          System.out.print("Updated To | "+record.getRfid()+" |   "+record.getCampus()+"   |  "+record.getBldg()+"  | "+record.getRm()+" |   "+
                  record.getComments()+"   |   "+record.getTimeStamp()+"    | "+record.getLastScanedBy()+" | "+record.getPurchaseOrder()+" | "+record.getServiceTag()+"\n");
          
          //test getMinute, getHour, getDay, getMonth, and getYear
          System.out.print("Individual time settings = "+record.getScanYear()+"/"+record.getScanMonth()+"/"+record.getScanDay()+" at "+record.getScanHour()+":"+record.getScanMinute()+"\n");
          //test getLocation
          System.out.print("Location: "+record.getLocation()+"\n");
           
     }//end Main Method   
}//End Class


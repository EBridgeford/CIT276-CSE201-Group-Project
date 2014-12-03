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
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.swing.*;


/**
 * This Java bean Object holds values for a device then provides getters and 
 * setters for each value
 * @author Bladelaven
 */
public class RecordObj{
     // These are variable Values wrapped up in a wrapper which adds some extra functionality
     private StringProperty RFID, campus, bldg, lastScanedBy, comments, purchaseOrder, serviceTag, strTimeStamp, rm, location; 
     private IntegerProperty  scanYear, scanDay, scanMonth, scanHour, scanMinute;
     
     //Initializes the Obj with the values passed in

    /**
     * This method sets all of the initial values for the object
     * @param strRFID
     * @param strLastScanedBy
     * @param timeStamp
     * @param comments
     */
         public RecordObj(String strRFID,  String strLastScanedBy, String timeStamp, String comments){
          this.RFID = new SimpleStringProperty(strRFID);
          this.lastScanedBy = new SimpleStringProperty(strLastScanedBy);
          this.setTimeStamp(timeStamp);
          this.setComments(comments);
          this.setLocation("___/___/___");
          this.setPurchaseOrder("_");
          this.setServiceTag("_");
          
     }//END Initialization 

    /**
     * This method accepts a timeStamp as a string then parses the timeStamp into
     * individual values which are set to the setters for Year, month, day, hour
     * and minute. Then it sets a standardized value for a string value of timeStamp.
     * @return String
     */
     public final String getTimeStamp(){return this.strTimeStamp.get();}                                                   
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
     }                                                                      

    /**
     * This method returns a string value for timeStamp.
     * @return StringProperty
     */
    public StringProperty strTimeStampProperty(){return this.strTimeStamp;}  

    /**
     *This method returns a string value for RFID.
     * @return String
     */
    public String getRfid(){return this.RFID.get();}                                                          

    /**
     * This method sets the RFID value to the value of the passed in string.
     * @param str
     */
    public void setRfid(String str){this.RFID.set(str);}   

    /**
     * This method returns the simpleStringProperty value of the RFID.
     * @return StringProperty 
     */
    public StringProperty rfidProperty(){return this.RFID;}                  
     
    /**
     * This method returns the string value of campus.
     * @return String
     */
     public String getCampus(){return this.campus.get();}                                                                
     public void setCampus(String str){this.campus = new SimpleStringProperty(str);}                         

    /**
     * This method returns a simpleStringProperty for campus
     * @return StringProperty 
     */
    public StringProperty campusProperty(){return this.campus;}           

    /**
     * This method returns the string value of the building location.
     * @return String
     */
     public String getBldg(){return this.bldg.get();}                                                                   
     public void setBldg(String str){this.bldg = new SimpleStringProperty(str);}                        

    /**
     * This method returns the simpleStringProperty value for the building location.
     * @return StringProperty 
     */
    public StringProperty bldgProperty(){return this.bldg;}                   

    /**
     * This method returns the string value of the room location.
     * @return String
     */
     public String getRm(){return this.rm.get();}                                                                     
     public void setRm(String str){this.rm = new SimpleStringProperty(str);}                            

    /**
     * This method returns the SimpleStringProperty value of the room location.
     * @return StringProperty 
     */
    public StringProperty rmProperty(){return this.rm;}                    
     
    /**
     * This method returns the string value of the entire location
     * @return String
     */
    public String getLocation(){return this.location.get();}                                                  

    /**
     * This method accepts a string in the format HAM/MOS/300 and then parses the
     * string into values for campus, BLDG, and room and then calls setters for 
     * each then saves a string value called location in a standardized format.
     * @param str
     */
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

    /**
     * This method returns a SimpleStringProperty representing the location.
     * @return StringProperty 
     */
    public StringProperty locationProperty(){return this.location;}          
     

    /**
     * This method returns a string value representing the lastScannedBy value.
     * @return String
     */
    public String getLastScanedBy(){return this.lastScanedBy.get();}                                                      
    private void setLastScanedBy(String str){this.lastScanedBy = new SimpleStringProperty(str);}               

    /**
     * This method returns a SimpleStringProperty for the lastScannedBy value.
     * @return StringProperty 
     */
    public StringProperty lastScanedByProperty(){return this.lastScanedBy;}   

    /**
     * This method returns the string value of the comments value.
     * @return String
     */
         public String getComments(){return this.comments.get();}                                                            

    /**
     * This method sets the string value of comment
     * @param str
     */
    public void setComments(String str){this.comments = new SimpleStringProperty(str);}                      

    /**
     * This method returns a SimpleStringProperty for comments
     * @return StringProperty 
     */
    public StringProperty commentsProperty(){return this.comments;}           

    /**
     * This method returns a string value representing the PO
     * @return String
     */
    public String getPurchaseOrder(){return this.purchaseOrder.get();}                                                

    /**
     * This method sets the PO to str.
     * @param str
     */
    public void setPurchaseOrder(String str){this.purchaseOrder = new SimpleStringProperty(str);}          

    /**
     * This method returns a SimpleStringValue for the PO value
     * @return StringProperty 
     */
    public StringProperty purchaseOrderProperty(){return this.purchaseOrder;}

    /**
     * This method returns a string value for the serviceTag value.
     * @return String
     */
    public String getServiceTag(){return this.serviceTag.get();}                                                       

    /**
     * This method sets the servicetag value to str.
     * @param str
     */
    public void setServiceTag(String str){this.serviceTag = new SimpleStringProperty(str);}                 

    /**
     * This method returns a SimpleStringProperty value for the servicetag.
     * @return StringProperty 
     */
    public StringProperty serviceTagProperty(){return this.serviceTag;} 
    
    /**
     * This method returns an int for year.
     * @return int
     */
     public int getScanYear(){return this.scanYear.get();}                                                                
     private void setScanYear(int x){this.scanYear = new SimpleIntegerProperty(x);}               

    /**
     * This method returns a simpleIntegerProperty for year.
     * @return simpleIntegerProperty
     */
    public IntegerProperty scanYearProperty(){return this.scanYear;}      

    /**
     * This method returns an int for month.
     * @return int
     */
     public int getScanMonth(){return this.scanMonth.get();}                                                         
     private void setScanMonth(int x){this.scanMonth = new SimpleIntegerProperty(x);}                     

    /**
     * This method returns a simpleIntegerProperty for month.
     * @return simpleIntegerProperty
     */
    public IntegerProperty scanMonthProperty(){return this.scanMonth;}      

    /**
     * This method returns an int value for day
     * @return int
     */
     public int getScanDay(){return this.scanDay.get();}                                                                   
     private void setScanDay(int x){this.scanDay = new SimpleIntegerProperty(x);}                        

    /**
     * This method returns the simpleIntegerProperty for day
     * @return simpleIntegerProperty
     */
    public IntegerProperty scanDayProperty(){return this.scanDay;}         
    
    /**
     * This method returns an int value for hour.
     * @return int
     */
     public int getScanHour(){return this.scanHour.get();}                                                                 
     private void setScanHour(int x){ this.scanHour = new SimpleIntegerProperty(x);}                      

    /**
     * this method returns a simpleIntegerProperty for hour
     * @return simpleIntegerProperty
     */
    public IntegerProperty scanHourProperty(){return this.scanHour;}          

    /**
     * This method returns an int value for minute
     * @return int
     */
     public int getScanMinute(){return this.scanMinute.get();}                                                            
     private void setScanMinute(int x){this.scanMinute = new SimpleIntegerProperty(x);}                    

    /**
     * This method returns a simpleIntegerProperty for minute
     * @return simpleIntegerProperty
     */
    public IntegerProperty scanMinuteProperty(){return this.scanMinute;}    
     

    /**
     * used for testing
     * @param args
     */
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


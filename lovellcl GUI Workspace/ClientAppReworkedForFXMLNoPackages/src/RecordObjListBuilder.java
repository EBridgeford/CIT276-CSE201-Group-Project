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
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * This object creates an observable list of RecordObjs and then manages that list
 * @author Patrick Scott Hadley
 */
public class RecordObjListBuilder{
    private RecordObj rec;                                                     //Initiate the RecordObj
    private List<RecordObj> recordList;                                        //Initiate a list to hold all recordObjs
    ObservableList<RecordObj> records;                                         //Initiate an Observable records List
    
    /**
     * This method constructs the RecordObjListBuilder by instantiating global 
     * variables.
     */
   public RecordObjListBuilder(){
       recordList = new ArrayList<RecordObj>();                                //create a new list of RecordObjs
       records = FXCollections.observableList(recordList);                     //Make the recordList Observable
   }
   
   /**
    * This method adds a record to the list of records
    * @param RFID
    * @param purchaseOrder
    * @param serviceTag
    * @param lastScanedBy
    * @param strTimeStamp
    * @param comments
    * @param rm
    * @param bldg
    * @param campus 
    */
   public void addRecord(String RFID, String purchaseOrder, String serviceTag, String lastScanedBy, String strTimeStamp, String comments, String rm, String bldg, String campus){
       this.rec = new RecordObj(RFID, lastScanedBy, strTimeStamp, comments);
       this.rec.setPurchaseOrder(purchaseOrder);
       this.rec.setServiceTag(serviceTag);
       this.rec.setLocation(campus.substring(0,3)+"/"+bldg.substring(0,3)+
               "/"+rm.substring(0,3));
       this.recordList.add(rec);
   }
   
   /**
    * This method accepts the current and new values of a record and then finds 
    * the existing record and then updates that record and then returns the new 
    * observable list.
    * @param currentRFID
    * @param currentPO
    * @param currentServiceTag
    * @param currentLastScanedBy
    * @param currentTimeStamp
    * @param currentComments
    * @param currentLocation
    * @param newRFID
    * @param newPO
    * @param newServiceTag
    * @param newLastScanedBy
    * @param newTimeStamp
    * @param newComments
    * @param newLocation 
    * @return ObservableList of RecordObjs
    */
   public ObservableList updateRecord(String currentRFID, String currentPO, String currentServiceTag, String currentLastScanedBy, String currentTimeStamp, String currentComments, String currentLocation,
           String newRFID, String newPO, String newServiceTag, String newLastScanedBy, String newTimeStamp, String newComments, String newLocation){
       
        for(RecordObj record : recordList){                                        //Iterate through the list to find the recordObj to update
            if(currentRFID.equalsIgnoreCase(record.getRfid())){
                if(currentPO.equalsIgnoreCase(record.getPurchaseOrder())){
                    if(currentServiceTag.equalsIgnoreCase(record.getServiceTag())){
                        if(currentLastScanedBy.equalsIgnoreCase(record.getLastScanedBy())){
                            if(currentTimeStamp.equalsIgnoreCase(record.getTimeStamp())){
                                if(currentComments.equalsIgnoreCase(record.getComments())){
                                    if(currentLocation.equalsIgnoreCase(record.getLocation())){
                                        rec.setRfid(newRFID);
                                        rec.setPurchaseOrder(newPO);
                                        rec.setServiceTag(newServiceTag);
                                        rec.setComments(newComments);
                                        rec.setLocation(newLocation);
                                        System.out.print(" Found the record to update! \n Updating!! \n\n");
                                        return this.records;
                                    }else
                                        System.out.print("\n "+record.getLocation()+" Does not match "+ newLocation+"\n");
                                }else
                                    System.out.print("\n "+record.getComments()+" Does not match "+newComments+"\n");
                            }else
                                System.out.print("\n "+record.getTimeStamp()+" Does not match "+newTimeStamp+"\n");
                        }else
                            System.out.print("\n "+record.getLastScanedBy()+" Does not match "+newLastScanedBy+"\n");
                    }else
                        System.out.print("\n "+record.getServiceTag()+" Does not match "+currentServiceTag+"\n");
                }else
                    System.out.print("\n "+record.getPurchaseOrder()+" Does not match "+newPO+"\n");
            }else
                System.out.print("\n "+record.getRfid()+" Does not match "+newRFID+"\n");
        }//END FOR
       
       return this.records;
   }
   
   /**
     * For testing purposes This method prints a list of all records in the observable list to the console
     */
    public void printObservableLists(){
        System.out.print(" Found the following table: \n");
        int x = 1;
        System.out.print(" Row |      RFID#       | PurchaseOrder | ServiceTag# |  Location   | Last Scanned on  | Last Scanned by  | Comments  \n");
        for(RecordObj rec : records){
            System.out.print(String.format("%4d",x)+" | "+String.format("%16s", rec.getRfid())+" | "+String.format("%13s",rec.getPurchaseOrder())+" | "+
                    String.format("%11s",rec.getServiceTag())+" | "+String.format("%11s",rec.getLocation())+" | "+String.format("%16s",rec.getTimeStamp())+" | "+
                    String.format("%16s",rec.getLastScanedBy())+" | "+rec.getComments()+"\n");
            x++;
        }
        System.out.print("\n");
    }
   
   /**
    * This method returns an observable list
    * @return ObservableList
    */
   public ObservableList getRecords(){return this.records;}

    /**
     * This method tests basic functionality of each method
     * The testing file is located in the same folder as this object and is called testData
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RecordObjListBuilder list = new RecordObjListBuilder();
        List<RecordObj> recordList = new ArrayList<RecordObj>(); 
        ObservableList<RecordObj> obList = FXCollections.observableList(recordList);       
        
        list.addRecord("RFID_G1234567890", "PO_1234567890", "ST_12345678", "Hadleyps", "11/17/2014 19:51", "These are my comments!!!", "300", "MOS", "HAM");
        list.printObservableLists();
        
        obList = list.getRecords();
        
        for(RecordObj rec : obList){
            obList = list.updateRecord(rec.getRfid(), rec.getPurchaseOrder(), rec.getServiceTag(), rec.getLastScanedBy(), rec.getTimeStamp(), rec.getComments(), rec.getLocation(),
           "RFID_S0987654321", "PO_0987654321", "ST_87654321", "Bilbo Baggins", "11/17/2014 20:12", "New Comment", "HAM/MOS/500");
        }
        list.printObservableLists();
        
        list.addRecord("RFID_", "PO_", "ST_", "you", "11/17/2014 19:51", "comment", "300", "MOS", "HAM");
        
        list.updateRecord("RFID_", "PO_", "ST_", "you", "11/17/2014 19:51", "comment", "HAM/MOS/300",
                "RFID_G1234567890", "PO_1234567890", "ST_12345678", "Hadleyps", "11/17/2014 19:51", "These are my comments!!!", "HAM/MOS/500");
        list.printObservableLists();
        
    }
    
}


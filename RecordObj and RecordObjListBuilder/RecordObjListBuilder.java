import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * This object creates an observable list of RecordObjs and then manages that list
 * @author Patrick Scott Hadley
 */
public class RecordObjListBuilder{
    private DBConnection db;
    private RecordObj rec;                                                     //Initiate the RecordObj
    private List<RecordObj> recordList;                                        //Initiate a list to hold all recordObjs
    ObservableList<RecordObj> records;                                         //Initiate an Observable records List
    
    /**
     * This method constructs the RecordObjListBuilder by instantiating global 
     * variables.
     * @param 
     */
   public RecordObjListBuilder(){
       db = new DBConnection();
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
    * This method will call clearList() to start a new list of RecordObjs then
    * forward the passed in search parameters to the dataBaseInteractionsObj so 
    * that as results are returned the dataBaseInteractionsObj will call 
    * addRecord() to populate an observable list of RecordObjs, that list is 
    * then returned. Non specified search parameters should be sent in as "*".
    * 
    * @param strCampus
    * @param strBuilding
    * @param strRoom
    * @param strPO
    * @return ObservableList of RecordObjs
    */
   public ObservableList search(String strCampus, String strBuilding, String strRoom, String strPO){
       this.clearList();
       this.db.generalQuery(strCampus, strBuilding, strRoom, strPO);
       return this.records;
   }
   
   /**
    * This method accepts the current and new values of a record and then passes 
    * those values to the DBConnection obj which communicates the values to the 
    * DB, the database finds the existing record and then updates that record
    * The method then calls updateInList() in order to update the RecordObj and
    * then returns the new observable list.
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
       
       this.db.updateRecord(currentRFID, currentPO, currentServiceTag, currentLastScanedBy, currentTimeStamp, currentComments, /*room*/currentLocation.substring(8, 11), /*bldg*/currentLocation.substring(4, 7), /*campus*/currentLocation.substring(0, 3),
               newRFID, newPO, newServiceTag, newLastScanedBy, newTimeStamp, newComments, /*room*/newLocation.substring(8, 11), /*bldg*/newLocation.substring(4, 7), /*campus*/newLocation.substring(0, 3));
       
       this.updateInList(currentRFID, currentPO, currentServiceTag, currentLastScanedBy, currentTimeStamp, currentComments, currentLocation,
               newRFID, newPO, newServiceTag, newComments, newLocation);
       
       return this.records;
   }
   
   //NOTE: This method may not be needed
   /**
    * This method will search for the existing RecordObj and update the values 
    * to the new values so they are displayed in the observable list.
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
    * @param newComments
    * @param newLocation 
    */
   private void updateInList(String currentRFID, String currentPO, String currentServiceTag, String currentLastScanedBy, String currentTimeStamp, String currentComments, String currentLocation,
           String newRFID, String newPO, String newServiceTag, String newComments, String newLocation){
       
       for(RecordObj rec : recordList){                                        //Iterate through the list to find the recordObj to update
            if(currentRFID.equalsIgnoreCase(rec.getCampus())){
                if(currentPO.equalsIgnoreCase(rec.getPurchaseOrder())){
                    if(currentServiceTag.equalsIgnoreCase(rec.getServiceTag())){
                        if(currentLastScanedBy.equalsIgnoreCase(rec.getLastScanedBy())){
                            if(currentTimeStamp.equalsIgnoreCase(rec.getTimeStamp())){
                                if(currentComments.equalsIgnoreCase(rec.getComments())){
                                    if(currentLocation.equalsIgnoreCase(rec.getLocation())){
                                        rec.setRfid(newRFID);
                                        rec.setPurchaseOrder(newPO);
                                        rec.setServiceTag(newServiceTag);
                                        rec.setComments(newComments);
                                        rec.setLocation(newLocation);
                                        return;
        }}}}}}}}//END FOR
   }
   
   /**
    * This Method clears the current queried list of records.
    * @param 
    */
   private void clearList(){
       this.recordList.clear();
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }
    
}

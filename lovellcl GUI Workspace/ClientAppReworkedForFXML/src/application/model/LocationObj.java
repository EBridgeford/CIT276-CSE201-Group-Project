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
package application.model;
import javafx.beans.property.StringProperty;

/**
 * This object creates a room, building, and campus object and retains the relationship between them
 * while providing return methods for each objects simpleStringProperties, String values, and the object
 * it's self. This object also calls setters for each object.
 * @author Patrick Scott Hadley 
 */
public class LocationObj{
   private RoomObj room;
   private BLDGObj bldg;
   private CampusObj campus;

    /**
     * This method instantiates the room, BLDG, and campus objects.
     * @param camp
     * @param bld
     * @param rm 
     */
    public LocationObj(String camp, String bld, String rm){
        room = new RoomObj(rm);
        bldg = new BLDGObj(bld);
        campus = new CampusObj(camp);
    }//END INITIALIZATION
    
     /**
      * This method returns the campuses string value.
      * @return String
      */
     public final String getCampus(){return this.campus.getCampus();}          
     
     /**
      * This method calls the campus object's set method.
      * @param str 
      */
     public final void setCampus(String str){this.campus.setCampus(str);}      
     
     /**
      * This method returns the campus object's SimpleStringProperty value.
      * @return SimpleStringProperty
      */
     public StringProperty campusProperty(){return this.campus.campusProperty();}
     
     /**
      * This method returns the campus object
      * @return CampusObj
      */
     public CampusObj campusObj(){return this.campus;}                         
     
     /**
      * This method returns the string value of the BLDG object.
      * @return String
      */
     public String getBLDG(){return this.bldg.getBld();}                 
     
     /**
      * This method sets the BLDG object's string value.
      * @param str
      */
     public void setBLDG(String str){this.bldg.setBld(str);}             
     
     /**
      * This method returns the BLDG's property value.
      * @return SimpleStringProperty
      */
     public StringProperty bldgProperty(){return this.bldg.bldProperty();}     
     
     /**
      * This method returns the BLDGObj.
      * @return BLDGObj
      */
     public BLDGObj bldgObj(){return this.bldg;}                               
     
     /**
      * this method returns the string value of the RoomObj.
      * @return String
      */
     public String getRM(){return this.room.getRm();}                    
     
     /**
      * This method sets the string value of the RoomObj.
      * @param str
      */
     public void setRM(String str){this.room.setRm(str);}                
     
     /**
      * This method returns the RoomObj's property value.
      * @return SimpleStringProperty
      */
     public StringProperty rmProperty(){return this.room.rmProperty();}        
     
     /**
      * This method returns the RoomObj.
      * @return RoomObj
      */
     public RoomObj rmObj(){return this.room;}                                 
     
    /**
     * This method is used for testing.
     * @param args 
     */
    public static void main(String[] args) {
        
        //Initialization test
        LocationObj room = new LocationObj("MUH", "MOS", "300");
        
        //Test getters
        System.out.print("Location : "+room.getCampus()+" | "+room.getBLDG()+" | "+room.getRM()+"\n");
        
        //Test setters
        room.setCampus("MUM");
        room.setBLDG("JST");
        room.setRM("100");
        
        //Test getters again
        System.out.print("Location : "+room.getCampus()+" | "+room.getBLDG()+" | "+room.getRM()+"\n");
        
    }//END MAIN
}//END CLASS

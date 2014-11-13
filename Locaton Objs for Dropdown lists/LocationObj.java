
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bladelaven
 */
public class LocationObj{
   private RoomObj room;
   private BLDGObj bldg;
   private CampusObj campus;

    
    public LocationObj(String camp, String bld, String rm){
        room = new RoomObj(rm);
        bldg = new BLDGObj(bld);
        campus = new CampusObj(camp);
    }//END INITIALIZATION
    
     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for Campus
     public final String getCampus(){return this.campus.getCampus();}          // Returns the properties value                                              
     public final void setCampus(String str){this.campus.setCampus(str);}      // Sets the properties value
     public StringProperty campusProperty(){return this.campus.campusProperty();}// Returns the property itself
     public CampusObj campusObj(){return this.campus;}                         // Returns the object itself
     
     //Getters and Setters for BLDG
     public final String getBLDG(){return this.bldg.getBld();}                 // Returns the properties value                                              
     public final void setBLDG(String str){this.bldg.setBld(str);}             // Sets the properties value
     public StringProperty bldgProperty(){return this.bldg.bldProperty();}     // Returns the property itself
     public BLDGObj bldgObj(){return this.bldg;}                               // Returns the object itself
     
     //Getters and Setters for Room
     public final String getRM(){return this.room.getRm();}                    // Returns the properties value                                              
     public final void setRM(String str){this.room.setRm(str);}                // Sets the properties value
     public StringProperty rmProperty(){return this.room.rmProperty();}        // Returns the property itself
     public RoomObj rmObj(){return this.room;}                                 // Returns the object itself

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
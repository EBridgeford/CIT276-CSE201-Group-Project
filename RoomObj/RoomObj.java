/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.tracker.pro;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Bladelaven
 */
public class RoomObj{
   
    private StringProperty strCampus, strBld; 
     private IntegerProperty intRm;
    
    public RoomObj(String campus, String bld, int rm){
        this.strCampus = new SimpleStringProperty(campus);                  //Initialize the Campus Name
        this.strBld = new SimpleStringProperty(bld);                        //Initialize the Building Name
        this.intRm = new SimpleIntegerProperty(rm);                         //Initialize the room #
    }//END INITIALIZATION
    
     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for strCampus
     public final String getCampus(){return this.strCampus.get();}          // Returns the properties value                                              
     public final void setCampus(String str){this.strCampus.set(str);}      // Sets the properties value
     public StringProperty campusProperty(){return this.strCampus;}         // Returns the property itself
     
     //Getters and Setters for strBld
     public final String getBld(){return this.strBld.get();}                // Returns the properties value                                              
     public final void setBld(String str){this.strBld.set(str);}            // Sets the properties value
     public StringProperty bldProperty(){return this.strBld;}               // Returns the property itself
     
     //INT SETTERS/GETTERS*******************************************************************************
     //Getters and Setters for rm
     public final int getRm(){return this.intRm.get();}                           // Returns the properties value                                              
     public final void setRm(int x){this.intRm.set(x);}                           // Sets the properties value
     public IntegerProperty rmProperty(){return this.intRm;}                      // Returns the property itself

    public static void main(String[] args) {
        
        //Initialization test
        RoomObj room = new RoomObj("MUH", "MOS", 300);
        
        //Test getters
        System.out.print("Location : "+room.getCampus()+" | "+room.getBld()+" | "+room.getRm()+"\n");
        
        //Test setters
        room.setCampus("MUM");
        room.setBld("JST");
        room.setRm(100);
        
        //Test getters again
        System.out.print("Location : "+room.getCampus()+" | "+room.getBld()+" | "+room.getRm()+"\n");
        
    }//END MAIN
}//END CLASS

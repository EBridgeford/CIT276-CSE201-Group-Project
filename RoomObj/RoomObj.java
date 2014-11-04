import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Bladelaven
 */
public class RoomObj{
   
    private StringProperty strRm;
    
    public RoomObj(String rm){
        this.strRm = new SimpleStringProperty(rm);                         //Initialize the room #
    }//END INITIALIZATION
    
     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for rm
     public final String getRm(){return this.strRm.get();}                     // Returns the properties value                                              
     public final void setRm(String str){this.strRm.set(str);}                 // Sets the properties value
     public StringProperty rmProperty(){return this.strRm;}                    // Returns the property itself

    public static void main(String[] args) {
        
        //Initialization test
        RoomObj room = new RoomObj("300");
        
        //Test getters
        System.out.print("Location : "+room.getRm()+"\n");
        
        //Test setters
        room.setRm("100");
        
        //Test getters again
        System.out.print("Location : "+room.getRm()+"\n");
        
    }//END MAIN
}//END CLASS
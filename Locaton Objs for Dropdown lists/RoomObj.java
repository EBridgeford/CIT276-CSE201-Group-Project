import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This object holds the value of the room
 * @author Patrick Scott Hadley
 */
public class RoomObj{
   
    private StringProperty strRm;
    
    /**
     * This method Instantiates the room Object
     * @param rm 
     */
    public RoomObj(String rm){
        this.strRm = new SimpleStringProperty(rm);                             //Initialize the room #
    }//END INITIALIZATION

     /**
      * This method returns the string value of the room object.
      * @param
      * @return string value of room
      */
     public final String getRm(){return this.strRm.get();}                     // Returns the properties value
     
     /**
     * This method sets the string value of the property to the passed in string's value.
     * @param str 
     */
     public final void setRm(String str){this.strRm.set(str);}                 // Sets the properties value
     
     /**
     * This method returns the room property
     * @param 
     * @return simpleStringProperty
     */
     public StringProperty rmProperty(){return this.strRm;}                    // Returns the property itself

     /**
      * This method is used for testing.
      * @param args 
      */
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
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Bladelaven
 */
public class BLDGObj{
   
    private StringProperty strBld; 
    
    public BLDGObj(String bld){
        this.strBld = new SimpleStringProperty(bld);                        //Initialize the Building Name
    }//END INITIALIZATION
    
     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for strBld
     public final String getBld(){return this.strBld.get();}                // Returns the properties value                                              
     public final void setBld(String str){this.strBld.set(str);}            // Sets the properties value
     public StringProperty bldProperty(){return this.strBld;}               // Returns the property itself

    public static void main(String[] args) {
        
        //Initialization test
        BLDGObj room = new BLDGObj("MOS");
        
        //Test getters
        System.out.print("Location : "+room.getBld()+"\n");
        
        //Test setters
        room.setBld("JST");
        
        //Test getters again
        System.out.print("Location : "+room.getBld()+"\n");
        
    }//END MAIN
}//END CLASS
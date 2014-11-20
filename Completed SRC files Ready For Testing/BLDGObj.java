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

/**
 * This object holds the value of the building
 * @author Patrick Scott Hadley
 */
public class BLDGObj{
   
    private StringProperty strBld; 
    
    /**
     * This method initializes the BLDG object.
     * @param bld
     */
    public BLDGObj(String bld){
        this.strBld = new SimpleStringProperty(bld);                        
    }//END INITIALIZATION
    
     /**
      * This method returns the string value of the BLDG object
      * @return String
      */
     public final String getBld(){return this.strBld.get();}       
     
     /**
      * This method sets the string value of the BLDG object
      * @param str
      */
     public final void setBld(String str){this.strBld.set(str);}     
     
     /**
      * This method returns the property value of the BLDG object.
      * @return SimpleStringProperty
      */
     public StringProperty bldProperty(){return this.strBld;}               

     /**
      * This method is used for testing.
      * @param args 
      */
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

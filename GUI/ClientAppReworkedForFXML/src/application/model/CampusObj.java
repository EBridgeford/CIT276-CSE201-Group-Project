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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This object holds the value of the campus
 * @author Patrick Scott Hadley
 */
public class CampusObj{
   
    private StringProperty strCampus;
    
    /**
     * This method Instantiates the campus object
     * @param campus 
     */
    public CampusObj(String campus){
        this.strCampus = new SimpleStringProperty(campus);                  
    }//END INITIALIZATION
    
     /**
      * This method returns the string value of the campus object.
      * @return String Campus
      */
     public final String getCampus(){return this.strCampus.get();}      
             
     /**
      * This method sets the campus properties string value.
     * @param str
      */
     public final void setCampus(String str){this.strCampus.set(str);}  
     
     /**
      * This method returns the campus object's property value
      * @return simpleStringProperty
      */
     public StringProperty campusProperty(){return this.strCampus;}         
     
     /**
      * This method is used for testing.
      * @param args 
      */
     public static void main(String[] args) {
        
        //Initialization test
        CampusObj room = new CampusObj("HAM");
        
        //Test getters
        System.out.print("Location : "+room.getCampus()+"\n");
        
        //Test setters
        room.setCampus("MUM");
        
        //Test getters again
        System.out.print("Location : "+room.getCampus()+"\n");
        
    }//END MAIN
}//END CLASS


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CampusObj{
   
    private StringProperty strCampus;
    
    public CampusObj(String campus){
        this.strCampus = new SimpleStringProperty(campus);                  //Initialize the Campus Name
    }//END INITIALIZATION
    
     //STRING SETTERS/GETTERS****************************************************************************
     //Getters and Setters for strCampus
     public final String getCampus(){return this.strCampus.get();}          // Returns the properties value                                              
     public final void setCampus(String str){this.strCampus.set(str);}      // Sets the properties value
     public StringProperty campusProperty(){return this.strCampus;}         // Returns the property itself
     
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
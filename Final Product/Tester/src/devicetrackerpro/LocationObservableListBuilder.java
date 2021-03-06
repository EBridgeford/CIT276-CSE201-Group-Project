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
import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

//For Testing
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This method builds a master list of LocationObjs from the Database then builds
 * observable lists of RoomObjs, BLDGObjs, and CampusObjs from the master list based
 * on selections made in the dropdown list then sorts the observable lists
 * alphabetically and returns them to the GUI.  
 * @author Patrick Scott Hadley
 */
public class LocationObservableListBuilder{
    
    private LocationObj loc;                                                   //Instantiate the LocationObj
    private List<LocationObj> allLocationsList;                                //Instantiate a masterlist to hold all Objs
    private List<RoomObj> rmList;                                              //Instantiate a list to hold available rooms
    private List<CampusObj> campusList;                                        //Instantiate a list to hold avalable campuses
    private List<BLDGObj> bldgList;                                            //Instantiate a list to hold available BLDGs
    ObservableList<RoomObj> rooms;                                             //Instantiate an Observable rooms List
    ObservableList<CampusObj> campuses;                                        //Instantiate an Observable campuses List
    ObservableList<BLDGObj> bldgs;                                             //Instantiate an Observable bldge List

    /**
     * This method Initializes the Lists to ArrayLists and then makes those lists
     * Observable, while adding the default list values for each in the allLocationsList
     */
    public LocationObservableListBuilder(){
       allLocationsList = new ArrayList<LocationObj>();                        //create an observable masterlist to hold all Objs
       campusList = new ArrayList<CampusObj>();                                //create a new observable list of campusObjs
       bldgList = new ArrayList<BLDGObj>();                                    //create a new observable list of BLDGObjs
       rmList = new ArrayList<RoomObj>();                                      //create a new observable list of roomObjs
       this.addToAllLocationsList("All", "All", "All");                        //Create the default selection
       rooms = FXCollections.observableList(rmList);                           //Make the rmList Observable
       campuses = FXCollections.observableList(campusList);                    //Make the campusList Observable
       bldgs = FXCollections.observableList(bldgList);                         //Make the bldgList Observable
    }//END Initiation procedure
    
    /**
     * This method adds each new Location to the allLocationsList when called by
     * the DBInteractionObj and ObjConstructor.
     * @param camp
     * @param bldg
     * @param rm 
     */
    public void addToAllLocationsList(String camp,String bldg, String rm){
        if(checkDuplicateMaster(camp, bldg, rm)){                              //If the location does not already exist
            loc = new LocationObj(camp, bldg, rm);                             //Create the new location Obj
            allLocationsList.add(loc);                                         //Add the location to the master list
    }}
    
    /**
     * This method returns the observable list of CampusObjs.
     * @return ObservableList
     */
    public ObservableList getCampusList(){return campuses;}
    
    /**
     * This method returns the observable list of BLDGObjs.
     * @return ObservableList
     */
    public ObservableList getBLDGList(){return bldgs;}
    
    /**
     * This method returns the observable list of RoomObjs.
     * @return ObservableList
     */
    public ObservableList getRMList(){return rooms;}
            
    /**
     * This method controls the process of Initialy filtering duplicate listings 
     * from each observable list after all values are Initially added from the 
     * database, then calls methods to sort each observable list alphabetically.
     */
    public void buildLists(){
        for(LocationObj loc : allLocationsList){                               //Cycle through the master list
          if(filterRooms(loc.getRM())){                                        //If the room does not exist in the room list
              rmList.add(loc.rmObj());                                         //Then add it to the room list
          }  
          if(filterBLDGs(loc.getBLDG())){                                      //If the building does not exist in the building list
              bldgList.add(loc.bldgObj());                                     //Then add it to the building list
          }
          if(filterCampuses(loc.getCampus())){                                 //If the campus does not exist in the campus list
              campusList.add(loc.campusObj());                                 //Then add it to the campus list
        }}
        
        //Call methods to sort each list alphabetically
        sortCampusList();
        sortBLDGList();
        sortRmList();
    }
    
    /**
     * This method will rebuild the observable lists based on the selection state
     * of the dropdowns passed in as parameters by clearing the existing observable
     * lists then filtering duplicate listings from of the lists then sorting the
     * observable lists alphabetically. 
     * @param campSelection
     * @param bldgSelection
     * @param rmSelection 
     */   
    public void reBuildLists(String campSelection, String bldgSelection, String rmSelection){
        clearLists();
        if(((campSelection.equalsIgnoreCase("All"))&&(bldgSelection.equalsIgnoreCase("All")))&&(rmSelection.equalsIgnoreCase("All"))){
            buildLists();
        }else{
            //Go through the MasterList and populate the lists with non duplicate objects
            for(LocationObj loc : allLocationsList){
                //If the campus, bldg, and room match the passed in parameters or is set to all
                if(((loc.getCampus().equalsIgnoreCase(campSelection))||(campSelection.equalsIgnoreCase("All")))&&
                        ((loc.getBLDG().equalsIgnoreCase(bldgSelection))||(bldgSelection.equalsIgnoreCase("All")))&&
                        ((loc.getRM().equalsIgnoreCase(rmSelection))||(rmSelection.equalsIgnoreCase("All")))){
                    if(filterRooms(loc.getRM())){                              //If the room does not exist in the room list
                        rmList.add(loc.rmObj());                               //Then add it to the room list
                    }
                    if(filterBLDGs(loc.getBLDG())){                            //If the building does not exist in the building list
                        bldgList.add(loc.bldgObj());                           //Then add it to the building list
                    }
                    if(filterCampuses(loc.getCampus())){                       //If the campus does not exist in the campus list
                        campusList.add(loc.campusObj());                       //Then add it to the campus list
            }}}}
        
        //Call methods to sort the lists alphabetically
        sortCampusList();
        sortBLDGList();
        sortRmList();
    }
    
    /**
     * For testing purposes This method prints lists of each observable list to the console
     */
    public void printObservableLists(){
        System.out.print("\n \n Found the following table \n");
        System.out.print(" Room:");
        for(RoomObj rm : rooms){
            System.out.print(" "+rm.getRm()+", ");
        }
        System.out.print("\n BLDG:");
        for(BLDGObj bld : bldgs){
            System.out.print(" "+bld.getBld()+", ");
        }
        System.out.print("\n Campus:");
        for(CampusObj camp : campuses){
            System.out.print(" "+camp.getCampus()+", ");
    }}
    
    /**
     * This method clears each observable list, then adds the default list values.
     */
    private void clearLists(){
        //Clear the lists
        campusList.clear();                                                    //Empty the list of campusObjs
        bldgList.clear();                                                      //Empty the list of BLDGObjs
        rmList.clear();                                                        //Empty the list of roomObjs
        
        //Add default values back to each list
        CampusObj camp = new CampusObj("All");                                 //Create a new campus Obj
        campusList.add(camp);                                                  //Then add it to the campus list
        BLDGObj bldg = new BLDGObj("All");                                     //Create a new building Obj 
        bldgList.add(bldg);                                                    //Then add it to the building list
        RoomObj rm = new RoomObj("All");                                       //Create a new room Obj 
        rmList.add(rm);                                                        //Then add it to the room list
    }
    
    /**
     * This method checks the allLocationsList to ensure a location is only entered 
     * into the list once. If the location is found, the method immediately stops 
     * running and returns false, other wise the entire allLocationsList is checked
     * and a true value is returned.
     * @param camp
     * @param bldg
     * @param rm
     * @return 
     */
    private boolean checkDuplicateMaster(String camp, String bldg, String rm){
        for(LocationObj loc : allLocationsList){                               //Iterate through the list to see is the ocation already exists
            if((camp.equalsIgnoreCase(loc.getCampus()))&&                      //If campus is the exists and
                    (bldg.equalsIgnoreCase(loc.getBLDG()))&&                   //If BLDG is the exists and
                    (rm.equalsIgnoreCase(loc.getRM()))){                       //If room is the exists
                return false;}//END IF                                         //Return false if entire location exists
        }//END FOR
        return true;                                                           //Otherwise return true
    }
    
    /**
     * This method cycles through the Room list looking for objects with values 
     * matching the passed in string, if a matching value is found the method 
     * immediately stops running and returns false, other wise the entire room 
     * List is checked and a true value is returned.
     * @param room
     * @return 
     */
    private boolean filterRooms(String room){
        for(RoomObj rm : rmList){                                              //Cycle through room list
            if(room.equalsIgnoreCase(rm.getRm())){                             //Look for duplicat entries in the list
                return false; }//END IF                                        //If found return false
        }//END FOR
        return true;                                                           //Otherwise return true if the room does not exist in the list
     }//END filterRooms
    
    /**
     * This method cycles through the campus list looking for objects with values 
     * matching the passed in string, if a matching value is found the method 
     * immediately stops running and returns false, other wise the entire campus 
     * List is checked and a true value is returned.
     * @param campus
     * @return 
     */
    private boolean filterCampuses(String campus){
        for(CampusObj camp : campusList){                                      //Cycle through the campus list
            if(campus.equalsIgnoreCase(camp.getCampus())){                     //Look for duplicat entries in the list
                return false; }//END IF                                        //If found return false
        }//END FOR
        return true;                                                           //Otherwise return true if the campus does not exist in the list
    }
    
    /**
     * This method cycles through the BLDG list looking for objects with values 
     * matching the passed in string, if a matching value is found the method 
     * immediately stops running and returns false, other wise the entire BLDG 
     * List is checked and a true value is returned.
     * @param bld
     * @return 
     */
    private boolean filterBLDGs(String bld){
        for(BLDGObj bldg : bldgList){                                          //Cycle through the building list
            if(bld.equalsIgnoreCase(bldg.getBld())){                           //Look for duplicat entries in the list
                return false; }//END IF                                        //If found return false
        }//END FOR
        return true;                                                           //Otherwise return true if the building does not exist in the list
    }
    
    /**
     * This method sorts the campusList alphabetically while leaving the 
     * default value of "All" at the top of the list.
     */
    private void sortCampusList(){
          CampusObj clone1;                                                    //declare a clone for the current indexed campus
          CampusObj clone2;                                                    //declare a clone for the next indexed campus
          int result = 0;                                                      // result starts in a neutral position
          
          for(int i = 0; i < this.campusList.size(); i++)                      //we will sort through as many times as may possibly be needed since this is a bubblesort...
          {
               int index = 1;                                                  //index starts at 1 B/C 1 is the first position we want to grab for comparison and it needs to be resert for each iteration
               for( CampusObj camp : this.campusList )                         //Start an Iterator....For Each loop
               {
                    if (index < this.campusList.size())                        //if were not destined for an indexoutofbounds exception
                    {
                         clone1 = camp;                                        //Get the current campus for comparison
                         clone2 = this.campusList.get(index);                  //Get the next campus for comparison
                         String camp1 = ((clone1.getCampus()).toUpperCase());
                         if(camp1.equalsIgnoreCase("All"))
                             camp1 = "0";
                         String camp2 = ((clone2.getCampus()).toUpperCase());  //make string variables representing campuses from both objects
                         if(camp2.equalsIgnoreCase("All"))
                             camp2 = "0";
                         
                         result = camp1.compareTo(camp2);                      //Compares values and returns an int which tells if the values compare less than (-1), equal (0), or greater than (1)
                         if (result > 0)                                       //If clone1 is greater than clone2
                         {
                              this.campusList.set((index -1), clone2);         //swap the objects 
                              this.campusList.set(index, clone1);              //swap the objects
                    }} 
                    index++;                                                   //increment index to track where we set and get List objects           
    }}}
    
    /**
     * This method sorts the BLDG List alphabetically while leaving the 
     * default value of "All" at the top of the list.
     */
    private void sortBLDGList(){
          BLDGObj clone1;                                                      //declare a clone for the current indexed bldg
          BLDGObj clone2;                                                      //declare a clone for the next indexed bldg
          int result = 0;                                                      // result starts in a neutral position
          
          for(int i = 0; i < this.bldgList.size(); i++)                        //we will sort through as many times as may possibly be needed since this is a bubblesort...
          {
               int index = 1;                                                  //index starts at 1 B/C 1 is the first position we want to grab for comparison and it needs to be resert for each iteration
               for( BLDGObj bldg : this.bldgList )                             //Start an Iterator....For Each loop
               {
                    if (index < this.bldgList.size())                          //if were not destined for an indexoutofbounds exception
                    {
                         clone1 = bldg;                                        //Get the current bldg for comparison
                         clone2 = this.bldgList.get(index);                    //Get the next bldg for comparison
                         String bldg1 = ((clone1.getBld()).toUpperCase());
                         if(bldg1.equalsIgnoreCase("All"))
                             bldg1 = "0";
                         String bldg2 = ((clone2.getBld()).toUpperCase());     //make string variables representing bldgs from both objects
                         if(bldg2.equalsIgnoreCase("All"))
                             bldg2 = "0";
                         
                         result = bldg1.compareTo(bldg2);                      //Compares values and returns an int which tells if the values compare less than (-1), equal (0), or greater than (1)
                         if (result > 0)                                       //If bldg1 is greater than bldg2
                         {
                              this.bldgList.set((index -1), clone2);           //swap the objects 
                              this.bldgList.set(index, clone1);                //swap the objects
                    }} 
                    index++;                                                   //increment index to track where we set and get List objects           
    }}}
    
    /**
     * This method sorts the room List alphabetically while leaving the 
     * default value of "All" at the top of the list.
     */
    private void sortRmList(){
          RoomObj clone1;                                                      //declare a clone for the current indexed room
          RoomObj clone2;                                                      //declare a clone for the next indexed room
          int result = 0;                                                      // result starts in a neutral position
          
          for(int i = 0; i < this.rmList.size(); i++)                          //we will sort through as many times as may possibly be needed since this is a bubblesort...
          {
               int index = 1;                                                  //index starts at 1 B/C 1 is the first position we want to grab for comparison and it needs to be resert for each iteration
               for( RoomObj rm : this.rmList )                                 //Start an Iterator....For Each loop
               {
                    if (index < this.rmList.size())                            //if were not destined for an indexoutofbounds exception
                    {
                         clone1 = rm;                                          //Get the current room for comparison
                         clone2 = this.rmList.get(index);                      //Get the next room for comparison
                         String rm1 = ((clone1.getRm()).toUpperCase());
                         if(rm1.equalsIgnoreCase("All"))
                             rm1 = "0";
                         String rm2 = ((clone2.getRm()).toUpperCase());        //make string variables representing rooms from both objects
                         if(rm2.equalsIgnoreCase("All"))
                             rm2 = "0";
                         
                         result = rm1.compareTo(rm2);                          //Compares values and returns an int which tells if the values compare less than (-1), equal (0), or greater than (1)
                         if (result > 0)                                       //If room1 is greater than room2
                         {
                              this.rmList.set((index -1), clone2);             //swap the objects 
                              this.rmList.set(index, clone1);                  //swap the objects
                    }} 
                    index++;                                                   //increment index to track where we set and get List objects           
    }}}
    
    /**
     * This method is used for testing purposes.
     * @param args 
     */
    public static void main(String[] args) {
        String filePath = ("E:\\portableapps\\Documents\\Java 271\\WordTranslator\\pirate.txt");
        File file = null;
        Scanner fileIn = null;                                                 //create a scanner object and set it to null initialy
        LocationObservableListBuilder loc = new LocationObservableListBuilder();
        
        JFileChooser chooser = new JFileChooser();                             //Create a JFileChooser object to get the file
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);                 //set the type of resource the user can choose
        chooser.setCurrentDirectory(new File(filePath));                       // Start in root directory
        int status = chooser.showOpenDialog(null);                             //Pops up an "Open File" file chooser dialog
        try{
             if(status == JFileChooser.APPROVE_OPTION){file = (chooser.getSelectedFile());}
        }catch(RuntimeException e){
             JOptionPane.showMessageDialog(null, "No File Selected", "No File Selected "+e.getMessage() ,
                     JOptionPane.PLAIN_MESSAGE);}                              //pop up a message to let the user know whats going on 

        try{fileIn = new Scanner(new FileInputStream(file));
        }catch (IOException e){
               JOptionPane.showMessageDialog(null,e.getMessage(), "Invalid file name \n"+e, JOptionPane.ERROR_MESSAGE);
        }//END Catch
        
          loc.buildLists();                                                    //Build the list after masterlist is complete
          loc.printObservableLists();                                          //Read values stored in all observable lists
          
        while (fileIn.hasNextLine()){
           String camp = fileIn.nextLine();                                    //create a variable to get the next line
           String bld = fileIn.nextLine();                                     //create a variable to get the next line
           String rm = fileIn.nextLine();                                      //create a variable to get the next line
           loc.addToAllLocationsList(camp, bld, rm);                           //create a new location object in the master object list
        }//END WHILE
          fileIn.close();
          
          loc.buildLists();                                                    //Build the list after masterlist is completed
          loc.printObservableLists();                                          //Prints values stored in all observable lists to console
          
          loc.clearLists();                                                    //Will clear the lists but not the master list
          loc.printObservableLists();                                          //Prints values stored in all observable lists to console
          
          loc.reBuildLists("All", "All", "All");                               //Test Selection of all in all fields
          loc.printObservableLists();                                          //Prints values stored in all observable lists to console
          
          loc.reBuildLists("Ham", "MOS", "300");                               //Tests selection in all drop downs
          loc.printObservableLists();                                          //Prints values stored in all observable lists to console
          
          loc.reBuildLists("all", "all", "300");                               //Tests selection in just the last drop down
          loc.printObservableLists();                                          //Prints values stored in all observable lists to console
          
          System.out.print("\n");
    }//END MAIN
}//END CLASS

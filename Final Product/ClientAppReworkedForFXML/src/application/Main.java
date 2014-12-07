package application;

import java.io.IOException;
import java.net.URL;

import application.view.RootLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**The Main class is used to launch the GUI. It loads the FXML 
 * view, and gives the controller class an instance of the stage. 
 * 
 * @author Craig Lovell 
 */
public class Main extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;
    private final URL ROOT_LAYOUT_FILE_NAME = getClass().getResource("view/RootLayout.fxml");
    
    /**The start method is called automatically to start the 
     * JavaFX application.  
     * 
     * @author Craig Lovell  
     * @param primaryStage The top level container of the 
     * JavaFX application. 
     */
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Client Application");
        
        initRootLayout();
    } // End start() method

    /**Initializes the root layout.
     * 
     * @author Craig Lovell  
     */
    public void initRootLayout() {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ROOT_LAYOUT_FILE_NAME);
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(rootLayout,screenBounds.getWidth()/1.2,screenBounds.getHeight()/1.2);
            //scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            
            RootLayout controller = loader.getController();
            controller.setMainApp(this);
        } // End try statement 
        catch (IOException e) {
            e.printStackTrace();
        } // End catch statement 
    } // End initRootLayout() method
    
    /**This method returns the stage of the currently launched 
     * JavaFX application. 
     * 
     * @author Craig Lovell
     * @return The top level container of the JavaFX application. 
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    } // End getPrimaryStage() method

    /**This method is the first method that is called in GUI 
     * application. It calls the launch method of the Application 
     * class, which eventually calls the start() method. 
     * 
     * @author Craig Lovell
     * @param args The string arguments passed in from the console. 
     */
    public static void main(String[] args) {
        launch(args);
    } // End main() method
    
} // End Main class
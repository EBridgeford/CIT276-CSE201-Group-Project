

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private final URL ROOT_LAYOUT_FILE_NAME = getClass().getResource("RootLayout.fxml");
    
    @Override
    public void start(Stage primaryStage) {
     
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Client Application");
        
        initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
        	
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ROOT_LAYOUT_FILE_NAME);
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(rootLayout,screenBounds.getWidth()/1.2,screenBounds.getHeight()/1.2);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            
            RootLayout controller = loader.getController();
            controller.setMainApp(this);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public ObservableList<RecordObj> getInitialData() {
    	
    	return FileLoader.data;
    	
    }

    public static void main(String[] args) {
    	
        launch(args);
    
    }
    
}
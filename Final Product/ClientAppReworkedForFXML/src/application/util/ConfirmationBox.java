package application.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**This class is used to build a confirmation box. 
 * 
 * @author Craig Lovell
 */
public class ConfirmationBox {
	private static Stage stage;
	private static boolean btnYesClicked;
	
	/**This method displays a confirmation dialog box. 
	 * 
	 * @author Craig Lovell 
	 * @param message The message that is displayed to the user in 
	 * the center of the confirmation dialog box. 
	 * @param title The title of the confirmation dialog box. 
	 * @param textYes The text label of the yes button. 
	 * @param textNo The text label of the no button. 
	 * @return A boolean value that is true if the yes button is 
	 * clicked, and false if the no button is clicked. 
	 */
	public static boolean show(String message, String title, String textYes, String textNo) {
		btnYesClicked = false;
		
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(500);
		stage.setMinHeight(150);
		stage.setResizable(false);
		
		HBox paneLbl = new HBox();
		Label lbl = new Label();
		lbl.setText(message);
		//lbl.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
		paneLbl.setAlignment(Pos.CENTER);
		paneLbl.getChildren().addAll(lbl);
		
		Button btnYes = new Button();
		btnYes.setText(textYes);
		btnYes.setOnAction(e -> buttonYesClicked());
		btnYes.getStyleClass().addAll("red-button");
		btnYes.setMaxWidth(Double.MAX_VALUE);
		
		Button btnNo = new Button();
		btnNo.setText(textNo);
		btnNo.setOnAction(e -> buttonNoClicked());
		btnNo.getStyleClass().addAll("blue-button");
		btnNo.setMaxWidth(Double.MAX_VALUE);
		
		HBox paneBtn = new HBox(20);
		HBox.setHgrow(btnYes, Priority.ALWAYS);
		HBox.setHgrow(btnNo, Priority.ALWAYS);
		paneBtn.getChildren().addAll(btnYes, btnNo);
		
		VBox vBox = new VBox(20);
		vBox.setPadding(new Insets(50));
		vBox.getChildren().addAll(paneLbl, paneBtn);
		
		Scene scene = new Scene(vBox);
		stage.setScene(scene);
		stage.showAndWait();
		return btnYesClicked;
	} // End show() method
	
	/**This method is called when the yes button is clicked. It 
	 * closes the dialog box and sets the boolean return value 
	 * to true. 
	 * 
	 * @author Craig Lovell 
	 */
	private static void buttonYesClicked() {
		stage.close();
		btnYesClicked = true;
	} // End buttonYesClicked() method
	
	/**This method is called when the no button is clicked. It 
	 * closes the dialog box and sets the boolean return value 
	 * to false. 
	 * 
	 * @author Craig Lovell 
	 */
	private static void buttonNoClicked() {
		stage.close();
		btnYesClicked = false;
	} // End buttonNoClicked() method
} // End ConfirmationBox class

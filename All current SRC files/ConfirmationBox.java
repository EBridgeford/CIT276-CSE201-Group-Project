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

public class ConfirmationBox {

	private static Stage stage;
	private static boolean btnYesClicked;
	
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
		btnYes.setOnAction(e -> btnYesClicked());
		btnYes.getStyleClass().addAll("red-button");
		btnYes.setMaxWidth(Double.MAX_VALUE);
		
		Button btnNo = new Button();
		btnNo.setText(textNo);
		btnNo.setOnAction(e -> btnNoClicked());
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
		
	}
	
	private static void btnYesClicked() {
		
		stage.close();
		btnYesClicked = true;
		
	}
	
	private static void btnNoClicked() {
		
		stage.close();
		btnYesClicked = false;
		
	}
	
}

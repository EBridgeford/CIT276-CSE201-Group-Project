package application.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBox {

	private static Stage stage;
	
	public static void show(String message, String title) {
		
		stage = new Stage();
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
		btnYes.setText("Ok");
		btnYes.setOnAction(e -> btnYesClicked());
		btnYes.getStyleClass().addAll("red-button");
		btnYes.setMaxWidth(Double.MAX_VALUE);
		
		HBox paneBtn = new HBox(20);
		HBox.setHgrow(btnYes, Priority.ALWAYS);
		paneBtn.getChildren().addAll(btnYes);
		
		VBox vBox = new VBox(20);
		vBox.setPadding(new Insets(50));
		vBox.getChildren().addAll(paneLbl, paneBtn);
		
		Scene scene = new Scene(vBox);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private static void btnYesClicked() {
		
		stage.close();
		
	}
	
}

package application.view;

import java.io.File;
import java.sql.SQLException;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import application.Main;
import application.model.BLDGObj;
import application.model.CampusObj;
import application.model.RecordObj;
import application.model.RoomObj;
import application.util.ConfirmationBox;
import application.util.DBConnection;
import application.util.LocationObservableListBuilder;
import application.util.QuickParser;
import application.util.RecordObjListBuilder;

/**This class is a controller class, for the RootLayout.fxml view. 
 * It contains the logic for all of the components in the GUI view. 
 * 
 * @author Craig Lovell
 * @version 1.0
 */
public class RootLayout {

	/**This enum type allows you to keep track of which type of 
	 * file was read. 
	 * 
	 * @author Craig Lovell
	 */
	public enum FileType {
		INVENTORY, INITIAL, UNASSIGNED
	} // End FileType enum

	/**This enum type allows you to keep track of which screen the 
	 * user is currently looking at in the GUI.
	 * 
	 * @author Craig Lovell
	 */
	public enum CurrentTabScreen {
		INVENTORY, INITIAL, SEARCH
	} // End CurrentTabScreen enum
	
	private FileType fileType = FileType.UNASSIGNED;
	private CurrentTabScreen currentTabScreen = CurrentTabScreen.SEARCH;

	@FXML
	private TableView<RecordObj> tableView;
	@FXML
	private TextField searchField;
	@FXML
	private TextField searchPurchaseOrderField;
	@FXML
	private TableColumn<RecordObj, String> rfid;
	@FXML
	private TableColumn<RecordObj, String> campus;
	@FXML
	private TableColumn<RecordObj, String> building;
	@FXML
	private TableColumn<RecordObj, String> room;
	@FXML
	private TableColumn<RecordObj, String> comments;
	@FXML
	private TableColumn<RecordObj, String> lastScannedOn;
	@FXML
	private TableColumn<RecordObj, String> lastScannedBy;
	@FXML
	private TableColumn<RecordObj, String> purchaseOrder;
	@FXML
	private TableColumn<RecordObj, String> serviceTag;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab searchTab;
	@FXML
	private Tab scanTab;
	@FXML
	private Label notificationLabel;
	@FXML
	private ComboBox<CampusObj> campusCombo;
	@FXML
	private ComboBox<BLDGObj> buildingCombo;
	@FXML
	private ComboBox<RoomObj> roomCombo;

	private Main main;
	private Stage primaryStage;
	private DBConnection connection;
	private ObservableList<RecordObj> masterSearchData;
	private ObservableList<RecordObj> presentationSearchData;
	private ObservableList<RecordObj> masterInitialData;
	private ObservableList<RecordObj> presentationInitialData;
	private ObservableList<RecordObj> masterInventoryData;
	private ObservableList<RecordObj> presentationInventoryData;

	private LocationObservableListBuilder locationBuilder;
	private boolean isConnected;
	private ObservableList<CampusObj> campusComboData;
	private ObservableList<BLDGObj> buildingComboData;
	private ObservableList<RoomObj> roomComboData;

	private RecordObj currentRecord;

	private SequentialTransition sequentialTransition = new SequentialTransition();

	private CampusObj campusObj;
	private RoomObj roomObj;
	private BLDGObj buildingObj;

	@FXML
	/**This method is called automatically by the FXML loader when 
	 * all injected class members are instantiated. Therefore none 
	 * of the members will be null, when the method is called. It 
	 * is used to give initial values to the objects that are used 
	 * in the GUI. It is also used to add listeners to components 
	 * in the GUI. 
	 * 
	 * @author Craig Lovell
	 */
	private void initialize() {
		rfid.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("rfid"));
		campus.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("campus"));
		building.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("bldg"));
		room.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("rm"));
		comments.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("comments"));
		lastScannedOn.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("strTimeStamp"));
		lastScannedBy.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("lastScanedBy"));
		purchaseOrder.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("purchaseOrder"));
		serviceTag.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("serviceTag"));

		rfid.setCellFactory(TextFieldTableCell.forTableColumn());
		campus.setCellFactory(TextFieldTableCell.forTableColumn());
		building.setCellFactory(TextFieldTableCell.forTableColumn());
		room.setCellFactory(TextFieldTableCell.forTableColumn());
		comments.setCellFactory(TextFieldTableCell.forTableColumn());
		purchaseOrder.setCellFactory(TextFieldTableCell.forTableColumn());
		serviceTag.setCellFactory(TextFieldTableCell.forTableColumn());

		campusObj = new CampusObj("");
		roomObj = new RoomObj("");
		buildingObj = new BLDGObj("");
		
		tableView.setRowFactory(new RightClickOnRowHandler());
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
				if(newTab == searchTab) {
					currentTabScreen = CurrentTabScreen.SEARCH;
					changeToSearchTable();
				} // End if statement
				else if(newTab == scanTab) {
					switch (fileType) {
					case INVENTORY:
						currentTabScreen = CurrentTabScreen.INVENTORY;
						break;
					case INITIAL:
						currentTabScreen = CurrentTabScreen.INITIAL;
						break;
					default:
						break;
					} // End switch statement 
					changeToScanTable();
				} // End else if statement
			} // End changed() method
		});

		searchField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateFilteredData();
			} // End changed() method
		});

		try {
			connection = new DBConnection();
			isConnected = true;
		} // End try statement
		catch (ClassNotFoundException e) {
			setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
			isConnected = false;
		} // End catch statement
		catch (SQLException e) {
			setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
			isConnected = false;
		} // End catch statement
		loadDropDownInfo();

		campusCombo.setOnAction((event) -> {comboBoxHandler(event);});
		buildingCombo.setOnAction((event) -> {comboBoxHandler(event);});
		roomCombo.setOnAction((event) -> {comboBoxHandler(event);});
	} // End initialize() method 

	/**This method is called by the Main class after loading the 
	 * FXML file into the scene graph. It gives the controller 
	 * class access to the stage of the GUI. It also instantiates 
	 * the observable list used by the table view, and then it 
	 * loads the initial observable list for the table view control. 
	 * 
	 * @author Craig Lovell
	 * @param main An object of type main, given by the main class. 
	 */
	public void setMainApp(Main main) {
		this.main = main;
		this.primaryStage = main.getPrimaryStage();
	
		masterSearchData = FXCollections.observableArrayList();
		presentationSearchData = FXCollections.observableArrayList();
	
		masterInventoryData = FXCollections.observableArrayList();
		presentationInventoryData = FXCollections.observableArrayList();
	
		masterInitialData = FXCollections.observableArrayList();
		presentationInitialData = FXCollections.observableArrayList();
	
		presentationSearchData.addAll(masterSearchData);
		presentationInventoryData.addAll(masterInventoryData);
		presentationInitialData.addAll(masterInitialData);

		tableView.setItems(presentationSearchData);
	} // End setMainApp() method

	@FXML
	/**This method sets the campus, building, and room objects based 
	 * on which item is selected from the drop downs. 
	 * 
	 * @author Craig Lovell
	 * @param event An action event object. 
	 */
	private void comboBoxHandler(ActionEvent event) {
		if(isConnected) {
			if(event.getSource() == campusCombo) {
				campusObj = campusCombo.getSelectionModel().getSelectedItem();
			} // End if statement
			else if(event.getSource() == buildingCombo) {
				buildingObj = buildingCombo.getSelectionModel().getSelectedItem();
			} // End else if statement 
			else if(event.getSource() == roomCombo) {
				roomObj = roomCombo.getSelectionModel().getSelectedItem();
			} // End else if statement 
		} // End if statement 
	} // End comboBoxHandler() method

	/**This method loads the campus, building, and room information 
	 * into the drop down menus from the database. 
	 * 
	 * @author Craig Lovell
	 */
	private void loadDropDownInfo() {
		if(isConnected) {
			try {
				locationBuilder = connection.getLocationData();

				buildingComboData = locationBuilder.getBLDGList();
				campusComboData = locationBuilder.getCampusList();
				roomComboData = locationBuilder.getRMList();

				buildingCombo.setItems(buildingComboData);
				campusCombo.setItems(campusComboData);
				roomCombo.setItems(roomComboData);

				buildingCombo.getSelectionModel().selectFirst();
				campusCombo.getSelectionModel().selectFirst();
				roomCombo.getSelectionModel().selectFirst();
			} // End try statement 
			catch (SQLException e) {
				setNotificationAnimation("Connection Problem, Could Not Pull Information", Color.RED);
			} // End catch statement 
		} // End if statement
		else {
			setNotificationAnimation("Not Connect", Color.RED);
		} // End else statement 
	} // End loadDropDownInfo() method 

	@FXML
	/**This method queries the database for information which it 
	 * uses to rebuild the observable arrays used to display 
	 * information in the table view control. 
	 * 
	 * @author Craig Lovell
	 * @param event An action event object. 
	 */
	private void getDataFromDatabase(ActionEvent event) {
		if(isConnected) {
			if(campusObj.getCampus() != null &&
					buildingObj.getBld() != null && 
					roomObj.getRm() != null &&
					searchPurchaseOrderField.getText() != null) {
				try {
					String tempCampus = campusObj.getCampus();
					String tempBuilding = buildingObj.getBld();
					String tempRoom = roomObj.getRm();
					
					if(campusObj.getCampus().equalsIgnoreCase("ALL")) {tempCampus = "";}
					if(buildingObj.getBld().equalsIgnoreCase("ALL")) {tempBuilding = "";}
					if(roomObj.getRm().equalsIgnoreCase("ALL")) {tempRoom = "";}
					
					RecordObjListBuilder recordObjListBuilder = connection.generalQuery(
							tempCampus, 
							tempBuilding, 
							tempRoom, 
							searchPurchaseOrderField.getText());
					
					masterSearchData.clear();
					masterSearchData = recordObjListBuilder.getRecords();
					
					presentationSearchData.clear();
					presentationSearchData.addAll(masterSearchData);
					
				} // End try statement 
				catch (SQLException e) {
					try {
						System.out.println("Restarted");
						connection = new DBConnection();
						isConnected = true;
					} // End try statement  
					catch (ClassNotFoundException ex) {
						setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
						isConnected = false;
					} // End catch statement 
					catch (SQLException ex) {
						setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
						isConnected = false;
					} // End catch statement 
				} // End catch statement 
			} // End if statement
		} // End if statement
	} // End getDataFromDatabase() method 

	/**This method is called while searching for information 
	 * locally. It clears the data in the table, and rebuilds 
	 * the data in the table based on the search data. 
	 * 
	 * @author Craig Lovell
	 */
	private void updateFilteredData() {
		switch (currentTabScreen) {
		case INVENTORY:
			presentationInventoryData.clear();

			for (RecordObj record: masterInventoryData) {
				if (matchesFilter(record)) {
					presentationInventoryData.add(record);
				} // End if statement
			} // End for loop 
			break;
		case INITIAL:
			presentationInitialData.clear();

			for (RecordObj record: masterInitialData) {
				if (matchesFilter(record)) {
					presentationInitialData.add(record);
				} // End if statement
			} // End for loop 
			break;
		case SEARCH:
			presentationSearchData.clear();

			for (RecordObj record: masterSearchData) {
				if (matchesFilter(record)) {
					presentationSearchData.add(record);
				} // End if statement
			} // End for loop 
			break;
		default:
			break;
		}// End switch statement
	} // End updateFilteredData() method 

	/**This method is called by the updateFilteredData() method. If 
	 * the given record object contains the string in the search 
	 * field it return true, if it does not it returns false. 
	 * 
	 * @author Craig Lovell
	 * @param record The record object to be searched. 
	 */
	private boolean matchesFilter(RecordObj record) {
		String filterString = searchField.getText().trim();
		if (filterString == null || filterString.isEmpty()) {
			return true;
		} // End if statement

		if (record.getRfid().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}  // End if statement
		else if (record.getCampus().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getBldg().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getRm().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getComments().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getTimeStamp().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getLastScanedBy().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getPurchaseOrder().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		else if (record.getServiceTag().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		}
		return false; 
	} // End matchesFilter() method

	@FXML
	/**This method opens a file dialog to pick a .csv file. It then 
	 * calls the loadDataFromFile() method to load the data form 
	 * the file into the table view control.
	 * 
	 * @author Craig Lovell
	 * @param event An action event object. 
	 */
	private void importCSVFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import CSV File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
		
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			loadDataFromFile(file);
		} // End if statement
	} // End importCSVFile() method

	/**This method loads the data from the given file into the 
	 * table view control. 
	 * 
	 * @author Craig Lovell
	 * @param file A file object of a .csv file. 
	 */
	private void loadDataFromFile(File file) {
		String fileName = file.getName();
		QuickParser test = new QuickParser();

		if(fileName.trim().toUpperCase().startsWith("INV")) {
			fileType = FileType.INVENTORY;
			tableView.getColumns().clear();
			tableView.getColumns().addAll(rfid,campus,building,room,lastScannedOn,lastScannedBy,purchaseOrder,serviceTag,comments);
			masterInventoryData.clear();
			masterInventoryData = test.Parse(file, fileType);
			presentationInventoryData.clear();
			presentationInventoryData.addAll(masterInventoryData);
			switch (fileType) {
			case INVENTORY:
				currentTabScreen = CurrentTabScreen.INVENTORY;
				break;
			case INITIAL:
				currentTabScreen = CurrentTabScreen.INITIAL;
				break;
			default:
				break;
			} // End switch statement 
		} // End if statement
		else if(fileName.toUpperCase().startsWith("INI")) {
			fileType = FileType.INITIAL;
			tableView.getColumns().clear();
			tableView.getColumns().addAll(rfid,campus,building,room,lastScannedOn,lastScannedBy,purchaseOrder,serviceTag,comments);
			masterInitialData.clear();
			masterInitialData = test.Parse(file, fileType);
			presentationInitialData.clear();
			presentationInitialData.addAll(masterInitialData);
			switch (fileType) {
			case INVENTORY:
				currentTabScreen = CurrentTabScreen.INVENTORY;
				break;
			case INITIAL:
				currentTabScreen = CurrentTabScreen.INITIAL;
				break;
			default:
				break;
			} // End switch statement 
		} // End else if statement 
		else {
			fileType = FileType.UNASSIGNED;
		} // End else statement
		changeToScanTable();
	} // End loadDataFromFile() method

	/**This method changes the content in the table view control 
	 * based on which file you have loaded. 
	 * 
	 * @author Craig Lovell
	 */
	private void changeToScanTable() {
		switch (fileType) {
		case INVENTORY:
			tableView.setItems(presentationInventoryData);
			break;
		case INITIAL:
			tableView.setItems(presentationInitialData);
			break;
		default:
			tableView.getColumns().clear();
			break;
		} // End switch statement
	} // End changeToScanTable() method

	/**This method changes the content in the table view control 
	 * to search data.  
	 * 
	 * @author Craig Lovell
	 */
	private void changeToSearchTable() {
		tableView.setItems(presentationSearchData);

		if(fileType == FileType.UNASSIGNED) {
			tableView.getColumns().clear();
			tableView.getColumns().addAll(rfid,campus,building,room,lastScannedOn,lastScannedBy,purchaseOrder,serviceTag,comments);
		} // End if statement
	} // End changeToSearchTable() method

	/**This method displays an animated notification in the 
	 * middle of the menu bar. 
	 * 
	 * @author Craig Lovell
	 * @param labelText The text to display as a notification.
	 * @param color The color of the notification. 
	 */
	private void setNotificationAnimation(String labelText, Color color) {
		if(sequentialTransition.getStatus() != Status.RUNNING) {
			notificationLabel.setText(labelText);
			notificationLabel.setTextFill(color);

			FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1.5));
			fadeInTransition.setFromValue(0);
			fadeInTransition.setToValue(1);

			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));

			FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1.5));
			fadeOutTransition.setFromValue(1);
			fadeOutTransition.setToValue(0);

			sequentialTransition = new SequentialTransition(notificationLabel, fadeInTransition, pauseTransition, fadeOutTransition);
			sequentialTransition.setCycleCount(1);
			sequentialTransition.playFromStart();
		} // End if statement
		else {
			sequentialTransition.stop();

			notificationLabel.setText(labelText);
			notificationLabel.setTextFill(color);

			FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1.5));
			fadeInTransition.setFromValue(0);
			fadeInTransition.setToValue(1);

			PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));

			FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1.5));
			fadeOutTransition.setFromValue(1);
			fadeOutTransition.setToValue(0);

			sequentialTransition = new SequentialTransition(notificationLabel, fadeInTransition, pauseTransition, fadeOutTransition);
			sequentialTransition.setCycleCount(1);
			sequentialTransition.playFromStart();
		} // End else statement 
	} // End setNotificationAnimation() method

	@FXML
	/**This method is called whenever the user tries to commit 
	 * a change for a table view cell. It makes the user confirm 
	 * that they really want to change the cell value. 
	 * 
	 * @author Craig Lovell
	 * @param event A CellEditEvent object. 
	 */
	private void tableCellEditCommitHandler(CellEditEvent<RecordObj, String> event) {
		int indexOfRow = event.getTablePosition().getRow();
		currentRecord = event.getTableView().getItems().get(indexOfRow);
		String newCellValue = event.getNewValue();
		String oldCellValue = event.getOldValue();
	
		boolean yes = ConfirmationBox.show("Are you sure you want to edit?", "Conformation", "Yes", "No");
	
		if(yes) {
			if(event.getSource() == rfid) {
				currentRecord.setRfid(newCellValue);
			} // End if statement
			else if(event.getSource() == campus) {
				currentRecord.setCampus(newCellValue);
			} // End else if statement
			else if(event.getSource() == building) {
				currentRecord.setBldg(newCellValue);
			} // End else if statement
			else if(event.getSource() == room) {
				currentRecord.setRm(newCellValue);
			} // End else if statement
			else if(event.getSource() == comments) {
				currentRecord.setComments(newCellValue);
			} // End else if statement
			else if(event.getSource() == purchaseOrder) {
				currentRecord.setPurchaseOrder(newCellValue);
			} // End else if statement
			else if(event.getSource() == serviceTag) {
				currentRecord.setServiceTag(newCellValue);
			} // End else if statement
			setNotificationAnimation("Updated cell value from \"" + oldCellValue + "\" to \"" + newCellValue + "\"", Color.GREEN);
		} // End if statement
		else {
			if(event.getSource() == rfid) {
				currentRecord.setRfid(null);
				currentRecord.setRfid(oldCellValue);
			} // End if statement
			else if(event.getSource() == campus) {
				currentRecord.setCampus(null);
				currentRecord.setCampus(oldCellValue);
			} // End else if statement
			else if(event.getSource() == building) {
				currentRecord.setBldg(null);
				currentRecord.setBldg(oldCellValue);
			} // End else if statement
			else if(event.getSource() == room) {
				currentRecord.setRm(null);
				currentRecord.setRm(newCellValue);
			} // End else if statement
			else if(event.getSource() == comments) {
				currentRecord.setComments(null);
				currentRecord.setComments(oldCellValue);
			} // End else if statement
			else if(event.getSource() == purchaseOrder) {
				currentRecord.setPurchaseOrder(null);
				currentRecord.setPurchaseOrder(oldCellValue);
			} // End else if statement
			else if(event.getSource() == serviceTag) {
				currentRecord.setServiceTag(null);
				currentRecord.setServiceTag(oldCellValue);
			} // End else if statement
			setNotificationAnimation("Did not update cell value of \"" + oldCellValue + "\"", Color.GREEN);
		} // End else statement
	} // End tableCellEditCommitHandler() method

	/**This is a private inner handler class. This handler makes a 
	 * context menu whenever you right click a row in a table view 
	 * control. The context menu lets you delete the currently 
	 * selected row. 
	 * 
	 * @author Craig Lovell
	 */
	private class RightClickOnRowHandler implements Callback<TableView<RecordObj>, TableRow<RecordObj>> {
		public TableRow<RecordObj> call(TableView<RecordObj> tableView) {
			TableRow<RecordObj> row = new TableRow<>();
			ContextMenu rowMenu = new ContextMenu();
			MenuItem removeItem = new MenuItem("Delete");
			rowMenu.getItems().addAll(removeItem);			

			removeItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					boolean yes = ConfirmationBox.show("Are you sure you want to do it?", "WARNING", "YES", "NO");	
					currentRecord = row.getItem();

					if(yes) {
						masterSearchData.remove(currentRecord);
						tableView.getItems().remove(currentRecord);
						setNotificationAnimation("Deleted row \"" + currentRecord.toString() + "\"", Color.GREEN);
					} // End if statement
					else {
						setNotificationAnimation("Did not delete row \"" + currentRecord.toString() + "\"", Color.GREEN);
					} // End else statement 
				} // End handle() method
			});

			// Only display context menu for non-null items:
			row.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(row.itemProperty()))
					.then(rowMenu)
					.otherwise((ContextMenu)null));

			return row;
		} // End call() method
	} // End RightClickOnRowHandler class

	@FXML
	/**This method clears the text in the search field. 
	 * 
	 * @author Craig Lovell
	 * @param event An action event object. 
	 */
	private void clearSearchFieldHandler(ActionEvent event) {
		searchField.clear();
	} // End clearSearchFieldHandler() method

	@FXML
	/**This method de-selects the currently selected row in the 
	 * tableview control. 
	 * 
	 * @author Craig Lovell
	 * @param event An event object. 
	 */
	private void clearTableSelection(Event event) {
		tableView.getSelectionModel().clearSelection();
	} // End clearTableSelection() method
	
	@FXML
	/**This method closes the GUI application
	 * 
	 * @author Craig Lovell
	 * @param event An action event object. 
	 */
	private void closeApplication(ActionEvent event) {
		System.exit(0);
	}
} // End RootLayout class







import java.io.File;
import java.sql.SQLException;
import java.util.Queue;

import com.sun.javafx.collections.SetAdapterChange;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class RootLayout {

	public enum FileType {
		INVENTORY, INITIAL, UNASSIGNED
	}

	public enum CurrentTabScreen {

		INVENTORY, INITIAL, SEARCH, UNASSIGNED

	}
	
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

				}
				else if(newTab == scanTab) {

					changeCurrentTabScreen();
					changeToScanTable();

				}

			}

		});

		searchField.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				updateFilteredData();

			}

		});

		try {

			connection = new DBConnection();
			isConnected = true;

		} 
		catch (ClassNotFoundException e) {

			setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
			isConnected = false;

		} 
		catch (SQLException e) {

			setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
			isConnected = false;

		}

		loadDropDownInfo();

		campusCombo.setOnAction((event) -> {
			comboBoxHandler(event);
		});
		buildingCombo.setOnAction((event) -> {
			comboBoxHandler(event);
		});
		roomCombo.setOnAction((event) -> {
			comboBoxHandler(event);
		});
	}

	private void changeCurrentTabScreen() {
		switch (fileType) {
		case INVENTORY:
			currentTabScreen = CurrentTabScreen.INVENTORY;
			break;

		case INITIAL:
			currentTabScreen = CurrentTabScreen.INITIAL;
			break;

		default:
			break;
		}
	}

	@FXML
	private void comboBoxHandler(ActionEvent e) {

		if(isConnected) {

			if(e.getSource() == campusCombo) {

				campusObj = campusCombo.getSelectionModel().getSelectedItem();

			}
			else if(e.getSource() == buildingCombo) {

				buildingObj = buildingCombo.getSelectionModel().getSelectedItem();

			}
			else if(e.getSource() == roomCombo) {

				roomObj = roomCombo.getSelectionModel().getSelectedItem();

			}

		}

	}

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

				//campusCombo.setDisable(false);
				//buildingCombo.setDisable(true);
				//roomCombo.setDisable(true);

			} 
			catch (SQLException e) {

				setNotificationAnimation("Connection Problem, Could Not Pull Information", Color.RED);

			}

		}
		else {

			setNotificationAnimation("Not Connect", Color.RED);

		}

	}

	@FXML
	private void clearSearchFieldHandler(ActionEvent event) {

		searchField.clear();

	}

	@FXML
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
					
				} 
				catch (SQLException e) {
					try {
						System.out.println("Restarted");
						connection = new DBConnection();
						isConnected = true;
					} 
					catch (ClassNotFoundException ex) {

						setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
						isConnected = false;

					} 
					catch (SQLException ex) {

						setNotificationAnimation("Connection Problem, Could Not Connect To Server", Color.RED);
						isConnected = false;

					}
				}
								
			}
			
		}

	}

	@FXML
	private void importCSVFile(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			openFile(file);
		}

	}

	private void updateFilteredData() {

		switch (currentTabScreen) {
		case INVENTORY:
			presentationInventoryData.clear();

			for (RecordObj record: masterInventoryData) {
				
				if (matchesFilter(record)) {

					presentationInventoryData.add(record);

				}

			}
			break;
		case INITIAL:
			presentationInitialData.clear();
			System.out.println("Initial");

			for (RecordObj record: masterInitialData) {

				if (matchesFilter(record)) {

					presentationInitialData.add(record);

				}

			}
			break;
		case SEARCH:
			presentationSearchData.clear();

			for (RecordObj record: masterSearchData) {

				if (matchesFilter(record)) {

					presentationSearchData.add(record);

				}

			}
			break;

		default:
			break;
		}

	}

	private boolean matchesFilter(RecordObj record) {

		String filterString = searchField.getText().trim();

		if (filterString == null || filterString.isEmpty()) {
			return true;
		}

		if (record.getRfid().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
			return true;
		} 
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
	}

	private void openFile(File file) {
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
			changeCurrentTabScreen();
			
		}
		else if(fileName.toUpperCase().startsWith("INI")) {
			fileType = FileType.INITIAL;
			tableView.getColumns().clear();
			tableView.getColumns().addAll(rfid,campus,building,room,lastScannedOn,lastScannedBy,purchaseOrder,serviceTag,comments);
			masterInitialData.clear();
			masterInitialData = test.Parse(file, fileType);
			presentationInitialData.clear();
			presentationInitialData.addAll(masterInitialData);
			changeCurrentTabScreen();
		}
		else {
			fileType = FileType.UNASSIGNED;
		}
		
		changeToScanTable();
	}

	private static void configureFileChooser(FileChooser fileChooser) {      
		fileChooser.setTitle("Import CSV File");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home"))
				);                 
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("CSV", "*.csv")
				);
	}

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
		}

	}

	private void changeToSearchTable() {

		tableView.setItems(presentationSearchData);

		if(fileType == FileType.UNASSIGNED) {

			tableView.getColumns().clear();
			tableView.getColumns().addAll(rfid,campus,building,room,lastScannedOn,lastScannedBy,purchaseOrder,serviceTag,comments);

		}


	}

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

		}
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

		}

	}

	@FXML
	public void tableCellEditCommitHandler(CellEditEvent<RecordObj, String> event) {

		int indexOfRow = event.getTablePosition().getRow();
		currentRecord = event.getTableView().getItems().get(indexOfRow);
		String newCellValue = event.getNewValue();
		String oldCellValue = event.getOldValue();

		boolean yes = ConfirmationBox.show("Are you sure you want to edit?", "Conformation", "Yes", "No");

		if(yes) {

			if(event.getSource() == rfid) {

				currentRecord.setRfid(newCellValue);

			}
			else if(event.getSource() == campus) {

				currentRecord.setCampus(newCellValue);

			}
			else if(event.getSource() == building) {

				currentRecord.setBldg(newCellValue);

			}
			else if(event.getSource() == room) {

				currentRecord.setRm(newCellValue);

			}
			else if(event.getSource() == comments) {

				currentRecord.setComments(newCellValue);

			}
			else if(event.getSource() == purchaseOrder) {

				currentRecord.setPurchaseOrder(newCellValue);

			}
			else if(event.getSource() == serviceTag) {

				currentRecord.setServiceTag(newCellValue);

			}

			setNotificationAnimation("Updated cell value from \"" + oldCellValue + "\" to \"" + newCellValue + "\"", Color.GREEN);

		}
		else {

			if(event.getSource() == rfid) {

				currentRecord.setRfid(null);
				currentRecord.setRfid(oldCellValue);

			}
			else if(event.getSource() == campus) {

				currentRecord.setCampus(null);
				currentRecord.setCampus(oldCellValue);

			}
			else if(event.getSource() == building) {

				currentRecord.setBldg(null);
				currentRecord.setBldg(oldCellValue);

			}
			else if(event.getSource() == room) {

				currentRecord.setRm(null);
				currentRecord.setRm(newCellValue);

			}
			else if(event.getSource() == comments) {

				currentRecord.setComments(null);
				currentRecord.setComments(oldCellValue);

			}
			else if(event.getSource() == purchaseOrder) {

				currentRecord.setPurchaseOrder(null);
				currentRecord.setPurchaseOrder(oldCellValue);

			}
			else if(event.getSource() == serviceTag) {

				currentRecord.setServiceTag(null);
				currentRecord.setServiceTag(oldCellValue);

			}

			setNotificationAnimation("Did not update cell value of \"" + oldCellValue + "\"", Color.GREEN);

		}


	}

	public void setMainApp(Main main) {

		this.main = main;
		this.primaryStage = main.getPrimaryStage();

		initializeData();

	}

	private void initializeData() {

		masterSearchData = FXCollections.observableArrayList();
		presentationSearchData = FXCollections.observableArrayList();

		masterInventoryData = FXCollections.observableArrayList();
		presentationInventoryData = FXCollections.observableArrayList();

		masterInitialData = FXCollections.observableArrayList();
		presentationInitialData = FXCollections.observableArrayList();

		presentationSearchData.addAll(masterSearchData);
		tableView.setItems(presentationSearchData);

		presentationInventoryData.addAll(masterInventoryData);

		presentationInitialData.addAll(masterInitialData);

	}

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

					}
					else {

						setNotificationAnimation("Did not delete row \"" + currentRecord.toString() + "\"", Color.GREEN);

					}

				}

			});

			// only display context menu for non-null items:
			row.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(row.itemProperty()))
					.then(rowMenu)
					.otherwise((ContextMenu)null));

			return row;

		}

	}

	@FXML
	private void clearTableSelection(Event event) {

		tableView.getSelectionModel().clearSelection();

	}

}






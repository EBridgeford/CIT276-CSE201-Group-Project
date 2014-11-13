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

import java.io.File;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class RootLayout {

	public enum FileType {
		INVENTORY, INITIAL, UNASSIGNED
	}

	FileType fileType = FileType.UNASSIGNED;

	@FXML
	private TableView<RecordObj> tableView;
	@FXML
	private DatePicker calendar;
	@FXML
	private TextField searchField;
	@FXML
	private TableColumn<RecordObj, String> rfid;
	@FXML
	private TableColumn<RecordObj, String> campus;
	@FXML
	private TableColumn<RecordObj, String> building;
	@FXML
	private TableColumn<RecordObj, Integer> room;
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

	private Main main;
	private Stage primaryStage;
	private ObservableList<RecordObj> masterSearchData;
	private ObservableList<RecordObj> presentationSearchData;
	private ObservableList<RecordObj> masterInitialData;
	private ObservableList<RecordObj> presentationInitialData;
	private ObservableList<RecordObj> masterInventoryData;
	private ObservableList<RecordObj> presentationInventoryData;

	private RecordObj currentRecord;

	private SequentialTransition sequentialTransition = new SequentialTransition();


	@FXML
	private void initialize() {

		rfid.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("rfid"));
		campus.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("campus"));
		building.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("bldg"));
		room.setCellValueFactory(new PropertyValueFactory<RecordObj, Integer>("rm"));
		comments.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("comments"));
		lastScannedOn.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("strTimeStamp"));
		lastScannedBy.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("lastScanedBy"));
		purchaseOrder.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("purchaseOrder"));
		serviceTag.setCellValueFactory(new PropertyValueFactory<RecordObj, String>("serviceTag"));

		rfid.setCellFactory(TextFieldTableCell.forTableColumn());
		campus.setCellFactory(TextFieldTableCell.forTableColumn());
		building.setCellFactory(TextFieldTableCell.forTableColumn());
		//room.setCellFactory(TextFieldTableCell.forTableColumn());
		comments.setCellFactory(TextFieldTableCell.forTableColumn());
		lastScannedOn.setCellFactory(TextFieldTableCell.forTableColumn());
		lastScannedBy.setCellFactory(TextFieldTableCell.forTableColumn());
		purchaseOrder.setCellFactory(TextFieldTableCell.forTableColumn());
		serviceTag.setCellFactory(TextFieldTableCell.forTableColumn());

		tableView.setRowFactory(new RightClickOnRowHandler());

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {

				if(newTab == searchTab) {

					changeToSearchTable();

				}
				else if(newTab == scanTab) {

					changeToScanTable();

				}

			}

		});

	}

	@FXML
	private void searchFieldHandler(ActionEvent event) {

		updateFilteredData();

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

		presentationSearchData.clear();

		for (RecordObj record: masterSearchData) {

			if (matchesFilter(record)) {

				presentationSearchData.add(record);

			}

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
		else if (String.valueOf(record.getRm()).toLowerCase().indexOf(filterString.toLowerCase()) != -1) {

			return true;

		}
		else if (record.getComments().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {

			return true;

		}
		else if (record.getStrTimeStamp().toLowerCase().indexOf(filterString.toLowerCase()) != -1) {

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

	private static void configureFileChooser(FileChooser fileChooser) {      
		fileChooser.setTitle("Import CSV File");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home"))
				);                 
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("CSV", "*.csv")
				);
	}

	private void openFile(File file) {

		String fileName = file.getName();
		if(fileName.trim().toUpperCase().startsWith("INVENTORY")) {

			fileType = FileType.INVENTORY;

		}
		else if(fileName.startsWith("INITIAL")) {

			fileType = FileType.INITIAL;

		}
		else {

			fileType = FileType.UNASSIGNED;

		}

		//		System.out.println(fileType == FileType.INVENTORY ? 0 : 1);
		QuickParser test = new QuickParser();
		test.parse(file, 1);

		changeToScanTable();

	}

	private void changeToScanTable() {

		switch (fileType) {
		case INVENTORY:
			tableView.setItems(presentationInventoryData);
			tableView.getColumns().clear();
			tableView.getColumns().addAll(campus,building,room,lastScannedBy,lastScannedOn,comments);
			break;
		case INITIAL:
			tableView.setItems(presentationInitialData);
			tableView.getColumns().clear();
			tableView.getColumns().addAll(serviceTag,lastScannedBy,lastScannedOn,comments);
			break;

		default:
			tableView.getColumns().clear();
			break;
		}

	}

	private void changeToSearchTable() {

		tableView.setItems(presentationSearchData);
		tableView.getColumns().clear();
		tableView.getColumns().addAll(rfid,campus,building,room,comments,lastScannedOn,lastScannedBy,purchaseOrder,serviceTag);

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

				//currentRecord.setSerialNumber(newCellValue);

			}
			else if(event.getSource() == comments) {

				currentRecord.setComments(newCellValue);

			}
			else if(event.getSource() == lastScannedOn) {

				currentRecord.setStrTimeStamp(newCellValue);

			}
			else if(event.getSource() == lastScannedBy) {

				currentRecord.setLastScanedBy(newCellValue);

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

				//currentRecord.setSerialNumber(newCellValue);

			}
			else if(event.getSource() == comments) {

				currentRecord.setComments(null);
				currentRecord.setComments(oldCellValue);

			}
			else if(event.getSource() == lastScannedOn) {

				currentRecord.setStrTimeStamp(null);
				currentRecord.setStrTimeStamp(oldCellValue);

			}
			else if(event.getSource() == lastScannedBy) {

				currentRecord.setLastScanedBy(null);
				currentRecord.setLastScanedBy(oldCellValue);

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

		masterSearchData.addAll(main.getInitialData());
		presentationSearchData.addAll(masterSearchData);
		tableView.setItems(presentationSearchData);

		masterInventoryData.addAll(new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory dsf jadskfjkalsfljadlsflakdsfjkljasfajdlsjkadks jksdlkjasdkjaskkads jaksaklsd jkasdkajjasdlaksdj kajds kajsdjfkasd lkjasdkjadkls jlkasd lkaj fklasdj lfkasdkfjasdj fkasdkf", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"));
		presentationInventoryData.addAll(masterInventoryData);
		masterInitialData.addAll(new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Initial", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"),
				new RecordObj("111111111", "HUM", "MOS", 300, "hadleyps       ", "14/10/27 09:20:30:15", "Inventory", "w52as00003215", "zjkfjasfk"));
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







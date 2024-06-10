package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends Application {
	private TableView<Branch> tableView;
	private TableColumn<Branch, Integer> tcRestId;
	private TableColumn<Branch, Integer> tcBranchId;
	private TableColumn<Branch, Integer> tcMenuId;
	private TableColumn<Branch, String> tcPhoneNumber;
	private TableColumn<Branch, String> tcOpeningHours;
	private TableColumn<Branch, String> tcLocation;
	private TextField txtBranchId;
	private TextField txtPhoneNumber;
	private TextField txtLocation;
	private TextField txtOpeningHours;
	private TextField txtMenuId;
	private TextField txtResturantId;

	@Override
	public void start(Stage primaryStage) {
		
		System.out.println("hello");

		// Create the root AnchorPane
		AnchorPane root = new AnchorPane();
		root.setPrefSize(722, 396);

		// Create the first VBox with Labels
		VBox labelVBox = new VBox(15);
		labelVBox.setLayoutX(2.0);
		labelVBox.setLayoutY(37.0);
		labelVBox.setPrefSize(93, 133);

		// Create Labels
		Label lblBranchId = new Label("branch_id");
		lblBranchId.setFont(new Font(14));

		Label lblPhoneNumber = new Label("phoneNumber");
		lblPhoneNumber.setFont(new Font(14));

		Label lblLocation = new Label("location");
		lblLocation.setFont(new Font(14));

		Label lblOpeningHours = new Label("openinghours");
		lblOpeningHours.setFont(new Font(14));

		Label lblMenuId = new Label("menu_id");
		lblMenuId.setFont(new Font(14));

		Label lblResturantId = new Label("resturant_id");
		lblResturantId.setFont(new Font(14));

		// Add labels to the VBox
		labelVBox.getChildren().addAll(lblBranchId, lblPhoneNumber, lblLocation, lblOpeningHours, lblMenuId,
				lblResturantId);

		// Create the second VBox with TextFields
		VBox textFieldVBox = new VBox(10);
		textFieldVBox.setLayoutX(101.0);
		textFieldVBox.setLayoutY(37.0);
		textFieldVBox.setPrefSize(126, 158);

		// Create TextFields
		txtBranchId = new TextField();
		txtBranchId.setAccessibleText("branch_idtxt");
		txtBranchId.setPrefSize(117, 26);

		txtPhoneNumber = new TextField();
		txtPhoneNumber.setAccessibleText("phoneNumbertxt");

		txtLocation = new TextField();
		txtLocation.setAccessibleText("locationtxt");

		txtOpeningHours = new TextField();
		txtOpeningHours.setAccessibleText("opening_hourstxt");

		txtMenuId = new TextField();
		txtMenuId.setAccessibleText("menu_idtxt");

		txtResturantId = new TextField();
		txtResturantId.setAccessibleText("resturant_idtxt");

		// Add text fields to the VBox
		textFieldVBox.getChildren().addAll(txtBranchId, txtPhoneNumber, txtLocation, txtOpeningHours, txtMenuId,
				txtResturantId);

		tableView = new TableView<>();
		tableView.setId("tv_sailors");
		tableView.setAccessibleText("rest_tableVeiw");
		tableView.setLayoutX(243.0);
		tableView.setLayoutY(32.0);
		tableView.setPrefSize(453.0, 352.0);

		// Create the TableColumns
		tcRestId = new TableColumn<>("Rest_id");
		tcRestId.setPrefWidth(64.0);

		tcBranchId = new TableColumn<>("branch_id");
		tcBranchId.setPrefWidth(78.67);

		tcMenuId = new TableColumn<>("menu_id");
		tcMenuId.setPrefWidth(112.0);

		tcPhoneNumber = new TableColumn<>("phoneNumber");
		tcPhoneNumber.setPrefWidth(75.0);

		tcOpeningHours = new TableColumn<>("openinghours");
		tcOpeningHours.setPrefWidth(75.0);

		tcLocation = new TableColumn<>("location");
		tcLocation.setPrefWidth(75.0);

		// Add the columns to the TableView
		tableView.getColumns().addAll(tcRestId, tcBranchId, tcMenuId, tcPhoneNumber, tcOpeningHours, tcLocation);

		tableView.setOnMouseClicked(e -> {
			handleRowSelection(e);
		});
		// Create buttons
		Button btnInsert = new Button("insert");
		btnInsert.setAccessibleText("insertbtn");
		btnInsert.setLayoutX(125);
		btnInsert.setLayoutY(255);
		btnInsert.setPrefSize(78, 26);
		btnInsert.setOnAction(event -> {
			insertRecord();
		});

		Button btnUpdate = new Button("update");
		btnUpdate.setAccessibleText("updatebtn");
		btnUpdate.setLayoutX(125);
		btnUpdate.setLayoutY(294);
		btnUpdate.setPrefSize(78, 26);
		btnUpdate.setOnAction(event -> {
			try {
				updateRecord();
			} catch (Exception e) {
				displayAlert("ERROR ! check enteries");
			}
		});

		Button btnDelete = new Button("delete");
		btnDelete.setAccessibleText("deletebtn");
		btnDelete.setLayoutX(125);
		btnDelete.setLayoutY(332);
		btnDelete.setPrefSize(78, 26);
		btnDelete.setOnAction(event -> {
			deleteButton();
		});

		// Add all components to the root AnchorPane
		root.getChildren().addAll(labelVBox, textFieldVBox, tableView, btnInsert, btnUpdate, btnDelete);

		// Create a scene and set the stage
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX Application");
		primaryStage.show();
		showBranches();
	}

	// Event handlers (insert, update, and delete) should be implemented according
	// to your requirements.
	private void insertRecord() {
		int branchID = Integer.parseInt(txtBranchId.getText().trim());
		int menuID = Integer.parseInt(txtMenuId.getText().trim());
		int restID = Integer.parseInt(txtResturantId.getText().trim());

		System.out.println("branchID: " + branchID);
		if (branchIDExists(branchID)) {
			displayAlert("branch_id already exists.");
			System.out.println("heelooooo1");
			return;
		} else {
			if (!menuIDExists(menuID)) {
				displayAlert("menu_id not exists.");
				System.out.println("heelooooo2");
				return;
			} else {
				if (!restIDExists(restID)) {
					displayAlert("resturant_id not exists.");
				} else {
					String query = "INSERT INTO phase3.branch VALUES ('" + branchID + "','"
							+ txtPhoneNumber.getText().trim() + "','" + txtLocation.getText().trim() + "','"
							+ txtOpeningHours.getText().trim() + "','" + menuID + "','" + restID + "')";
					executeQuery(query);
					showBranches();
				}
			}
		}

	}

	private boolean branchIDExists(int branch_id) {
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.branch where branch.branch_id='" + branch_id + "'";
			ResultSet rs = stmt.executeQuery(quere);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private boolean menuIDExists(int menu_id) {

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.menu where menu.menu_id='" + menu_id + "'";
			ResultSet rs = stmt.executeQuery(quere);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private boolean restIDExists(int resturant_id) {

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.resturant where resturant.resturant_id='" + resturant_id + "'";
			ResultSet rs = stmt.executeQuery(quere);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private void updateRecord() throws Exception {
//		int index = tableView.getSelectionModel().getSelectedIndex();
//		System.out.println("Index is :" + index);
//		if (index <= -1) {
//			System.out.println("No row selected.");
//			return;
//		}
//
//		Branch selectedBranch = tableView.getSelectionModel().getSelectedItem();
//		int branchID =selectedBranch.getBranch_id();
//		int menuID =selectedBranch.getMenu_id();
//		int restID =selectedBranch.getResturant_id();
//		String location =selectedBranch.getLocation();
//		String phone =selectedBranch.getPhoneNumber();
//		String houres =selectedBranch.getOpeninghours();
//		
		int branchID2 = Integer.parseInt(txtBranchId.getText().trim());
		int menuID2 = Integer.parseInt(txtMenuId.getText().trim());
		int restID2 = Integer.parseInt(txtResturantId.getText().trim());

		try {
			String query = "UPDATE phase3.branch SET menu_id = '" + menuID2 + "', phoneNumber = '"
					+ txtPhoneNumber.getText().trim() + "', location = '" + txtLocation.getText().trim()
					+ "', openinghours = '" + txtOpeningHours.getText().trim() + "', resturant_id = " + restID2
					+ " WHERE branch_id = " + branchID2;
			executeQuery(query);
		} catch (Exception e) {
			throw new Exception();
		}
		showBranches();

	}

	private void deleteButton() {
		int branchID2 = Integer.parseInt(txtBranchId.getText().trim());
		String query = "DELETE FROM phase3.branch WHERE branch_id =" + branchID2 + "";
		executeQuery(query);
		showBranches();
	}

	public static void main(String[] args) {
		Connection con = DBConnection.getConnection();
		if (con == null) {
			System.out.println("Connection Failed");
		} else {
			System.out.println("Connection Success");

		}
		launch(args);
	}

	private void showBranches() {
		String query = "SELECT * FROM phase3.branch";
		ObservableList<Branch> branchesList = getBranches(query);
		// Bind columns with properties
		tcBranchId.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("branch_id"));
		tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<Branch, String>("phoneNumber"));
		tcLocation.setCellValueFactory(new PropertyValueFactory<Branch, String>("location"));
		tcOpeningHours.setCellValueFactory(new PropertyValueFactory<Branch, String>("openinghours"));
		tcMenuId.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("menu_id"));
		tcRestId.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("resturant_id"));

		tableView.setItems(branchesList);

	}

	public ObservableList<Branch> getBranches(String query) {
		ObservableList<Branch> branchesList = FXCollections.observableArrayList();

		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {

			while (rs.next()) {
				Branch branch = new Branch(rs.getInt("branch_id"), rs.getString("phoneNumber"),
						rs.getString("location"), rs.getString("openinghours"), rs.getInt("menu_id"),
						rs.getInt("resturant_id"));
				branchesList.add(branch);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return branchesList;
	}

	public void handleRowSelection(MouseEvent event) {
		System.out.println("Inside handleRowSelection.");
		int index = tableView.getSelectionModel().getSelectedIndex();
		System.out.println("Index is :" + index);
		if (index <= -1) {
			System.out.println("No row selected.");
			return;
		}

		Branch selectedBranch = tableView.getSelectionModel().getSelectedItem();
		if (selectedBranch != null) {
			txtBranchId.setText(String.valueOf(selectedBranch.getBranch_id()));
			txtLocation.setText(selectedBranch.getLocation());
			txtMenuId.setText(String.valueOf(selectedBranch.getMenu_id()));
			txtOpeningHours.setText(String.valueOf(selectedBranch.getOpeninghours()));
			txtPhoneNumber.setText(String.valueOf(selectedBranch.getPhoneNumber()));
			txtResturantId.setText(String.valueOf(selectedBranch.getResturant_id()));
		}
	}

	private void displayAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void executeQuery(String query) {
		Connection conn = DBConnection.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

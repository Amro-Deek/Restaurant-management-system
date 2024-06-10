package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CusPane extends Pane {

	private AnchorPane root;
	private TableView<Customer> tableView;
	private TableColumn<Customer, Integer> customerIdColumn;
	private TableColumn<Customer, String> nameColumn;
	private TableColumn<Customer, Integer> addressIdColumn;
	private TextField customerIdTextField;
	private TextField nameTextField;
	private TextField addressIdTextField;
	private Label customerIdLabel;
	private Label nameLabel;
	private Label addressIdLabel;
	private Button deleteButton1;
	private Button updateButton;
	private Button insertButton;
	private Button deleteButton2;

	private Customer selectedCustomer;

	public CusPane() {
		root = new AnchorPane();
		root.setPrefSize(764.0, 557.0);

		tableView = new TableView<>();
		tableView.setLayoutX(406.0);
		tableView.setLayoutY(27.0);
		tableView.setPrefSize(331.0, 255.0);
		AnchorPane.setBottomAnchor(tableView, 275.0);
		AnchorPane.setLeftAnchor(tableView, 406.0);
		AnchorPane.setRightAnchor(tableView, 27.0);
		AnchorPane.setTopAnchor(tableView, 27.0);

		customerIdColumn = new TableColumn<>("customerId");
		nameColumn = new TableColumn<>("name");
		addressIdColumn = new TableColumn<>("addressId");

		tableView.getColumns().addAll(customerIdColumn, nameColumn, addressIdColumn);

		customerIdTextField = new TextField();
		customerIdTextField.setLayoutX(232.0);
		customerIdTextField.setLayoutY(64.0);

		nameTextField = new TextField();
		nameTextField.setLayoutX(232.0);
		nameTextField.setLayoutY(102.0);

		addressIdTextField = new TextField();
		addressIdTextField.setLayoutX(232.0);
		addressIdTextField.setLayoutY(142.0);

		customerIdLabel = new Label("customer_id");
		customerIdLabel.setLayoutX(67.0);
		customerIdLabel.setLayoutY(65.0);
		customerIdLabel.setTextFill(javafx.scene.paint.Color.RED);
		customerIdLabel.setFont(new Font("Serif Bold", 20.0));

		nameLabel = new Label("customer_name");
		nameLabel.setLayoutX(62.0);
		nameLabel.setLayoutY(104.0);
		nameLabel.setTextFill(javafx.scene.paint.Color.RED);
		nameLabel.setFont(new Font("Serif Bold", 19.0));

		addressIdLabel = new Label("address_id");
		addressIdLabel.setLayoutX(68.0);
		addressIdLabel.setLayoutY(144.0);
		addressIdLabel.setTextFill(javafx.scene.paint.Color.RED);
		addressIdLabel.setFont(new Font("Serif Bold", 19.0));

		deleteButton1 = new Button("delete");
		deleteButton1.setLayoutX(270.0);
		deleteButton1.setLayoutY(186.0);
		deleteButton1.setMnemonicParsing(false);
		deleteButton1.setPrefSize(73.0, 26.0);
		deleteButton1.setOnAction(event -> {
			if (selectedCustomer == null) {
				displayAlert("Please choose a Customer !");
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are You Sure ? ");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					deleteButton();
				}
			}
		});

		updateButton = new Button("update");
		updateButton.setLayoutX(160.0);
		updateButton.setLayoutY(186.0);
		updateButton.setMnemonicParsing(false);
		updateButton.setPrefSize(71.0, 26.0);
		updateButton.setOnAction(event -> {
			try {
				updateRecord();
			} catch (Exception e) {
				displayAlert("ERROR ! check enteries");
			}
		});

		insertButton = new Button("insert");
		insertButton.setLayoutX(49.0);
		insertButton.setLayoutY(186.0);
		insertButton.setMnemonicParsing(false);
		insertButton.setPrefSize(71.0, 26.0);
		insertButton.setOnAction(event -> {
			insertRecord();
		});

		deleteButton2 = new Button("SHow");
		deleteButton2.setLayoutX(115.0);
		deleteButton2.setLayoutY(236.0);
		deleteButton2.setMnemonicParsing(false);
		deleteButton2.setPrefSize(161.0, 26.0);

		tableView.setOnMouseClicked(e -> {
			handleRowSelection(e);
		});

		root.getChildren().addAll(tableView, customerIdTextField, nameTextField, addressIdTextField, customerIdLabel,
				nameLabel, addressIdLabel, deleteButton1, updateButton, insertButton, deleteButton2);

		showCustomers();
		getChildren().add(root);
	}

	private void insertRecord() {
		int cusId;
		try {
			cusId = Integer.parseInt(customerIdTextField.getText().trim());
			int addressId = Integer.parseInt(addressIdTextField.getText().trim());

			System.out.println("cusId: " + cusId);
			if (cusIDExists(cusId)) {
				displayAlert("cusId already exists.");
				System.out.println("heelooooo1");
				return;
			} else {
				if (!addressIDExists(addressId)) {
					displayAlert("address_id not exists.");
				} else {
					String query = "INSERT INTO phase3.customer VALUES ('" + cusId + "','"
							+ nameTextField.getText().trim() + "','" + addressId + "')";
					executeQuery(query);
					showCustomers();
				}

			}
		} catch (NumberFormatException wx) {

		}

	}

	private void updateRecord() throws Exception {
		int cusID2 = Integer.parseInt(customerIdTextField.getText().trim());
		int addressID2 = Integer.parseInt(addressIdTextField.getText().trim());

		try {
			String query = "UPDATE phase3.customer SET customer_name = '" + nameTextField.getText().trim()
					+ "', address_id = '" + addressID2 + "' WHERE customer_id = " + cusID2;
			executeQuery(query);
		} catch (Exception e) {
			throw new Exception();
		}
		showCustomers();
	}

	private void deleteButton() {
		int cusID2;
		try {
			cusID2 = Integer.parseInt(customerIdTextField.getText().trim());
			String query = "DELETE FROM phase3.customer WHERE customer_id =" + cusID2 + "";
			executeQuery(query);
			showCustomers();
			;
			;
		} catch (NumberFormatException ex) {

		}

	}

	private boolean cusIDExists(int customer_id) {

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.customer where customer.customer_id='" + customer_id + "'";
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

	private boolean addressIDExists(int address_id) {
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.address where address.address_id='" + address_id + "'";
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

	private void showCustomers() {
		String query = "SELECT * FROM phase3.customer";
		ObservableList<Customer> customersList = getCustomers(query);
		customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_name"));
		addressIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("address_id"));

		tableView.setItems(customersList);

	}

	public ObservableList<Customer> getCustomers(String query) {
		ObservableList<Customer> customersList = FXCollections.observableArrayList();
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"),
						rs.getInt("address_id"));
				customersList.add(customer);
			}
		} catch (SQLException ex) {
		}
		return customersList;
	}

	public void handleRowSelection(MouseEvent event) {
		System.out.println("Inside handleRowSelection.");
		int index = tableView.getSelectionModel().getSelectedIndex();
		System.out.println("Index is :" + index);
		if (index <= -1) {
			System.out.println("No row selected.");
			return;
		}
		Customer selectedcustomer = tableView.getSelectionModel().getSelectedItem();
		int id = selectedcustomer.getCustomer_id();
		if (selectedcustomer != null) {
			customerIdTextField.setText(String.valueOf(selectedcustomer.getCustomer_id()));
			nameTextField.setText(String.valueOf(selectedcustomer.getCustomer_name()));
			addressIdTextField.setText(String.valueOf(selectedcustomer.getAddress_id()));

		}
		selectedCustomer = selectedcustomer;

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

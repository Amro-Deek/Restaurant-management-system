package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
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

public class EmployeePane extends Pane {

	private AnchorPane root;
	private TableView<Employee> tableView;
	private TableColumn<Employee, Integer> empIdColumn;
	private TableColumn<Employee, Integer> branchIdColumn;
	private TableColumn<Employee, String> nameColumn;
	private TableColumn<Employee, String> positionColumn;
	private TableColumn<Employee, String> contactInfoColumn;
	private TableColumn<Employee, Integer> salaryColumn;
	private TextField empIdTextField;
	private TextField branchIdTextField;
	private TextField contactInfoTextField;
	private TextField positionTextField;
	private TextField nameTextField;
	private TextField salaryTextField;
	private Label empIdLabel;
	private Label branchIdLabel;
	private Label contactInfoLabel;
	private Label positionLabel;
	private Label nameLabel;
	private Label salaryLabel;
	private Button deleteButton;
	private Button updateButton;
	private Button insertButton;

	private Employee selectedEmployee;

	public EmployeePane() {
		root = new AnchorPane();
		root.setPrefSize(949.0, 595.0);

		tableView = new TableView<>();
		tableView.setLayoutX(354.0);
		tableView.setLayoutY(26.4);
		tableView.setPrefSize(563.0, 293.0);
		AnchorPane.setBottomAnchor(tableView, 276.0);
		AnchorPane.setLeftAnchor(tableView, 354.0);
		AnchorPane.setRightAnchor(tableView, 32.0);
		AnchorPane.setTopAnchor(tableView, 26.0);

		empIdColumn = new TableColumn<>("empId");
		branchIdColumn = new TableColumn<>("branchId");
		nameColumn = new TableColumn<>("name");
		positionColumn = new TableColumn<>("position");
		contactInfoColumn = new TableColumn<>("contactInfo");
		salaryColumn = new TableColumn<>("salary");

		tableView.getColumns().addAll(empIdColumn, branchIdColumn, nameColumn, positionColumn, contactInfoColumn,
				salaryColumn);

		empIdTextField = new TextField();
		empIdTextField.setLayoutX(155.0);
		empIdTextField.setLayoutY(26.0);

		branchIdTextField = new TextField();
		branchIdTextField.setLayoutX(155.0);
		branchIdTextField.setLayoutY(63.0);

		contactInfoTextField = new TextField();
		contactInfoTextField.setLayoutX(155.0);
		contactInfoTextField.setLayoutY(103.0);

		positionTextField = new TextField();
		positionTextField.setLayoutX(154.0);
		positionTextField.setLayoutY(145.0);

		nameTextField = new TextField();
		nameTextField.setLayoutX(155.0);
		nameTextField.setLayoutY(183.0);

		salaryTextField = new TextField();
		salaryTextField.setLayoutX(155.0);
		salaryTextField.setLayoutY(223.0);

		empIdLabel = new Label("emp_Id");
		empIdLabel.setLayoutX(46.0);
		empIdLabel.setLayoutY(26.0);
		empIdLabel.setTextFill(javafx.scene.paint.Color.RED);
		empIdLabel.setFont(new Font("Serif Bold", 20.0));

		branchIdLabel = new Label("branch_Id");
		branchIdLabel.setLayoutX(40.0);
		branchIdLabel.setLayoutY(65.0);
		branchIdLabel.setTextFill(javafx.scene.paint.Color.RED);
		branchIdLabel.setFont(new Font("Serif Bold", 19.0));

		contactInfoLabel = new Label("contactInfo");
		contactInfoLabel.setLayoutX(36.0);
		contactInfoLabel.setLayoutY(105.0);
		contactInfoLabel.setTextFill(javafx.scene.paint.Color.RED);
		contactInfoLabel.setFont(new Font("Serif Bold", 19.0));

		positionLabel = new Label("position");
		positionLabel.setLayoutX(36.0);
		positionLabel.setLayoutY(146.0);
		positionLabel.setTextFill(javafx.scene.paint.Color.RED);
		positionLabel.setFont(new Font("Serif Bold", 21.0));

		nameLabel = new Label("name");
		nameLabel.setLayoutX(38.0);
		nameLabel.setLayoutY(184.0);
		nameLabel.setTextFill(javafx.scene.paint.Color.RED);
		nameLabel.setFont(new Font("Serif Bold", 20.0));

		salaryLabel = new Label("salary");
		salaryLabel.setLayoutX(35.0);
		salaryLabel.setLayoutY(224.0);
		salaryLabel.setTextFill(javafx.scene.paint.Color.RED);
		salaryLabel.setFont(new Font("Serif Bold", 20.0));

		deleteButton = new Button("delete");
		deleteButton.setLayoutX(257.0);
		deleteButton.setLayoutY(272.0);
		deleteButton.setMnemonicParsing(false);
		deleteButton.setPrefSize(73.0, 26.0);
		deleteButton.setOnAction(event -> {
			if (selectedEmployee == null) {
				displayAlert("Please choose a Employee !");
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
		updateButton.setLayoutX(151.0);
		updateButton.setLayoutY(272.0);
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
		insertButton.setLayoutX(47.0);
		insertButton.setLayoutY(272.0);
		insertButton.setMnemonicParsing(false);
		insertButton.setPrefSize(71.0, 26.0);
		insertButton.setOnAction(event -> {
			insertRecord();
		});

		tableView.setOnMouseClicked(e -> {
			handleRowSelection(e);
		});

		root.getChildren().addAll(tableView, empIdTextField, branchIdTextField, contactInfoTextField, positionTextField,
				nameTextField, salaryTextField, empIdLabel, branchIdLabel, contactInfoLabel, positionLabel, nameLabel,
				salaryLabel, deleteButton, updateButton, insertButton);
		showEmployees();
		getChildren().add(root);
	}

	private void insertRecord() {
		int empId;
		try {
			empId = Integer.parseInt(empIdTextField.getText().trim());
			int branchId = Integer.parseInt(branchIdTextField.getText().trim());

			System.out.println("empId: " + empId);
			if (empIDExists(empId)) {
				displayAlert("emp_id already exists.");
				System.out.println("heelooooo1");
				return;
			} else {
				if (!branchIDExists(branchId)) {
					displayAlert("branchId not exists.");
				} else {
					String query = "INSERT INTO phase3.employee VALUES ('" + empId + "','" + branchId + "','"
							+ contactInfoTextField.getText().trim() + "','" + positionTextField.getText().trim() + "','"
							+ nameTextField.getText().trim() + "','"
							+ Double.parseDouble(salaryTextField.getText().trim()) + "')";
					executeQuery(query);
					showEmployees();
					;
				}

			}
		} catch (NumberFormatException wx) {

		}

	}

	private void updateRecord() throws Exception {
		int empID2 = Integer.parseInt(empIdTextField.getText().trim());
		int branchID2 = Integer.parseInt(branchIdTextField.getText().trim());

		try {
			String query = "UPDATE phase3.employee SET salary = '"
					+ Double.parseDouble(salaryTextField.getText().trim()) + "', branch_id = '" + branchID2
					+ "', contactInfo = '" + contactInfoTextField.getText().trim() + "', position = '"
					+ positionTextField.getText().trim() + "', name = '" + nameTextField.getText().trim()
					+ "' WHERE emp_id = " + empID2;
			executeQuery(query);
		} catch (Exception e) {
			throw new Exception();
		}
		showEmployees();
		;
	}

	private void deleteButton() {
		int empID2;
		try {
			empID2 = Integer.parseInt(empIdTextField.getText().trim());
			String query = "DELETE FROM phase3.employee WHERE emp_id =" + empID2 + "";
			executeQuery(query);
			showEmployees();
			;
		} catch (NumberFormatException ex) {

		}

	}

	private boolean empIDExists(int emp_id) {

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.employee where employee.emp_id='" + emp_id + "'";
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

	private void showEmployees() {
		String query = "SELECT * FROM phase3.employee";
		ObservableList<Employee> employeesList = getEmployees(query);
		empIdColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("empId"));
		branchIdColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("branchId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		positionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
		contactInfoColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("contactInfo"));
		salaryColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("salary"));

		tableView.setItems(employeesList);

	}

	public ObservableList<Employee> getEmployees(String query) {
		ObservableList<Employee> employeesList = FXCollections.observableArrayList();
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				Employee employee = new Employee(rs.getInt("emp_id"), rs.getInt("branch_id"),
						rs.getString("contactInfo"), rs.getString("position"), rs.getString("name"),
						rs.getInt("salary"));
				employeesList.add(employee);
			}
		} catch (SQLException ex) {
		}
		return employeesList;
	}

	public void handleRowSelection(MouseEvent event) {
		System.out.println("Inside handleRowSelection.");
		int index = tableView.getSelectionModel().getSelectedIndex();
		System.out.println("Index is :" + index);
		if (index <= -1) {
			System.out.println("No row selected.");
			return;
		}
		Employee selectedemployee = tableView.getSelectionModel().getSelectedItem();
		int id = selectedemployee.getEmpId();
		if (selectedemployee != null) {
			empIdTextField.setText(String.valueOf(selectedemployee.getEmpId()));
			branchIdTextField.setText(String.valueOf(selectedemployee.getBranchId()));
			contactInfoTextField.setText(String.valueOf(selectedemployee.getContactInfo()));
			positionTextField.setText(String.valueOf(selectedemployee.getPosition()));
			nameTextField.setText(String.valueOf(selectedemployee.getName()));
			salaryTextField.setText(String.valueOf(selectedemployee.getSalary()));

		}
		selectedEmployee = selectedemployee;

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

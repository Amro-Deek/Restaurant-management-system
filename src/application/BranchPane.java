package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BranchPane extends AnchorPane {
	// --> Amro Deek <-- 

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

	// labels
	private Label lblOrdersInDay;
	private Label lblOrdersInMonth;
	private Label lblOrdersInYear;
	private Label lblNumOfEmployees;
	private Label lblNumOfTables;
	private Label lblAvgSalaries;

	private Branch selectedBranch;

	private PieChart branchMenuItemChart;

	public BranchPane() {

		AnchorPane root = new AnchorPane();
		// root.setPrefSize(722, 396);
		root.setPrefSize(887, 551);

		// Create the first VBox with Labels
		VBox labelVBox = new VBox(18);
		labelVBox.setLayoutX(6);
		labelVBox.setLayoutY(21);
		labelVBox.setPrefSize(93, 133);

		// Create Labels
		Label lblBranchId = new Label("branch_id");
		lblBranchId.setFont(new Font("Serif Bold", 14));
		lblBranchId.setStyle("-fx-font: bold 15px serif;");

		Label lblPhoneNumber = new Label("phoneNumber");
		lblPhoneNumber.setFont(new Font("Serif Bold", 14));
		lblPhoneNumber.setStyle("-fx-font: bold 15px serif;");

		Label lblLocation = new Label("location");
		lblLocation.setFont(new Font("Serif Bold", 14));
		lblLocation.setStyle("-fx-font: bold 15px serif;");

		Label lblOpeningHours = new Label("openinghours");
		lblOpeningHours.setFont(new Font("Serif Bold", 14));
		lblOpeningHours.setStyle("-fx-font: bold 15px serif;");

		Label lblMenuId = new Label("menu_id");
		lblMenuId.setFont(new Font("Serif Bold", 14));
		lblMenuId.setStyle("-fx-font: bold 15px serif;");

		Label lblResturantId = new Label("resturant_id");
		lblResturantId.setFont(new Font("Serif Bold", 14));
		lblResturantId.setStyle("-fx-font: bold 15px serif;");

		labelVBox.getChildren().addAll(lblBranchId, lblPhoneNumber, lblLocation, lblOpeningHours, lblMenuId,
				lblResturantId);

		VBox textFieldVBox = new VBox(10);
		textFieldVBox.setLayoutX(107);
		textFieldVBox.setLayoutY(19);
		textFieldVBox.setPrefSize(162, 202);

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

		textFieldVBox.getChildren().addAll(txtBranchId, txtPhoneNumber, txtLocation, txtOpeningHours, txtMenuId,
				txtResturantId);

		tableView = new TableView<>();
		tableView.setId("tv_sailors");
		tableView.setAccessibleText("rest_tableVeiw");
		tableView.setLayoutX(297);
		tableView.setLayoutY(13);
		tableView.setPrefSize(562, 253);

		tcRestId = new TableColumn<>("Rest_id");
		tcRestId.setPrefWidth(64.0);

		tcBranchId = new TableColumn<>("branch_id");
		tcBranchId.setPrefWidth(78.67);

		tcMenuId = new TableColumn<>("menu_id");
		tcMenuId.setPrefWidth(75.0);

		tcPhoneNumber = new TableColumn<>("phoneNumber");
		tcPhoneNumber.setPrefWidth(132.67);

		tcOpeningHours = new TableColumn<>("openinghours");
		tcOpeningHours.setPrefWidth(102.0);

		tcLocation = new TableColumn<>("location");
		tcLocation.setPrefWidth(106.67);

		tableView.getColumns().addAll(tcRestId, tcBranchId, tcMenuId, tcPhoneNumber, tcOpeningHours, tcLocation);

		tableView.setOnMouseClicked(e -> {
			handleRowSelection(e);
		});
		Button btnInsert = new Button("insert");
		btnInsert.setLayoutX(14);
		btnInsert.setLayoutY(237);
		btnInsert.setPrefSize(78, 26);
		btnInsert.setTextFill(javafx.scene.paint.Color.RED);
		btnInsert.setOnAction(event -> {
			insertRecord();
		});

		Button btnUpdate = new Button("update");
		btnUpdate.setLayoutX(95);
		btnUpdate.setLayoutY(237);
		btnUpdate.setPrefSize(88, 26);
		btnUpdate.setTextFill(javafx.scene.paint.Color.RED);
		btnUpdate.setOnAction(event -> {
			try {
				updateRecord();
			} catch (Exception e) {
				displayAlert("ERROR ! check enteries");
			}
		});
		Button btnDelete = new Button("delete");
		btnDelete.setLayoutX(188);
		btnDelete.setLayoutY(237);
		btnDelete.setPrefSize(78, 26);
		btnDelete.setTextFill(javafx.scene.paint.Color.RED);
		btnDelete.setOnAction(event -> {
			if (selectedBranch == null) {
				displayAlert("Please choose a Branch !");
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are You Sure ? ");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					deleteButton();
				}
			}
		});

		VBox vboxArabicLabels = new VBox(20);
		vboxArabicLabels.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
		vboxArabicLabels.setLayoutX(689);
		vboxArabicLabels.setLayoutY(280);
		vboxArabicLabels.setPrefSize(179, 210);

		lblOrdersInDay = new Label("إجمالي عدد الطلبات لهذا اليوم : ");
		lblOrdersInDay.setStyle("-fx-font: bold 15px serif;");
		lblOrdersInDay.setAccessibleText("ordersInDayLbl");

		lblOrdersInMonth = new Label("إجمالي عدد الطلبات لهذا الشهر : ");
		lblOrdersInMonth.setStyle("-fx-font: bold 15px serif;");
		lblOrdersInMonth.setAccessibleText("ordersInMonthLbl");

		lblOrdersInYear = new Label("إجمالي عدد الطلبات لهذه السنة : ");
		lblOrdersInYear.setStyle("-fx-font: bold 15px serif;");
		lblOrdersInYear.setAccessibleText("ordersInYearLbl");

		lblNumOfEmployees = new Label("عدد الموظفين : ");
		lblNumOfEmployees.setStyle("-fx-font: bold 15px serif;");
		lblNumOfEmployees.setAccessibleText("numOfEmployeesLbl");

		lblNumOfTables = new Label("عدد الطاولات : ");
		lblNumOfTables.setStyle("-fx-font: bold 15px serif;");
		lblNumOfTables.setAccessibleText("numOfTablesLbl");

		lblAvgSalaries = new Label("متوسط رواتب الموظفين : ");
		lblAvgSalaries.setStyle("-fx-font: bold 15px serif;");
		lblAvgSalaries.setAccessibleText("avgSalariesLbl");

		vboxArabicLabels.getChildren().addAll(lblOrdersInDay, lblOrdersInMonth, lblOrdersInYear, lblNumOfEmployees,
				lblNumOfTables, lblAvgSalaries);

		// Show Employees Button
		Button btnShowEmployees = new Button("Show Employees");
		btnShowEmployees.setLayoutX(89);
		btnShowEmployees.setLayoutY(276);
		btnShowEmployees.setTextFill(javafx.scene.paint.Color.RED);
		btnShowEmployees.setAccessibleText("ShowEmployeesbtn");
		btnShowEmployees.setOnAction(e -> {
			if (selectedBranch != null) {
				String q = "select * from phase3.employee e where e.branch_id = '" + selectedBranch.getBranch_id()
						+ "'";
				ObservableList<Employee> list = gitEmplyeesForBranch(q);
				TableView<Employee> tv = new TableView<Employee>();
				// Create columns
				TableColumn<Employee, Integer> empIdCol = new TableColumn<>("Emp ID");
				empIdCol.setCellValueFactory(new PropertyValueFactory<>("empId"));

				TableColumn<Employee, Integer> branchIdCol = new TableColumn<>("Branch ID");
				branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));

				TableColumn<Employee, String> contactInfoCol = new TableColumn<>("Contact Info");
				contactInfoCol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

				TableColumn<Employee, String> positionCol = new TableColumn<>("Position");
				positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

				TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
				nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

				TableColumn<Employee, Integer> salaryCol = new TableColumn<>("Salary");
				salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

				// Add columns to the table
				tv.getColumns().add(empIdCol);
				tv.getColumns().add(branchIdCol);
				tv.getColumns().add(contactInfoCol);
				tv.getColumns().add(positionCol);
				tv.getColumns().add(nameCol);
				tv.getColumns().add(salaryCol);
				tv.setItems(list);
				Scene sc = new Scene(tv);
				Stage s = new Stage();
				s.setScene(sc);
				s.setTitle("Emplyees for Branch " + selectedBranch.getBranch_id());
				s.show();

			} else {
				displayAlert("Please Select A Branch !");
			}
		});

		branchMenuItemChart = new PieChart();
		root.getChildren().addAll(labelVBox, textFieldVBox, tableView, btnInsert, btnUpdate, btnDelete,
				vboxArabicLabels, btnShowEmployees, branchMenuItemChart);
		showBranches();
		getChildren().add(root);
	}

	private void insertRecord() {
		int branchID;
		try {
			branchID = Integer.parseInt(txtBranchId.getText().trim());
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
		} catch (NumberFormatException wx) {

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
		int branchID2;
		try {
			branchID2 = Integer.parseInt(txtBranchId.getText().trim());
			String query = "DELETE FROM phase3.branch WHERE branch_id =" + branchID2 + "";
			executeQuery(query);
			showBranches();
		} catch (NumberFormatException ex) {

		}

	}

	private void showBranches() {
		String query = "SELECT * FROM phase3.branch";
		ObservableList<Branch> branchesList = getBranches(query);
		tcBranchId.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("branch_id"));
		tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<Branch, String>("phoneNumber"));
		tcLocation.setCellValueFactory(new PropertyValueFactory<Branch, String>("location"));
		tcOpeningHours.setCellValueFactory(new PropertyValueFactory<Branch, String>("openinghours"));
		tcMenuId.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("menu_id"));
		tcRestId.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("resturant_id"));
		tableView.setItems(branchesList);
	}

	public ObservableList<Employee> gitEmplyeesForBranch(String query) {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				Employee emp = new Employee(rs.getInt("emp_id"), rs.getInt("branch_id"), rs.getString("contactInfo"),
						rs.getString("position"), rs.getString("name"), rs.getInt("salary"));
				list.add(emp);
			}
		} catch (SQLException ex) {
		}
		return list;
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
		Branch selectedbranch = tableView.getSelectionModel().getSelectedItem();
		int id = selectedbranch.getBranch_id();
		if (selectedbranch != null) {
			txtBranchId.setText(String.valueOf(selectedbranch.getBranch_id()));
			txtLocation.setText(selectedbranch.getLocation());
			txtMenuId.setText(String.valueOf(selectedbranch.getMenu_id()));
			txtOpeningHours.setText(String.valueOf(selectedbranch.getOpeninghours()));
			txtPhoneNumber.setText(String.valueOf(selectedbranch.getPhoneNumber()));
			txtResturantId.setText(String.valueOf(selectedbranch.getResturant_id()));
			LocalDate currentDate = LocalDate.now(); // Current date

			int day = currentDate.getDayOfMonth();
			int month = currentDate.getMonthValue(); // Months are 1-based in LocalDate
			int year = currentDate.getYear();
			lblOrdersInDay.setText("إجمالي عدد الطلبات لهذا اليوم : " + totalOrdersInDay(day, month, year, id));
			lblOrdersInMonth.setText("إجمالي عدد الطلبات لهذا الشهر : " + totalOrdersInMonth(month, year, id));
			lblOrdersInYear.setText("إجمالي عدد الطلبات لهذه السنة : " + totalOrdersInYear(year, id));
			lblNumOfEmployees.setText("عدد الموظفين : " + NumOfEmp(id));
			lblNumOfTables.setText("عدد الطاولات : " + NumOfTables(id));
			lblAvgSalaries.setText("متوسط رواتب الموظفين : " + avgSalaries(id));

			// branchMenuItemChart = new PieChart();
			branchMenuItemChart.setAccessibleText("");
			branchMenuItemChart.setLayoutX(300);
			branchMenuItemChart.setLayoutY(280);
			branchMenuItemChart.setPrefHeight(220);
			branchMenuItemChart.setPrefWidth(320);
			branchMenuItemChart.getData().clear();
			branchMenuItemChart.setTitle("المبيعات لكل منتج في الفرع " + selectedbranch.getBranch_id());
			String query = "select mi.name ,count(*) as count from menu_item mi , orders o , order_menu_item omi where o.branch_id = '"
					+ selectedbranch.getBranch_id()
					+ "' and mi.item_id = omi.item_id and o.order_id=omi.order_id group by mi.item_id";
			try (Connection conn = DBConnection.getConnection();
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(query)) {
				while (rs.next()) {
					Data d1 = new PieChart.Data(rs.getString("name"), rs.getInt("count"));
					branchMenuItemChart.getData().add(d1);
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		selectedBranch = selectedbranch;

	}

	private String avgSalaries(int id) {
		String query = "SELECT AVG(e.salary) as avg FROM phase3.employee as e WHERE e.branch_id = '" + id + "'";
		double num = 0;
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				num = rs.getInt("avg");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return String.format("%.2f", num);
	}

	private int NumOfTables(int id) {
		String query = "select count(*) as count from phase3.diningtable as dt where dt.branch_id ='" + id + "' ";
		int num = 0;
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				num = rs.getInt("count");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	private int NumOfEmp(int id) {
		String query = "select count(*) as count from phase3.employee as e where e.branch_id ='" + id + "' ";
		int num = 0;
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				num = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	private int totalOrdersInYear(int year, int id) {
		String query = "select count(*) as count from phase3.orders where orders.branch_id ='" + id
				+ "' and year(order_date) = '" + year + "' ";
		int totalOrders = 0;
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				totalOrders = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalOrders;
	}

	private int totalOrdersInMonth(int month, int year, int id) {
		String query = "select count(*) as count from phase3.orders where orders.branch_id ='" + id
				+ "' and year(order_date) = '" + year + "' and month(order_date) ='" + month + "' ";
		int totalOrders = 0;
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				totalOrders = rs.getInt("count");
				System.out.println(rs.getInt("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalOrders;
	}

	private int totalOrdersInDay(int day, int month, int year, int id) {
		String query = "select count(*) as count from phase3.orders where orders.branch_id ='" + id
				+ "' and year(order_date) = '" + year + "' and month(order_date) ='" + month
				+ "' and day(order_date) ='" + day + "'";
		int totalOrders = 0;
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				totalOrders = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalOrders;
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

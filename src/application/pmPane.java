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

public class pmPane extends Pane {

    private TableView<PaymentMethod> tableView;
    private TableColumn<PaymentMethod, Integer> pmIDColumn;
    private TableColumn<PaymentMethod, Double> cashColumn;
    private TableColumn<PaymentMethod, String> creditCardColumn;
    private TableColumn<PaymentMethod, String> mobilePaymentColumn;
    private TextField pmIDField;
    private TextField cashField;
    private TextField creditCardField;
    private TextField mobilePaymentField;
    private Label pmIDLabel;
    private Label cashLabel;
    private Label creditCardLabel;
    private Label mobilePaymentLabel;

	private PaymentMethod selectedPayment;

	public pmPane(){
		 AnchorPane root = new AnchorPane();

	        tableView = new TableView<>();
	        tableView.setLayoutX(380.8);
	        tableView.setLayoutY(27.0);
	        tableView.setPrefHeight(271.0);
	        tableView.setPrefWidth(400.0);
	        AnchorPane.setBottomAnchor(tableView, 255.0);
	        AnchorPane.setLeftAnchor(tableView, 381.0);
	        AnchorPane.setRightAnchor(tableView, 27.0);
	        AnchorPane.setTopAnchor(tableView, 27.0);

	        pmIDColumn = new TableColumn<>("pmID");
	        pmIDColumn.setPrefWidth(73.60003358125687);
	        cashColumn = new TableColumn<>("cash");
	        cashColumn.setPrefWidth(81.5999755859375);
	        creditCardColumn = new TableColumn<>("credit card");
	        creditCardColumn.setPrefWidth(147.99989318847656);
	        mobilePaymentColumn = new TableColumn<>("mobile payment");
	        mobilePaymentColumn.setPrefWidth(151.199951171875);
	        tableView.getColumns().addAll(pmIDColumn, cashColumn, creditCardColumn, mobilePaymentColumn);

	        pmIDField = new TextField();
	        pmIDField.setLayoutX(214.0);
	        pmIDField.setLayoutY(62.0);


	        cashField = new TextField();
	        cashField.setLayoutX(214.0);
	        cashField.setLayoutY(105.0);

	        creditCardField = new TextField();
	        creditCardField.setLayoutX(214.0);
	        creditCardField.setLayoutY(145.0);

	        mobilePaymentField = new TextField();
	        mobilePaymentField.setLayoutX(214.0);
	        mobilePaymentField.setLayoutY(187.0);

	        pmIDLabel = new Label("payment method id :");
	        pmIDLabel.setLayoutX(40.0);
	        pmIDLabel.setLayoutY(65.0);
	        pmIDLabel.setTextFill(javafx.scene.paint.Color.RED);
	        Font pmIDFont = new Font("Serif Bold", 17.0);
	        pmIDLabel.setFont(pmIDFont);

	        cashLabel = new Label("cash :");
	        cashLabel.setLayoutX(143.0);
	        cashLabel.setLayoutY(104.0);
	        cashLabel.setTextFill(javafx.scene.paint.Color.RED);
	        Font cashFont = new Font("Serif Bold", 19.0);
	        cashLabel.setFont(cashFont);

	        creditCardLabel = new Label("credit card :");
	        creditCardLabel.setLayoutX(68.0);
	        creditCardLabel.setLayoutY(141.0);
	        creditCardLabel.setTextFill(javafx.scene.paint.Color.RED);
	        Font creditCardFont = new Font("Serif Bold", 23.0);
	        creditCardLabel.setFont(creditCardFont);
	        
	        
	        mobilePaymentLabel = new Label("mobile payment :");
	        mobilePaymentLabel.setLayoutX(60.0);
	        mobilePaymentLabel.setLayoutY(187.0);
	        mobilePaymentLabel.setTextFill(javafx.scene.paint.Color.RED);
	        Font mobilePaymentFont = new Font("Serif Bold", 19.0);
	        mobilePaymentLabel.setFont(mobilePaymentFont);
	        
	        

	        Button deleteButton = new Button("delete");
	        deleteButton.setLayoutX(270.0);
	        deleteButton.setLayoutY(230.0);
	        deleteButton.setOnAction(event -> {
				if (selectedPayment == null) {
					displayAlert("Please choose a Payment !");
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Are You Sure ? ");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						deleteButton();
					}
				}
			});
	        

	        Button updateButton = new Button("update");
	        updateButton.setLayoutX(160.0);
	        updateButton.setLayoutY(230.0);
	        updateButton.setOnAction(event -> {
				try {
					updateRecord();
				} catch (Exception e) {
					displayAlert("ERROR ! check enteries");
				}
			});
	        

	        Button insertButton = new Button("insert");
	        insertButton.setLayoutX(49.0);
	        insertButton.setLayoutY(230.0);
	        insertButton.setOnAction(event -> {
				insertRecord();
			});
	        
	        
	        Button showButton = new Button("show");
	        showButton.setLayoutX(116.0);
	        showButton.setLayoutY(270.0);
	        
	        
			tableView.setOnMouseClicked(e -> {
				handleRowSelection(e);
			});
	        root.getChildren().addAll(tableView, pmIDField, cashField, creditCardField, mobilePaymentField,
	                pmIDLabel, cashLabel, creditCardLabel, mobilePaymentLabel, deleteButton, updateButton, insertButton, showButton);

	        showPayments();
	        getChildren().add(root);
	}
   
	private void insertRecord() {
		int pmId;
		try {
			pmId = Integer.parseInt(pmIDField.getText().trim());
			double cash = Double.parseDouble(cashField.getText().trim());

			System.out.println("pmId: " + pmId);
			if (pmIDExists(pmId)) {
				displayAlert("pmId already exists.");
				System.out.println("heelooooo1");
				return;
			} else {
				String query = "INSERT INTO phase3.payment_method VALUES ('" + pmId + "','"+ cash +"','" 
						+ creditCardField.getText().trim() + "','" + mobilePaymentField.getText().trim() + "')";
				System.out.println(query);
				executeQuery(query);
				showPayments();
				
			}
		} catch (NumberFormatException wx) {

		}

	}
	

	private void updateRecord() throws Exception {
		int pmId = Integer.parseInt(pmIDField.getText().trim());
		double cash = Double.parseDouble(cashField.getText().trim());
		try {
			String query = "UPDATE phase3.payment_method SET cash = '" + cash + "', credit_card = '"
					+ creditCardField.getText().trim()  + "', mobile_payment = '"+ mobilePaymentField.getText().trim() +"' WHERE payment_method_id = " + pmId;
			executeQuery(query);
		} catch (Exception e) {
			throw new Exception();
		}
		showPayments();
	}
	
	
	private void deleteButton() {
		int pmId;
		try {
			pmId = Integer.parseInt(pmIDField.getText().trim());
			String query = "DELETE FROM phase3.payment_method WHERE payment_method_id =" + pmId + "";
			executeQuery(query);
			showPayments();;;
		} catch (NumberFormatException ex) {

		}

	}
	
	private boolean pmIDExists(int pmId) {

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.payment_method where payment_method.payment_method_id='" + pmId + "'";
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

	
	
	private void showPayments() {
		String query = "SELECT * FROM phase3.payment_method";
		ObservableList<PaymentMethod> paymentsList = getPayments(query);
		pmIDColumn.setCellValueFactory(new PropertyValueFactory<PaymentMethod, Integer>("payment_method_id"));
		cashColumn.setCellValueFactory(new PropertyValueFactory<PaymentMethod, Double>("cash"));
		creditCardColumn.setCellValueFactory(new PropertyValueFactory<PaymentMethod, String>("credit_card"));
		mobilePaymentColumn.setCellValueFactory(new PropertyValueFactory<PaymentMethod, String>("mobile_payment"));


		tableView.setItems(paymentsList);
		
	}
	
	public ObservableList<PaymentMethod> getPayments(String query) {
		ObservableList<PaymentMethod> paymentsList = FXCollections.observableArrayList();
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				PaymentMethod paymentMethod = new PaymentMethod(rs.getInt("payment_method_id"), rs.getDouble("cash"),rs.getString("credit_card"),rs.getString("mobile_payment"));
				paymentsList.add(paymentMethod);
			}
		} catch (SQLException ex) {
		}
		return paymentsList;
	}
	
	
	public void handleRowSelection(MouseEvent event) {
		System.out.println("Inside handleRowSelection.");
		int index = tableView.getSelectionModel().getSelectedIndex();
		System.out.println("Index is :" + index);
		if (index <= -1) {
			System.out.println("No row selected.");
			return;
		}
		PaymentMethod selectedpayment = tableView.getSelectionModel().getSelectedItem();
		int id = selectedpayment.getPayment_method_id();
		if (selectedpayment != null) {
			pmIDField.setText(String.valueOf(selectedpayment.getPayment_method_id()));
			cashField.setText(String.valueOf(selectedpayment.getCash()));
			creditCardField.setText(String.valueOf(selectedpayment.getCredit_card()));
			mobilePaymentField.setText(String.valueOf(selectedpayment.getMobile_payment()));

			

		}
		selectedPayment = selectedpayment;

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

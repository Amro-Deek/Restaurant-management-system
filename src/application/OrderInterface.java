package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;


public class OrderInterface extends Pane {
    private ArrayList<Order> orders;
    private ObservableList<Order> dataList;
    private Connection con;


   
    public OrderInterface () {
    	 orders = new ArrayList<>();

         try {
             getData();
             dataList = FXCollections.observableArrayList(orders);
             tableView();
         } catch (SQLException | ClassNotFoundException e) {
             e.printStackTrace();
         }
    }

    private void tableView() {
        TableView<Order> orderTable = new TableView<>();
        Scene scene = new Scene(new Group());
       
        Label label = new Label("Orders");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.BLACK);
        orderTable.setEditable(true);

        TableColumn<Order, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setMinWidth(100);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        orderIdCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Integer> t) -> {
        	 t.getRowValue().setOrderId(t.getNewValue());
             updateOrderId(t.getRowValue().getOrderId(), t.getNewValue());
         });

        TableColumn<Order, Integer> branchIdCol = new TableColumn<>("Branch ID");
        branchIdCol.setMinWidth(100);
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        branchIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        branchIdCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Integer> t) -> {
        	 t.getRowValue().setBranchId(t.getNewValue());
             updateBranchId(t.getRowValue().getOrderId(), t.getNewValue());
         });

        TableColumn<Order, Integer> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setMinWidth(100);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        customerIdCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Integer> t) -> {
        	 t.getRowValue().setCustomerId(t.getNewValue());
             updateCustomerId(t.getRowValue().getOrderId(), t.getNewValue());
         });
        TableColumn<Order, Integer> deliveryIdCol = new TableColumn<>("Delivery ID");
        deliveryIdCol.setMinWidth(100);
        deliveryIdCol.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        deliveryIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        deliveryIdCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Integer> t) -> {
        	 t.getRowValue().setDeliveryId(t.getNewValue());
             updateDeliveryId(t.getRowValue().getOrderId(), t.getNewValue());
         });
        
        TableColumn<Order, Integer> paymentMethodIdCol = new TableColumn<>("Payment Method ID");
        paymentMethodIdCol.setMinWidth(100);
        paymentMethodIdCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethodId"));
        paymentMethodIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        paymentMethodIdCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Integer> t) -> {
        	 t.getRowValue().setPaymentMethodId(t.getNewValue());
             updatePaymentMethodId(t.getRowValue().getOrderId(), t.getNewValue());
         });

        TableColumn<Order, Integer> tableIdCol = new TableColumn<>("Table ID");
        tableIdCol.setMinWidth(100);
        tableIdCol.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        tableIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableIdCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Integer> t) -> {
        	 t.getRowValue().setTableId(t.getNewValue());
             updateTableId(t.getRowValue().getOrderId(), t.getNewValue());
         });

        TableColumn<Order, Date> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setMinWidth(200);
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderDateCol.setCellFactory(column -> new TableCell<Order, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Customize the formatting as needed
                }
            }
        });
        orderDateCol.setOnEditCommit((TableColumn.CellEditEvent<Order, Date> t) -> {
        	((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOrderDate(t.getNewValue());
			updateOrderDate(t.getRowValue().getOrderId(), t.getNewValue());
		});
        
        

        orderTable.setItems(dataList);
        orderTable.getColumns().addAll(orderIdCol, branchIdCol, customerIdCol, deliveryIdCol, paymentMethodIdCol, tableIdCol, orderDateCol);

        final TextField addOrderId = new TextField();
        addOrderId.setPromptText("Order ID");
        addOrderId.setMaxWidth(branchIdCol.getPrefWidth());
        
        final TextField addBranchId = new TextField();
        addBranchId.setPromptText("Branch ID");
        addBranchId.setMaxWidth(branchIdCol.getPrefWidth());

        final TextField addCustomerId = new TextField();
        addCustomerId.setPromptText("Customer ID");
        addCustomerId.setMaxWidth(customerIdCol.getPrefWidth());

        final TextField addDeliveryId = new TextField();
        addDeliveryId.setPromptText("Delivery ID");
        addDeliveryId.setMaxWidth(deliveryIdCol.getPrefWidth());

        final TextField addPaymentMethodId = new TextField();
        addPaymentMethodId.setPromptText("Payment Method ID");
        addPaymentMethodId.setMaxWidth(paymentMethodIdCol.getPrefWidth());

        final TextField addTableId = new TextField();
        addTableId.setPromptText("Table ID");
        addTableId.setMaxWidth(tableIdCol.getPrefWidth());

        final TextField addOrderDate = new TextField();
        addOrderDate.setPromptText("Order Date (yyyy-mm-dd)");
        addOrderDate.setMaxWidth(orderDateCol.getPrefWidth());

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            Order order = new Order(
            	Integer.parseInt(addOrderId.getText()),
                Integer.parseInt(addBranchId.getText()),
                Integer.parseInt(addCustomerId.getText()),
                Integer.parseInt(addDeliveryId.getText()),
                Integer.parseInt(addPaymentMethodId.getText()),
                Integer.parseInt(addTableId.getText()),
                Date.valueOf(LocalDate.parse(addOrderDate.getText()))
            );
            dataList.add(order);
            insertRow(order);

            addOrderId.clear();
            addBranchId.clear();
            addCustomerId.clear();
            addDeliveryId.clear();
            addPaymentMethodId.clear();
            addTableId.clear();
            addOrderDate.clear();
        });
        
        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Order> selectedRows = orderTable.getSelectionModel().getSelectedItems();
            ArrayList<Order> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                dataList.remove(row); // Remove the row from the dataList
                deleteRow(row); // Delete the row from the database
            });
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(addOrderId,addBranchId, addCustomerId, addDeliveryId, addPaymentMethodId, addTableId, addOrderDate, addButton,deleteButton);
        hBox.setSpacing(10);

        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, orderTable, hBox);

       getChildren().add(vbox);
    }

    private void getData() throws SQLException, ClassNotFoundException {
        connectDB();
        String sql = "SELECT * FROM orders";
        ResultSet rs = con.createStatement().executeQuery(sql);

        while (rs.next()) {
            orders.add(new Order(
                rs.getInt("order_id"),
                rs.getInt("branch_id"),
                rs.getInt("customer_id"),
                rs.getInt("delivery_id"),
                rs.getInt("payment_method_id"),
                rs.getInt("table_id"),
                rs.getDate("order_date")
            ));
        }
        rs.close();
        con.close();
    }

    private void connectDB() throws ClassNotFoundException, SQLException {

		

		con = DBConnection.getConnection();

	}

    private void executeStatement(String sql) throws SQLException {
        con.createStatement().execute(sql);
    }



    private void insertRow(Order order) {
        try {
            connectDB();

            // Check if branch exists, if not, insert it
            if (!branchExists(order.getBranchId())) {
                insertBranch(order.getBranchId());
            }

            // Check if customer exists, if not, insert it
            if (!CustomerExists(order.getCustomerId())) {
                insertCustomer(order.getCustomerId());
            }

            // Check if delivery exists, if not, insert it
            if (!DeliveryExists(order.getDeliveryId())) {
                insertDelivery(order.getDeliveryId());
            }

            // Check if payment method exists, if not, insert it
            if (!PaymentMethodExists(order.getPaymentMethodId())) {
                insertPaymentMethod(order.getPaymentMethodId());
            }

            // Check if table exists, if not, insert it
            if (!tableExists(order.getTableId())) {
                insertTable(order.getTableId());
            }
            
            String formattedDate = order.getOrderDate() != null ? order.getOrderDate().toString() : null;
            String insertOrderQuery = "INSERT INTO orders (order_id, branch_id, customer_id, delivery_id, payment_method_id, table_id, order_date) " +
                                      "VALUES (" + order.getOrderId() + ", " + order.getBranchId() + ", " + order.getCustomerId() + ", " + order.getDeliveryId() + 
                                      ", " + order.getPaymentMethodId() + ", " + order.getTableId() + ", " + (formattedDate != null ? "'" + formattedDate + "'" : "NULL") + ")";
            executeStatement(insertOrderQuery);

            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    
    
    
    


	private void insertTable(int tableId)throws SQLException {
	    // Insert branch into the branches table
	    String insertQuery = "INSERT INTO diningtable (table_id) VALUES (" +  tableId + ")";
	    java.sql.Statement statement = (java.sql.Statement) con.createStatement();
	    statement.executeUpdate(insertQuery);
	    statement.close();
	}


	private boolean tableExists(int tableId)throws SQLException {
	    // Check if branch exists in the branches table
	    String query = "SELECT * FROM diningtable WHERE table_id = " + tableId;
	    Statement statement = (java.sql.Statement) con.createStatement();
	    ResultSet resultSet = statement.executeQuery(query);
	    boolean exists = resultSet.next();
	    statement.close();
	    resultSet.close();
	    return exists;
	}

	private boolean PaymentMethodExists(int PaymentMethodId) throws SQLException {
	    // Check if branch exists in the branches table
	    String query = "SELECT * FROM payment_method WHERE payment_method_id = " + PaymentMethodId;
	    Statement statement = (Statement) con.createStatement();
	    ResultSet resultSet = statement.executeQuery(query);
	    boolean exists = resultSet.next();
	    statement.close();
	    resultSet.close();
	    return exists;
	}

	private void insertPaymentMethod(int PaymentMethodId) throws SQLException {
	    // Insert branch into the branches table
	    String insertQuery = "INSERT INTO payment_method (payment_method_id) VALUES (" +  PaymentMethodId + ")";
	    Statement statement = (Statement) con.createStatement();
	    statement.executeUpdate(insertQuery);
	    statement.close();
	}

	private boolean DeliveryExists(int deliveryId) throws SQLException {
	    // Check if branch exists in the branches table
	    String query = "SELECT * FROM delivery WHERE delivery_id = " + deliveryId;
	    Statement statement = (Statement) con.createStatement();
	    ResultSet resultSet = statement.executeQuery(query);
	    boolean exists = resultSet.next();
	    statement.close();
	    resultSet.close();
	    return exists;
	}

	private void insertDelivery(int deliveryId) throws SQLException {
	    // Insert branch into the branches table
	    String insertQuery = "INSERT INTO delivery (delivery_id) VALUES (" + deliveryId + ")";
	    Statement statement = (Statement) con.createStatement();
	    statement.executeUpdate(insertQuery);
	    statement.close();
	}

	private boolean CustomerExists(int customerId) throws SQLException {
	    // Check if branch exists in the branches table
	    String query = "SELECT * FROM customer WHERE customer_id = " + customerId;
	    Statement statement = (Statement) con.createStatement();
	    ResultSet resultSet = statement.executeQuery(query);
	    boolean exists = resultSet.next();
	    statement.close();
	    resultSet.close();
	    return exists;
	}

	private void insertCustomer(int customerId) throws SQLException {
	    // Insert branch into the branches table
	    String insertQuery = "INSERT INTO customer (customer_id) VALUES (" + customerId + ")";
	    Statement statement = (Statement) con.createStatement();
	    statement.executeUpdate(insertQuery);
	    statement.close();
	}

	// Add these methods after the insertData method in the AddressInterface class

	private boolean branchExists(int branchId) throws SQLException {
	    // Check if branch exists in the branches table
	    String query = "SELECT * FROM branch WHERE branch_id = " + branchId;
	    Statement statement = (Statement) con.createStatement();
	    ResultSet resultSet = statement.executeQuery(query);
	    boolean exists = resultSet.next();
	    statement.close();
	    resultSet.close();
	    return exists;
	}

	private void insertBranch(int branchId) throws SQLException {
	    // Insert branch into the branches table
		Random random = new Random();
        int randomInt = random.nextInt(1000);
        
        String insertQuery2 = "INSERT INTO branch (branch_id, resturant_id) VALUES (" + branchId + ", " + randomInt + ")";
        String insertQuery = "INSERT INTO resturant (resturant_id) VALUES (" + randomInt + ")";
	    Statement statement = (Statement) con.createStatement();
	    statement.executeUpdate(insertQuery);
	    statement.close();
	    Statement statement2 = (Statement) con.createStatement();
	    statement2.executeUpdate(insertQuery2);
	    statement2.close();
	}
	// Similarly, add methods for other tables like customers, delivery, payment_methods, tables, etc.


	private void deleteRow(Order row) {
	    try {
	        connectDB();
	        executeStatement("DELETE FROM order_menu_item WHERE order_id=" + row.getOrderId());
	        executeStatement("DELETE FROM orders WHERE order_id=" + row.getOrderId());
	        con.close();
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


	public void updateOrderId(int oldID, int newID) {
	    try {
	        connectDB();
	        executeStatement("UPDATE orders SET order_id = " + newID + " WHERE order_id = " + oldID + ";");
	        executeStatement("UPDATE order_menu_item SET order_id = " + newID + " WHERE order_id = " + oldID + ";");
	        con.close();
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


    private void updateBranchId(int orderId, int branchId) {
        try {
            connectDB();
            if (!branchExists(branchId)) {
                // If the branch doesn't exist, insert it into the branch table
            	
                insertBranch(branchId);
            }
            
            executeStatement("UPDATE orders SET branch_id = " +  branchId + " WHERE order_id = " + orderId + ";");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomerId(int orderId, int customerId) {
        try {
            connectDB();
             
            
            if (!CustomerExists(customerId)) {
                // If the branch doesn't exist, insert it into the branch table
            	
                insertCustomer(customerId);
            }
            
            executeStatement("UPDATE orders SET customer_id = " +  customerId + " WHERE order_id = " + orderId + ";");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateDeliveryId(int orderId, int deliveryId) {
        try {
            connectDB();
            
            if (!DeliveryExists(deliveryId)) {
                // If the branch doesn't exist, insert it into the branch table
            	
                insertDelivery(deliveryId);
            } 
            
            executeStatement("UPDATE orders SET delivery_id = " +  deliveryId + " WHERE order_id = " + orderId + ";");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updatePaymentMethodId(int orderId, int paymentMethodId) {
        try {
            connectDB(); 
            if (!PaymentMethodExists(paymentMethodId)) {
                // If the branch doesn't exist, insert it into the branch table
            	
                insertPaymentMethod(paymentMethodId);
            }
            
            executeStatement("UPDATE orders SET payment_method_id = " +  paymentMethodId + " WHERE order_id = " + orderId + ";");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateTableId(int orderId, int tableId) {
        try {
            connectDB();
            
            if (!tableExists(tableId)) {
                // If the branch doesn't exist, insert it into the branch table
            	
                insertTable(tableId);
            }
            
            executeStatement("UPDATE orders SET table_id = " +  tableId + " WHERE order_id = " + orderId + ";");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void updateOrderDate(int orderId, Date orderDate) {
        try {
            connectDB();
       executeStatement("UPDATE orders SET order_date = " +  orderDate + " WHERE order_id = " + orderId + ";");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package application;

// Yazan Yousef 1191706
import static javafx.stage.Modality.NONE;
import javafx.application.Application;
import static javafx.stage.Modality.NONE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

public class InventoryitemInterface extends Pane {
	private ArrayList<Inventoryitem> prods;
	private ObservableList<Inventoryitem> dataList;
	private Connection con;

	public static void main(String[] args) {
		Application.launch(args);
	}

	public InventoryitemInterface() {
		prods = new ArrayList<>();

		try {
			getData();
			dataList = FXCollections.observableArrayList(prods);
			tableView();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void tableView() {
		TableView<Inventoryitem> inventoryitemtable = new TableView<Inventoryitem>();
		Scene sc = new Scene(new Group());
		Label l = new Label("Inventory Item");
		l.setFont(new Font("Arial", 30));
		l.setTextFill(Color.BLACK);
		inventoryitemtable.setEditable(true);
		inventoryitemtable.setMaxHeight(600);
		inventoryitemtable.setMaxWidth(600);

		TableColumn<Inventoryitem, Integer> inventoryItemIdCol = new TableColumn<Inventoryitem, Integer>(
				"Inventory Item Id");
		inventoryItemIdCol.setMinWidth(120);
		inventoryItemIdCol.setCellValueFactory(new PropertyValueFactory<Inventoryitem, Integer>("inventoryItemId"));
		inventoryItemIdCol.setCellFactory(
				TextFieldTableCell.<Inventoryitem, Integer>forTableColumn(new IntegerStringConverter()));
		inventoryItemIdCol.setOnEditCommit((CellEditEvent<Inventoryitem, Integer> t) -> {
			updateInventoryItemId(t.getRowValue().getInventoryItemId(), t.getNewValue());
			t.getRowValue().setInventoryItemId(t.getNewValue());
		});

		TableColumn<Inventoryitem, String> nameCol = new TableColumn<Inventoryitem, String>("Name");
		nameCol.setMinWidth(120);
		nameCol.setCellValueFactory(new PropertyValueFactory<Inventoryitem, String>("name"));
		nameCol.setCellFactory(TextFieldTableCell.<Inventoryitem>forTableColumn());
		nameCol.setOnEditCommit((CellEditEvent<Inventoryitem, String> t) -> {
			t.getRowValue().setName(t.getNewValue());
			updateName(t.getRowValue().getInventoryItemId(), t.getNewValue());
		});

		TableColumn<Inventoryitem, Integer> quantityCol = new TableColumn<Inventoryitem, Integer>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<Inventoryitem, Integer>("quantity"));
		quantityCol.setCellFactory(
				TextFieldTableCell.<Inventoryitem, Integer>forTableColumn(new IntegerStringConverter()));
		quantityCol.setOnEditCommit((CellEditEvent<Inventoryitem, Integer> t) -> {
			t.getRowValue().setQuantity(t.getNewValue());
			updateQuantity(t.getRowValue().getInventoryItemId(), t.getNewValue());
		});

		inventoryitemtable.setItems(dataList);
		inventoryitemtable.getColumns().addAll(inventoryItemIdCol, nameCol, quantityCol);

		final TextField addID = new TextField();
		addID.setPromptText("ID");
		addID.setMaxWidth(inventoryItemIdCol.getPrefWidth());

		final TextField addName = new TextField();
		addName.setMaxWidth(nameCol.getPrefWidth());
		addName.setPromptText("Name");

		final TextField addQuantity = new TextField();
		addQuantity.setMaxWidth(quantityCol.getPrefWidth());
		addQuantity.setPromptText("Quantity");

		final Button addButton = new Button("Add");
		addButton.setOnAction((ActionEvent e) -> {
			Inventoryitem rc = new Inventoryitem(Integer.valueOf(addID.getText()),
					Integer.valueOf(addQuantity.getText()), addName.getText());
			dataList.add(rc);
			insertData(rc);
			addID.clear();
			addName.clear();
			addQuantity.clear();
		});

		final HBox hb = new HBox();
		final Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((ActionEvent e) -> {
			ObservableList<Inventoryitem> selectedRows = inventoryitemtable.getSelectionModel().getSelectedItems();
			ArrayList<Inventoryitem> rows = new ArrayList<>(selectedRows);
			rows.forEach(row -> {
				inventoryitemtable.getItems().remove(row);
				deleteRow(row);
				inventoryitemtable.refresh();
			});
		});

		hb.getChildren().addAll(addID, addName, addQuantity, addButton, deleteButton);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(l, inventoryitemtable, hb);
		getChildren().add(vbox);
	}

	private void insertData(Inventoryitem p) {
		try {
			connectDB();
			ExecuteStatement("INSERT INTO inventory_item (inventory_item_id,quantity,name) VALUES ("
					+ p.getInventoryItemId() + ", '" + p.getQuantity() + "', '" + p.getName() + "');");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getData() throws SQLException, ClassNotFoundException {
		String SQL;
		connectDB();
		SQL = "SELECT * FROM inventory_item ORDER BY inventory_item_id";
		Statement stat = con.createStatement();
		ResultSet r = stat.executeQuery(SQL);

		while (r.next())
			prods.add(new Inventoryitem(Integer.parseInt(r.getString(1)), Integer.parseInt(r.getString(2)),
					r.getString(3)));

		r.close();
		stat.close();
		con.close();
	}

	private void connectDB() throws ClassNotFoundException, SQLException {
		
		con = DBConnection.getConnection();
	}

	public void ExecuteStatement(String SQL) throws SQLException {
		try {
			Statement stat = con.createStatement();
			stat.executeUpdate(SQL);
			stat.close();
		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("failed");
		}
	}

	private void updateInventoryItemId(int oldId, int newId) {
		try {
			connectDB();
			ExecuteStatement(
					"UPDATE inventory_item SET inventory_item_id = " + newId + " WHERE inventory_item_id = " + oldId);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateName(int ID, String name) {
		try {
			connectDB();
			ExecuteStatement("UPDATE inventory_item SET name = '" + name + "' WHERE inventory_item_id = " + ID + ";");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateQuantity(int ID, int quantity) {
		try {
			connectDB();
			ExecuteStatement(
					"UPDATE inventory_item SET quantity = " + quantity + " WHERE inventory_item_id = " + ID + ";");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void deleteRow(Inventoryitem row) {
		try {
			connectDB();
			ExecuteStatement("DELETE FROM inventory_item WHERE inventory_item_id = " + row.getInventoryItemId() + ";");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void showDialog(Window owner, Modality modality, TableView<Inventoryitem> table) {
		Stage stage = new Stage();
		stage.initOwner(owner);
		stage.initModality(modality);
		HBox root = new HBox();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);
	}
}

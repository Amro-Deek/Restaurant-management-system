package application;

//Yazan Yousef 1191706
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
import javafx.util.converter.DoubleStringConverter;

public class AddressInterface extends Pane {
	private ArrayList<Address> prods;
	private ObservableList<Address> dataList;
	private Connection con;

	AddressInterface() {
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

		TableView<Address> addresstable = new TableView<Address>();
		Scene sc = new Scene(new Group());
		Label l = new Label("Address");
		l.setFont(new Font("Arial", 30));
		l.setTextFill(Color.BLACK);
		addresstable.setEditable(true);
		addresstable.setMaxHeight(600);
		addresstable.setMaxWidth(600);

		TableColumn<Address, Integer> IDCol = new TableColumn<Address, Integer>("Address ID");
		IDCol.setMinWidth(120);
		IDCol.setCellValueFactory(new PropertyValueFactory<Address, Integer>("AddressId"));
		IDCol.setCellFactory(TextFieldTableCell.<Address, Integer>forTableColumn(new IntegerStringConverter()));
		IDCol.setOnEditCommit((CellEditEvent<Address, Integer> t) -> {
			updateAddressId(t.getRowValue().getAddressId(), t.getNewValue());
			t.getRowValue().setAddressId(t.getNewValue());
		});

		TableColumn<Address, String> streetCol = new TableColumn<Address, String>("Street");
		streetCol.setMinWidth(120);
		streetCol.setCellValueFactory(new PropertyValueFactory<Address, String>("Street"));
		streetCol.setCellFactory(TextFieldTableCell.<Address>forTableColumn());
		streetCol.setOnEditCommit((CellEditEvent<Address, String> t) -> {
			updateStreet(t.getRowValue().getAddressId(), t.getNewValue());
			t.getRowValue().setStreet(t.getNewValue());
		});

		TableColumn<Address, String> cityCol = new TableColumn<Address, String>("City");
		cityCol.setMinWidth(120);
		cityCol.setCellValueFactory(new PropertyValueFactory<Address, String>("City"));
		cityCol.setCellFactory(TextFieldTableCell.<Address>forTableColumn());
		cityCol.setOnEditCommit((CellEditEvent<Address, String> t) -> {
			updateCity(t.getRowValue().getAddressId(), t.getNewValue());
			t.getRowValue().setCity(t.getNewValue());
		});

		addresstable.setItems(dataList);
		addresstable.getColumns().addAll(IDCol, streetCol, cityCol);

		final TextField addID = new TextField();
		addID.setPromptText("ID");
		addID.setMaxWidth(IDCol.getPrefWidth());

		final TextField addStreet = new TextField();
		addStreet.setMaxWidth(streetCol.getPrefWidth());
		addStreet.setPromptText("Street");

		final TextField addCity = new TextField();
		addCity.setMaxWidth(cityCol.getPrefWidth());
		addCity.setPromptText("City");

		final Button addButton = new Button("Add");
		addButton.setOnAction((ActionEvent e) -> {
			Address rc;
			rc = new Address(Integer.valueOf(addID.getText()), addStreet.getText(), addCity.getText());
			dataList.add(rc);
			insertData(rc);
			addID.clear();
			addStreet.clear();
			addCity.clear();
		});

		final HBox hb = new HBox();

		final Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((ActionEvent e) -> {
			ObservableList<Address> selectedRows = addresstable.getSelectionModel().getSelectedItems();
			ArrayList<Address> rows = new ArrayList<>(selectedRows);
			rows.forEach(row -> {
				addresstable.getItems().remove(row);
				deleteRow(row);
				addresstable.refresh();
			});
		});

		hb.getChildren().addAll(addID, addStreet, addCity, addButton, deleteButton);
		hb.setSpacing(3);

		final Button updateButton = new Button("update All");
		updateButton.setOnAction((ActionEvent e) -> {
			showDialog(null, NONE, addresstable);

		});

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(l, addresstable, hb);
		getChildren().add(vbox);
	}

	private void insertData(Address address) {
		try {
			connectDB();
			ExecuteStatement("INSERT INTO address (address_id, street, city) VALUES (" + address.getAddressId() + ", '"
					+ address.getStreet() + "', '" + address.getCity() + "');");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();

		SQL = "select * from Address order by address_id";
		Statement stat = con.createStatement();
		ResultSet r = stat.executeQuery(SQL);

		while (r.next())
			prods.add(new Address(Integer.parseInt(r.getString(1)), r.getString(2), r.getString(3)));

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

	private void updateAddressId(int oldId, int newId) {
		try {
			connectDB();

			// Update the address_id in the address table
			ExecuteStatement("UPDATE address SET address_id = " + newId + " WHERE address_id = " + oldId);

			// Update the address_id in the customer table
			ExecuteStatement("UPDATE customer SET address_id = " + newId + " WHERE address_id = " + oldId);

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateStreet(int ID, String street) {

		try {
			connectDB();
			ExecuteStatement("update  address set street = '" + street + "' where address_Id = " + ID + ";");
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateCity(int ID, String city) {

		try {

			connectDB();
			ExecuteStatement("update  address set city = '" + city + "' where address_Id = " + ID + ";");
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void deleteRow(Address row) {
		try {
			connectDB();
			ExecuteStatement(
					"DELETE FROM orders WHERE customer_id IN (SELECT customer_id FROM customer WHERE address_id = "
							+ row.getAddressId() + ")");
			ExecuteStatement("DELETE FROM customer WHERE address_id=" + row.getAddressId());
			ExecuteStatement("DELETE FROM address WHERE address_id=" + row.getAddressId());
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void showDialog(Window owner, Modality modality, TableView<Address> table) {
		Stage stage = new Stage();
		stage.initOwner(owner);
		stage.initModality(modality);
		HBox root = new HBox();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);
	}
}

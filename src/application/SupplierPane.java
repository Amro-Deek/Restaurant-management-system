package application;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SupplierPane extends Pane{

	public SupplierPane() {
		Pane supplierP = new Pane();
		supplierP.setPrefSize(600, 400);

		// Create Buttons
		Button deleteSup = new Button("Delete");
		deleteSup.setLayoutX(155);
		deleteSup.setLayoutY(330);
		deleteSup.setPrefSize(81, 36);

		Button insertSup = new Button("Insert");
		insertSup.setLayoutX(155);
		insertSup.setLayoutY(240);
		insertSup.setPrefSize(81, 36);

		Button updateSup = new Button("Update");
		updateSup.setLayoutX(155);
		updateSup.setLayoutY(285);
		updateSup.setPrefSize(81, 36);

		// Create TableView and TableColumns
		TableView<Object> tableView = new TableView<>();
		tableView.setLayoutX(50);
		tableView.setLayoutY(14);
		tableView.setPrefSize(498, 207);

		TableColumn<Object, String> colSupplierID = new TableColumn<>("Supplier ID");
		colSupplierID.setPrefWidth(114);

		TableColumn<Object, String> colName = new TableColumn<>("Name");
		colName.setPrefWidth(142);

		TableColumn<Object, String> colContactInfo = new TableColumn<>("Contact Info");
		colContactInfo.setPrefWidth(131);

		TableColumn<Object, String> colAddress = new TableColumn<>("Address");
		colAddress.setPrefWidth(110);

		tableView.getColumns().addAll(colSupplierID, colName, colContactInfo, colAddress);

		// Create TextFields
		TextField txtSupplierID = new TextField();
		txtSupplierID.setLayoutX(261);
		txtSupplierID.setLayoutY(246);
		txtSupplierID.setPromptText("Supplier ID");

		TextField txtName = new TextField();
		txtName.setLayoutX(261);
		txtName.setLayoutY(291);
		txtName.setPromptText("Name");

		TextField txtContactInfo = new TextField();
		txtContactInfo.setLayoutX(261);
		txtContactInfo.setLayoutY(336);
		txtContactInfo.setPromptText("Contact Info");

		TextField txtAddress = new TextField();
		txtAddress.setLayoutX(428);
		txtAddress.setLayoutY(264);
		txtAddress.setPromptText("Address");

		// Add all components to the root pane
		supplierP.getChildren().addAll(deleteSup, insertSup, updateSup, tableView, txtSupplierID, txtName,
				txtContactInfo, txtAddress);
		getChildren().add(supplierP);
	}
}

package application;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class DeliveryPane extends Pane{
	public DeliveryPane() {
		Pane deliveryP = new Pane();
		deliveryP.setPrefSize(600, 400);

		// Create Buttons
		Button deleteDeli = new Button("Delete");
		deleteDeli.setLayoutX(155);
		deleteDeli.setLayoutY(330);
		deleteDeli.setPrefSize(81, 36);

		Button insertDeli = new Button("Insert");
		insertDeli.setLayoutX(155);
		insertDeli.setLayoutY(240);
		insertDeli.setPrefSize(81, 36);

		Button updateDeli = new Button("Update");
		updateDeli.setLayoutX(155);
		updateDeli.setLayoutY(285);
		updateDeli.setPrefSize(81, 36);

		// Create TableView and TableColumns
		TableView<Object> tableView2 = new TableView<>();
		tableView2.setLayoutX(50);
		tableView2.setLayoutY(14);
		tableView2.setPrefSize(500, 201);

		TableColumn<Object, String> colDeliveryID = new TableColumn<>("Delivery ID");
		colDeliveryID.setPrefWidth(100);

		TableColumn<Object, String> colOrderID = new TableColumn<>("Order ID");
		colOrderID.setPrefWidth(100);

		TableColumn<Object, String> colAddress2 = new TableColumn<>("Address");
		colAddress2.setPrefWidth(100);

		TableColumn<Object, String> colDeliveryTime = new TableColumn<>("Delivery Time");
		colDeliveryTime.setPrefWidth(100);

		TableColumn<Object, String> colStatus = new TableColumn<>("Status");
		colStatus.setPrefWidth(100);

		tableView2.getColumns().addAll(colDeliveryID, colOrderID, colAddress2, colDeliveryTime, colStatus);

		// Create TextFields
		TextField txtDeliveryID = new TextField();
		txtDeliveryID.setLayoutX(261);
		txtDeliveryID.setLayoutY(246);
		txtDeliveryID.setPromptText("Delivery ID");

		TextField txtOrderID = new TextField();
		txtOrderID.setLayoutX(261);
		txtOrderID.setLayoutY(291);
		txtOrderID.setPromptText("Order ID");

		TextField txtAddress2 = new TextField();
		txtAddress2.setLayoutX(261);
		txtAddress2.setLayoutY(336);
		txtAddress2.setPromptText("Address");

		TextField txtTime = new TextField();
		txtTime.setLayoutX(431);
		txtTime.setLayoutY(273);
		txtTime.setPromptText("Time");

		TextField txtStatus = new TextField();
		txtStatus.setLayoutX(431);
		txtStatus.setLayoutY(324);
		txtStatus.setPromptText("Status");

		// Add all components to the root pane
		deliveryP.getChildren().addAll(deleteDeli, insertDeli, updateDeli, tableView2, txtDeliveryID, txtOrderID,
				txtAddress2, txtTime, txtStatus);
		getChildren().add(deliveryP);

	}
}

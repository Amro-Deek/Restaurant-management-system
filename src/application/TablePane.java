package application;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TablePane extends Pane {
	TablePane() {
		Pane tableP = new Pane();
		tableP.setPrefSize(600, 400);

		// Create Buttons
		Button deleteTab = new Button("Delete");
		deleteTab.setLayoutX(155);
		deleteTab.setLayoutY(330);
		deleteTab.setPrefSize(81, 36);

		Button insertTab = new Button("Insert");
		insertTab.setLayoutX(155);
		insertTab.setLayoutY(240);
		insertTab.setPrefSize(81, 36);

		Button updateTab = new Button("Update");
		updateTab.setLayoutX(155);
		updateTab.setLayoutY(285);
		updateTab.setPrefSize(81, 36);

		// Create TableView and TableColumns
		TableView<Object> tableView1 = new TableView<>();
		tableView1.setLayoutX(50);
		tableView1.setLayoutY(14);
		tableView1.setPrefSize(498, 207);

		TableColumn<Object, String> colTableID = new TableColumn<>("Table ID");
		colTableID.setPrefWidth(166);

		TableColumn<Object, String> colBranchID = new TableColumn<>("Branch ID");
		colBranchID.setPrefWidth(166);

		TableColumn<Object, String> colCapacity = new TableColumn<>("Capacity");
		colCapacity.setPrefWidth(166);

		tableView1.getColumns().addAll(colTableID, colBranchID, colCapacity);

		// Create TextFields
		TextField txtTableID = new TextField();
		txtTableID.setLayoutX(261);
		txtTableID.setLayoutY(246);
		txtTableID.setPromptText("Table ID");

		TextField txtBranchID = new TextField();
		txtBranchID.setLayoutX(261);
		txtBranchID.setLayoutY(291);
		txtBranchID.setPromptText("Branch ID");

		TextField txtCapacity = new TextField();
		txtCapacity.setLayoutX(261);
		txtCapacity.setLayoutY(336);
		txtCapacity.setPromptText("Capacity");

		// Add all components to the root pane
		tableP.getChildren().addAll(deleteTab, insertTab, updateTab, tableView1, txtTableID, txtBranchID, txtCapacity);

		getChildren().add(tableP);
	}
}

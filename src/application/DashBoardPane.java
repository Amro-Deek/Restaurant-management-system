package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class DashBoardPane extends Pane {
	// --> Amro Deek : المبيعات لكل منتج (لكل الفروع)<-- 
	private PieChart branchMenuItemChart;

	public DashBoardPane() {

		branchMenuItemChart = new PieChart();
		branchMenuItemChart.setAccessibleText("");
		branchMenuItemChart.setLayoutX(10);
		branchMenuItemChart.setLayoutY(10);
		branchMenuItemChart.setPrefHeight(320);
		branchMenuItemChart.setPrefWidth(320);
		branchMenuItemChart.setTitle("المبيعات لكل منتج (لكل الفروع)");
		showDataforbranchMenuItemChart();
		getChildren().add(branchMenuItemChart);
	}

	private void showDataforbranchMenuItemChart() {
		String query = "select mi.name ,count(*) as count from menu_item mi , orders o , order_menu_item omi where mi.item_id = omi.item_id and o.order_id=omi.order_id group by mi.item_id";
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
//
//	public void executeQuery(String query) {
//		Connection conn = DBConnection.getConnection();
//		Statement st;
//		try {
//			st = conn.createStatement();
//			st.executeUpdate(query);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	private void displayAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}

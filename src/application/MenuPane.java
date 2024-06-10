package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenuPane extends Pane {
	// --> Amro Deek <-- 
	private TableView<MenuItem> menuItemsTableView;
	private Button addMenuBtn;
	private TextField menuIDTxt;
	private Button deleteMenuBtn;
	private Button updateMenuBtn;
	private TableView<MenuStat> MenuTableView;

	private TableColumn<MenuItem, Integer> itemIdCol;
	private TableColumn<MenuItem, String> sizeCol;
	private TableColumn<MenuItem, String> nameCol;
	private TableColumn<MenuItem, String> typeCol;
	private TableColumn<MenuItem, Double> priceCol;
	private TableColumn<MenuItem, String> descriptionCol;
	private TableColumn<MenuStat, Integer> menuIDCol;
	private TableColumn<MenuStat, Integer> numOfItemsCol;

	private TextField itemIDTxt;
	private TextField sizeTxt;
	private TextField nameTxt;
	private TextField typeTxt;
	private TextField priceTxt;
	private TextField descriptionTxt;

	private Button insertBtn;
	private Button deleteBtn;
	private Button updateBtn;

	private MenuStat selectedMenu;

	@SuppressWarnings("unchecked")
	public MenuPane() {

		menuItemsTableView = new TableView<>();
		menuItemsTableView.setLayoutX(223);
		menuItemsTableView.setLayoutY(14);
		menuItemsTableView.setPrefSize(653, 226);
		menuItemsTableView.setOnMouseClicked(e -> {
			handleRowSelectionForItmes(e);
		});

		itemIdCol = new TableColumn<>("Item ID");
		sizeCol = new TableColumn<>("Size");
		nameCol = new TableColumn<>("Name");
		typeCol = new TableColumn<>("Type");
		priceCol = new TableColumn<>("Price");
		descriptionCol = new TableColumn<>("Description");
		itemIdCol.setPrefWidth(98.67);
		sizeCol.setPrefWidth(86);
		nameCol.setPrefWidth(100);
		typeCol.setPrefWidth(65.33);
		priceCol.setPrefWidth(72.67);
		descriptionCol.setPrefWidth(350);
		menuItemsTableView.getColumns().addAll(itemIdCol, sizeCol, nameCol, typeCol, priceCol, descriptionCol);

		menuIDTxt = new TextField();
		menuIDTxt.setLayoutX(29);
		menuIDTxt.setLayoutY(267);
		menuIDTxt.setPrefSize(186, 25);
		menuIDTxt.setPromptText("enter Menu ID");

		addMenuBtn = new Button("Add New Menu");
		addMenuBtn.setLayoutX(70);
		addMenuBtn.setLayoutY(310);
		addMenuBtn.setPrefSize(104, 25);
		addMenuBtn.setTextFill(javafx.scene.paint.Color.RED);
		addMenuBtn.setOnAction(e -> {
			insertMenu();
		});

		deleteMenuBtn = new Button("Delete Menu");
		deleteMenuBtn.setLayoutX(70);
		deleteMenuBtn.setLayoutY(388);
		deleteMenuBtn.setPrefSize(104, 25);
		deleteMenuBtn.setTextFill(javafx.scene.paint.Color.RED);
		deleteMenuBtn.setOnAction(e -> {
			if (selectedMenu == null) {
				displayAlert("Please choose a Menu !");
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are You Sure ? ");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					deleteMenu();
				}
			}
		});

		updateMenuBtn = new Button("Update Menu ID");
		updateMenuBtn.setLayoutX(70);
		updateMenuBtn.setLayoutY(350);
		updateMenuBtn.setTextFill(javafx.scene.paint.Color.RED);
		updateMenuBtn.setOnAction(e -> {
			updateMenuID();
		});

		MenuTableView = new TableView<>();
		MenuTableView.setLayoutX(29);
		MenuTableView.setLayoutY(14);
		MenuTableView.setPrefSize(186, 226);

		menuIDCol = new TableColumn<>("Menu ID");
		menuIDCol.setPrefWidth(87.33);
		numOfItemsCol = new TableColumn<>("# of Items");
		numOfItemsCol.setPrefWidth(97.33);
		MenuTableView.getColumns().addAll(menuIDCol, numOfItemsCol);
		MenuTableView.setOnMouseClicked(e -> {
			handleRowSelection(e);
		});
		Label label = new Label("enter new Item to Selected Menu :");
		label.setAlignment(javafx.geometry.Pos.CENTER);
		label.setLayoutX(229);
		label.setLayoutY(252);
		label.setTextFill(javafx.scene.paint.Color.BLACK);
		label.setStyle("-fx-font: normal bold 13px serif;");

		VBox vBox = new VBox();
		vBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
		vBox.setLayoutX(421);
		vBox.setLayoutY(249);
		vBox.setPrefSize(141, 202);
		vBox.setSpacing(10);

		itemIDTxt = new TextField();
		itemIDTxt.setPromptText("Item ID");
		sizeTxt = new TextField();
		sizeTxt.setPromptText("Size");
		nameTxt = new TextField();
		nameTxt.setPromptText("Name");
		typeTxt = new TextField();
		typeTxt.setPromptText("Type");
		priceTxt = new TextField();
		priceTxt.setPromptText("Price");
		descriptionTxt = new TextField();
		descriptionTxt.setPromptText("Description");

		vBox.getChildren().addAll(itemIDTxt, sizeTxt, nameTxt, typeTxt, priceTxt, descriptionTxt);

		insertBtn = new Button("insert");
		insertBtn.setLayoutX(395);
		insertBtn.setLayoutY(461);
		insertBtn.setTextFill(javafx.scene.paint.Color.RED);
		insertBtn.setOnAction(e -> {
			if (!allTextFilled()) {
				int itemID;
				try {
					itemID = Integer.parseInt(itemIDTxt.getText());
					double price;
					try {
						price = Double.parseDouble(priceTxt.getText());
						String disc = descriptionTxt.getText();
						String type = typeTxt.getText();
						String name = nameTxt.getText();
						String size = sizeTxt.getText();
						MenuStat ms = MenuTableView.getSelectionModel().getSelectedItem();
						if (ms == null) {
							displayAlert("Please choose what Menu you want to add the item in !");
						} else {
							int selectedMenuID = ms.getMenuId();
							if (menuItemIDExists(itemID, selectedMenuID)) {
								displayAlert("Item ID already exists !");
							} else {

								String query = "insert into phase3.menu_item values ('" + itemID + "','" + size + "','"
										+ name + "','" + type + "','" + price + "','" + disc + "')";
								executeQuery(query);
								String query2 = "insert into phase3.menu_menu_item values ('" + selectedMenuID + "','"
										+ itemID + "')";
								executeQuery(query2);
								showMenus();
								showMenuItems("" + selectedMenuID);
							}

						}

					} catch (Exception ex) {
						displayAlert("Price should be a Number !");
					}
				} catch (Exception ex) {
					displayAlert("Item ID should be an Integer !");
				}

			} else {
				displayAlert("please fill all records !!");
			}
		});

		deleteBtn = new Button("delete");
		deleteBtn.setLayoutX(466);
		deleteBtn.setLayoutY(461);
		deleteBtn.setTextFill(javafx.scene.paint.Color.RED);
		deleteBtn.setOnAction(e -> {
			MenuStat ms = MenuTableView.getSelectionModel().getSelectedItem();
			if (ms == null) {
				displayAlert("Please choose what Menu you want to delete the item from !");
			} else {
				MenuItem item = menuItemsTableView.getSelectionModel().getSelectedItem();
				if (item == null) {
					displayAlert("Please choose what Item you want to delete from Menu" + ms.getMenuId());
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Are You Sure ? ");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						String query = "delete from phase3.menu_menu_item as mmi where mmi.menu_id ='" + ms.getMenuId()
								+ "' and mmi.item_id ='" + item.getItem_id() + "'";
						executeQuery(query);
						showMenus();
						showMenuItems(ms.getMenuId() + "");
					}
				}
			}
		});

		updateBtn = new Button("update");
		updateBtn.setLayoutX(536);
		updateBtn.setLayoutY(461);
		updateBtn.setTextFill(javafx.scene.paint.Color.RED);
		updateBtn.setOnAction(e -> {
			MenuStat ms = MenuTableView.getSelectionModel().getSelectedItem();
			if (ms == null) {
				displayAlert("Please choose what Menu you want to update it's item !");
			} else {
				MenuItem item = menuItemsTableView.getSelectionModel().getSelectedItem();
				if (item == null) {
					displayAlert("Please choose what Item you want to update in Menu " + ms.getMenuId());
				} else {
					int itemID;
					try {
						itemID = Integer.parseInt(itemIDTxt.getText());
						double price;
						try {
							price = Double.parseDouble(priceTxt.getText());
							String disc = descriptionTxt.getText();
							String type = typeTxt.getText();
							String name = nameTxt.getText();
							String size = sizeTxt.getText();
							String query = "update phase3.menu_item set size = '" + size + "',name = '" + name
									+ "',type = '" + type + "',description = '" + disc + "',price = '" + price
									+ "' where item_id = '" + itemID + "'";
							executeQuery(query);
							showMenus();
							showMenuItems(ms.getMenuId() + "");
						} catch (Exception ex) {
							displayAlert("Price should be a Number !");
						}
					}

					catch (Exception ex2) {
						displayAlert("Item ID should be an Integer !");
					}

				}
			}
		});

		// upload Menus on table View.
		showMenus();
		getChildren().addAll(menuItemsTableView, addMenuBtn, menuIDTxt, deleteMenuBtn, updateMenuBtn, MenuTableView,
				label, vBox, insertBtn, deleteBtn, updateBtn);
	}

	private boolean allTextFilled() {
		if (itemIDTxt.getText().isEmpty() || sizeTxt.getText().isEmpty() || nameTxt.getText().isEmpty()
				|| typeTxt.getText().isEmpty() || priceTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty()) {
			return true;
		}
		return false;
	}

	private void handleRowSelectionForItmes(MouseEvent e) {
		int index = menuItemsTableView.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		MenuItem menuItem = menuItemsTableView.getSelectionModel().getSelectedItem();
		if (menuItem != null) {
			itemIDTxt.setText(String.valueOf(menuItem.getItem_id()));
			typeTxt.setText(menuItem.getType());
			descriptionTxt.setText(menuItem.getDescription());
			nameTxt.setText(menuItem.getName());
			priceTxt.setText(String.valueOf(menuItem.getPrice()));
			sizeTxt.setText(menuItem.getSize());
		}
	}

	private void updateMenuID() {
		int menuID;
		try {
			menuID = Integer.parseInt(menuIDTxt.getText());
			if (menuIDExists(menuID)) {
				displayAlert("Menu ID already exists !!");
			} else {
				MenuStat menu = MenuTableView.getSelectionModel().getSelectedItem();
				if (menu != null) {
					int prevmenuID = menu.getMenuId();
					String query = "update phase3.menu set menu.menu_id ='" + menuID + "' where menu.menu_id ='"
							+ prevmenuID + "'";
					executeQuery(query);
					showMenus();
				} else {
					displayAlert("please choose which Menu From table you want to update !");
				}
			}
		} catch (NumberFormatException e) {
			displayAlert("Menu ID should be an Integer !");
		}
	}

	private void insertMenu() {
		int menuID;
		try {
			menuID = Integer.parseInt(menuIDTxt.getText());
			if (menuIDExists(menuID)) {
				displayAlert("Menu ID already exists !!");
			} else {
				String query = "insert into phase3.menu values ('" + menuID + "')";
				executeQuery(query);
				showMenus();
			}
		} catch (NumberFormatException ex) {
			displayAlert("Menu ID should be an Integer !");
		}
	}

	private boolean menuIDExists(int menuID) {
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.menu where menu.menu_id='" + menuID + "'";
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

	private boolean menuItemIDExists(int menuItemID, int selectedMenuID) {
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String quere = "select * from phase3.menu_menu_item WHERE menu_menu_item.menu_id='" + selectedMenuID
					+ "'and menu_menu_item.item_id='" + menuItemID + "'";
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

	public void handleRowSelection(MouseEvent event) {
		System.out.println("Inside handleRowSelection.");
		int index = MenuTableView.getSelectionModel().getSelectedIndex();
		System.out.println("Index is :" + index);
		if (index <= -1) {
			System.out.println("No row selected.");
			return;
		}
		MenuStat SelecteMenu = MenuTableView.getSelectionModel().getSelectedItem();
		if (SelecteMenu != null) {
			menuIDTxt.setText(String.valueOf(SelecteMenu.getMenuId()));
			showMenuItems("" + SelecteMenu.getMenuId());
		}
		selectedMenu = SelecteMenu;
	}

	private void deleteMenu() {
		int menuID;
		try {
			menuID = Integer.parseInt(menuIDTxt.getText());
			if (!menuIDExists(menuID)) {
				displayAlert("Menu ID not exist !!");
			}
			String query = "delete from phase3.menu where menu.menu_id ='" + menuID + "'";
			executeQuery(query);
			showMenus();
		} catch (NumberFormatException ex) {
			displayAlert("Menu ID should be an integer !!");
		}
	}

	private void showMenus() {
		// String query = "select m.menu_id , count(mmi.item_id) AS item_count from
		// phase3.menu m ,phase3.menu_item mi ,phase3.menu_menu_item mmi where m.menu_id
		// = mmi.menu_id and mmi.item_id=mi.item_id group by m.menu_id";

		// this query handles the case when adding a new menu that has no items.
		String query = "SELECT m.menu_id, (SELECT COUNT(mmi.item_id) FROM phase3.menu_menu_item mmi WHERE m.menu_id = mmi.menu_id) AS item_count FROM phase3.menu m;";
		ObservableList<MenuStat> menus = getMenus(query);
		menuIDCol.setCellValueFactory(new PropertyValueFactory<MenuStat, Integer>("menuId"));
		numOfItemsCol.setCellValueFactory(new PropertyValueFactory<MenuStat, Integer>("NoOfItems"));
		MenuTableView.setItems(menus);
	}

	private ObservableList<MenuStat> getMenus(String query) {
		ObservableList<MenuStat> list = FXCollections.observableArrayList();

		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				MenuStat menuStat = new MenuStat(rs.getInt("menu_id"), rs.getInt("item_count"));
				list.add(menuStat);
			}
		} catch (SQLException ex) {
			System.err.println("SQL Exception occurred: " + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	private void showMenuItems(String text) {
		String query = "SELECT mi.* FROM phase3.menu_item mi JOIN phase3.menu_menu_item mmi ON mi.item_id = mmi.item_id WHERE mmi.menu_id = '"
				+ text + "'";
		ObservableList<MenuItem> menuItems = getMenuItems(query);
		itemIdCol.setCellValueFactory(new PropertyValueFactory<MenuItem, Integer>("item_id"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("size"));
		nameCol.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("name"));
		typeCol.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("type"));
		priceCol.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("price"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("description"));
		menuItemsTableView.setItems(menuItems);
	}

	private ObservableList<MenuItem> getMenuItems(String query) {
		ObservableList<MenuItem> list = FXCollections.observableArrayList();
		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				MenuItem menuItem = new MenuItem(rs.getInt("item_id"), rs.getString("size"), rs.getString("name"),
						rs.getString("type"), rs.getDouble("price"), rs.getString("description"));
				list.add(menuItem);
			}
		} catch (SQLException ex) {
		}
		return list;
	}

	public void executeQuery(String query) {
		Connection conn = DBConnection.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception ex) {

		}
	}

	private void displayAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}

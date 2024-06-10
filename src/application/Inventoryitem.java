package application;

public class Inventoryitem {
	public Inventoryitem(int inventoryItemId, int quantity, String name) {
		super();
		this.inventoryItemId = inventoryItemId;
		this.quantity = quantity;
		this.name = name;
	}
	private int inventoryItemId;
	private int quantity;
	 private String name;
	public int getInventoryItemId() {
		return inventoryItemId;
	}
	public void setInventoryItemId(int inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

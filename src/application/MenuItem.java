package application;

public class MenuItem {
	// --> Amro Deek <-- 
	private int item_id;
	private String size;
	private String name;
	private String type;
	private double price;
	private String description;

	public MenuItem(int item_id, String size, String name, String type, double price, String description) {
		super();
		this.item_id = item_id;
		this.size = size;
		this.name = name;
		this.type = type;
		this.price = price;
		this.description = description;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MenuItem [item_id=" + item_id + ", size=" + size + ", name=" + name + ", type=" + type + ", price="
				+ price + ", description=" + description + "]";
	}

}

package application;

public class Branch {
	// --> Amro Deek <-- 
	private int branch_id;
	private String phoneNumber;
	private String location;
	private String openinghours;
	private int menu_id;
	private int resturant_id;

	public Branch(int branch_id, String phoneNumber, String location, String openinghours, int menu_id,
			int resturant_id) {
		super();
		this.branch_id = branch_id;
		this.phoneNumber = phoneNumber;
		this.location = location;
		this.openinghours = openinghours;
		this.menu_id = menu_id;
		this.resturant_id = resturant_id;
	}

	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOpeninghours() {
		return openinghours;
	}

	public void setOpeninghours(String openinghours) {
		this.openinghours = openinghours;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public int getResturant_id() {
		return resturant_id;
	}

	public void setResturant_id(int resturant_id) {
		this.resturant_id = resturant_id;
	}

}

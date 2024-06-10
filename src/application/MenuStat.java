package application;

public class MenuStat {
	// --> Amro Deek <-- 
	private int menuId;
	private int NoOfItems;

	public MenuStat(int menuId, int noOfItems) {
		super();
		this.menuId = menuId;
		NoOfItems = noOfItems;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getNoOfItems() {
		return NoOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		NoOfItems = noOfItems;
	}

	@Override
	public String toString() {
		return "MenuStat [menuId=" + menuId + ", NoOfItems=" + NoOfItems + "]";
	}

}

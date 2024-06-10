package application;

public class Customer {
	private int customer_id;
	private String customer_name;
	private int address_id;
	
	
	
	public Customer(int customer_id, String customer_name, int address_id) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.address_id = address_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}



}

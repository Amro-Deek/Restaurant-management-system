package application;

public class PaymentMethod {

	private int payment_method_id;
	private double cash;
	private String credit_card;
	private String mobile_payment;
	public PaymentMethod(int payment_method_id, double cash, String credit_card, String mobile_payment) {
		super();
		this.payment_method_id = payment_method_id;
		this.cash = cash;
		this.credit_card = credit_card;
		this.mobile_payment = mobile_payment;
	}
	public int getPayment_method_id() {
		return payment_method_id;
	}
	public void setPayment_method_id(int payment_method_id) {
		this.payment_method_id = payment_method_id;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public String getCredit_card() {
		return credit_card;
	}
	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}
	public String getMobile_payment() {
		return mobile_payment;
	}
	public void setMobile_payment(String mobile_payment) {
		this.mobile_payment = mobile_payment;
	}

}

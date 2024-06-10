package application;

import java.sql.Date;

public class Order {
    private int orderId;
    private int branchId;
    private int customerId;
    private int deliveryId;
    private int paymentMethodId;
    private int tableId;
    private Date orderDate;

    
    
    
    
    
    public Order(int orderId, int branchId, int customerId, int deliveryId, int paymentMethodId, int tableId, Date orderDate) {
        this.orderId = orderId;
        this.branchId = branchId;
        this.customerId = customerId;
        this.deliveryId = deliveryId;
        this.paymentMethodId = paymentMethodId;
        this.tableId = tableId;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public void setOrderDateString(String orderDateString) {
        this.orderDate = orderDateString != null && !orderDateString.isEmpty() ? Date.valueOf(orderDateString) : null;
    }
}



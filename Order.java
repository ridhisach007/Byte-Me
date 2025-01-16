public class Order {
    private String status;
    private String order_name;
    private String customer_id;
    private int quantity;

    public Order(String order_name, String customer_id,int quantity) {
        this.order_name = order_name;
        this.customer_id = customer_id;
        this.status="processing";
        this.quantity=quantity;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getOrder_name() {
        return order_name;
    }
    public String getCustomerID() {
        return customer_id;
    }
   public int getQuantity() {
        return quantity;
   }
}

import java.io.*;
import java.util.*;

public class Admin_Canteen extends data{

    private HashMap<String,Menu> canteen_menu;
    private ArrayList<Order> cancelled_orders;
    private Queue<Order> pending_orders;
    private Queue<Order> completed_orders;


    public Admin_Canteen(){

        this.canteen_menu = new HashMap<>();
        this.pending_orders = new LinkedList<Order>();
        this.completed_orders = new LinkedList<>();
        this.cancelled_orders = new ArrayList<>();
        canteen_menu.put("Pasta",new Menu("Snacks","In stock",50));
        canteen_menu.put("Burger",new Menu("Breads","In stock",40));
        canteen_menu.put("Rajma Chawal",new Menu("Main Course","In stock",50));
        canteen_menu.put("Pav Bhaji",new Menu("Snacks","In stock",100));
        canteen_menu.put("Roti",new Menu("Breads","Sold out",10));
        canteen_menu.put("Pizza",new Menu("Main Course","In stock",200));


    }
    private void read() {
        File file = new File("canteen_data.txt");

        if (!file.exists()) {
            System.out.println("canteen_data.txt not found.\n");
            write();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String itemName = data[0];
                String category = data[1];
                String available = data[2];
                int price = Integer.parseInt(data[3]);
                canteen_menu.put(itemName, new Menu(category, available, price));
            }
        } catch (IOException e) {
            System.err.println("Error reading menu data: " + e.getMessage());
        }
    }

    void write(){
        try (PrintWriter writer = new PrintWriter("canteen_data.txt")){
            writer.println("MENU");
            for (Map.Entry<String, Menu> entry : canteen_menu.entrySet()) {
                writer.println(entry.getKey() + "," +
                        entry.getValue().getCategory() + "," +
                        entry.getValue().getAvailable() + "," +
                        entry.getValue().getPrice());
            }




        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }



    }
    private void read_orders() {
        File order_file = new File("order_data.txt");

        if (!order_file.exists()) {
            System.out.println("order_data.txt not found.\n");
            write();
            return;
        }

        try (BufferedReader order_reader = new BufferedReader(new FileReader(order_file))) {
            String line;
            order_reader.readLine();
            while ((line = order_reader.readLine()) != null) {
                String[] data = line.split(",");
                String CustomerID = data[0];
                String OrderName = data[1];
                String Status = data[2];
                Integer Quantity = Integer.parseInt(data[3]);
                //int price = Integer.parseInt(data[3]);
                pending_orders.add(new Order(OrderName,CustomerID,Quantity));
                //completed_orders.add(new Order(OrderName,CustomerID,Quantity));
            }
        } catch (IOException e) {
            System.err.println("Error reading order data: " + e.getMessage());
        }
    }
    void order_write(){
        try (PrintWriter order_writer = new PrintWriter("order_data.txt")){
            order_writer.println("ORDERS");
            for (Order order : pending_orders) {
                order_writer.println(order.getOrder_name() + "," +
                        order.getCustomerID() + "," +
                        order.getStatus() + "," +
                        order.getQuantity());
            }

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }



    }
    public HashMap<String,Menu> getCanteen_menu(){
        return this.canteen_menu;
    }


    public void add_items(String item_name,String cat_name,int item_price){
        canteen_menu.put(item_name,new Menu(cat_name,"In stock",item_price));
         //System.out.print(canteen_menu);

    }
    public void display_items() {
        System.out.println("                         CANTEEN MENU                          ");
        System.out.printf("%-20s %-15s %-12s %-5s%n", "Item Name", "Category", "Available", "Price");
        System.out.println("--------------------------------------------------------------");

        for (Map.Entry<String, Menu> entry : canteen_menu.entrySet()) {
            String itemName = entry.getKey();
            Menu menuItem = entry.getValue();


            System.out.printf("%-20s %-15s %-12s %-5d%n",
                    itemName,
                    menuItem.getCategory(),
                    menuItem.getAvailable(),
                    menuItem.getPrice());
        }

        System.out.println("--------------------------------------------------------------");
    }


    public void update_items(String name_to_update,String updated_name,String updated_cat_name){
       if(canteen_menu.containsKey(name_to_update)){
           canteen_menu.get(name_to_update).setCategory(updated_cat_name);
           canteen_menu.put(updated_name,canteen_menu.get(name_to_update));
           canteen_menu.remove(name_to_update);
           System.out.print("Item successfully updated\n");
          // System.out.print(canteen_menu);
       }
       else{
           System.out.println("Item not found\n");
       }







    }
    public void remove_item(String remove_name){
         if(canteen_menu.containsKey(remove_name)){
             canteen_menu.remove(remove_name);
             System.out.print("Item successfully removed\n");
         }
         else{
             System.out.println("Item not found\n");
         }


    }
    public void modify_price(String price_update_name,int price_update){
         if(canteen_menu.containsKey(price_update_name)){
             canteen_menu.get(price_update_name).setPrice(price_update);
         }
         else{
             System.out.println("Item not found\n");
         }

    }
    public void update_availability(String available_name,String availability){
        if(canteen_menu.containsKey(available_name)){
            canteen_menu.get(available_name).setAvailable(availability);


        }
        else{
            System.out.println("Item not found\n");
        }


    }
    public void receive_order(Order order){
        pending_orders.add(order);
        //System.out.print(pending_orders);
        System.out.print("Order recieved for " + order.getOrder_name() +" by customer "+order.getCustomerID()+ "\n");

    }
    public void update_status() {
        Scanner scanner = new Scanner(System.in);
        List<Order> temp_remove = new ArrayList<>();

        for (Order order : pending_orders) {
            String item = order.getOrder_name();

            System.out.print("Enter status for " + item + " either processing/delivered! \n");
            String status = scanner.nextLine();

            if(!status.equals("processing")) {
                order.setStatus(status);
                //System.out.print(order.getStatus()+"\n");
                System.out.print("Order status updated for " + order.getOrder_name() + " to " + status + "\n");

                completed_orders.add(order);
                temp_remove.add(order);
            }

        }

        pending_orders.removeAll(temp_remove);



}
    public void view_pending_orders(){
        if(pending_orders.isEmpty()){
            System.out.println("No pending orders found\n");
        }
        else{
            System.out.println("Pending orders:\n");
            for(Order order : pending_orders){
                System.out.println(order.getCustomerID()+":"+order.getOrder_name());

            }
        }
    }
    public ArrayList<Order> customer_orders(String customerID) {

        ArrayList<Order> orders = new ArrayList<>();


        for (Order order : pending_orders) {

            if (order.getCustomerID().equals(customerID)) {
                orders.add(order);
            }
        }
        return orders;
    }
    public ArrayList<Order> delivered_orders(String customerID) {
        ArrayList<Order> comp_orders = new ArrayList<>();
        for (Order order : completed_orders) {
            if (order.getCustomerID().equals(customerID)) {
                comp_orders.add(order);
            }
        }
        return comp_orders;
    }


    public Queue<Order> getPendingOrders(){
        return pending_orders;
    }

    public void handleRequest(List<Customer_Canteen>customers) {
       // List<Customer_Canteen>customers=new ArrayList<>();
        for (Customer_Canteen customer : customers) {
            HashMap<String, ArrayList<String>> customerRequests = customer.getSplRequest();
            for (Map.Entry<String, ArrayList<String>> entry : customerRequests.entrySet()) {
                System.out.println("Customer ID: " + entry.getKey());
                System.out.println("Requests: " + entry.getValue());
            }
        }
    }
    public void remove_order(Order order){
        pending_orders.remove(order);
        cancelled_orders.add(order);
    }
    public void process_refund(String order_name){
        boolean found=false;
        for(Order order: cancelled_orders){
            if(order.getOrder_name().equals(order_name)){
                found=true;
                int refund_price=canteen_menu.get(order_name).getPrice();
                int refund_qty= order.getQuantity();
                int refund=refund_price*refund_qty;
                System.out.print("Refund for "+order_name+" price: "+refund+" has been initiated\n");
            }
        }
        if(!found){
            System.out.println("Order not found\n");

        }
    }
    public void report_generation(){
        int sales=0;
    for(Order order : completed_orders){
        int report=canteen_menu.get(order.getOrder_name()).getPrice();
        int qty=order.getQuantity();
        sales+=report*qty;
    }
    System.out.print("Total sales: "+sales+"\n");

    }
    public void viewReviews(Map<String, Customer_Canteen> customerMap) {
        for (Map.Entry<String, Customer_Canteen> entry : customerMap.entrySet()) {

            Customer_Canteen customer = entry.getValue();
            Map<String, List<String>> reviews_from_customer = customer.getReviews();


            for (Map.Entry<String, List<String>> rev : reviews_from_customer.entrySet()) {
                String item = rev.getKey();
                List<String> reviewList = rev.getValue();

                System.out.println(item + " reviews:");
                for (String review : reviewList) {
                    System.out.println(review);
                }
            }
            System.out.println();
        }
    }

}











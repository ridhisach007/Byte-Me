import java.io.*;
import java.util.*;
import java.util.TreeMap;

public class Customer_Canteen extends  data {
    private String login_id;
    private String password;
    private Admin_Canteen admin;
    private HashMap<String, Menu> student_menu;
    private ArrayList<String> requests;
    private HashMap<String,ArrayList<String>> spl_request;
    private Cart cart;
    private ArrayList<Order> order_history;
    private ArrayList<String>review;
    private HashMap<String,ArrayList<String>>reviewMap;

    public Customer_Canteen(String login_id, String password, Admin_Canteen admin) {
        this.login_id = login_id;
        this.password = password;
        this.admin = admin;
        this.student_menu = admin.getCanteen_menu();
        this.requests = new ArrayList<String>();
        this.spl_request=new HashMap<String,ArrayList<String>>();
        this.cart = new Cart(admin);
        this.order_history=new ArrayList<Order>();
        this.review=new ArrayList<String>();
        this.reviewMap=new HashMap<String,ArrayList<String>>();
    }

    void customer_order_write() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("order_history_data.txt", true))) {
            if (order_history.isEmpty()) {
                System.out.println("No orders to write!\n");
                return;
            }

            boolean orderFound = false;
            Iterator<Order> iterator = order_history.iterator();

            while (iterator.hasNext()) {
                Order order = iterator.next();
                for (Map.Entry<String, Menu> entry : student_menu.entrySet()) {
                    if (entry.getKey().equals(order.getOrder_name())) {
                        orderFound = true;

                        Menu menu = entry.getValue();

                        writer.println(order.getCustomerID() + "," +
                                order.getOrder_name() + "," +
                                order.getQuantity() + "," +
                                menu.getPrice()*order.getQuantity());
                        iterator.remove();
                        break;
                    }
                }
            }

            if (!orderFound) {
                System.out.println("No valid items found to write!\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void customer_order_read(String customerIdFilter) {
        try (BufferedReader reader = new BufferedReader(new FileReader("order_history_data.txt"))) {
            String line;
            System.out.println("Order history for Customer ID: " + customerIdFilter);
            System.out.printf("%-15s %-10s %-10s %-10s\n", "CustomerID", "Order", "Quantity", "Price");

            int c = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String customerId = parts[0];
                    String orderName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);

                    if (customerId.equals(customerIdFilter)) {
                        System.out.printf("%-15s %-10s %-10d %-10.2f\n", customerId, orderName, quantity, price);
                        c++;
                    }
                }
            }


            if (c == 0) {
                System.out.println("No orders found for Customer ID: " + customerIdFilter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void view_menu(Admin_Canteen admin) {


        System.out.println("                         CANTEEN MENU                          ");
        System.out.printf("%-20s %-15s %-12s %-5s%n", "Item Name", "Category", "Available", "Price");
        System.out.println("--------------------------------------------------------------");

        for (Map.Entry<String, Menu> entry : student_menu.entrySet()) {
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
    public void search(String search_name) {
        if (student_menu.containsKey(search_name)) {
            System.out.print("\n");
            System.out.print(search_name + "\n");
            System.out.print("------------------\n");
            Menu menu = student_menu.get(search_name);
            System.out.print("Category:" + menu.getCategory() + "\n");
            System.out.print("Available:" + menu.getAvailable() + "\n");
            System.out.print("Price:" + menu.getPrice() + "\n");
            System.out.print("------------------\n");

        } else {
            System.out.println("Sorry! No such item available.\n");
        }
    }
    public HashMap<Menu,String> find_name(){
        HashMap<Menu,String> menu_name = new HashMap<>();
        for(Map.Entry<String,Menu>entry : student_menu.entrySet()){
            menu_name.put(entry.getValue(), entry.getKey());
        }
        return menu_name;
    }
    public void filter(String category) {
        HashMap<String, List<Menu>> categoryMap = new HashMap<>();
        HashMap<Menu,String> itemNameMap=find_name();

        for (Map.Entry<String, Menu> entry : student_menu.entrySet()) {
            Menu menu = entry.getValue();
            String itemName = entry.getKey();
            String categoryName = menu.getCategory();

            if(!categoryMap.containsKey(categoryName)) {
                categoryMap.put(categoryName, new ArrayList<>());
            }
            categoryMap.get(categoryName).add(menu);

        }
        if (categoryMap.containsKey(category)) {
            System.out.printf("Items in category: %s%n", category);
            System.out.printf("%-20s %-15s %-12s %-5s%n", "Item Name", "Category", "Available", "Price");
            System.out.println("--------------------------------------------------------------");

            List<Menu> items = categoryMap.get(category);
            for (Menu menuItem : items) {
                String itemName = itemNameMap.get(menuItem);
                if (itemName == null) {
                    System.out.print("No item found!");
                } else {
                    System.out.printf("%-20s %-15s %-12s %-5d%n",
                            itemName,
                            menuItem.getCategory(),
                            menuItem.getAvailable(),
                            menuItem.getPrice());
                }
            }

            System.out.println("--------------------------------------------------------------");
        } else {
            System.out.println("Category not found.\n");
        }
    }

    public void sort_price(){
        HashMap<Menu,String>itemNameMap=find_name();
        TreeMap<Integer,List<Menu>>priceMap=new TreeMap<>();

        for(Map.Entry<String,Menu>entry :student_menu.entrySet()){
            Menu menu=entry.getValue();
            int price=menu.getPrice();

            if(!priceMap.containsKey(price)) {
                priceMap.put(price,new ArrayList<>());
            }
            priceMap.get(price).add(menu);

        }
        System.out.println("                         CANTEEN MENU                          ");
        System.out.printf("%-20s %-15s %-12s %-5s%n", "Item Name", "Category", "Available", "Price");
        System.out.println("--------------------------------------------------------------");

        for(Map.Entry<Integer,List<Menu>> entry:priceMap.entrySet()){
            List<Menu> price_menu= entry.getValue();

            for(Menu menu:price_menu){
                String itemName=itemNameMap.get(menu);
                System.out.printf("%-20s %-15s %-12s %-5d%n",
                        itemName,
                        menu.getCategory(),
                        menu.getAvailable(),
                        menu.getPrice());
            }
            }
        System.out.println("--------------------------------------------------------------");

        }
        public void add_cart(String item,int qty){
            if(!student_menu.containsKey(item)){
                System.out.println("Sorry! No such item available.\n");
            }
            else {
                int price = student_menu.get(item).getPrice();
                cart.addItem(item, qty, price);
                cart.viewCart();
            }

        }
        public void modify_cart(String item,int qty){
           if(student_menu.containsKey(item)){
               cart.modifyQty(item,qty);
               cart.viewCart();
           }
           else{
               System.out.println("Item not found.\n");
           }
        }
        public void remove_cart(String item){
            if(student_menu.containsKey(item)){
                cart.removeItem(item);
                cart.viewCart();
            }
            else{
                System.out.println("Item not found.\n");
            }
        }
        public void view_total(){
            cart.total_price();
        }
    public void customer_checkout(String item, String login_id) {
        cart.checkout(item, login_id);

        int qty = cart.getQuantity(item);
        if (qty > 0) {
            Order new_order = new Order(item, login_id, qty);
            if (!order_history.contains(new_order)) {
                order_history.add(new_order);
            }

            cart.getCartMap().remove(item);
            cart.getPriceMap().remove(item);
        } else {
            System.out.println("Invalid quantity for item: " + item);
        }
    }


    public void view_status(String login_id) {
        List<Order> status_order = admin.customer_orders(login_id);
        List<Order> comp_status_order=admin.delivered_orders(login_id);

        if (status_order.isEmpty()&&comp_status_order.isEmpty()) {
            System.out.println("No orders found for this customer.");
        } else if(comp_status_order.isEmpty()&&!status_order.isEmpty()) {
            for (Order order : status_order) {
                System.out.println("Order for " + order.getOrder_name() + " is " + order.getStatus());
            }

        }
        else if(status_order.isEmpty()&&!comp_status_order.isEmpty()) {
            for (Order order : comp_status_order) {
                System.out.println("Order for " + order.getOrder_name() + " is " + order.getStatus());
            }
        }

    }
       public void makeRequest(String login_id,String request){
          requests.add(request);
         spl_request.put(login_id,requests);
         System.out.print("Your request has successfully been sent\n");
       }

       public HashMap<String,ArrayList<String>> getSplRequest() {
            return spl_request;
       }

       public void orderHistory(String login_id){
           //customer_order_write();
           customer_order_read(login_id);
       }
    public void cancel_order(String cancel_item, String login_id) {
        boolean isCancellable = false;
        Iterator<Order> iterator = admin.getPendingOrders().iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getOrder_name().equals(cancel_item) && order.getCustomerID().equals(login_id)) {
                if (!order.getStatus().equals("Cancelled")) {
                    order.setStatus("Cancelled");
                    System.out.println("Your order for " + cancel_item + " has been cancelled.\n");
                    iterator.remove();
                    admin.remove_order(order);
                } else {
                    System.out.println("Order has already been cancelled.\n");
                }
                isCancellable = true;
                break;
            }
        }

        if (!isCancellable) {
            System.out.println("Order cannot be cancelled.\n");
        }
    }
    public void give_review(String item,String customer_review){
        boolean found=false;
        for(Order order:order_history){
            if(order.getOrder_name().equals(item)){
                found=true;
                review.add(customer_review);
                reviewMap.put(item,review);
                System.out.print("Review recorded. Thank you!\n");
                break;
            }
        }
        if(!found){
            System.out.println("Order not found.\n");
        }

    }
    public Map<String, List<String>> getReviews() {
        return new HashMap<>(reviewMap);
    }
    public void view_review(String item) {
        for(Customer_Canteen c : customers){
            ArrayList<String>reviews = c.reviewMap.get(item);
            for(String review:reviews){
                System.out.println(review);
            }
        }
    }
}

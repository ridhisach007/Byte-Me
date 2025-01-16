import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cart {
    private HashMap<String,Integer> cartMap;
    private HashMap<String,Integer> priceMap;
    private Admin_Canteen admin;


    public Cart(Admin_Canteen admin) {
        this.cartMap = new HashMap<>();
        this.priceMap = new HashMap<>();
        this.admin = admin;
    }

    public void addItem(String item,int quantity,int price){
        HashMap<String,Menu> cart_menu=new HashMap<>();
        cart_menu=admin.getCanteen_menu();
        if(cart_menu.get(item).getAvailable().equals("Sold out")){
            System.out.println("Item is already sold out");
        }
        else if(!cart_menu.containsKey(item)){
            System.out.print("Item not found");
        }
        else if(quantity<0){
            System.out.println("Quantity is negative");
        }
        else {
            cartMap.put(item, quantity);
            priceMap.put(item, price);
        }
    }

    public void modifyQty(String item,int qty){
        if(qty<0){
            System.out.println("Quantity is negative");
            return;
        }
        if(cartMap.containsKey(item)){
            cartMap.put(item,qty);
        }
        else {
            System.out.print("Item not present in cart\n");
        }
    }
    public void removeItem(String item){
        if(cartMap.containsKey(item)){
            cartMap.remove(item);
            priceMap.remove(item);
        }
        else {
            System.out.print("Item not present in cart\n");
        }

    }
    public void viewCart() {
        if (cartMap.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Items in Cart:");
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                String item= entry.getKey();
                int quantity = entry.getValue();
                System.out.println("Item: " + item + ", Quantity: " + quantity);
            }
        }

    }
    public void total_price(){
        int totalPrice = 0;
        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            Integer itemPrice = priceMap.get(entry.getKey());
            if (itemPrice != null) {
                totalPrice += entry.getValue() * itemPrice;
            }
        }
        System.out.println("Total Price: " + totalPrice);
    }

    public void checkout(String item,String login_id){
        if(cartMap.containsKey(item)){
            int quantity = cartMap.get(item);
            //cartMap.remove(item);
            //priceMap.remove(item);
            int price=priceMap.get(item);
            int total=price*quantity;
            System.out.print("Your total price is:"+ total+"\n");
            Scanner scanner=new Scanner(System.in);
            System.out.print("Enter the amount\n");
            int amount=scanner.nextInt();
            scanner.nextLine();
            if(amount==total) {
                Order order = new Order(item, login_id, quantity);
                System.out.print("Your order has been successfully placed!\n");

                admin.order_write();
                admin.receive_order(order);


                /*cartMap.remove(item);
                priceMap.remove(item);*/
            }
            else {
                System.out.print("Your total price is incorrect.\n");
            }

        }
        else{
            System.out.print("Item not present in cart\n");
        }
    }
    public int getQuantity(String item){
        return cartMap.get(item);
    }

    public HashMap<String,Integer> getCartMap() {
        return cartMap;
    }
    public HashMap<String,Integer> getPriceMap() {
        return priceMap;
    }



}

import java.io.*;
import java.util.*;


public class Main{
    static HashMap<String,String> map1=new HashMap<>();

    public static void login_write(String id,String password) {
        try (PrintWriter login_writer = new PrintWriter(new FileWriter("login_data.txt", true))) {

                login_writer.println(id + ","+
                        password);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean login_read(String login, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("login_data.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data=line.split(",");
                if(data.length==2){
                   String login_txt=data[0].trim();
                   String password_txt=data[1].trim();
                   if(login_txt.equals(login) && password_txt.equals(password)){
                       return true;
                   }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;


    }
    public static void main(String[] args) throws InvalidLoginException {

        Admin_Canteen admin= new Admin_Canteen();



        HashMap<String,Customer_Canteen> customerMap=new HashMap<>();
        //List<Customer_Canteen> customers=new ArrayList<>();
        //map1.put("ridhi","1");
       // map1.put("sheetal","2");



        System.out.print("\n");
        System.out.print("      WELCOME TO THE UNIVERSITY ONLINE ORDERING SYSTEM, BYTE ME!     \n");
        System.out.print("\n");

        boolean program_run=true;

        while(program_run){
            Scanner scanner= new Scanner(System.in);
            System.out.print("Enter as admin/customer or exit to terminate. \n");
            String user=scanner.nextLine();




            switch(user){
                case "admin" :
                    System.out.print("Enter password: ");
                    String password=scanner.nextLine();
                    try{
                        if(password.equals("1234")){

                            boolean admin_running=true;


                            while(admin_running){

                                System.out.print("Admin mode \n");
                                System.out.print("Select appropriate action\n");
                                System.out.print("1. Menu Management\n");
                                System.out.print("2. Order Management\n");
                                System.out.print("3. Report Generation\n");
                                System.out.print("4. View Review\n");
                                System.out.print("5. Log out\n");

                                String choice=scanner.nextLine();

                                switch(choice){
                                    case "1" :
                                        System.out.print("Menu Management Selected\n");
                                        System.out.print("\n");
                                        System.out.print("Select from the following\n");
                                        System.out.print("1.1 Add new items\n");
                                        System.out.print("1.2 Update items\n");
                                        System.out.print("1.3 Remove items\n");
                                        System.out.print("1.4 Modify prices\n");
                                        System.out.print("1.5 Update availability\n");
                                        System.out.print("1.6 Log out\n");

                                        String menu_choice=scanner.nextLine();

                                        switch(menu_choice){
                                            case "1.1":
                                                System.out.print("Add name of the item\n");
                                                String item_name=scanner.nextLine();
                                                System.out.print("Add name of the category\n");
                                                String cat_name=scanner.nextLine();
                                                while(true) {
                                                    try {
                                                        System.out.print("Add price of the item\n");
                                                        int item_price = scanner.nextInt();
                                                        scanner.nextLine();
                                                        admin.add_items(item_name,cat_name,item_price);
                                                        admin.write();
                                                        break;
                                                    }
                                                    catch (InputMismatchException e) {
                                                        System.out.println("Please enter a valid number");
                                                        scanner.nextLine();
                                                    }
                                                }

                                                System.out.print(item_name+" successfully added\n");
                                                System.out.print("\n");
                                                admin.display_items();

                                                break;

                                                 case "1.2":
                                                    System.out.print("Enter name of the item to update\n");
                                                    String name_to_update=scanner.nextLine();
                                                    System.out.print("Enter updated name of the item\n");
                                                    String updated_name=scanner.nextLine();
                                                    System.out.print("Enter updated category of the item\n");
                                                    String updated_cat_name=scanner.nextLine();
                                                    admin.update_items(name_to_update,updated_name,updated_cat_name);
                                                    admin.write();
                                                    admin.display_items();
                                                      System.out.print("\n");
                                                    break;


                                                    case "1.3":
                                                        System.out.print("Enter name of the item to remove\n");
                                                        String remove_name=scanner.nextLine();
                                                        admin.remove_item(remove_name);
                                                        admin.write();
                                                        System.out.print("\n");
                                                        admin.display_items();
                                                        break;

                                                    case "1.4":
                                                        System.out.print("Enter name of the item to update\n");
                                                        String price_update_name=scanner.nextLine();
                                                        while(true) {
                                                            try {
                                                                System.out.print("Enter modified price\n");
                                                                int price_update = scanner.nextInt();
                                                                scanner.nextLine();
                                                                admin.modify_price(price_update_name, price_update);
                                                                admin.write();
                                                                break;
                                                            }
                                                            catch (InputMismatchException e) {
                                                                System.out.println("Please enter a valid number");
                                                            }
                                                        }
                                                        System.out.print("\n");
                                                        admin.display_items();
                                                        break;
                                                   case "1.5":
                                                       System.out.print("Enter name of the item to update\n");
                                                       String availability_update_name=scanner.nextLine();
                                                       System.out.print("Enter modified availability\n");
                                                       String availability_update=scanner.nextLine();
                                                       admin.update_availability(availability_update_name,availability_update);
                                                       admin.write();
                                                       System.out.print("\n");
                                                       admin.display_items();
                                                       break;
                                                  case "1.6":
                                                      admin_running=false;
                                                      System.out.print("Logged out!\n");
                                                      break;

                                            default:
                                                System.out.print("Invalid option. Please Select again\n");


                                        }
                                        break;
                                    case "2" :
                                        System.out.print("Order Management Selected\n");
                                        System.out.print("\n");
                                        System.out.print("Select from the following\n");
                                        System.out.print("2.1 View pending items\n");
                                        System.out.print("2.2 Update order status\n");
                                        System.out.print("2.3 Process Refunds\n");
                                        System.out.print("2.4 Handle special requests\n");
                                        System.out.print("2.5 Log out\n");

                                        String admin_choice=scanner.nextLine();

                                        switch(admin_choice){
                                            case "2.1":
                                                admin.view_pending_orders();
                                                break;

                                             case "2.2":
                                                 //System.out.print("Enter name of the item\n");
                                                 //String item_name=scanner.nextLine();
                                                 //System.out.print("Enter updated status\n");
                                                 //String updated_status=scanner.nextLine();
                                                 admin.update_status();
                                                 admin.order_write();
                                                 break;
                                            case "2.3":
                                                System.out.print("Enter the name of the item to be refunded\n");
                                                String refund_name=scanner.nextLine();
                                                admin.process_refund(refund_name);
                                                break;

                                            case "2.4" :
                                               // admin.handleRequest(customers);
                                                break;
                                            case "2.5" :
                                                admin_running=false;
                                                System.out.print("Logged out!\n");
                                                break;

                                        }
                                        break;
                                    case "3"  :
                                        admin.report_generation();
                                        break;
                                    case "4"  :
                                        admin.viewReviews(customerMap);
                                        break;

                                    case "5":
                                        admin_running=false;
                                        System.out.print("Logged out!\n");
                                        break;

                                    default :
                                        System.out.print("Invalid option. Please Select Again\n");



                                }
                            }
                        }
                        else{
                            throw new InvalidLoginException("Invalid Credentials! Please try again.\n");

                        }

                    }
                    catch(InvalidLoginException e){
                        System.out.println(e.getMessage());

                    }
                    break;
                case "customer":
                    System.out.print("1.Log in or 2.Sign up\n");
                    String login=scanner.nextLine();
                    switch(login){
                        case "1":
                            System.out.print("Enter login id\n");
                            String login_id=scanner.nextLine();
                            System.out.print("Enter password\n");
                            String student_password=scanner.nextLine();

                            try {
                                if (!login_read(login_id,student_password)) {
                                    throw new InvalidLoginException("Invalid credentials! Please try again.\n");
                                }
                                Customer_Canteen customer = customerMap.get(login_id);

                                if (customer == null) {
                                    customer = new Customer_Canteen(login_id, student_password, admin);
                                    customerMap.put(login_id, customer);
                                }

                                        System.out.print("You are successfully logged in\n");
                                       //Customer_Canteen customer=customerMap.get(login_id);
                                        boolean customer_running = true;

                                        while (customer_running) {
                                            System.out.print("Customer mode\n");
                                            System.out.print("1. Browse Menu\n");
                                            System.out.print("2. Cart Operations\n");
                                            System.out.print("3. Order Tracking\n");
                                            System.out.print("4. Give review\n");
                                            System.out.print("5.View reviews\n");
                                            System.out.print("6. Log out\n");

                                            String customer_choice = scanner.nextLine();
                                            switch (customer_choice) {
                                                case "1":
                                                    System.out.print("Browse Menu selected\n");
                                                    System.out.print("1.1 View menu\n");
                                                    System.out.print("1.2 Search functionalities\n");
                                                    System.out.print("1.3 Filter by category\n");
                                                    System.out.print("1.4 Sort by price\n");
                                                    System.out.print("1.5 Log out\n");

                                                    String customer_choice2 = scanner.nextLine();

                                                    switch(customer_choice2){
                                                        case "1.1":
                                                            customer.view_menu(admin);
                                                            break;
                                                        case "1.2" :
                                                            System.out.print("Enter name of the item to search\n");
                                                            String search_name=scanner.nextLine();
                                                            customer.search(search_name);
                                                            break;
                                                        case "1.3" :
                                                            System.out.print("Enter category\n");
                                                            String category=scanner.nextLine();
                                                            customer.filter(category);
                                                            break;
                                                        case "1.4":
                                                            customer.sort_price();
                                                            break;
                                                        case "1.5"  :
                                                            customer_running=false;
                                                            System.out.print("Logged out!\n");
                                                            break;


                                                    }



                                                    break;
                                                case "2":
                                                    System.out.print("Cart Operations selected\n");
                                                    System.out.print("2.1 Add items\n");
                                                    System.out.print("2.2 Modify items\n");
                                                    System.out.print("2.3 Remove items\n");
                                                    System.out.print("2.4 View total\n");
                                                    System.out.print("2.5 Checkout\n");
                                                    System.out.print("2.6 Make special request\n");
                                                    System.out.print("2.7 Log out\n");

                                                    String customer_choice3 = scanner.nextLine();

                                                    switch(customer_choice3){
                                                        case "2.1":
                                                            System.out.print("Enter name of the item to add\n");
                                                            String add_cart_item=scanner.nextLine();
                                                            System.out.print("Enter quantity\n");
                                                            int quantity=scanner.nextInt();
                                                            scanner.nextLine();
                                                            customer.add_cart(add_cart_item,quantity);
                                                            break;

                                                        case "2.2" :
                                                            System.out.print("Enter name of the item to modify\n");
                                                            String modify_cart_item=scanner.nextLine();
                                                            System.out.print("Enter quantity\n");
                                                            int modified_qty=scanner.nextInt();
                                                            scanner.nextLine();
                                                            customer.modify_cart(modify_cart_item,modified_qty);
                                                            break;

                                                         case "2.3" :
                                                             System.out.print("Enter name of the item to remove\n");
                                                             String remove_cart_item=scanner.nextLine();
                                                             customer.remove_cart(remove_cart_item);
                                                             break;

                                                        case "2.4" :
                                                            customer.view_total();
                                                            break;

                                                        case "2.5" :
                                                            System.out.print("Enter name of the item to checkout\n");
                                                            String checkout_item=scanner.nextLine();
                                                            customer.customer_checkout(checkout_item,login_id);
                                                            customer.customer_order_write();
                                                            break;

                                                        case "2.6":
                                                            System.out.print("Enter your request\n");
                                                            String request=scanner.nextLine();
                                                            customer.makeRequest(login_id,request);
                                                            break;

                                                        case "2.7":
                                                            customer_running=false;
                                                            System.out.print("Logged out!\n");
                                                            break;

                                                        default :
                                                            System.out.print("Invalid option. Please Select again\n");

                                                    }

                                                    break;
                                                case "3":
                                                    System.out.print("Order Tracking selected\n");
                                                    System.out.print("3.1 View Order Status\n");
                                                    System.out.print("3.2 Cancel order\n");
                                                    System.out.print("3.3 Order history\n");
                                                    System.out.print("3.4 Log out\n");

                                                    String customer_choice4 = scanner.nextLine();

                                                    switch(customer_choice4){
                                                        case "3.1" :
                                                            customer.view_status(login_id);
                                                            break;


                                                        case "3.2" :
                                                            System.out.print("Enter the name of item to cancel\n");
                                                            String cancel_item=scanner.nextLine();
                                                            customer.cancel_order(cancel_item,login_id);
                                                            break;

                                                        case "3.3":
                                                            customer.orderHistory(login_id);
                                                            break;

                                                        case "3.4":
                                                            customer_running=false;
                                                            System.out.print("Logged out!\n");

                                                    }


                                                    break;

                                                case "4":
                                                    System.out.print("Enter the name of item to review\n");
                                                    String review_item=scanner.nextLine();
                                                    System.out.print("Enter review\n");
                                                    String review=scanner.nextLine();
                                                    customer.give_review(review_item,review);
                                                    break;
                                                case "5" :
                                                    System.out.print("Enter the name of the item\n");
                                                    String item_name=scanner.nextLine();
                                                    customer.view_review(item_name);
                                                    break;

                                                case "6":
                                                    customer_running = false;
                                                    System.out.print("Logged out!\n");
                                                    break;
                                                default:
                                                    System.out.print("Invalid option. Please Select Again\n");

                                            }
                                        }

                                }
                            catch (InvalidLoginException e) {
                                System.out.println(e.getMessage());
                            }


                        break;

                        case "2":
                            System.out.print("Create login id\n");
                            String create_login_id=scanner.nextLine();
                            System.out.print("Create password\n");
                            String create_password=scanner.nextLine();
                            //map1.put(create_login_id,create_password);
                            login_write(create_login_id,create_password);
                            Customer_Canteen customer= new Customer_Canteen(create_login_id,create_password,admin);
                            customerMap.put(create_login_id,customer);
                            data.addcustomers(customer);
                            System.out.print("You are successfully signed up!\n");
                            break;
                    }



                    break;

                case "exit":

                    program_run=false;
                    break;


                default:
                    System.out.print("Invalid role, enter admin/customer or exit. \n");
                    break;
            }

        }
    }
}

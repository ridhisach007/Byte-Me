import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class data {
    public HashMap<String,String> map1=new HashMap<>();
    public HashMap<String,Customer_Canteen> customerMap=new HashMap<>();
    public static List<Customer_Canteen> customers=new ArrayList<>();
    public static void addcustomers(Customer_Canteen c ){
        customers.add(c);
    }

}


public class Menu{
    //private String name;
    private String category;
    private String available;
    private int price;

    public Menu(String category,String available,int price){

        //this.name=name;
        this.category=category;
        this.available=available;
        this.price=price;

    }

   /*public String getName(){
        return name;
    }*/

    public String getCategory(){
        return category;
    }

    public String getAvailable(){
        return available;
    }

    public int getPrice(){
        return price;
    }
    @Override
    public String toString() {
        return String.format("Category: %s, Price: %d, Available: %s",
                category, price, available);
    }

    /*public void setName(String name){
        this.name=name;

    }*/

    public void setCategory(String category){
        this.category=category;
    }

    public void setPrice(int price){
        this.price=price;
    }

    public void setAvailable(String available){
        this.available=available;
    }


}

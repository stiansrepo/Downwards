package items;

 // @author laptopng34
 
public class Item {
    
    private String name;
    private String description;
    private double weight;
    private ItemType t;
   
    public Item(String name, String description, double weight, ItemType t){
        this.description=description;
        this.name=name;
        this.weight=weight;
        this.t=t;
    }
    
    public Item(String name, String description, double weight){
        this.description=description;
        this.name=name;
        this.weight=weight;
        this.t=t;
    }
    
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public double getWeight(){
        return weight;
    }
    public ItemType getType(){
        return t;
    }
    
}

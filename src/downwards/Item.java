package downwards;

 // @author laptopng34
 
public class Item {
    
    private String name;
    private String description;
    private double weight;
   
    public Item(String name, String description, double weight){
        this.description=description;
        this.name=name;
        this.weight=weight;
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
}

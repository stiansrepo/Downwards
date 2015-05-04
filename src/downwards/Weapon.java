package downwards;

 // @author laptopng34
public class Weapon extends Item {

    private int rolls;
    private int range;

    public Weapon(String name, String description, double weight, int rolls, int range) {
        super(name, description, weight);
        this.rolls = rolls;
        this.range = range;
    }
    
    public int getRolls(){
        return rolls;
    }
    
    public int getRange(){
        return range;
    }

}

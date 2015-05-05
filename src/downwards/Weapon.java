package downwards;

 // @author laptopng34
public class Weapon extends Item {

    private int rolls;
    private int range;
    private String description;
    private ItemType t = ItemType.WEAPON;

    public Weapon(String name, String description, double weight, int rolls, int range) {
        super(name, description, weight);
        this.rolls = rolls;
        this.range = range;
       }  

    public Weapon(Item item, int rolls, int range) {
        super(item.getName(), item.getDescription(), item.getWeight());
        this.rolls = rolls;
        this.range = range;
    }

    public int getRolls() {
        return rolls;
    }

    public int getRange() {
        return range;
    }

    public String getDice(){
        return Integer.toString(rolls) +"d" + Integer.toString(range);
    }
}

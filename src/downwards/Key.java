package downwards;

 // @author laptopng34
public class Key extends Item {

    private ItemType t = ItemType.KEY;
    private int id;

    public Key(String name, String description, double weight, int id) {
        super(name, description, weight);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

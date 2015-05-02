package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.Graphics2D;

public class Entity {

    private Map map;
    public int x;
    public int y;
    public int xs;
    public int ys;
    public int width;
    public int height;
    public Color color;
    public EntityType e;
    public int maxhealth;
    public int health;
    public int strength;
    public int defense;
    public Item[] inventory;
    public String name;

    public Entity(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e) {
        
        this.x = x;
        this.y = y;
        this.width=width;
        this.height=height;
        this.map = map;
        this.color = color;
        this.e=e;
        map.setEntity(this, x, y);
    }

    public void move() {
        map.setEntity(null, x, y);
        EntityMover em = new EntityMover(e);
        if (!(map.blocked(em, x+xs, y+ys))){
        x = x + xs;
        y = y + ys;
        }
        
        map.setEntity(this, x, y);
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

}

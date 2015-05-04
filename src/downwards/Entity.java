package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Entity {

    private Game game;
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
    public ArrayList<Item> inventory;
    public Weapon weapon;
    public String name;
    public boolean alive = true;

    public Entity(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.color = color;
        this.e = e;
        this.game = game;
        this.name = name;
        inventory = new ArrayList();
    }
    
    public Entity(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e, String name, Weapon w) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.color = color;
        this.e = e;
        this.game = game;
        this.name = name;
        this.weapon=weapon;
        inventory = new ArrayList();
    }

    public void setWeapon(Weapon w) {
        weapon = w;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public String getName() {
        return name;
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public EntityType getType() {
        return e;
    }

    public void move() {

        EntityMover em = new EntityMover(e);
        if (!game.getGameOver()) {
            if (!(map.blocked(em, x + xs, y + ys))) {
                map.setEntity(null, x, y);
                x = x + xs;
                y = y + ys;
                map.setEntity(this, x, y);
            } else if (map.getEntity(x + xs, y + ys) != null) {
                if (map.getEntity(x + xs, y + ys).getType() != this.e) {
                    if (map.getEntity(x + xs, y + ys).isAlive()) {
                        combat(map.getEntity(x + xs, y + ys));
                    }
                    xs = 0;
                    ys = 0;
                }
            }
        }
    }

    public void takeDamage(int d) {
        health = health - d;
        if (health <= 0) {
            kill();
        }
    }

    public void combat(Entity target) {
        game.combatparser.combat(this, target);
    }

    public int getStrength() {
        return strength;
    }

    public void paint(Graphics2D g2d) {
        if (alive) {
            g2d.setColor(color);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

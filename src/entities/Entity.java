package entities;

 // @author laptopng34
import pathfinding.EntityMover;
import core.Game;
import stats.Stats;
import items.Weapon;
import map.WorldMap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;

public class Entity {

    private Game game;
    private WorldMap map;
    public int x;
    public int y;
    public int xs;
    public int ys;
    public int width;
    public int height;
    public Color color;
    public EntityType e;
    public List inventory;
    public Weapon weapon;
    public String name;
    public boolean alive = true;
    public int maxhealth;
    public int health;
    public Stats stats;
    public int defense;
    public Entity standingOn;
    private FileInputStream filestream;

    public Entity(int x, int y, int width, int height, Color color, WorldMap map, Game game, EntityType e, String name, Stats stats) throws FileNotFoundException {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.color = color;
        this.e = e;
        this.game = game;
        this.name = name;
        this.stats = stats;
        inventory = new CopyOnWriteArrayList();
    }

    public Entity(int x, int y, int width, int height, Color color, WorldMap map, Game game, EntityType e, String name, Stats stats, Weapon weapon) throws FileNotFoundException {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.color = color;
        this.e = e;
        this.game = game;
        this.name = name;
        this.weapon = weapon;
        this.stats = stats;
        inventory = new CopyOnWriteArrayList();
    }

    public Entity(Color color, EntityType e, String name, Stats stats, Weapon weapon) throws FileNotFoundException {
        this.color = color;
        this.e = e;
        this.name = name;
        this.stats = stats;
        this.weapon = weapon;
        inventory = new CopyOnWriteArrayList();
    }

    public Entity(int x, int y, int width, int height, WorldMap map, Game game, Monster monster) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.game = game;
        this.color = monster.color;
        this.e = monster.e;
        this.name = monster.name;
        this.stats = monster.stats;
        this.weapon = monster.weapon;
        inventory = monster.inventory;
    }

    public Entity(Entity entity) throws FileNotFoundException {
        this.x = entity.x;
        this.y = entity.y;
        this.width = entity.width;
        this.height = entity.height;
        this.map = entity.map;
        this.color = entity.color;
        this.e = entity.e;
        this.game = entity.game;
        this.name = entity.name;
        this.weapon = entity.weapon;
        this.stats = entity.stats;
        this.alive = entity.alive;
        this.inventory = entity.inventory;
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

    public void move() throws FileNotFoundException {

        EntityMover em = new EntityMover(e);
        if (!game.getGameOver()) {
            if (!(map.blocked(em, x + xs, y + ys))) {
                if (standingOn != null) {
                    map.setEntity(standingOn, x, y);
                    standingOn = null;
                } else {
                    map.setEntity(null, x, y);
                }
                x = x + xs;
                y = y + ys;
                map.setEntity(this, x, y);
            } else if (map.getEntity(x + xs, y + ys) != null) {
                if (map.getEntity(x + xs, y + ys).getType() != this.e) {
                    if (map.getEntity(x + xs, y + ys).isAlive()) {
                        combat(map.getEntity(x + xs, y + ys));
                        xs = 0;
                        ys = 0;
                    } else if (!map.getEntity(x + xs, y + ys).isAlive()) {
                        if (standingOn != null) {
                            map.setEntity(standingOn, x, y);
                        } else {
                            map.setEntity(null, x, y);
                        }
                        standingOn = new Entity(map.getEntity(x + xs, y + ys));
                        x = x + xs;
                        y = y + ys;
                        map.setEntity(this, x, y);
                    }
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

    public Stats getStats() {
        return stats;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxhealth;
    }

    public int getDefense() {
        return defense;
    }

    public void levelUp() {
        stats.levelUp();
    }

    public void gainXp(int gained) {
        getStats().setXp(getStats().getXp() + gained);
    }

    public void paint(Graphics2D g2d) throws IOException {
        String path = "";
        if (alive) {
            path = getClass().getClassLoader().getResource(e.getFilePath()).getPath();
        } else {
            path = getClass().getClassLoader().getResource(e.getFilePathDead()).getPath();
        }
        File file = new File(path);
        BufferedImage bi = ImageIO.read(file);
        g2d.drawImage(bi, x * 20, y * 20, null);

        //g2d.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

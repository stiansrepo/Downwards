package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class Player extends Entity {

    private Game game;

    public Player(int x, int y, int width, int height, Color color, WorldMap map, Game game, EntityType e, String name, Stats stats) {
        super(x, y, width, height, color, map, game, e, name, stats);
        maxhealth = 100;
        health = maxhealth;
        defense = 10;
        this.stats = stats;
        weapon = new Weapon("Great Axe", "Smash!", 12, 1, 8);
        this.game = game;
        
    }

    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
                xs = 0;
                break;
            case KeyEvent.VK_D:
                xs = 0;
                break;
            case KeyEvent.VK_W:
                ys = 0;
                break;
            case KeyEvent.VK_S:
                ys = 0;
                break;
            case KeyEvent.VK_L:
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
                xs = -1;
                break;
            case KeyEvent.VK_D:
                xs = 1;
                break;
            case KeyEvent.VK_W:
                ys = -1;
                break;
            case KeyEvent.VK_S:
                ys = 1;
                break;
            case KeyEvent.VK_L:
                loot();
                break;
        }
    }

    public void loot() {
        if (standingOn!=null) {
            Iterator iterator = standingOn.inventory.iterator();
            while (iterator.hasNext()) {
                this.inventory.add((Item)iterator.next());                               
            }
            standingOn.inventory.clear();
            game.updateInventory();
        }
        
    }
}

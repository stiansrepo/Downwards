package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    private Game game;

    public Player(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e, String name) {
        super(x, y, width, height, color, map, game, e, name);
        maxhealth = 100;
        health = maxhealth;
        strength = 10;
        defense = 10;
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

    public void loot() {
        if (game.map.getEntity(x + xs, y + ys) != null) {
            for (Item i : game.map.getEntity(x + xs, y + ys).inventory) {
                inventory.add(i);
                game.map.getEntity(x + xs, y + ys).inventory.remove(i);
            }
        }
        game.updateInventory();
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

}

package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    public Player(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e) {
        super(x, y, width, height, color, map, game, e);
        name = "Player";
        maxhealth = 100;
        health = maxhealth;
        strength = 10;
        defense = 10;
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
        }
    }

}

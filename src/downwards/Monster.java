package downwards;

import java.awt.Color;

public class Monster extends Entity {

    private Player player;

    public Monster(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e) {
        super(x, y, width, height, color, map, game, e);
        maxhealth = 5;
        health = maxhealth;
        strength = 1;
        defense = 1;
        player = game.player;
        ys = 1;
        xs = 1;
    }

    public void target() {
        int targetx = player.getX() - x;
        int targety = player.getY() - y;

        if (targetx
                > 0) {
            xs = 1;

        } else if (targetx == 0) {
            xs = 0;
        } else if (targetx
                < 0) {
            xs = -1;
        }

        if (targety
                > 0) {
            ys = 1;
        } else if (targety == 0) {
            ys = 0;
        }else if (targety
                < 0) {
            ys = -1;
        }
    }
}

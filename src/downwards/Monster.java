package downwards;

import java.awt.Color;

public class Monster extends Entity {

    private Player player;
    private Map map;

    public Monster(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e, String name) {
        super(x, y, width, height, color, map, game, e, name);
        maxhealth = 20;
        health = maxhealth;
        strength = 1;
        defense = 1;
        player = game.player;
        ys = 0;
        xs = 0;
        this.map = map;
        weapon = new Weapon("Bare hands", "aw", 1, 1, 2);
    }

    public Monster(int x, int y, int width, int height, Color color, Map map, Game game, EntityType e, String name, Weapon weapon) {
        super(x, y, width, height, color, map, game, e, name, weapon);
        maxhealth = 20;
        health = maxhealth;
        strength = 1;
        defense = 1;
        player = game.player;
        ys = 0;
        xs = 0;
        this.map = map;
        this.weapon = weapon;
        inventory.add(weapon);
    }

    public void target() {
        int targetx = player.getX() - x;
        int targety = player.getY() - y;

        if (Math.abs(targetx) < 8 && Math.abs(targety) < 8) {
            boolean clear = true;
            for (int i = 0; i < targetx; i++) {
                for (int j = 0; j < targety; j++) {
                    if (map.getTile(x + i, y + j).getType() == TileType.WALL) {
                        clear = false;
                        xs = 0;
                        ys = 0;
                    }
                }
            }
            if (clear) {
                if (targetx
                        > 0) {
                    xs = 1;
                } else if (targetx == 0) {
                    xs = 0;
                } else if (targetx < 0) {
                    xs = -1;
                }
                /*else {
                 xs = 0;
                 ys = 0;
                 }*/
                if (targety
                        > 0) {
                    ys = 1;
                } else if (targety == 0) {
                    ys = 0;
                } else if (targety < 0) {
                    ys = -1;
                }
                /*else {
                 xs = 0;
                 ys = 0;
                 }*/
            }
        } else {
            xs = 0;
            ys = 0;
        }
    }
}

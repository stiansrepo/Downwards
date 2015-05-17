package entities;

import core.Game;
import items.Item;
import items.ItemType;
import stats.Stats;
import items.Weapon;
import map.WorldMap;
import map.TileType;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Random;

public class Monster extends Entity {

    private Player player;
    private WorldMap map;
    private Game game;
    private Random rnd;

    public Monster(int x, int y, int width, int height, Color color, WorldMap map, Game game, EntityType e, String name, Stats stats) throws FileNotFoundException {
        super(x, y, width, height, color, map, game, e, name, stats);
        maxhealth = 20;
        health = maxhealth;
        player = game.getPlayer();
        ys = 0;
        xs = 0;
        this.stats = stats;
        this.map = map;
        weapon = new Weapon("Bare hands", "aw", 1, 1, 2);
        this.game = game;
        rnd = new Random();
    }

    public Monster(int x, int y, int width, int height, Color color, WorldMap map, Game game, EntityType e, String name, Stats stats, Weapon weapon) throws FileNotFoundException {
        super(x, y, width, height, color, map, game, e, name, stats, weapon);
        maxhealth = 20;
        health = maxhealth;
        player = game.player;
        ys = 0;
        xs = 0;
        this.map = map;
        this.weapon = weapon;
        inventory.add(weapon);
        this.game = game;
        rnd = new Random();
    }

    public Monster(int x, int y, int width, int height, WorldMap map, Game game, Monster monster) throws FileNotFoundException {
        super(x, y, width, height, map, game, monster);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.game = game;
        this.player = game.getPlayer();
        this.color = monster.color;
        this.e = monster.e;
        this.maxhealth=monster.maxhealth;
        this.health=monster.health;
        this.name = monster.name;
        this.stats = monster.stats;
        this.weapon = monster.weapon;
        inventory = monster.inventory;
        rnd = new Random();
    }

    public Monster(Color color, EntityType e, String name, Stats stats, Weapon weapon, int hp) throws FileNotFoundException {
        super(color, e, name, stats, weapon);
        this.maxhealth = hp;
        this.health = maxhealth;
        ys = 0;
        xs = 0;
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
            xs = -1 + rnd.nextInt(3);
            ys = -1 + rnd.nextInt(3);
        }
    }

    public void interactWithTile() {
        if (map.getTile(x, y).getType() == TileType.RUBBLE) {
            map.getTile(x, y).setType(TileType.FLOOR);
            game.drawMapChange(x, y, TileType.FLOOR.getColor());
            inventory.add(new Item("Gold lump","Lumps of gold mined by the company.",1, ItemType.RUBBISH));
        }
    }
}

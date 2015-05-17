package entities;

 // @author laptopng34
import core.Game;
import items.Item;
import stats.Stats;
import items.Weapon;
import things.ThingType;
import things.Chest;
import map.WorldMap;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import things.Door;
import things.Thing;

public class Player extends Entity {

    private Game game;

    public Player(int x, int y, int width, int height, Color color, WorldMap map, Game game, EntityType e, String name, Stats stats) throws FileNotFoundException {
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
            case KeyEvent.VK_O:
                break;
        }
    }

    public void keyPressed(KeyEvent e) throws FileNotFoundException, IOException {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
                xs = -1;
                game.move();
                break;
            case KeyEvent.VK_D:
                xs = 1;
                game.move();
                break;
            case KeyEvent.VK_W:
                ys = -1;
                game.move();
                break;
            case KeyEvent.VK_S:
                ys = 1;
                game.move();
                break;
            case KeyEvent.VK_L:
                loot();
                break;
            case KeyEvent.VK_O:
                lootChest();
                openDoor();
                break;
        }
    }

    public void equipWeapon(Weapon w) {
        if (inventory.contains(w)) {
            inventory.add(weapon);
            weapon = w;
            inventory.remove(w);
        }
        game.updateInventory();
    }

    public void loot() {
        if (standingOn != null) {
            Iterator iterator = standingOn.inventory.iterator();
            while (iterator.hasNext()) {
                this.inventory.add((Item) iterator.next());
            }
            standingOn.inventory.clear();
            game.updateInventory();
        }
    }

    public void lootChest() throws IOException {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (game.getMap().getThing(x + k, y + l) != null) {
                    if ((game.getMap().getThing(x + k, y + l).getType() == ThingType.CHEST)) {
                        Chest chest = (Chest) game.getMap().getThing(x + k, y + l);
                        if (!chest.isEmpty()) {
                            this.inventory.addAll(chest.getItems());
                            game.updateInventory();
                            String ss = getClass().getClassLoader().getResource(chest.getType().getFilePathEmpty()).getPath();
                            game.drawMapChangeImage(x + k, y + l, ss);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void openDoor() throws IOException {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (game.getMap().getThing(x + k, y + l) != null) {
                    if ((game.getMap().getThing(x + k, y + l).getType() == ThingType.DOOR)) {
                        Door door = (Door) game.getMap().getThing(x + k, y + l);
                        if (!door.getOpen()) {
                            door.open();
                            String ss = getClass().getClassLoader().getResource(door.getType().getFilePath()).getPath();
                            game.drawMapChangeImage(x + k, y + l, ss);
                            return;
                        }
                        if (door.getOpen()) {
                            door.close();
                            String ss = getClass().getClassLoader().getResource(door.getType().getFilePathClosed()).getPath();
                            game.drawMapChangeImage(x + k, y + l, ss);
                            return;

                        }
                    }
                }
            }
        }
    }
}

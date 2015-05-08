package core;

 // @author laptopng34
import ui.InfoPanel;
import ui.Frame;
import ui.MapPanel;
import util.CombatParser;
import items.ItemGenerator;
import items.Weapon;
import stats.Stats;
import entities.Monster;
import pathfinding.EntityMover;
import entities.EntityType;
import entities.MonsterGenerator;
import entities.Player;
import things.ThingType;
import things.Chest;
import map.WorldMap;
import pathfinding.Node;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import map.Tile;
import map.TileType;

public class Game {

    public Frame frame;
    public WorldMap map;
    public Player player;
    public Monster[] monsters;
    public Random rnd;
    public int turn;
    private InfoPanel infoPanel;
    private MapPanel mapPanel;
    private boolean gameOver = false;
    private String[] infostring;
    private ItemGenerator itemGenerator;
    private MonsterGenerator monsterGenerator;
    public CombatParser combatparser;
    private Map<Node, Color> changeMap;

    public Game(Frame frame) {
        this.frame = frame;
        changeMap = new HashMap();
        itemGenerator = new ItemGenerator();
        monsterGenerator = new MonsterGenerator();
        combatparser = new CombatParser(this);
        infostring = new String[3];
        rnd = new Random();
        map = new WorldMap(this);
        setPlayerStart();
        setMonsterStart(25);

    }

    public void initPanels() {
        this.infoPanel = frame.getInfoPanel();
        this.mapPanel = frame.getMapPanel();
    }

    public Player getPlayer() {
        return player;
    }

    public void drawMapChange(int x, int y, Color c) {
        changeMap.put(new Node(x, y), c);
        mapPanel.drawMapChange(x, y, c);
        clearChangeMap();
    }

    public void clearChangeMap() {
        changeMap.clear();
    }

    public Map<Node, Color> getChangeMap() {
        return changeMap;
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    private void setMonsterStart(int amt) {
        monsters = new Monster[amt];
        int found = 0;
        EntityMover em = new EntityMover(EntityType.CAVETHING);
        while (found < amt) {
            int x = 1 + rnd.nextInt(map.getWidth() - 1);
            int y = 1 + rnd.nextInt(map.getHeight() - 1);
            if (map.getTerrain()[x][y].getType()==TileType.FLOOR) {
                Weapon wp = itemGenerator.generateWeapon();
                monsters[found] = new Monster(x, y, 1, 1, this.map, this, monsterGenerator.generateMonster());
                found++;
            }
        }
    }

   
    public void updateInventory() {

        infoPanel.updateInventory(player.inventory);
    }

    public void gameOver() {
        gameOver = true;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    private void setPlayerStart() {
        boolean found = false;
        Stats stats = new Stats(1, 0, 14, 8, 10, 10, 8, 10);
        EntityMover em = new EntityMover(EntityType.PLAYER);
         while (!found) {
         int x = 1 + rnd.nextInt(map.getWidth() - 1);
         int y = 1 + rnd.nextInt(map.getHeight() - 1);
         if (map.getTerrain()[x][y].getType()==TileType.FLOOR) {
         player = new Player(x, y, 1, 1, Color.BLUE, this.map, this, EntityType.PLAYER, "Player", stats);
         found = true;
         }
         }
        //player = new Player(5, 5, 1, 1, Color.BLUE, this.map, this, EntityType.PLAYER, "Player", stats);
    }

    public WorldMap getMap() {
        return map;
    }

    public void move() {
        player.move();
        for (Monster m : monsters) {
            if (m.isAlive()) {
                m.target();
                m.move();
                m.interactWithTile();
            }
        }
        if (player.getStats().getXp() > player.getStats().getLevel() * player.getStats().getLevel() * 500) {
            levelUp();
        }
        endTurn();
    }

    private void levelUp() {
        player.getStats().levelUp();
        setCombatInfo(player.name + " has reached level " + player.getStats().getLevel() + "!");
    }

    public int getTurn() {
        return turn;
    }

    public String getTurnString() {
        return Integer.toString(turn);
    }

    public void endTurn() {
        if (player.isAlive()) {
            turn++;
        }
        infostring[0] = getTurnString();
        infostring[1] = player.health + "/" + player.maxhealth;
        infoPanel.updateInfo();
    }

    public void setCombatInfo(String s) {
        infoPanel.updateCombat("\n" + s);
    }

    public Monster[] getMonsters() {
        return monsters;
    }

}

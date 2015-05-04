package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game {

    public WorldMap map;
    public Player player;
    public Monster[] monsters;
    public Random rnd;
    public int turn;
    private InfoPanel infopanel;
    private boolean gameOver = false;
    private String[] infostring;
    private ItemGenerator itemGenerator;
    public CombatParser combatparser;

    public Game(InfoPanel infopanel) {
        this.infopanel = infopanel;
        itemGenerator = new ItemGenerator();
        combatparser = new CombatParser(this);
        infostring = new String[3];
        rnd = new Random();
        map = new WorldMap();
        setPlayerStart();
        setMonsterStart(10);
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        move();
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    private void setMonsterStart(int amt) {
        monsters = new Monster[amt];
        int found = 0;
        EntityMover em = new EntityMover(EntityType.PLAYER);
        Stats stats = new Stats();
        while (found < amt) {
            int x = 1 + rnd.nextInt(map.getWidth() - 1);
            int y = 1 + rnd.nextInt(map.getHeight() - 1);
            if (!map.blocked(em, x, y)) {
                Weapon wp = itemGenerator.generateWeapon();
                monsters[found] = new Monster(x, y, 1, 1, Color.RED, this.map, this, EntityType.CAVETHING, "Cave thing", stats, wp);
                found++;
            }
        }
    }

    public void updateInventory() {
        
        infopanel.updateInventory(player.inventory);
    }

    public void gameOver() {
        gameOver = true;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    private void setPlayerStart() {
        boolean found = false;
        Stats stats = new Stats();
        EntityMover em = new EntityMover(EntityType.PLAYER);
        while (!found) {
            int x = 1 + rnd.nextInt(map.getWidth() - 1);
            int y = 1 + rnd.nextInt(map.getHeight() - 1);
            if (!map.blocked(em, x, y)) {
                player = new Player(x, y, 1, 1, Color.BLUE, this.map, this, EntityType.PLAYER, "Player", stats);
                found = true;
            }
        }
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
            }
        }
        endTurn();
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
        infopanel.updateInfo(infostring);
    }

    public void setCombatInfo(String s) {
        infopanel.updateCombat("\n" + s);
    }

    public Player getPlayer() {
        return player;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

}

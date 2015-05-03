package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game {

    public Map map;
    public Player player;
    public Monster[] monsters;
    public Random rnd;
    public int turn;
    private InfoPanel infopanel;
    private boolean gameOver=false;
    
    public Game(InfoPanel infopanel) {
        this.infopanel = infopanel;
        rnd = new Random();
        map = new Map();
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
        while (found < amt) {
            int x = 1 + rnd.nextInt(map.getWidth() - 1);
            int y = 1 + rnd.nextInt(map.getHeight() - 1);
            if (!map.blocked(em, x, y)) {
                monsters[found] = new Monster(x, y, 1, 1, Color.RED, this.map, this, EntityType.CAVETHING);
                found++;
            }
        }
    }
    
    public void combat(Entity attacker, Entity target){
        target.takeDamage(attacker.getStrength());
        if (!player.isAlive()){
            gameOver();
        }
    }
    
    public void gameOver(){
        gameOver=true;
    }
    
    public boolean getGameOver(){
        return gameOver;
    }

    private void setPlayerStart() {
        boolean found = false;
        EntityMover em = new EntityMover(EntityType.PLAYER);
        while (!found) {
            int x = 1 + rnd.nextInt(map.getWidth() - 1);
            int y = 1 + rnd.nextInt(map.getHeight() - 1);
            if (!map.blocked(em, x, y)) {
                player = new Player(x, y, 1, 1, Color.BLUE, this.map, this, EntityType.PLAYER);
                found = true;
            }
        }
    }

    public Map getMap() {
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
        turn++;
        infopanel.updateInfo(getTurnString());
    }
    
    public int getTurn(){
        return turn;
    }
    public String getTurnString(){
        return Integer.toString(turn);
    }

    public void endTurn() {

    }

    public Player getPlayer() {
        return player;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

}

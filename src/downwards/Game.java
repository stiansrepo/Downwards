package downwards;

 // @author laptopng34
import java.awt.Color;


public class Game {

    Map map;
    Player player;
    Monster[] monsters;

    public Game() {        
        map = new Map();
        player = new Player(10,10,1,1,Color.BLUE,this.map,EntityType.PLAYER);
        monsters = new Monster[3];
        monsters[0] = new Monster(15,15,1,1,Color.RED,this.map,EntityType.CAVETHING);
        monsters[1] = new Monster(30,25,1,1,Color.RED,this.map,EntityType.CAVETHING);
        monsters[2] = new Monster(45,35,1,1,Color.RED,this.map,EntityType.CAVETHING);
        
    }

    public Map getMap() {
        return map;
    }
    
    public void move(){
        
    }
    
    public void endTurn(){
        
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public Monster[] getMonsters(){
        return monsters;
    }

}

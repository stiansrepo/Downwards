package things;

 // @author laptopng34
import core.Game;
import map.WorldMap;

public class Thing {

    private int x;
    private int y;
    private ThingType t;
    private boolean in;
    private WorldMap map;
    private Game game;
    
    public Thing (int x, int y, ThingType t,boolean in, Game game){
        this.x=x;
        this.y=y;
        this.t=t;
        this.in=in;
        this.map=game.getMap();
        this.game=game;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public ThingType getType(){
        return t;
    }
    
    public boolean getInteractive(){
        return in;
    }
}

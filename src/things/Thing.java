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
    
    public Thing (int x, int y, ThingType t,boolean in, WorldMap map, Game game){
        this.x=x;
        this.y=y;
        this.t=t;
        this.in=in;
        this.map=map;
        this.game=game;
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

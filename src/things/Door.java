package things;

 // @author laptopng34
import core.Game;
import items.Key;
import things.Thing;
import things.ThingType;
import map.WorldMap;


public class Door extends Thing {
    
    private boolean open;
    private boolean locked;
    private Key key;
    private WorldMap map;
    
    public Door(int x, int y, ThingType t,boolean in, Game game){
    super(x, y, t, in, game);
        this.map=game.getMap();
        open=false;
        locked=false;
    }
    
    public void open() {
        open=true;
    }
    public void close(){
        open=false;
    }
    public boolean getOpen(){
        return open;
    }
    public boolean getLocked(){
        return locked;
    }
    
    public void lock(){
        locked=true;
    }
    
    public void unlock(){
        locked=false;
    }

}

package downwards;

 // @author laptopng34
public class Door extends Thing {
    
    private boolean open;
    private boolean locked;
    private Key key;
    
    public Door(int x, int y, ThingType t,boolean in, WorldMap map, Game game){
    super(x, y, t, in, map, game);
    }
    
    public void open() {

    }
    public void close(){
        
    }
    public boolean getOpen(){
        return open;
    }
    public boolean getLocked(){
        return locked;
    }
    
    public void lock(){
    
    }
    
    public void unlock(){
        
    }

}

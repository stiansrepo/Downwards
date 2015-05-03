package downwards;

 // @author laptopng34
public class Tile {

    private int x;
    private int y;
    private TileType t;
    
    public Tile(int x, int y, TileType t){
        this.x=x;
        this.y=y;
        this.t=t;
    }
    
    public TileType getType(){
        return t;
    }
    
    public void setType(TileType t){
        this.t=t;
    }
}

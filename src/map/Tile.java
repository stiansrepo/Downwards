package map;

 // @author laptopng34
public class Tile {

    private int x;
    private int y;
    private TileType t;
    private int cellX;
    private int cellY;
    
    public Tile(int x, int y, TileType t){
        this.x=x;
        this.y=y;
        this.t=t;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public TileType getType(){
        return t;
    }
    
    public void setType(TileType t){
        this.t=t;
    }
    
    public void setCellX(int cellX){
        this.cellX=cellX;
    }
    
    public int getCellX(){
        return cellX;
    }
    
    public void setCellY(int cellY){
        this.cellY=cellY;
    }
    
    public int getCellY(){
        return cellY;
    }
}

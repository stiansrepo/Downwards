package downwards;

 // @author laptopng34
 
public class MapGenerator {
    
    private int width;
    private int height;
    private Tile[][] terrain;
    
    public MapGenerator(int width, int height){
        this.width=width;
        this.height=height;
        terrain = new Tile[width][height];
        fillArea(0,0,width,height,TileType.FLOOR);
        fillArea(14,51,10,10,TileType.WALL);
        fillArea(5,20,20,10,TileType.GRASS);
    }
    
    
    private void fillArea(int x, int y, int areaWidth, int areaHeight, TileType type) {
        for (int xp = x; xp < x + areaWidth; xp++) {
            for (int yp = y; yp < y + areaHeight; yp++) {
                terrain[xp][yp] = new Tile(xp,yp,type);
            }
        }
    }
    
    public Tile[][] getTerrain(){
        return terrain;
    }
}   

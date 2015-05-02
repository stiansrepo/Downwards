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
    }
    
    
    private void fillArea(int x, int y, int areaWidth, int areaHeight, TileType type) {
        for (int xp = x; xp < x + areaWidth; xp++) {
            for (int yp = y; yp < y + areaHeight; yp++) {
                terrain[xp][yp] = new Tile(xp,yp,type);
            }
        }
    }
}   

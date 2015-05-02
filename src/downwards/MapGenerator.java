package downwards;

 // @author laptopng34
public class MapGenerator {

    private int width;
    private int height;
    private Tile[][] terrain;

    public MapGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        terrain = new Tile[width][height];
        fillArea(0, 0, width, height, TileType.WALL);
        makeRooms();

    }

    private void fillArea(int x, int y, int areaWidth, int areaHeight, TileType type) {
        for (int xp = x; xp < x + areaWidth; xp++) {
            for (int yp = y; yp < y + areaHeight; yp++) {
                terrain[xp][yp] = new Tile(xp, yp, type);
            }
        }
    }

    private void makeRooms() {
        int roomx = 5;
        int roomy = 5;
        int offx = 3;
        int offy = 3;
        for (int i = 0; i < 9; i++) {
            
            for (int j = 0; j < 9; j++) {

                if (((offx + roomx) < width)||((offy + roomy) < height)) {
                    fillArea(offx, offy, offx + roomx, offx + roomy, TileType.FLOOR);
                    offy += 5;
                }

            }            
            offy=5;
            offx+=5;
        }

    }

    public Tile[][] getTerrain() {
        return terrain;
    }
}

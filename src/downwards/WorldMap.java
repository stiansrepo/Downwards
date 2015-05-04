package downwards;

 // @author laptopng34
public class WorldMap implements MapInterface {

    public final int WIDTH = 100;
    public final int HEIGHT = 100;
    private Tile[][] terrain = new Tile[WIDTH][HEIGHT];
    private Entity[][] entities = new Entity[WIDTH][HEIGHT];
    private boolean[][] visited = new boolean[WIDTH][HEIGHT];

    public WorldMap() {

        MapGenerator mg = new MapGenerator(WIDTH,HEIGHT);
        terrain = mg.getTerrain();

    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    public boolean visited(int x, int y) {
        return visited[x][y];
    }
    
    public boolean[][] getAllVisited(){
        return visited;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }

    @Override
    public boolean blocked(Mover mover, int x, int y) {
        if (getEntity(x, y) != null) {
            return true;
        }
        EntityType e = ((EntityMover) mover).getType();

        if (e == EntityType.PLAYER) {
            return (terrain[x][y].getType() != TileType.FLOOR
                    && terrain[x][y].getType() != TileType.GRASS);
        }
        if (e == EntityType.CAVETHING) {
            return terrain[x][y].getType() != TileType.FLOOR;        
        }
        return true;
    }

    @Override
    public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
        return 1;
    }

    public Entity getEntity(int x, int y) {
        return entities[x][y];
    }

    public void setEntity(Entity e, int x, int y) {
        entities[x][y] = e;
    }

    public void clearVisited() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                visited[x][y] = false;
            }
        }
    }

    public Tile getTile(int x, int y) {
        return terrain[x][y];
    }

}

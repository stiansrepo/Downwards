package downwards;

 // @author laptopng34
public class Map implements MapInterface {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private Tile[][] terrain = new Tile[WIDTH][HEIGHT];
    private Entity[][] entities = new Entity[WIDTH][HEIGHT];
    private boolean[][] visited = new boolean[WIDTH][HEIGHT];

    public Map() {
        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public boolean visited(int x, int y) {
        return visited[x][y];
    }

    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }

    public boolean blocked(Mover mover, int x, int y) {
        if (getEntity(x, y) != null) {
            return true;
        }
        EntityType e = ((EntityMover) mover).getType();

        if (e == EntityType.PLAYER || e == EntityType.CAVETHING) {
            return terrain[x][y].getType() != TileType.FLOOR;
        }

        return true;

    }

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

}

package map;

 // @author laptopng34

import entities.Entity;
import entities.EntityMover;
import entities.EntityType;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import things.Thing;
import things.ThingType;


public class WorldMap implements MapInterface {

    public final int WIDTH = 200;
    public final int HEIGHT = 200;
    private Thing[][] things = new Thing[WIDTH][HEIGHT];
    private Tile[][] terrain = new Tile[WIDTH][HEIGHT];
    private Entity[][] entities = new Entity[WIDTH][HEIGHT];
    private boolean[][] visited = new boolean[WIDTH][HEIGHT];
    private CopyOnWriteArrayList<Tile[][]> rooms;

    public WorldMap() {
        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT);
        terrain = mg.getTerrain();
        rooms = mg.getRooms();
    }
    
    public CopyOnWriteArrayList<Tile[][]> getRooms(){
        return rooms;
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

    public boolean[][] getAllVisited() {
        return visited;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }

    public boolean blockedThing(ThingType t, int xt, int yt) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (getThing(xt+i, yt+j) != null) {
                    return true;
                }
            }
        }
        if (t == ThingType.CHEST) {
            return (terrain[xt][yt].getType() != TileType.FLOOR
                    && terrain[xt][yt].getType() != TileType.GRASS
                    && terrain[xt][yt].getType() != TileType.RUBBLE
                    && terrain[xt][yt].getType() != TileType.SILT
                    && terrain[xt][yt].getType() != TileType.GRIT);
        }
        return true;
    }

    @Override
    public boolean blocked(Mover mover, int x, int y) {
        if (getEntity(x, y) != null || getThing(x, y) != null) {
            return true;
        }
        EntityType e = ((EntityMover) mover).getType();

        if (e == EntityType.PLAYER) {
            return (terrain[x][y].getType() != TileType.FLOOR
                    && terrain[x][y].getType() != TileType.GRASS
                    && terrain[x][y].getType() != TileType.RUBBLE
                    && terrain[x][y].getType() != TileType.SILT
                    && terrain[x][y].getType() != TileType.GRIT);
        }
        if (e == EntityType.CAVETHING) {
            return (terrain[x][y].getType() != TileType.FLOOR
                    && terrain[x][y].getType() != TileType.RUBBLE
                    && terrain[x][y].getType() != TileType.SILT
                    && terrain[x][y].getType() != TileType.GRIT);
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

    public Thing getThing(int x, int y) {
        return things[x][y];
    }

    public void setThing(Thing t, int x, int y) {
        things[x][y] = t;
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

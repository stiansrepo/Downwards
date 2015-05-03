package downwards;

 // @author laptopng34
import java.util.Random;

public class MapGenerator implements MapInterface {

    private int width;
    private int height;
    private boolean[][] visited;

    private Tile[][] terrain;
    private Random rnd = new Random();

    public MapGenerator(int x, int y) {
        this.width = x;
        this.height = y;
        visited = new boolean[width][height];
        terrain = new Tile[width][height];
        generateMap();
    }

    private void generateMap() {
        randomFill(49);
        makeCaverns();
        makeCaverns();
        makeCaverns();
        makeCaverns();
        makeCaverns();
        makeCaverns();
        makeCaverns();
        createWallsAtBorders();
        fillDisjointedRooms();
    }

    private Tile[][] deepCopyTerrain() {
        Tile[][] newterrain = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newterrain[i][j] = new Tile(i, j, terrain[i][j].getType());
            }
        }
        return newterrain;
    }

    private void fillDisjointedRooms() {
        Tile[][] newterrain = deepCopyTerrain();
        boolean found = false;
        while (!found) {
            int rx = 1 + rnd.nextInt(width - 1);
            int ry = 1 + rnd.nextInt(height - 1);
            if (newterrain[rx][ry].getType() == TileType.FLOOR) {
                floodFill(rx, ry);
                if (countVisited() > 2000) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (!visited[i][j]) {
                                newterrain[i][j].setType(TileType.WALL);
                            }
                        }
                    }
                    terrain = newterrain;
                    found = true;
                }
                else{
                    clearVisited();
                }
            }
        }
    }

    private int countVisited() {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (visited[i][j] == true) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private void clearVisited() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                visited[x][y] = false;
            }
        }
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }

    public boolean visited(int x, int y) {
        return visited[x][y];
    }

    public boolean[][] getAllVisited() {
        return visited;
    }

    private void floodFill(int x, int y) {
        if (isOutOfBounds(x, y)) {
            return;
        }
        if ((visited(x, y))) {
            return;
        }
        if (terrain[x][y].getType() == TileType.WALL) {
            return;
        } else {
            if ((terrain[x][y].getType() == TileType.FLOOR)) {
                pathFinderVisited(x, y);
            }

        }
        floodFill(x + 1, y);
        floodFill(x - 1, y);
        floodFill(x, y + 1);
        floodFill(x, y - 1);
    }

    private void createWallsAtBorders() {
        for (int column = 0; column < height; column++) {
            for (int row = 0; row < width; row++) {
                if (row == 0) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);
                } else if (row == width - 1) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);

                } else if (column == 0) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);

                } else if (column == height - 1) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);
                }
            }
        }
    }

    private void randomFill(int chance) {
        for (int column = 0; column < height; column++) {
            for (int row = 0; row < width; row++) {
                if (row == 0) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);
                } else if (row == width - 1) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);

                } else if (column == 0) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);

                } else if (column == height - 1) {
                    terrain[row][column] = new Tile(row, column, TileType.WALL);

                } else {
                    if (rnd.nextInt(100) < chance) {
                        terrain[row][column] = new Tile(row, column, TileType.WALL);
                    } else {
                        terrain[row][column] = new Tile(row, column, TileType.FLOOR);
                    }
                }
            }
        }

    }

    private boolean isOutOfBounds(int x, int y) {
        if (x < 0 || y < 0) {
            return true;
        } else if (x > width - 1 || y > height - 1) {
            return true;
        }
        return false;
    }

    private boolean isWall(int x, int y) {
        if (isOutOfBounds(x, y)) {
            return false;
        }
        return (terrain[x][y].getType() == TileType.WALL);
    }

    private int findNeighbouringWalls(int x, int y, int rangeX, int rangeY) {
        int startX = x - rangeX;
        int startY = y - rangeY;
        int endX = x + rangeX;
        int endY = y + rangeY;

        int iX = startX;
        int iY = startY;

        int wallCounter = 0;

        for (iY = startY; iY <= endY; iY++) {
            for (iX = startX; iX <= endX; iX++) {
                if (!(iX == x && iY == y)) {
                    if (isWall(iX, iY)) {
                        wallCounter += 1;
                    }
                }
            }
        }

        return wallCounter;
    }

    private void makeCaverns() {
        // By initilizing column in the outter loop, its only created ONCE
        for (int column = 0, row = 0; row <= height - 1; row++) {
            for (column = 0; column <= width - 1; column++) {
                terrain[column][row] = placeWall(column, row);
            }
        }
    }

    private Tile placeWall(int x, int y) {
        int numWalls = findNeighbouringWalls(x, y, 1, 1);
        if (terrain[x][y].getType() == TileType.WALL) {
            if (numWalls >= 4) {
                return new Tile(x, y, TileType.WALL);
            }
            if (numWalls < 2) {
                return new Tile(x, y, TileType.FLOOR);
            }

        } else {
            if (numWalls >= 5) {
                return new Tile(x, y, TileType.WALL);
            }
        }
        return new Tile(x, y, TileType.FLOOR);
    }

    private void fillArea(int x, int y, int areaWidth, int areaHeight, TileType type) {
        for (int xp = x; xp < x + areaWidth; xp++) {
            for (int yp = y; yp < y + areaHeight; yp++) {
                terrain[xp][yp] = new Tile(xp, yp, type);
            }
        }
    }

    public Tile[][] getTerrain() {
        return terrain;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean blocked(Mover mover, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

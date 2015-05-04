package downwards;

 // @author laptopng34
import java.util.ArrayList;
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
        createWallsAtBorders();
        fillDisjointedRooms();

        generateLakes(10);

        caveIn();
        makeSilt();
        makeStraySilt(12);
        

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
        ArrayList<TileType> boundTypes = new ArrayList();
        boundTypes.add(TileType.WALL);

        Tile[][] newterrain = deepCopyTerrain();
        boolean found = false;
        while (!found) {
            int rx = 1 + rnd.nextInt(width - 1);
            int ry = 1 + rnd.nextInt(height - 1);
            if (newterrain[rx][ry].getType() == TileType.FLOOR) {
                floodFill(rx, ry, TileType.FLOOR, boundTypes);
                if (countVisited() > 2000) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (!visited[i][j]) {
                                newterrain[i][j].setType(TileType.WALL);
                            }
                        }
                    }
                    terrain = newterrain;
                    clearVisited();
                    found = true;
                } else {
                    clearVisited();
                }
            }

        }
    }

    private void floodFillLake() {
        ArrayList<TileType> boundTypes = new ArrayList();
        boundTypes.add(TileType.WALL);
        boundTypes.add(TileType.RUBBLE);
        boundTypes.add(TileType.WATER);

        Tile[][] newterrain = deepCopyTerrain();
        boolean found = false;
        while (!found) {
            int rx = 1 + rnd.nextInt(width - 1);
            int ry = 1 + rnd.nextInt(height - 1);
            if (newterrain[rx][ry].getType() == TileType.FLOOR) {
                floodFill(rx, ry, TileType.FLOOR, boundTypes);
                if (countVisited() > 500) {
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (visited[i][j]) {
                                newterrain[i][j].setType(TileType.WATER);
                            }
                        }
                    }
                    terrain = newterrain;
                    clearVisited();
                    found = true;
                } else {
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

    private void caveIn() {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (terrain[i][j].getType() == TileType.WALL) {
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if (terrain[i + k][j + l].getType() == TileType.FLOOR) {
                                terrain[i + k][j + l].setType(TileType.RUBBLE);
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeSilt() {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (terrain[i][j].getType() == TileType.WATER) {
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if (terrain[i + k][j + l].getType() == TileType.FLOOR) {
                                terrain[i + k][j + l].setType(TileType.SILT);
                            } else if (terrain[i + k][j + l].getType() == TileType.RUBBLE) {
                                terrain[i + k][j + l].setType(TileType.GRIT);
                            }
                        }
                    }
                }
            }
        }
    }

    public void makeStraySilt(int amt) {
        int counter = 0;
        while (counter < amt) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (terrain[i][j].getType() == TileType.SILT) {
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                if (!isOutOfBounds(i + k, j + l)) {
                                    if (terrain[i + k][j + l].getType() == TileType.FLOOR) {
                                        if (rnd.nextInt(100) > 85) {
                                            terrain[i+k][j+l].setType(TileType.SILT);
                                            counter++;
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }

    }

    public void generateLake(int x, int y, int widthLake, int heightLake) {
        for (int k = -1; k < widthLake; k++) {
            for (int l = -1; l < heightLake; l++) {
                if (!isOutOfBounds(x + k, y + l)) {
                    //if (terrain[x + k + 1][y + l + 1].getType() == TileType.WALL) {
                    if (findNeighbouringTiles(x + k, y + k, 2, 2, TileType.WALL) > 3) {

                        return;
                    } else {
                        if (((k == -1) && (l == -1))
                                || ((k == -1) && (l == heightLake - 1))
                                || ((k == widthLake - 1) && (l == -1))
                                || ((k == widthLake - 1) && (l == heightLake - 1))) {

                        } else {
                            terrain[x + k][y + l].setType(TileType.WATER);
                        }

                    }
                    if (findNeighbouringTiles(x + k, y + k, 1, 1, TileType.WATER) > 3) {
                        terrain[x + k][y + l].setType(TileType.WATER);
                    }

                }
            }
        }
    }

    public void generateLakes(int amt) {
        int counter = 0;
        int conx;
        int cony;
        while (counter < amt) {
            conx = 4 + rnd.nextInt(width - 4);
            cony = 4 + rnd.nextInt(height - 4);
            if (terrain[conx][cony].getType() == TileType.FLOOR) {
                generateLake(conx, cony, 2 + rnd.nextInt(5), 2 + rnd.nextInt(6));
                counter++;
            }
        }
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

    private void floodFill(int x, int y, TileType type, ArrayList<TileType> boundTypes) {
        if (isOutOfBounds(x, y)) {
            return;

        }
        if ((visited(x, y))) {
            return;
        }

        if (boundTypes.contains(terrain[x][y].getType())) {
            return;

        } else {
            if ((terrain[x][y].getType() == type)) {
                pathFinderVisited(x, y);
            }

        }
        floodFill(x + 1, y, type, boundTypes);
        floodFill(x - 1, y, type, boundTypes);
        floodFill(x, y + 1, type, boundTypes);
        floodFill(x, y - 1, type, boundTypes);
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

    private boolean isTile(int x, int y, TileType type) {
        if (isOutOfBounds(x, y)) {
            return false;
        }
        return (terrain[x][y].getType() == type);
    }

    private int findNeighbouringTiles(int x, int y, int rangeX, int rangeY, TileType type) {
        int startX = x - rangeX;
        int startY = y - rangeY;
        int endX = x + rangeX;
        int endY = y + rangeY;

        int iX = startX;
        int iY = startY;

        int tilesCounter = 0;

        for (iY = startY; iY <= endY; iY++) {
            for (iX = startX; iX <= endX; iX++) {
                if (!(iX == x && iY == y)) {
                    if (isTile(iX, iY, type)) {
                        tilesCounter += 1;
                    }
                }
            }
        }

        return tilesCounter;
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
        int numWalls = findNeighbouringTiles(x, y, 1, 1, TileType.WALL);
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

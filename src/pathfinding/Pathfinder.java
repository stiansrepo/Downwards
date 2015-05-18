package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import map.MapGenerator;
import map.MapInterface;
import map.Tile;
import map.TileType;
import map.WorldMap;
import things.ThingType;

public class Pathfinder {

    private MapGenerator mg;
    private ArrayList closed;
    private SortedList open;
    private Node[][] nodes;
    private int[][] doorLocs;
    private ArrayList<Node> targets;
    private Random random = new Random();

    public Pathfinder(MapGenerator mg) {
        this.mg = mg;
    }

    public int[][] getDoorLocs() {
        return doorLocs;
    }

    public void pathfind() {
        buildPath();
    }

    public void buildPath() {

        targets = findTargets();
        nodes = new Node[mg.getWidth()][mg.getHeight()];
        doorLocs = new int[mg.getWidth()][mg.getHeight()];
        for (int x = 0; x < mg.getWidth(); x++) {
            for (int y = 0; y < mg.getHeight(); y++) {
                nodes[x][y] = new Node(x, y);
            }
        }

        open = new SortedList();
        closed = new ArrayList();
        for (int i = 0; i < targets.size() - 1; i++) {
            boolean doorfound = false;
            int[][] heuristic = buildHeuristic(targets.get(i + 1).getX(), targets.get(i + 1).getY());
            boolean done = false;
            Node from = targets.get(i);
            Node to = targets.get(i + 1);
            Node current = from;
            current.setCost(heuristic[current.getX()][current.getY()] * 14);
            open.add(current);
            while (!done && open.size() != 0) {
                heuristic = buildHeuristic(targets.get(i + 1).getX(), targets.get(i + 1).getY());

                if (current.equals(targets.get(i + 1))) {
                    done = true;
                }
                closed.add(current);
                open.remove(current);

                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        int xp = x + current.getX();
                        int yp = y + current.getY();
                        open.add(nodes[xp][yp]);
                    }
                }

                Node cheapestNode = current;

                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }

                        int cheapest = current.getCost();
                        int xp = x + current.getX();
                        int yp = y + current.getY();

                        int mcost = 10;
                        if ((x == -1 || x == 1) && (y == 1 || y == -1)) {
                            mcost = 14;
                        }

                        if (true) {
                            if ((x != 0) && (y != 0)) {
                                continue;
                            }
                        }

                        if (heuristic[xp][yp] * mcost < cheapest) {
                            cheapestNode = nodes[xp][yp];
                            cheapestNode.setCost(heuristic[xp][yp] * mcost);
                        } else {
                            closed.add(nodes[xp][yp]);
                            open.remove(nodes[xp][yp]);
                        }
                    }
                }
                mg.getTerrain()[cheapestNode.getX()][cheapestNode.getY()].setType(TileType.FLOOR);

                if (!mg.blockedThing(ThingType.DOOR, cheapestNode.getX(), cheapestNode.getY())) {
                    if (!doorfound) {
                        mg.setDoor(cheapestNode.getX(), cheapestNode.getY());
                        doorfound = true;
                    }
                }
                cheapestNode.setParent(current);
                current = cheapestNode;
            }
        }
    }

    public int getHeuristicCost(int fromX, int fromY, int toX, int toY, int target) {
        int from = buildHeuristic(targets.get(target).getX(), targets.get(target).getY())[fromX][fromY];
        int to = buildHeuristic(targets.get(target).getX(), targets.get(target).getY())[toX][toY];
        return to - from;
    }

    public ArrayList<Node> findTargets() {
        Random rnd = new Random();
        targets = new ArrayList();
        CopyOnWriteArrayList<Tile[][]> roomlist = mg.getRooms();
        //8Collections.shuffle(roomlist, new Random());
        for (Tile[][] t : roomlist) {
            boolean found = false;
            int x = 2 + rnd.nextInt(mg.getCellSizeX() - 2);
            int y = 2 + rnd.nextInt(mg.getCellSizeY() - 2);
            while (!found) {

                if (t[x][y].getType() == TileType.FLOOR) {
                    targets.add(new Node(t[x][y].getX(), t[x][y].getY()));
                    found = true;
                } else {
                    x = 2 + rnd.nextInt(mg.getCellSizeX() - 2);
                    y = 2 + rnd.nextInt(mg.getCellSizeY() - 2);
                }
            }
        }
        return targets;
    }

    public int[][] buildHeuristic(int targetx, int targety) {
        int[][] heuristicMap = new int[mg.getHeight()][mg.getWidth()];
        for (int i = 0; i < mg.getWidth(); i++) {
            for (int j = 0; j < mg.getHeight(); j++) {
                int distancex;
                int distancey;
                distancex = Math.abs((targetx - i));
                distancey = Math.abs((targety - j));
                if (mg.getTerrain()[i][j].getType() == TileType.FLOOR) {
                    heuristicMap[i][j] = distancex + distancey;
                } else {
                    heuristicMap[i][j] = distancex + distancey;
                }
            }
        }
        return heuristicMap;
    }

    private class SortedList {

        private ArrayList list = new ArrayList();

        public ArrayList getList() {
            return list;
        }

        public Object first() {
            return list.get(0);
        }

        public void clear() {
            list.clear();
        }

        public void add(Object o) {
            list.add(o);
            Collections.sort(list);
        }

        public void remove(Object o) {
            list.remove(o);
        }

        public int size() {
            return list.size();
        }

        public boolean contains(Object o) {
            return list.contains(o);
        }
    }
}

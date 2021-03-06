package pathfinding;

 // @author laptopng34
public class Node implements Comparable {

    private int x;
    private int y;
    private Node parent;
    private int h = -1;
    private int g;
    private int f;
    private int cost;
    private int depth;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, int h) {
        this.x = x;
        this.y = y;
        this.h = h;
    }

    public Node getParent() {
        return parent;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
  
    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;
        Node c = (Node) object;

        if (c.x == this.x && c.y == this.y) {
            sameSame = true;
        }

        return sameSame;
    }

    public int setParent(Node parent) {
        depth = parent.depth + 1;
        this.parent = parent;

        return depth;
    }

    public int compareTo(Object other) {
        Node o = (Node) other;

        int f = h + cost;
        int of = o.h + o.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}

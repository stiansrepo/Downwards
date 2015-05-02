/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downwards;

/**
 *
 * @author laptopng34
 */
public interface MapInterface {
    
    public int getWidth();
    
    public int getHeight();
    
    public void pathFinderVisited(int x, int y);
    
    public boolean blocked(Mover mover, int x, int y);
    
    public float getCost (Mover mover, int sx, int sy, int tx, int ty);
    
}

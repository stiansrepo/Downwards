/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.Color;

public enum TileType {

    WALL(Color.BLACK),
    WATER(new Color(24, 24, 195)),
    GRASS(new Color(109, 185, 66)),
    RUBBLE(new Color(93, 93, 93)),
    FLOOR(Color.GRAY),
    SILT(new Color(206, 187, 67)),
    GRIT(new Color(138, 114, 87));
    
    private Color c;
    
    private TileType(Color c){
        this.c=c;
    }
    
    public Color getColor(){
        return c;
    }
    
}

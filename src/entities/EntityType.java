/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author laptopng34
 */
public enum EntityType {
    
    PLAYER("player.png"),
    CAVETHING("cavething.png");
    
    private String s;
    
    private EntityType(String s){
        this.s=s;
    }
    
    public String getFilePath(){
        return s;
    }
    
    public String getFilePathDead(){
        return "dead"+s;
    }
    
    
}

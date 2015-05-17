package things;

public enum ThingType {
    
    DOOR("door.png"),
    CHEST("chest.png"),
    TRAP("trap.png");
    
    private String s;
    
    private ThingType(String s){
        this.s=s;
    }
    
    public String getFilePath(){
        return s;
    }
    
    public String getFilePathClosed(){
        return "closed"+s;
    }
    
    public String getFilePathEmpty(){
        return "empty"+s;
    }
    
}

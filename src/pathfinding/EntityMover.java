package pathfinding;

 // @author laptopng34
import entities.EntityType;
import pathfinding.Mover;


 
public class EntityMover implements Mover {
    
    private EntityType e;
    
    public EntityMover(EntityType e){
        this.e=e;
    }
    
    public EntityType getType(){
        return e;
    }

}

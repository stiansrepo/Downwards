package downwards;

 // @author laptopng34
 
public class EntityMover implements Mover {
    
    private EntityType e;
    
    public EntityMover(EntityType e){
        this.e=e;
    }
    
    public EntityType getType(){
        return e;
    }

}

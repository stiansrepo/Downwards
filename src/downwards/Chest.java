package downwards;

 // @author laptopng34
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chest extends Thing {

    private CopyOnWriteArrayList contents;

    public Chest(int x, int y, ThingType t, boolean in, WorldMap map, Game game) {
        super(x, y, t, in,map,game);
        map.setThing(this, x, y);
    }
    
    public void addContents(Item i) {
        if (contents == null) {
            contents = new CopyOnWriteArrayList();
        }
        contents.add(i);
    }

    public void addContents(ArrayList a) {
        if (contents == null) {
            contents = new CopyOnWriteArrayList();
        }
        for (Object i : a) {
            contents.add((Item) i);
        }
    }

    public CopyOnWriteArrayList getItems() {
        if (contents != null) {
            CopyOnWriteArrayList c = new CopyOnWriteArrayList(contents);
            contents.clear();
            return c;
        }
        return null;
    }
    
    public boolean isEmpty(){
        return contents==null;
    }
}

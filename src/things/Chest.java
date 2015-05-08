package things;

 // @author laptopng34
import core.Game;
import items.Item;
import map.WorldMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import map.MapGenerator;

public class Chest extends Thing {

    private CopyOnWriteArrayList contents;
    private boolean empty;

    public Chest(int x, int y, ThingType t, boolean in, Game game) {
        super(x, y, t, in, game);
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
            empty=true;
            return c;
        }
        return null;
    }
    
    
    public boolean isEmpty(){
        return empty;
    }
}

package items;
// @author laptopng34

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItemGenerator {
    
    Map<String, String> weaponStrings = Collections.synchronizedMap(new HashMap<String, String>());

    public ItemGenerator() {
        weaponStrings.put("Half-molten pickaxe", "A half-molten pickaxe. Where did the heat come from?");
        weaponStrings.put("Sharpened Rock", "Looks like a sharpened rock.");
        weaponStrings.put("Petrified Wyrm's Tail", "This bizarre. angular piece of mineral looks like it was broken off a petrified wyrm.");
        weaponStrings.put("Hammer", "A tool from one of the cavern's long abandoned workshops.");
    }

    public Item generateItem(Type t) {
        Item item = null;
        switch (t) {
            case WEAPON:
                Random rnd = new Random();
                List<String> keys = new ArrayList<String>(weaponStrings.keySet());
                String randomKey = keys.get(rnd.nextInt(keys.size()));
                String name = randomKey;
                String description = weaponStrings.get(randomKey);
                item = new Item(name, description, 1,ItemType.WEAPON);
        }
        return item;
    }

    public Weapon generateWeapon() {
        Weapon weapon;
        Random rnd = new Random();
        weapon = new Weapon(generateItem(Type.WEAPON), 1+rnd.nextInt(2), 2+rnd.nextInt(6));
        return weapon;
    }

    public enum Type {

        WEAPON,
        ARMOR,
        POTION,
        GENERIC;
    }
}

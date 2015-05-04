package downwards;
// @author laptopng34

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class MonsterGenerator {

    ArrayList<Color> colors = new ArrayList();
    ArrayList<EntityType> entityTypes = new ArrayList();
    ArrayList<String> names = new ArrayList();
    ArrayList<Stats> stats = new ArrayList();
    ArrayList<Weapon> weapons = new ArrayList();
    Random rnd = new Random();
    ItemGenerator itemGenerator = new ItemGenerator();

    public MonsterGenerator() {
        addColors();
        addEntities();
        addNames();
        addStats();
        addWeapons();
    }

    public Monster generateMonster() {
        Color c = colors.get(rnd.nextInt(colors.size()));
        EntityType et = entityTypes.get(rnd.nextInt(entityTypes.size()));
        String name = names.get(rnd.nextInt(names.size()));
        Stats stat = stats.get(rnd.nextInt(stats.size()));
        Weapon weapon = weapons.get(rnd.nextInt(weapons.size()));
        Monster m = new Monster(c, et, name, stat, weapon);
        return m;
    }

    private void addColors() {
        colors.add(new Color(3, 155, 50));
        colors.add(new Color(10, 135, 100));
        colors.add(new Color(35, 195, 130));
    }

    private void addEntities() {
        entityTypes.add(EntityType.CAVETHING);
    }

    private void addNames() {
        names.add("Gokdo");
        names.add("Bersias");
        names.add("Jocoa");
        names.add("Voetron");
    }

    private void addStats() {
        stats.add(new Stats());
    }

    private void addWeapons() {
        weapons.add(itemGenerator.generateWeapon());
        weapons.add(itemGenerator.generateWeapon());
        weapons.add(itemGenerator.generateWeapon());
        weapons.add(itemGenerator.generateWeapon());
        weapons.add(itemGenerator.generateWeapon());
        weapons.add(itemGenerator.generateWeapon());
        weapons.add(itemGenerator.generateWeapon());
    }

}

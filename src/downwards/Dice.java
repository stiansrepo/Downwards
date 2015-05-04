package downwards;

 // @author laptopng34
import java.util.Random;

public class Dice {

    public static int rollDice(int rolls, int range) {
        Random rnd = new Random();
        int roll = 0;
        for (int i = 0; i < rolls; i++) {
            roll = 1 + rnd.nextInt(range - 1);
        }
        return roll;
    }

    public static int rollWeapon(Weapon w) {
        return rollDice(w.getRolls(),w.getRange());
    }
}

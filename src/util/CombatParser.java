package util;

import core.Game;
import entities.Entity;
import java.util.ArrayList;

 // @author laptopng34
public class CombatParser {

    private ArrayList<String> combatLog;
    private String combatLine;

    private Game game;

    public CombatParser(Game game) {
        this.game = game;
        combatLine = "";
        combatLog = new ArrayList();
    }

    public void combat(Entity attacker, Entity target) {
        float str = attacker.getStats().getStr();
        float pct = 0.1f;
        int statsDmg = Math.round(str*pct);
        int dmg = Dice.rollWeapon(attacker.getWeapon()) + statsDmg;
        target.takeDamage(dmg);
        combatLine = attacker.name + "'s " + attacker.getWeapon().getName().toLowerCase() + " deals " + dmg + " damage to " + target.name + "!";
        if (!target.isAlive()) {
            attacker.gainXp(target.getStats().getXpValue());
            combatLine =combatLine + "\n" + target.name + " falls screaming to its death!";
        }
        game.setCombatInfo(combatLine);
        combatLog.add(combatLine);
        if (!game.player.isAlive()) {
            game.gameOver();
        }
    }

    public String getCombatLine() {
        return combatLine;
    }

    public ArrayList<String> getCombatLog() {
        return combatLog;
    }

}

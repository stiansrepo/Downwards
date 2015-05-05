package stats;

 // @author laptopng34
import java.util.ArrayList;


public class Stats {

    private int level;
    private int xp;
    private int str;
    private int ite;
    private int dex;
    private int con;
    private int wis;
    private int cha;

    public Stats(int level, int xp, int str, int ite, int dex, int con, int wis, int cha) {
        this.level = level;
        this.xp = xp;
        this.str = str;
        this.ite = ite;
        this.dex = dex;
        this.con = con;
        this.wis = wis;
        this.cha = cha;
    }

    public int getXpValue() {
        return level * 200;
    }
    
    public void levelUp(){
        level++;
        
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getIte() {
        return ite;
    }

    public void setIte(int ite) {
        this.ite = ite;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public int getWis() {
        return wis;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public int getCha() {
        return cha;
    }

    public void setCha(int cha) {
        this.cha = cha;
    }
    
    public ArrayList<String> getPrimaryStats(){
        ArrayList<String> statList = new ArrayList<>();
        statList.add("Strength: " + Integer.toString(str));
        statList.add("Intelligence: " + Integer.toString(ite));
        statList.add("Dexterity: " + Integer.toString(dex));
        statList.add("Constitution: " + Integer.toString(con));
        statList.add("Wisdom: " + Integer.toString(wis));
        statList.add("Charisma: " + Integer.toString(cha));
        return statList;
        
    }

}

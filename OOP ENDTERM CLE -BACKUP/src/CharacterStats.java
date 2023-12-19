import java.util.Random;

public abstract class CharacterStats {
    Random random = new Random();
    String name;
    private String status;
    private int lvl;
    private double hp, mana, atk, def, spd, luck, crtRate, crtDmg, acc;

    CharacterStats(String name, int lvl, double hp, double atk, double mana, double def, double spd, double luck,double crtRate, double crtDmg, double acc, String status){
        this.name = name;
        this.lvl = lvl;
        this.hp = hp;
        this.atk = atk;
        this.mana = mana;
        this.def = def;
        this.spd = spd;
        this.luck = luck;
        this.crtRate = crtRate;
        this.crtDmg = crtDmg;
        this.acc = acc;
        this.status = status;
    }

    public CharacterStats() {

    }

    public int getLvl() {
        return lvl;
    }
    public double getHp(){
        return hp;
    }
    public double getAtk() {
        return atk;
    }
    public double getDef() {
        return def;
    }
    public double getMana() {
        return mana;
    }
    public double getSpd() {
        return spd;
    }
    public double getLuck() {
        return luck;
    }
    public double getCrtRate() {
        return crtRate;
    }
    public double getCrtDmg() {
        return crtDmg;
    }

    public double getAcc() {
        return acc;
    }

    public String getStatus() {
        return status;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public void setCrtRate(double crtRate) {
        this.crtRate = crtRate;
    }

    public void setCrtDmg(double crtDmg) {
        this.crtDmg = crtDmg;
    }

    public void setLuck(double luck) {
        this.luck = luck;
    }

    public void setSpd(double spd) {
        this.spd = spd;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public void setStatus(String status) {
        this.status = status;
    }
 }


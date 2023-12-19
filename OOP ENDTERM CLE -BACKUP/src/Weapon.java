import java.io.Serializable;

public abstract class Weapon implements Serializable {
    String name;
    String element;
    String aff;
    private int atk, def, hp;
    private double crtRate, crtDmg, acc;

    public Weapon(String name, String element, String aff, int atk, double crtRate, double crtDmg, double acc){
        this.name = name;
        this.element = element;
        this.aff = aff;
        this.atk = atk;
        this.crtRate = crtRate;
        this.crtDmg = crtDmg;
        this.acc = acc;
    }
    public Weapon(String name, String element, String aff, int def, int hp){
        this.name = name;
        this.element = element;
        this.aff = aff;
        this.def = def;
        this.hp = hp;
    }
    public void displayWeapon(){
        System.out.println(
                "-----------------------\nName: " + name +"\nAffiliation: "+ aff +"    Element: " + element + "\nATK: "+ atk +"   CRIT RATE: " + (crtRate*100)+ "%" + "\nCRIT DAMAGE: " + (crtDmg*100) + "%" + "   ACCURACY: " + (acc * 100) + "%" + "\n-----------------------");
    }
    public void displayShield(){
        System.out.println(
                "-----------------------\nName: " + name +"\nAffiliation: "+ aff +"    Element: " + element + "\nDEF: "+ def +"   HP: " + hp + "\n-----------------------");
    }
    public int getAtk() {
        return atk;
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
    public int getDef() {
        return def;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setAtk(int atk) {
        this.atk = atk;
    }
    public void setCrtRate(double crtRate) {
        this.crtRate = crtRate;
    }
    public void setCrtDmg(double crtDmg) {
        this.crtDmg = crtDmg;
    }
    public void setDef(int def) {
        this.def = def;
    }
}
class WeaponList implements Serializable {
    RegularWeapons sword = new RegularWeapons("Amandig Cutlass","Populi","Amandig Armory - The main source of weapons in Amandig",50, 0.15,1.20,1);
    RegularWeapons shield = new RegularWeapons("Kawal Shield","Populi","Amandig Armory - The main source of weapons in Amandig", 30,50);
    DivineWeapons divineAxe = new DivineWeapons("Timber's Downfall", "Natura", "Dumakulem - The Forest Deity of the Mountains", 130, 0.05,2.0, 1,75);
    DivineWeapons divineBow = new DivineWeapons("Ripple of the Torrential Dewdrop", "Pluvia", "Anitun Tabu - The Goddess of Rain", 75, 0.30, 1.30, 1,0.7,60);
    DivineWeapons divineWhip = new DivineWeapons("Cries of the Vox Populi", "Populi", "Idianale - The Civil Deity of Justice and Labor", 20, 0,1,1,0.7,40);
    DivineWeapons divineTrident = new DivineWeapons("Thunder God's Wrath", "Fulmen", "Habagat - The God of the Northern Storm", 110, 0.45, 1.35, 0.70,0.6,80);
    DivineWeapons divineShield = new DivineWeapons("Protection of the Graceful Waves", "Aequor", "Aman Sinaya - The Primordial Deity of the Ocean", 120, 300,100);
    DivineWeapons divineBlades = new DivineWeapons("Divine Twins of Omnipotence", "Divinus", "Bathala - The Supreme God of the Amandig Pantheon", 200, 0.80, 1.50,1, 150);
}
class RegularWeapons extends Weapon implements WeaponActions{
    public RegularWeapons(String name, String element, String aff, int atk, double crtRate, double crtDmg, double acc) {
        super(name, element, aff, atk, crtRate, crtDmg, acc);
    }

    public RegularWeapons(String name, String element, String aff, int def, int hp) {
        super(name, element, aff, def, hp);
    }

    @Override
    public int weaponAtk1(Inventory i, WeaponList weapon, Laum laum, Gods god, Operations op) {
        System.out.println("-----------------------\n> [LAUM]: You used your " + name);
        int damage = op.damageCalculator(laum.getAtk(), getAtk(), (getCrtRate() + laum.getCrtRate()), (getCrtDmg() + laum.getCrtDmg()), laum.getLuck(), op.moveHits(laum.getAcc(), laum.getLuck()));
        return damage;
    }
    @Override
    public int weaponAtk2(Inventory i, WeaponList weapon, Laum laum, Operations op, StatusEffects e, StatListOperation stat, Gods god, Gods tempGod) {
        return 0;
    }

    @Override
    public void shield(StatListOperation stat, StatusEffects e, Laum laum, Laum tempLaum, Operations op) {
        System.out.println("> [LAUM]: You used your " + name);
        stat.laumShielded(e,laum, tempLaum,op, getDef(), getHp());
    }
}
class DivineWeapons extends Weapon implements WeaponActions{
    private double statusAcc;
    private final int mana;

    public DivineWeapons(String name, String element, String aff, int atk, double crtRate, double crtDmg, double acc, int mana) {
        super(name, element, aff, atk, crtRate, crtDmg, acc);
        this.mana = mana;
    }
    public DivineWeapons(String name, String element, String aff, int atk, double crtRate, double crtDmg, double acc, double statusAcc, int mana) {
        super(name, element, aff, atk, crtRate, crtDmg, acc);
        this.statusAcc = statusAcc;
        this.mana = mana;

    }
    public DivineWeapons(String name, String element, String aff, int def, int hp, int mana) {
        super(name, element, aff, def, hp);
        this.mana = mana;

    }
    @Override
    public int weaponAtk1(Inventory i, WeaponList weapon, Laum laum, Gods god, Operations op) {
        if ((laum.getMana() - mana) > 0) {
            System.out.println("> [LA-UM]: You used your " + name);
            laum.setMana(laum.getMana() - mana);
            setBonus(i,weapon);
            int damage = op.damageCalculator(laum.getAtk(), getAtk(), (getCrtRate() + laum.getCrtRate()), (getCrtDmg() + laum.getCrtDmg()), laum.getLuck(), op.moveHits(laum.getAcc(), laum.getLuck()));
            resetBonus(i,weapon);
            return damage;
        }else{
            System.out.println("> [LA-UM]: Not enough mana to use this weapon...");
            return 0;
        }
    }
    @Override
    public int weaponAtk2(Inventory i, WeaponList weapon, Laum laum, Operations op, StatusEffects e, StatListOperation stat, Gods god, Gods tempGod) {
        if ((laum.getMana() - mana) > 0) {
            System.out.println("-----------------------\n> [LA-UM]: You used your " + name);
            setBonus(i,weapon);
            int damage = op.damageCalculator(getAtk(),laum.getAtk(),(getCrtRate() + laum.getCrtRate()),(getCrtDmg() + laum.getCrtDmg()), laum.getLuck(), op.moveHits((laum.getAcc()*getAcc()), laum.getLuck()));
            stat.godStatusHit(e, statusAcc, laum.getLuck(), element, god.getStatus(), god, tempGod,op.hit);
            laum.setMana(laum.getMana() - mana);
            resetBonus(i,weapon);
            return damage;
        }else{
            System.out.println("-----------------------\n> [LA-UM]: You used your " + name);
            return 0;
        }

    }
    public void shield(StatListOperation stat, StatusEffects e, Laum laum, Laum tempLaum, Operations op) {
        if ((laum.getMana() - mana) > 0) {
            System.out.println("-----------------------\n> [LA-UM]: You used your " + name);
            stat.laumVitalize(e, laum, tempLaum, op, getDef(), getHp());
            laum.setMana(laum.getMana() - mana);
        } else{
            System.out.println("-----------------------\n> [LA-UM]Not enough mana to use this shield...");

        }
    }

    public int getMana() {
        return mana;
    }

    public void setBonus(Inventory i, WeaponList weapon){
        try {
            if (i.dWeaponEquipped.get(0).element.equals(i.divineEquipped.get(0).getElement())) {
                    if (i.dWeaponEquipped.get(0).element.equals("Natura") && (i.divineEquipped.get(0).getElement().equals("Natura"))) {
                        weapon.divineAxe.setAtk(weapon.divineAxe.getAtk() + 15);
                        System.out.println(3333);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Pluvia") && (i.divineEquipped.get(0).getElement().equals("Pluvia"))) {
                        weapon.divineBow.setCrtDmg(weapon.divineBow.getCrtDmg() + 0.10);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Populi") && (i.divineEquipped.get(0).getElement().equals("Populi"))) {
                        weapon.divineWhip.setCrtRate(weapon.divineWhip.getCrtRate() + 0.05);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Fulmen") && (i.divineEquipped.get(0).getElement().equals("Fulmen"))) {
                        weapon.divineTrident.setCrtDmg(weapon.divineTrident.getCrtDmg() + 0.30);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Aequor") && (i.divineEquipped.get(0).getElement().equals("Aequor"))) {
                        weapon.shield.setDef(weapon.divineShield.getDef() + 30);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Divinus") && (i.divineEquipped.get(0).getElement().equals("Divinus"))) {
                        weapon.divineBlades.setAtk(weapon.divineBlades.getAtk() + 50);
                    }
                }
        }catch (Exception e){

        }

    }
    public void resetBonus(Inventory i, WeaponList weapon){
        try {
            if (i.dWeaponEquipped.get(0).element.equals(i.divineEquipped.get(0).getElement())) {
                System.out.println(22222);
                    if (i.dWeaponEquipped.get(0).element.equals("Natura") && (i.divineEquipped.get(0).getElement().equals("Natura"))) {
                        weapon.divineAxe.setAtk(weapon.divineAxe.getAtk() - 15);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Pluvia") && (i.divineEquipped.get(0).getElement().equals("Pluvia"))) {
                        weapon.divineBow.setCrtDmg(weapon.divineBow.getCrtDmg() - 0.10);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Populi") && (i.divineEquipped.get(0).getElement().equals("Populi"))) {
                        weapon.divineWhip.setCrtRate(weapon.divineWhip.getCrtRate() - 0.05);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Fulmen") && (i.divineEquipped.get(0).getElement().equals("Fulmen"))) {
                        weapon.divineTrident.setCrtDmg(weapon.divineTrident.getCrtDmg() - 0.30);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Aequor") && (i.divineEquipped.get(0).getElement().equals("Aequor"))) {
                        weapon.shield.setDef(weapon.divineShield.getDef() - 30);
                    } else if (i.dWeaponEquipped.get(0).element.equals("Divinus") && (i.divineEquipped.get(0).getElement().equals("Divinus"))) {
                        weapon.divineBlades.setAtk(weapon.divineBlades.getAtk() - 50);
                    }
                }

        } catch (Exception e){

        }

    }
}







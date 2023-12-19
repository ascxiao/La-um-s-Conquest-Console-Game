import java.io.Serializable;

public abstract class Armor implements Serializable{
    String name, desc;
    private String element;
    private double def, hp;
    Armor(String name, String desc, String element, double def, double hp){
        this.name = name;
        this.desc = desc;
        this.element = element;
        this.def = def;
        this.hp = hp;
    }

    public Armor() {

    }

    public String getElement() {
        return element;
    }
    public void setDef(double def) {
        this.def = def;
    }
    public void setHp(double hp) {
        this.hp = hp;
    }
    public double getDef() {
        return def;
    }
    public double getHp() {
        return hp;
    }
    public abstract void addStat(Laum laum);
    public void displayArmor(){
        System.out.println(
                "-----------------------\nArmor: " + name +"\nDescription: " + desc +"\nElement: "+ element + "\nDEF: " + getDef() + "     HP: " + getHp() + "\n-----------------------");
    }

}
class ArmorList implements Serializable {
    RegularArmor laumTunic = new RegularArmor("La-um's Tunic","The fabric is made from royal silk gifted by the Amandig's Royal Family","",15,15);
    DivineArmor  narraGuard = new DivineArmor("Narra Warden's Guard","Made from the bark of the overgrowth of the Great Narra Tree", "Natura", 17,13);
    DivineArmor rainPlate = new DivineArmor("Raindop-Patterned Chainmail","Frozen raindrops consist this armor. It is said that it has health vitalizing properties.", "Pluvia", 11, 19);
    DivineArmor justiciar = new DivineArmor("Justiciar's Armor","An armor that is worn by divine justice bringer.", "Populi", 18,12);
    DivineArmor stormLord = new DivineArmor("Stormlord's Aegis","This armor creates a faint and constant crackle sound.", "Fulmen",25,5);
    DivineArmor tidalCloak = new DivineArmor("Sinaya's Tidal Cloak","Worn by the great goddess, Aman Sinaya. It flows with the waves.","Aequor",20,10);
    DivineArmor bathalaPendant = new DivineArmor("Bathala's Divine Pendant","This pendant embodies the power of the divine god, Bathala. Provides the wearer divine protection", "Divinus", 50,50);
}
class DivineArmor extends Armor{
    DivineArmor(String name, String desc, String element, double def, double hp) {
        super(name, desc, element, def, hp);
    }
    public DivineArmor() {
    }
    @Override
    public void addStat(Laum laum) {
        laum.setHp(laum.getHp() + getHp());
        laum.setDef(laum.getDef() + getDef());
    }
    /*public void setBonus(Inventory i, WeaponList weapon){
        if(!boost) {
            if (i.dWeaponEquipped.get(0).name.equals("Natura") && (i.divineEquipped.get(0).name.equals("Natura"))) {
                weapon.divineAxe.setAtk(weapon.divineAxe.getAtk() + 15);
            } else if (i.dWeaponEquipped.get(0).name.equals("Pluvia") && (i.divineEquipped.get(0).toString().equals("Pluvia"))) {
                weapon.divineBow.setCrtDmg(weapon.divineBow.getCrtDmg() + 0.10);
            } else if (i.dWeaponEquipped.get(0).name.equals("Populi") && (i.divineEquipped.get(0).toString().equals("Populi"))) {
                weapon.divineWhip.setCrtRate(weapon.divineWhip.getCrtRate() + 0.05);
            } else if (i.dWeaponEquipped.get(0).name.equals("Fulmen") && (i.divineEquipped.get(0).toString().equals("Fulmen"))) {
                weapon.divineTrident.setCrtDmg(weapon.divineTrident.getCrtDmg() + 0.30);
            } else if (i.dWeaponEquipped.get(0).name.equals("Aequor") && (i.divineEquipped.get(0).toString().equals("Aequor"))) {
                weapon.shield.setDef(weapon.divineShield.getDef() + 30);
            } else if (i.dWeaponEquipped.get(0).name.equals("Divinus") && (i.divineEquipped.get(0).toString().equals("Divinus"))) {
                weapon.divineBlades.setAtk(weapon.divineBlades.getAtk() + 50);
            }
            this.boost = true;
        }

    }
    public void setBoost(boolean boost) {
        this.boost = boost;
    }*/

}
class RegularArmor extends Armor{

    RegularArmor(String name, String desc, String element, double def, double hp) {
        super(name, desc, element, def, hp);
    }

    public RegularArmor() {
        super();
    }

    @Override
    public void addStat(Laum laum) {
        laum.setHp(laum.getHp() + getHp());
        laum.setDef(laum.getDef() + getDef());
    }
}


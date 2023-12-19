import java.io.Serializable;

public abstract class Items implements Serializable {
    String name, desc, type;
    private int quan;
    private double health, mana, atk, def;

    Items(String name, String desc, String type, int quan, double health, double mana, double atk, double def){
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.quan = quan;
        this.health = health;
        this.mana = mana;
        this.atk = atk;
        this.def = def;
    }

    public void displayItem(){
        System.out.println(
                "-----------------------\nItem: " + name +"\n" + "Quantity: " + quan + "\n" + "Description: " + desc +"\n"+ "-----------------------");
    }
    public int getQuan() {
        return quan;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public double getAtk() {
        return atk;
    }

    public double getDef() {
        return def;
    }
    public void setQuan(int quan) {
        this.quan = quan;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public void setDef(double def) {
        this.def = def;
    }
}
class ItemLists implements Serializable{
    HealingItems brewVitality = new HealingItems(
            "Brew of Vitality","Heals 50 HP. \nIt is a common medicinal brew that is found in local apothecaries...","Health Item",
            4,50,0,0,0);
    HealingItems healingDraught = new HealingItems(
            "Healing Draught", "Heals 70 HP. \nA blend of different exotic herbs that awakens healing energy within one's body...","Health Item+",
            0,70,0,0,0);
    HealingItems adarnaElixir = new HealingItems(
            "Adarna Elixir","Heals 110 HP. \nA feather of the legendary Ibong Adarna is infused in this concoction...", "Health Item++",
            0,110,0,0,0);
    HealingItems rainDrop = new HealingItems(
            "Bottle of Anitun's Raindrops", "Heals 60 and gain 70 Mana. \nA bottle of divine water that vitalizes both mind and body...","Revitalizing Item",
            0, 60, 70,0,0);
    HealingItems leafEncanto = new HealingItems(
            "Encanto Leaf", "Gain 40 Mana. \nA leaf picked from an enchanted tree. Ironically, these trees are normal...", "Mana Item",
            6,0, 40, 0,0);
    HealingItems lampongTear = new HealingItems(
            "Lampong's Tear", "Gain 100 Mana. \nA frozen tear of the mythical deer, Lampong. These can be found along the trail of Lampong...", "Mana Item+",
            0,0,100,0,0);
    HealingItems diwataHeart = new HealingItems(
            "Diwata's Heart", "Gain 200. \nA heart bestowed by an enchanting fairy. Those who consumes this heart will experience a surge of magic in their veins...", "Mana Item++",
            0,0,200,0,0);
    PowerItems narraBlood = new PowerItems(
            "Blood of a Narra Tree", "Gain +10 ATK until the end of the battle. \nThere's a myth that the sap of a Narra tree makes you stronger for a short while...", "Stat Boost Item",
            2,0,0,10,0);
    PowerItems pactKatipunan = new PowerItems(
            "Pact of the Katipunan", "Gain +20 ATK and 10 DEF until the end of the battle. \nA magical scroll containing the words written by the first heroes of the land...","Stat Boost Item+",
            0,0,0,20,15);
    PowerItems bakunawaRef = new PowerItems(
            "Bakunawa's Reflection","Gain +50 ATK and 20 DEF until the end of the battle. \nA frozen reflection of the legendary dragon, Bakunawa. They say that if you see your own reflection in the broken mirror, then you will be blessed by the might of the lunar dragon", "Stat Boost Item++",
            0,0,0,50,30);
}

class HealingItems extends Items{
    HealingItems(String name, String desc, String type, int quan, double health, double mana, double atk, double def) {
        super(name, desc, type, quan, health, mana, atk, def);
    }
}

class PowerItems extends Items{

    PowerItems(String name, String desc, String type, int quan, double health, double mana, double atk, double def) {
        super(name, desc, type, quan, health, mana, atk, def);
    }
}



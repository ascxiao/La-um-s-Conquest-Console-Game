import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Inventory implements Serializable {
    Random random = new Random();
    ItemLists item = new ItemLists();
    WeaponList weapon = new WeaponList();
    ArmorList armor = new ArmorList();
    Operations op;
    private int turnUsage = 0;
    String armored = "---";
    ArrayList <RegularWeapons> regularWeapons = new ArrayList<>(List.of(new RegularWeapons[]{weapon.sword, weapon.shield}));
    ArrayList <DivineWeapons> divineWeapons = new ArrayList<>(List.of(new DivineWeapons[]{}));
    ArrayList <RegularArmor> regularArmors = new ArrayList<>(List.of(new RegularArmor[]{armor.laumTunic}));
    ArrayList <DivineArmor> divineArmors = new ArrayList<>(List.of(new DivineArmor[]{}));
    ArrayList <HealingItems> healingItems = new ArrayList<>(List.of(new HealingItems[]{
            item.brewVitality,item.healingDraught, item.adarnaElixir, item.rainDrop,
            item.leafEncanto, item.lampongTear, item.diwataHeart}));
    ArrayList <PowerItems> powerItems = new ArrayList<>(List.of(new PowerItems[]{
            item.narraBlood, item.pactKatipunan, item.bakunawaRef}));
    ArrayList <RegularArmor> regularEquipped = new ArrayList<>(List.of(new RegularArmor[]{regularArmors.get(0)}));
    ArrayList <DivineArmor> divineEquipped = new ArrayList<>(List.of(new DivineArmor[]{}));
    ArrayList <DivineWeapons> dWeaponEquipped = new ArrayList<>(List.of(new DivineWeapons[]{}));


    Inventory(Operations op, Laum laum, WeaponList weapon){
        this.op = op;
        this.weapon = weapon;
    }

    Inventory(){};

    public void addDivine(Gods god){
        switch(god.element) {
            case "Natura":
                divineWeapons.add(weapon.divineAxe);
                divineArmors.add(armor.narraGuard);
                break;
            case "Pluvia":
                divineWeapons.add(weapon.divineBow);
                divineArmors.add(armor.rainPlate);
                break;
            case "Populi":
                divineWeapons.add(weapon.divineWhip);
                divineArmors.add(armor.justiciar);
                break;
            case "Fulmen":
                divineWeapons.add(weapon.divineTrident);
                divineArmors.add(armor.stormLord);
                break;
            case "Aequor":
                divineWeapons.add(weapon.divineShield);
                divineArmors.add(armor.tidalCloak);
                break;
            case "Divinus":
                divineWeapons.add(weapon.divineBlades);
                divineArmors.add(armor.bathalaPendant);
                break;
        }
        System.out.println("""
                > The dust of the god collated in the air... A sudden shine solidified and turned into a weapon and an armor...
                > Congatulations! You have received the god's divine manifestation!..""");
    }
    public void randomRewardTier1(){
        int items123 = random.nextInt(100);
        int items4 = random.nextInt(100);
        int items5 = random.nextInt(100);

        if(items123 > 90){
            System.out.println("You received Healing Draught (+70HP) x 2!..");
            System.out.println("You received Bottle of Anitun's Raindrops (+60HP | +70 Mana) x 1!..");
            item.healingDraught.setQuan(item.healingDraught.getQuan() + 2);
            item.rainDrop.setQuan(item.rainDrop.getQuan() + 1);
        } else if(90 > items123 && items123 > 85){
            System.out.println("You have received Brew of Vitality (+50HP) x 3!...");
            item.brewVitality.setQuan(item.brewVitality.getQuan() + 3);
        } else if (85 > items123){
            System.out.println("You have received Brew of Vitality (+50HP) x 2!...");
            item.brewVitality.setQuan(item.brewVitality.getQuan() + 2);
        }

        if(items4 > 90){
            System.out.println("You have received Lampong's Tear (+100 Mana) x 1");
            item.lampongTear.setQuan(item.lampongTear.getQuan() + 1);
        } else {
            System.out.println("You have received Encanto Leaf (+40 Mana) x 1");
            item.leafEncanto.setQuan(item.leafEncanto.getQuan() + 1);
        }

        if(items5 > 80){
            System.out.println("You have received Pact of the Katipunan (+20 ATK) x 1");
            item.pactKatipunan.setQuan(item.pactKatipunan.getQuan() + 1);
        } else {
            System.out.println("You have received Blood of a Narra Tree (+10 ATK) x 1");
            item.narraBlood.setQuan(item.narraBlood.getQuan() + 1);
        }
    }
    public void randomRewardTier2(){
        int items123 = random.nextInt(100);
        int items4 = random.nextInt(100);
        int items5 = random.nextInt(100);

        if(items123 > 90){
            System.out.println("You received Adarna Elixir (+110 HP) x 1!..");
            System.out.println("You received Bottle of Anitun's Raindrops (+60HP | +70 Mana) x 2!..");
            item.adarnaElixir.setQuan(item.adarnaElixir.getQuan() + 1);
            item.rainDrop.setQuan(item.rainDrop.getQuan() + 2);
        } else if(90 > items123 && items123 > 85){
            System.out.println("You have received Healing Draught (+70HP) x 2!...");
            System.out.println("You have received Brew of Vitality (+50HP) x 1!...");
            item.healingDraught.setQuan(item.healingDraught.getQuan() + 2);
            item.brewVitality.setQuan(item.brewVitality.getQuan() + 1);
        } else if (85 > items123){
            System.out.println("You have received Healing Draught (+70HP) x 1!...");
            item.healingDraught.setQuan(item.healingDraught.getQuan() + 1);
        }

        if(items4 > 90){
            System.out.println("You have received Lampong's Tear (+100 Mana) x 2");
            item.lampongTear.setQuan(item.lampongTear.getQuan() + 2);
        } else {
            System.out.println("You have received Encanto Leaf (+40 Mana) x 2");
            item.leafEncanto.setQuan(item.leafEncanto.getQuan() + 2);
        }

        if(items5 > 90){
            System.out.println("You have received Bakunawa's Reflection (+50 ATK | +20 DEF) x 1");
            item.bakunawaRef.setQuan(item.bakunawaRef.getQuan() + 1);
        } else {
            System.out.println("You have received Pact of the Katipunan (+20 ATK | +10 DEF) x 1");
            item.pactKatipunan.setQuan(item.pactKatipunan.getQuan() + 1);
        }
    }
    public void randomRewardTier3(){
        int items123 = random.nextInt(100);
        int items4 = random.nextInt(100);
        int items5 = random.nextInt(100);

        if(items123 > 60){
            System.out.println("You received Adarna Elixir (+110) x 2!..");
            System.out.println("You received Bottle of Anitun's Raindrops (+60HP | +70 Mana) x 1!..");
            item.adarnaElixir.setQuan(item.adarnaElixir.getQuan() + 2);
            item.rainDrop.setQuan(item.rainDrop.getQuan() + 1);
        } else {
            System.out.println("You have received Healing Draught (+70HP) x 3!...");
            item.healingDraught.setQuan(item.healingDraught.getQuan() + 3);
        }

        if(items4 > 70){
            System.out.println("You have received Diwata's Heart (+200 Mana) x 2");
            item.diwataHeart.setQuan(item.diwataHeart.getQuan() + 2);
        } else {
            System.out.println("You have received Lampong's Tear (+40 Mana) x 2");
            item.lampongTear.setQuan(item.lampongTear.getQuan() + 2);
        }

        if(items5 > 50){
            System.out.println("You have received Bakunawa's Reflection (+50 ATK | +20 DEF) x 1");
            item.bakunawaRef.setQuan(item.bakunawaRef.getQuan() + 1);
        } else {
            System.out.println("You have received Pact of the Katipunan (+20 ATK | +10 DEF) x 1");
            item.pactKatipunan.setQuan(item.pactKatipunan.getQuan() + 1);
        }
    }
    public void useHealthItem(HealingItems heal, Laum laum, Laum tempLaum){
        op.laumHealthAdder(laum, heal.getHealth(), tempLaum.getHp());
        op.laumManaAdder(laum,heal.getMana(), tempLaum.getMana());
    }
    public void usePowerItem(PowerItems power, Laum laum){
        laum.setAtk(laum.getAtk() + power.getAtk());
        laum.setDef(laum.getDef() + power.getDef());
    }

}

class ArmorEquip {

    Inventory i = new Inventory();
    String tempArmor;

    ArmorEquip(Inventory i){
        this.i = i;
    }
    public void equipArmor(boolean divine, int x, Laum laum) {

        if (divine) {
            if (i.divineEquipped.size() == 0) {
                i.divineEquipped.add(i.divineArmors.get(x));
                armorStats(laum, i.divineArmors.get(x));
                tempArmor = i.divineArmors.get(x).name;
                System.out.println("[LA-UM]: You have equipped " + i.divineArmors.get(x).name);
                i.armored = i.divineArmors.get(x).name;
            } else {
                removeDivineArmor(laum);
                i.divineEquipped.add(i.divineArmors.get(x));
                tempArmor = i.divineArmors.get(x).name;
                System.out.println("[LA-UM]: You have equipped " + i.divineArmors.get(x).name);
                armorStats(laum, i.divineArmors.get(x));
                i.armored = i.divineArmors.get(x).name;
            }
        } else {
            if (i.regularEquipped.size() == 0) {
                System.out.println("[LA-UM]: You have equipped " + i.regularArmors.get(0).name);
                i.regularEquipped.add(i.regularArmors.get(0));
                armorStats(laum, i.regularArmors.get(0));
                i.armored = i.divineArmors.get(x).name;
            } else {
                removeRegularArmor(laum);
                System.out.println("[LA-UM]: You have equipped " + i.regularArmors.get(0).name);
                i.regularEquipped.add(i.regularArmors.get(0));
                armorStats(laum, i.regularArmors.get(0));
                i.armored = i.divineArmors.get(x).name;
            }
        }
    }
    public void removeDivineArmor(Laum laum) {
            if (i.divineEquipped.size() != 0) {
                System.out.println("[LA-UM]: You have removed " + tempArmor);
                i.armored = "---";
                if ((laum.getHp() - i.divineEquipped.get(0).getHp()) > 0) {
                    removeStats(laum, i.divineEquipped.get(0));
                }
                i.divineEquipped.remove(0);
            }
    }
    public void removeRegularArmor(Laum laum) {
            if (i.regularEquipped.size() == 1) {
                System.out.println("[LA-UM]: You have removed " + i.regularArmors.get(0).name);
                i.armored = "---";
                if ((laum.getHp() - i.divineEquipped.get(0).getHp()) > 0) {
                    removeStats(laum, i.regularEquipped.get(0));
                }
                i.divineEquipped.remove(0);

            }
    }
    public void armorStats(Laum laum, Armor armor){
        laum.setDef((laum.getDef()) + armor.getDef());
        laum.setHp((laum.getHp()+ armor.getHp()));
    }
    public void removeStats(Laum laum, Armor armor){
        laum.setDef((laum.getDef()) - armor.getDef());
        laum.setHp((laum.getHp() - armor.getHp()));
    }
    public void equipWeapon(int x) {
            if (i.dWeaponEquipped.size() == 0) {
                i.dWeaponEquipped.add(i.divineWeapons.get(x));
                System.out.println("[LA-UM]: You have equipped " + i.dWeaponEquipped.get(0).name);
            }
    }
    public void removeDivineWeapon() {
        if (i.dWeaponEquipped.size() == 1) {
            i.dWeaponEquipped.remove(0);
        }
    }

}

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BattleSystem {
    Characters characters;
    WeaponList weapon;
    ArmorList armor;
    Operations op;
    StatusEffects e;
    StatListOperation stat;
    Laum laum = new Laum();
    Gods gods;
    Scanner input;
    ItemLists items;
    Inventory i;
    Interface inter;
    ArmorEquip equip;
    boolean exit, battleExit, escape, victory, gameExit;
    BattleSystem(Characters characters, Laum laum, Gods god, WeaponList weapon, ArmorList armor, Operations op, StatusEffects e, StatListOperation stat, Inventory i, ItemLists items, Scanner input, ArmorEquip equip, Interface inter) {
        this.characters = characters;
        this.laum = laum;
        this.gods = god;
        this.weapon = weapon;
        this.armor = armor;
        this.op = op;
        this.e = e;
        this.stat = stat;
        this.i = i;
        this.items = items;
        this.input = input;
        this.equip = equip;
        this.inter = inter;
    }

    public void battle(Gods god, AccountOperations a) throws IOException, ClassNotFoundException {
        escape = false;
        battleExit = false;
        victory = false;
        System.out.println("-----------------------");
        god.encounter();
        laum.encounter();
        Laum tempLaum = new Laum(laum);
        Gods tempGods = new Gods(god);

        while(!battleExit) {
            while ((laum.getHp() > 0) && (god.getHp() > 0) && !battleExit) {
                System.out.println("-----------------------");
                inter.displayHealthBar(laum.getHp(),tempLaum.getHp(), laum.name, laum.getLvl(), laum.getStatus());
                inter.displayHealthBar(god.getHp(),tempGods.getHp(), god.name, god.getLvl(), god.getStatus());

                if (op.turnIndicator(laum.getSpd(), god.getSpd())) {
                menu(god, tempGods, laum, tempLaum, i, items, equip, a);
                if(escape){
                    break;
                }
                    if (god.getHp() <= 0){
                        break;
                    }
                godMoves(op, god, tempGods, tempLaum);

                stat.decayingHealth(laum);
                stat.laumStatusTurns(laum, tempLaum);
                stat.godStatusTurns(god, tempGods);
                stat.manaRegenerate(laum, tempLaum);
                } else {
                    godMoves(op, god, tempGods, tempLaum);
                    if (laum.getHp() <= 0){
                        break;
                    }
                    menu(god, tempGods, laum, tempLaum, i, items, equip, a);
                    if(escape){
                        break;
                    }

                    stat.decayingHealth(laum);
                    stat.laumStatusTurns(laum, tempLaum);
                    stat.godStatusTurns(god, tempGods);
                    stat.manaRegenerate(laum, tempLaum);
                }
            }
            if((laum.getHp() <= 0) && (god.getHp() > 0) && !battleExit){
                System.out.println("-----------------------\n>>> YOU HAVE DIED");
                laum.death();
                god.enemyDefeat();
                System.out.println("-----------------------");
                stat.laumResetStats(laum, tempLaum);
                stat.godResetStats(god, tempGods);
                stat.laumLvlUp(false,laum);
                battleExit = true;
            }
            else if ((god.getHp() <= 0) && (laum.getHp() > 0) && !battleExit){
                System.out.println("-----------------------\n>>> YOU HAVE DEFEATED THE GOD");
                god.death();
                laum.enemyDefeat();
                System.out.println("-----------------------");
                System.out.println("> You have leveled up by 5 levels! You are now level " + (laum.getLvl() + 5));
                System.out.println("-----------------------");
                int x = 0;
                switch (god.element) {
                    case "Natura", "Pluvia" -> i.randomRewardTier1();
                    case "Populi", "Fulmen" -> i.randomRewardTier2();
                    case "Aequor" -> i.randomRewardTier3();
                }
                switch (god.element) {
                    case "Natura" -> x = 2;
                    case "Pluvia" -> x = 3;
                    case "Populi" -> x = 4;
                    case "Fulmen" -> x = 5;
                    case "Aequor" -> x = 6;
                }
                stat.laumResetStats(laum, tempLaum);
                stat.godResetStats(god, tempGods);
                i.addDivine(god);
                a.save(a.currentFile,x,a.currentAccount,i);
                stat.laumLvlUp(true, laum);
                victory = true;
                battleExit = true;
            }
        }
    }
    public void menu(Gods god, Gods tempGod, Laum laum, Laum tempLaum, Inventory i, ItemLists items, ArmorEquip equip, AccountOperations a) throws IOException, ClassNotFoundException {
        exit = false;
        int choice;

        while (!exit) {
            try {
                System.out.println("-----------------------\nPLAYER: " + a.currentAccount.getName());
                System.out.println("LA-UM" + " (LVL " + laum.getLvl() + "): ");
                System.out.println("HP: " + Math.ceil(laum.getHp()) + " / " + Math.ceil(tempLaum.getHp()) + " | Mana: " + Math.ceil(laum.getMana()) + " / " + Math.ceil(tempLaum.getMana()) + " | Status: " + laum.getStatus());
                System.out.println("ATK: " + Math.ceil(laum.getAtk()) + " | DEF: " + Math.ceil(laum.getDef())  + " | SPD: " + Math.ceil(laum.getSpd()) + " | ACC: " + (laum.getAcc())* 100);
                System.out.println("Armor: " + i.armored);
                System.out.print(
                        """
                                -----------------------
                                     CHOOSE ACTION
                                1. ATTACK
                                2. OPEN INVENTORY
                                3. CHECK OPPONENT
                                4. ESCAPE!
                                5. LOG OUT
                                -----------------------
                                """
                );
                System.out.print("CHOOSE ACTION: ");
                choice = input.nextInt();
                if (choice == 1) {
                    laumAtkInput(god, tempGod, laum, tempLaum);
                } else if (choice == 2) {
                    laumInventory(i, items, equip, tempLaum);
                } else if (choice == 3) {
                    displayWeapon(god, tempGod);
                } else if (choice == 4){
                    if(op.escapes(laum.getSpd(), god.getSpd())){
                        System.out.println("-----------------------\n[LA-UM]: I escaped... Embarrassing, I better prepare more next time! ");
                        exit = true;
                        battleExit = true;
                        escape = true;
                    } else {
                        System.out.println("-----------------------\n[LA-UM]: I cannot escape!! ");
                        exit = true;
                    }
                } else if (choice == 5){
                    System.out.println("""
                            -----------------------
                            > LOGGING OUT...
                            """);
                    battleExit = true;
                    escape = true;
                    victory = true;
                    gameExit = true;
                    a.logOut();
                    break;
                }

            } catch (Exception e) {
                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                input.nextLine();
            }
        }
    }
    public void laumAtkInput(Gods god, Gods tempGod, Laum laum, Laum tempLaum) {
        boolean exit1 = false, exit2 = false;
        int choice, choice2;

        while (!exit1) {
            try {
            exit2 = false;
                System.out.print(
                        """
                                -----------------------
                                      WEAPON TYPE
                                1. Basic Weapon
                                2. Divine Weapon
                                3. << BACK
                                -----------------------
                                """
                );
                System.out.print("Choose Weapon Type: ");
                choice = input.nextInt();
                if (choice == 1) {
                    exit2 = false;
                    while (!exit2) {
                        System.out.print("""
                                -----------------------
                                Basic Weapons:
                                1. Amandig Cutlass (ATK: 50)
                                2. Knight Shield (DEF: 30 | HP: 50)
                                3. << BACK
                                -----------------------
                                """);
                        System.out.print("Choose Weapon Type: ");
                        try {
                            choice2 = input.nextInt();
                            if (choice2 == 1) {
                                op.godHealthReducer(god, i.regularWeapons.get(0).weaponAtk1(i, weapon, laum,god,op), laum);
                                exit2 = true;
                                exit1 = true;
                                exit = true;
                            } else if (choice2 == 2) {
                                i.regularWeapons.get(1).shield(stat, e, laum, tempLaum, op);
                                exit2 = true;
                                exit1 = true;
                                exit = true;
                            } else if (choice2 == 3) {
                                exit2 = true;
                            } else {
                                System.out.println("-----------------------\n\n[LA-UM]: Huh? I think I lost my focus there... ");
                            }
                        } catch (Exception e) {
                            System.out.println("-----------------------\n\n[LA-UM]: Huh? I think I lost my focus there... ");
                            input.nextLine();
                        }
                    }
                }
                else if (choice == 2) {
                    while (!exit2) {
                        System.out.print(
                                """
                                        -----------------------
                                        Divine Weapons:\s
                                        """);
                        int y = 1;
                        for (int x = 0; i.divineWeapons.size() > x; x++) {
                            try {
                                System.out.println((y) + "." + i.divineWeapons.get(x).name + "\n(Mana:"+i.divineWeapons.get(x).getMana() + " | ATK:" + i.divineWeapons.get(x).getAtk() + "| CRT RATE: " + (i.divineWeapons.get(x).getCrtRate()*100) +"%" + "| CRT DAMAGE: " + (i.divineWeapons.get(x).getCrtDmg()*100) +"%)");
                                y++;
                            } catch (Exception e){
                                System.out.println((y) + "." + i.divineWeapons.get(x).name + "\n(Mana:"+i.divineWeapons.get(x).getMana() + " | DEF:" + i.divineWeapons.get(x).getDef() + " | HP: " + i.divineWeapons.get(0).getHp() + ")");
                                y++;
                            }
                        }
                        for (int x = 0; (6 - i.divineWeapons.size()) > x; x++) {
                            System.out.println((y) + ". ---");
                            y++;
                        }

                        System.out.println(y + ". << BACK");
                        System.out.println("-----------------------");
                        System.out.print("Choose Item: ");
                        try {
                            choice2 = input.nextInt();
                            if (choice2 == 1) {
                                try {
                                    equip.equipWeapon(0);
                                    op.godHealthReducer(god, i.divineWeapons.get(0).weaponAtk1(i, weapon, laum,god,op), laum);
                                    equip.removeDivineWeapon();
                                    exit2 = true;
                                    exit1 = true;
                                    exit = true;
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\n[LA-UM]: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 2) {
                                try {
                                    equip.equipWeapon(1);
                                    op.godHealthReducer(god, i.divineWeapons.get(1).weaponAtk2(i, weapon, laum,op,e,stat,god,tempGod), laum);
                                    equip.removeDivineWeapon();
                                    exit2 = true;
                                    exit1 = true;
                                    exit = true;
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 3) {
                                try {
                                    equip.equipWeapon(2);
                                    op.godHealthReducer(god, i.divineWeapons.get(2).weaponAtk2(i, weapon, laum,op,e,stat,god,tempGod), laum);
                                    equip.removeDivineWeapon();
                                    exit2 = true;
                                    exit1 = true;
                                    exit = true;
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 4) {
                                try {
                                    equip.equipWeapon(3);
                                    op.godHealthReducer(god, i.divineWeapons.get(3).weaponAtk2(i, weapon, laum,op,e,stat,god,tempGod), laum);
                                    equip.removeDivineWeapon();
                                    exit2 = true;
                                    exit1 = true;
                                    exit = true;
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 5) {
                                try {
                                    equip.equipWeapon(4);
                                    i.divineWeapons.get(4).shield(stat, e, laum, tempLaum, op);
                                    equip.removeDivineWeapon();
                                    exit2 = true;
                                    exit1 = true;
                                    exit = true;
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 6) {
                                try {
                                    equip.equipWeapon(5);
                                    op.godHealthReducer(god, i.divineWeapons.get(5).weaponAtk1(i, weapon,laum, god, op), laum);
                                    equip.removeDivineWeapon();
                                    exit2 = true;
                                    exit1 = true;
                                    exit = true;
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 7){
                                exit2 = true;
                            }
                        } catch (Exception e) {
                            System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            input.nextLine();
                        }
                    }
                }
                else if (choice == 3){
                    exit1 = true;
                }
            } catch (Exception e) {
                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                input.nextLine();
            }
        }
    }
    public void laumInventory(Inventory i, ItemLists items, ArmorEquip equip, Laum tempLaum) {
        boolean exit1 = false, exit2 = false, exit3;
        int choice, choice2, choice3, choice4, choice5;

        while (!exit1) {
            try {
                System.out.print(
                        """
                                -----------------------
                                      Inventory
                                1. Health/Mana Items
                                2. Power Items
                                3. Weapons
                                4. Armor
                                5. << BACK
                                -----------------------
                                """
                );
                System.out.print("Choose Category to Check: ");
                choice = input.nextInt();
                input.nextLine();
                if (choice == 1) {
                    exit2 = false;
                    while (!exit2) {
                        System.out.print(
                                """
                                        -----------------------
                                        Health/Mana Items:\s
                                        """);
                        for (int x = 0; i.healingItems.toArray().length > x; x++) {
                            System.out.println((x + 1) + "." + i.healingItems.get(x).name + "--- x " + i.healingItems.get(x).getQuan());
                        }
                        System.out.println("8. << BACK");
                        System.out.println("-----------------------");
                        System.out.print("Choose Item: ");
                        try {
                            choice2 = input.nextInt();
                            if (choice2 == 1) {
                                items.brewVitality.displayItem();
                                System.out.println("-----------------------");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Use item?: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if(items.brewVitality.getQuan() > 0){
                                            if(laum.getHp() < tempLaum.getHp()) {
                                                System.out.println("-----------------------");
                                                System.out.println("\nYou used " + items.brewVitality.name);
                                                i.useHealthItem(items.brewVitality,laum, tempLaum);
                                                items.brewVitality.setQuan(items.brewVitality.getQuan() - 1);
                                                i.healingItems.get(0).setQuan(i.healingItems.get(0).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                                exit2 = true;
                                            }
                                        } else {
                                            System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                            exit2 = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 2) {
                                items.healingDraught.displayItem();
                                System.out.println("-----------------------\n");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if(items.healingDraught.getQuan() > 0) {
                                            if(laum.getHp() < tempLaum.getHp()) {
                                                System.out.println("-----------------------");
                                                System.out.println("You used " + items.healingDraught.name);
                                                i.useHealthItem(items.healingDraught,laum, tempLaum);
                                                items.healingDraught.setQuan(items.healingDraught.getQuan() - 1);
                                                i.healingItems.get(1).setQuan(i.healingItems.get(1).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                                exit2 = true;
                                            }
                                        } else {
                                            System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                            exit2 = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 3) {
                                items.adarnaElixir.displayItem();
                                System.out.println("-----------------------\n");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.adarnaElixir.getQuan() > 0) {
                                            if(laum.getHp() < tempLaum.getHp()) {
                                                System.out.println("-----------------------");
                                                System.out.println("You used " + items.adarnaElixir.name);
                                                i.useHealthItem(items.adarnaElixir,laum, tempLaum);
                                                items.healingDraught.setQuan(items.healingDraught.getQuan() - 1);
                                                i.healingItems.get(2).setQuan(i.healingItems.get(2).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                            }
                                        } else {
                                            System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                            exit2 = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 4) {
                                items.rainDrop.displayItem();
                                System.out.println("-----------------------\n");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.rainDrop.getQuan() > 0) {
                                            if(laum.getHp() < tempLaum.getHp()) {
                                                System.out.println("-----------------------");
                                                System.out.println("You used " + items.rainDrop.name);
                                                i.useHealthItem(items.rainDrop,laum, tempLaum);
                                                items.healingDraught.setQuan(items.rainDrop.getQuan() - 1);
                                                i.healingItems.get(3).setQuan(i.healingItems.get(3).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                                exit2 = true;
                                            }
                                    } else {
                                        System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                        exit2 = true;
                                    }
                                }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 5) {
                                items.leafEncanto.displayItem();
                                System.out.println("-----------------------\n");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.leafEncanto.getQuan() > 0) {
                                            if(laum.getMana() < tempLaum.getMana()) {
                                                System.out.println("-----------------------");
                                                System.out.println("You used " + items.leafEncanto.name);
                                                i.useHealthItem(items.leafEncanto,laum, tempLaum);
                                                items.leafEncanto.setQuan(items.leafEncanto.getQuan() - 1);
                                                i.healingItems.get(4).setQuan(i.healingItems.get(4).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                                exit2 = true;
                                            }
                                    } else {
                                        System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                        exit2 = true;
                                    }
                                }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 6) {
                                items.lampongTear.displayItem();
                                System.out.println("-----------------------\n");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.lampongTear.getQuan() > 0) {
                                            if(laum.getMana() < tempLaum.getMana()) {
                                                System.out.println("-----------------------");
                                                System.out.println("You used " + items.lampongTear.name);
                                                i.useHealthItem(items.lampongTear,laum, tempLaum);
                                                items.lampongTear.setQuan(items.lampongTear.getQuan() - 1);
                                                i.healingItems.get(5).setQuan(i.healingItems.get(5).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                            exit2 = true;
                                            }
                                    } else {
                                        System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                        exit2 = true;
                                    }
                                }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 7) {
                                items.diwataHeart.displayItem();
                                System.out.println("-----------------------\n");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.diwataHeart.getQuan() > 0) {
                                            if(laum.getMana() < tempLaum.getMana()) {
                                                System.out.println("-----------------------");
                                                System.out.println("You used " + items.diwataHeart.name);
                                                i.useHealthItem(items.diwataHeart,laum, tempLaum);
                                                items.diwataHeart.setQuan(items.diwataHeart.getQuan() - 1);
                                                i.healingItems.get(6).setQuan(i.healingItems.get(6).getQuan() - 1);
                                                exit2 = true;
                                                exit1 = true;
                                            } else {
                                                System.out.println("-----------------------\n[LA-UM]: I think I don't need this at the moment... ");
                                                exit2 = true;
                                            }
                                        } else {
                                            System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                            exit2 = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 8) {
                                exit2 = true;
                            } else {
                                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            }
                        } catch (Exception e) {
                            System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            input.nextLine();
                        }
                    }
                }
                else if (choice == 2) {
                    exit2 = false;
                    while (!exit2) {
                        System.out.print(
                                "-----------------------\n" +
                                        "Power Items: \n");
                        for (int x = 0; i.powerItems.toArray().length > x; x++) {
                            System.out.println((x + 1) + "." + i.powerItems.get(x).name + "--- x " + i.powerItems.get(x).getQuan());
                        }
                        System.out.println("4. << BACK");
                        System.out.println("-----------------------");
                        System.out.print("Choose Item: ");
                        try {
                            choice2 = input.nextInt();
                            if (choice2 == 1) {
                                items.narraBlood.displayItem();
                                System.out.println("-----------------------");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.narraBlood.getQuan() > 0) {
                                            System.out.println("-----------------------");
                                            System.out.println("You used " + items.narraBlood.name);
                                            i.usePowerItem(items.narraBlood, laum);
                                            items.narraBlood.setQuan(items.narraBlood.getQuan() - 1);
                                            i.powerItems.get(0).setQuan(i.powerItems.get(0).getQuan() - 1);
                                            exit2 = true;
                                            exit1 = true;
                                    } else {
                                        System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                        exit2 = true;
                                    }
                                }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 2) {
                                items.pactKatipunan.displayItem();
                                System.out.println("-----------------------");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.pactKatipunan.getQuan() > 0) {
                                            System.out.println("-----------------------");
                                            System.out.println("You used " + items.pactKatipunan.name);
                                            i.usePowerItem(items.pactKatipunan, laum);
                                            items.pactKatipunan.setQuan(items.pactKatipunan.getQuan() - 1);
                                            i.powerItems.get(1).setQuan(i.powerItems.get(1).getQuan() - 1);
                                            exit2 = true;
                                            exit1 = true;
                                        } else {
                                        System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                        exit2 = true;
                                    }
                                }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 3) {
                                items.bakunawaRef.displayItem();
                                System.out.println("-----------------------");
                                System.out.println("Do you want to use this? Press 1 if yes: ");
                                System.out.print("Choose Item: ");
                                try {
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                        if (items.bakunawaRef.getQuan() > 0) {
                                            System.out.println("-----------------------");
                                            System.out.println("You used " + items.bakunawaRef.name);
                                            i.usePowerItem(items.bakunawaRef, laum);
                                            items.bakunawaRef.setQuan(items.bakunawaRef.getQuan() - 1);
                                            i.powerItems.get(2).setQuan(i.powerItems.get(2).getQuan() - 1);
                                            exit2 = true;
                                            exit1 = true;

                                        } else {
                                            System.out.println("-----------------------\n[LA-UM]: I think I don't have that anymore... ");
                                            exit2 = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                                    input.nextLine();
                                }
                            } else if (choice2 == 4) {
                                exit2 = true;
                            } else {
                                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            }
                        } catch (Exception e) {
                            System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            input.nextLine();
                        }
                    }
                }
                else if (choice == 3) {
                    exit2 = false;
                    while (!exit2) {
                        System.out.print(
                                """
                                        -----------------------
                                        Weapons:\s
                                        """);
                        int y = 1;
                        for (int x = 0; i.regularWeapons.size() > x; x++) {
                            System.out.println((y) + "." + i.regularWeapons.get(x).name);
                            y++;
                        }
                        for (int x = 0; i.divineWeapons.size() > x; x++) {
                            System.out.println((y) + "." + i.divineWeapons.get(x).name);
                            y++;
                        }
                        for (int x = 0; (6 - i.divineWeapons.size()) > x; x++) {
                            System.out.println((y) + ". ---");
                            y++;
                        }

                        System.out.println(y + ". << BACK");
                        System.out.println("-----------------------");
                        System.out.print("Choose Item: ");
                        try {
                            choice2 = input.nextInt();
                            if (choice2 == 1) {
                                weapon.sword.displayWeapon();
                                System.out.println("Input any number to go back...");
                                System.out.print("Input: ");
                                choice3 = input.nextInt();
                                if (choice3 != 0) {
                                    exit2 = true;
                                } else {
                                    exit2 = true;
                                }
                            } else if (choice2 == 2) {
                                weapon.shield.displayShield();
                                System.out.println("Input any number to go back...");
                                System.out.print("Input: ");
                                choice3 = input.nextInt();
                                if (choice3 != 0) {
                                    exit2 = true;
                                } else {
                                    exit2 = true;
                                }
                            } else if (choice2 == 3) {
                                try {
                                    i.divineWeapons.get(0).displayWeapon();
                                    System.out.println("Input any number to go back...(Enter 0 to go back to Main Inventory)");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 != 0) {
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            } else if (choice2 == 4) {
                                try {
                                    i.divineWeapons.get(1).displayWeapon();
                                    System.out.println("Input any number to go back...(Enter 0 to go back to Main Inventory)");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 != 0) {
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            } else if (choice2 == 5) {
                                try {
                                    i.divineWeapons.get(2).displayWeapon();
                                    System.out.println("Input any number to go back...(Enter 0 to go back to Main Inventory)");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 != 0) {
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            } else if (choice2 == 6) {
                                try {
                                    i.divineWeapons.get(3).displayWeapon();
                                    System.out.println("Input any number to go back...(Enter 0 to go back to Main Inventory)");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 != 0) {
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            } else if (choice2 == 7) {
                                try {
                                    i.divineWeapons.get(4).displayWeapon();
                                    System.out.println("Input any number to go back...(Enter 0 to go back to Main Inventory)");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 != 0) {
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            } else if (choice2 == 8) {
                                try {
                                    i.divineWeapons.get(5).displayWeapon();
                                    System.out.println("Input any number to go back...(Enter 0 to go back to Main Inventory)");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 != 0) {
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            } else if (choice2 == 9) {
                                exit2 = true;
                                input.nextLine();
                            } else {
                                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            }
                        } catch (Exception e) {
                            System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            input.nextLine();
                        }
                    }
                }
                else if (choice == 4) {
                    exit2 = false;
                    while (!exit2) {
                        System.out.print(
                                """
                                        -----------------------
                                        Armor:\s
                                        """);
                        int y = 1;
                        for (int x = 0; i.regularArmors.size() > x; x++) {
                            System.out.println((y) + "." + i.regularArmors.get(x).name);
                            y++;
                        }
                        for (int x = 0; i.divineArmors.size() > x; x++) {
                            System.out.println((y) + "." + i.divineArmors.get(x).name);
                            y++;
                        }
                        for (int x = 0; (6 - i.divineArmors.size()) > x; x++) {
                            System.out.println((y) + ". ---");
                            y++;
                        }

                        System.out.println(y + ". << BACK");
                        System.out.println("-----------------------");
                        System.out.print("Choose Armor: ");
                        try {
                            choice2 = input.nextInt();
                            if (choice2 == 1) {
                                armor.laumTunic.displayArmor();
                                System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                System.out.print("Input: ");
                                choice3 = input.nextInt();
                                if (choice3 == 1) {
                                    try {
                                        if (i.regularEquipped.size() != 0) {
                                            System.out.println("You are already equipping Laum's Tunic");
                                            System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                            choice4 = input.nextInt();
                                            if (choice4 == 1) {
                                                equip.removeDivineArmor(laum);
                                                exit2 = true;
                                            } else {
                                                exit2 = true;
                                            }
                                        }
                                    } catch (Exception e) {
                                        equip.equipArmor(false, 0, laum);
                                        exit2 = true;
                                    }
                                } else {
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 2) {
                                try {
                                    i.divineArmors.get(0).displayArmor();
                                    System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                            if (i.divineEquipped.size() != 0) {
                                                System.out.println("You are already equipping " + (equip.tempArmor));

                                                if(equip.tempArmor.equals(i.divineArmors.get(0).name)) {
                                                    System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                                } else {
                                                    System.out.println("Do you want to replace it? Press 1 if yes (Press 0 to go back)...");
                                                }

                                                choice4 = input.nextInt();
                                                if (choice4 == 1) {
                                                    if(equip.tempArmor.equals(i.divineArmors.get(0).name)){
                                                        equip.removeDivineArmor(laum);
                                                        exit2 = true;
                                                    } else{
                                                        equip.equipArmor(true,0, laum);
                                                        exit2 = true;
                                                    }
                                                    exit2 = true;
                                                } else {
                                                    exit2 = true;
                                                }
                                            } else {
                                            equip.equipArmor(true, 0, laum);
                                            exit2 = true;
                                        }
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 3) {
                                try {
                                    i.divineArmors.get(1).displayArmor();
                                    System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                            if (i.divineEquipped.size() != 0) {
                                                System.out.println("You are already equipping " + (equip.tempArmor));

                                                if(equip.tempArmor.equals(i.divineArmors.get(1).name)) {
                                                    System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                                } else {
                                                    System.out.println("Do you want to replace it? Press 1 if yes (Press 0 to go back)...");
                                                }

                                                choice4 = input.nextInt();
                                                if (choice4 == 1) {
                                                    if(equip.tempArmor.equals(i.divineArmors.get(1).name)){
                                                        equip.removeDivineArmor(laum);
                                                        exit2 = true;
                                                    } else{
                                                        equip.equipArmor(true,1, laum);
                                                        exit2 = true;
                                                    }
                                                    exit2 = true;
                                                } else {
                                                    exit2 = true;
                                                }
                                            } else {
                                            equip.equipArmor(true, 1, laum);
                                            exit2 = true;
                                        }
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 4) {
                                try {
                                    i.divineArmors.get(2).displayArmor();
                                    System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                            if (i.divineEquipped.size() != 0) {
                                                System.out.println("You are already equipping " + (equip.tempArmor));

                                                if(equip.tempArmor.equals(i.divineArmors.get(2).name)) {
                                                    System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                                } else {
                                                    System.out.println("Do you want to replace it? Press 1 if yes (Press 0 to go back)...");
                                                }

                                                choice4 = input.nextInt();
                                                if (choice4 == 1) {
                                                    if(equip.tempArmor.equals(i.divineArmors.get(2).name)){
                                                        equip.removeDivineArmor(laum);
                                                        exit2 = true;
                                                    } else{
                                                        equip.equipArmor(true,2, laum);
                                                        exit2 = true;
                                                    }
                                                    exit2 = true;
                                                } else {
                                                    exit2 = true;
                                                }
                                            } else {
                                            equip.equipArmor(true, 2, laum);
                                            exit2 = true;
                                        }
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 5) {
                                try {
                                    i.divineArmors.get(3).displayArmor();
                                    System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                            if (i.divineEquipped.size() != 0) {
                                                System.out.println("You are already equipping " + (equip.tempArmor));

                                                if(equip.tempArmor.equals(i.divineArmors.get(3).name)) {
                                                    System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                                } else {
                                                    System.out.println("Do you want to replace it? Press 1 if yes (Press 0 to go back)...");
                                                }

                                                choice4 = input.nextInt();
                                                if (choice4 == 1) {
                                                    if(equip.tempArmor.equals(i.divineArmors.get(3).name)){
                                                        equip.removeDivineArmor(laum);
                                                        exit2 = true;
                                                    } else{
                                                        equip.equipArmor(true,3, laum);
                                                        exit2 = true;
                                                    }
                                                    exit2 = true;
                                                } else {
                                                    exit2 = true;
                                                }
                                            } else {
                                            equip.equipArmor(true, 3, laum);
                                            exit2 = true;
                                        }
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 6) {
                                try {
                                    i.divineArmors.get(4).displayArmor();
                                    System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                            if (i.divineEquipped.size() != 0) {
                                                System.out.println("You are already equipping " + (equip.tempArmor));

                                                if(equip.tempArmor.equals(i.divineArmors.get(4).name)) {
                                                    System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                                } else {
                                                    System.out.println("Do you want to replace it? Press 1 if yes (Press 0 to go back)...");
                                                }

                                                choice4 = input.nextInt();
                                                if (choice4 == 1) {
                                                    if(equip.tempArmor.equals(i.divineArmors.get(4).name)){
                                                        equip.removeDivineArmor(laum);
                                                        exit2 = true;
                                                    } else{
                                                        equip.equipArmor(true,4, laum);
                                                        exit2 = true;
                                                    }
                                                    exit2 = true;
                                                } else {
                                                    exit2 = true;
                                                }
                                            } else {
                                            equip.equipArmor(true, 4, laum);
                                            exit2 = true;
                                        }
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 7) {
                                try {
                                    i.divineArmors.get(5).displayArmor();
                                    System.out.println("Do you want to equip this armor? Press 1 if yes (Press 0 to go back)...");
                                    System.out.print("Input: ");
                                    choice3 = input.nextInt();
                                    if (choice3 == 1) {
                                            if (i.divineEquipped.size() != 0) {
                                                System.out.println("You are already equipping " + (equip.tempArmor));

                                                if(equip.tempArmor.equals(i.divineArmors.get(5).name)) {
                                                    System.out.println("Do you want to remove it? Press 1 if yes (Press 0 to go back)...");
                                                } else {
                                                    System.out.println("Do you want to replace it? Press 1 if yes (Press 0 to go back)...");
                                                }

                                                choice4 = input.nextInt();
                                                if (choice4 == 1) {
                                                    if(equip.tempArmor.equals(i.divineArmors.get(5).name)){
                                                        equip.removeDivineArmor(laum);
                                                        exit2 = true;
                                                    } else{
                                                        equip.equipArmor(true,5, laum);
                                                        exit2 = true;
                                                    }
                                                    exit2 = true;
                                                } else {
                                                    exit2 = true;
                                                }
                                            } else {
                                            equip.equipArmor(true, 5, laum);
                                            exit2 = true;
                                        }
                                    } else {
                                        exit2 = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("-----------------------\n\nLA-UM: Soon, I'll fill this space up... ");
                                    exit2 = true;
                                }
                            }
                            else if (choice2 == 8){
                                exit2 = true;
                            }
                            else {
                                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            }

                        } catch (Exception e) {
                            System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                            input.nextLine();
                        }
                    }
                } else if (choice == 5) {
                    exit1 = true;
                }
            } catch (Exception e) {
                System.out.println("-----------------------\n[LA-UM]: Huh? I think I lost my focus there... ");
                input.nextLine();
            }
        }
    }
    public void godMoves(Operations op, Gods god, Gods tempGod, Laum tempLaum){
        int indicator = op.random.nextInt(100);

        if (indicator >=  60){
            op.laumHealthReducer(laum, god.attackVar1(op),god);
        }
        else if (indicator >= 30){
            op.laumHealthReducer(laum, god.attackVar2(op), god);
        }
        else if (indicator >= 10){
            god.statusAtkVar1(e, stat, laum, tempLaum);
        } else if (indicator >= 1){
            if (god.getHp() <= (tempGod.getHp() * 0.30)){
                op.laumHealthReducer(laum, god.ultAtk(op), god);
            } else {
                op.laumHealthReducer(laum, god.attackVar2(op), god);
            }
        }
    }
    public void displayWeapon(Gods god, Gods tempGod){
        System.out.println(
                "-----------------------\nName: " + tempGod.name +
                        "\nAffiliation: "+ tempGod.aff +
                        "\nElement: " + tempGod.element +
                        "\nHP: " + god.getHp() + " / " + tempGod.getHp() +
                        "\nMana: " + god.getMana() + " / " + tempGod.getMana() +
                        "\nATK: "+ god.getAtk() + " | " + tempGod.getAtk() +
                        "\nDEF: " + god.getDef() + " | " + tempGod.getDef() +
                        "\nSPD: " + god.getSpd() + " | " + tempGod.getSpd() +
                        "\nCrit Rate:" + (god.getCrtRate() * 100) +"%" +
                        "\nCrit Damage: " + ((god.getCrtDmg() * 100)) + "%");
    }
    public void prepare(){
        String choice;

        System.out.print("-----------------------\nDo you want to prepare? (y/n): ");
        choice = input.next();

        if(choice.equalsIgnoreCase("y")){
            laumInventory(i,items,equip,laum);
        }
    }
}
class LoadSystemObjects{
    WeaponList weapon = new WeaponList();
    ArmorList armor = new ArmorList();
    Characters k = new Characters();
    Operations op = new Operations();
    StatusEffects e = new StatusEffects();
    StatListOperation stat = new StatListOperation();
    Scanner input = new Scanner(System.in);
    ItemLists items = new ItemLists();
    Interface inter = new Interface();
    GameProgress game = new GameProgress();
    File account;
    Inventory i;
    BattleSystem b;
    LoadSystemObjects(String username) throws IOException, ClassNotFoundException {
        this.account= new File (username);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(account));

        ArrayList <Object> loadData= (ArrayList<Object>) ois.readObject();

        this.i = (Inventory) loadData.get(1);
        ArmorEquip equip = new ArmorEquip(i);
        this.b = new BattleSystem(k, k.laum, k.dumakulem, weapon, armor, op,e,stat, this.i,items, input, equip, inter);
    }

}
class CreateSystemObjects{
    WeaponList weapon = new WeaponList();
    ArmorList armor = new ArmorList();
    Characters k = new Characters();
    Operations op = new Operations();
    Inventory i = new Inventory(op, k.laum, weapon);
    GameProgress game = new GameProgress();

}


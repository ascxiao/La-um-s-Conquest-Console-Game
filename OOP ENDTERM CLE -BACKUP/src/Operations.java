import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class Operations implements Serializable {
    Random random = new Random();
    boolean critical = false, hit;
    public int damageCalculator(double atk, double power, double crtRate, double crtDmg, double luck, boolean hits) {
        double damage;
        if (hits) {
            if (critical(crtRate, luck)) {
                damage = ((0.5 * atk) + (0.75 * power)) * crtDmg;
                critical = true;
                return (int) (damage);
            } else {
                damage = (0.5 * atk) + (0.75 * power);
                critical = false;
                return (int) (damage);
            }
        } else {
            System.out.println("> Attack missed!");
            return 0;
        }
    }
    public boolean critical(double crtRate, double luck) {
        boolean crit;
        int indicator = random.nextInt(100);

        crit = indicator <= (((crtRate * 100)) * luck);
        return crit;
    }
    public boolean turnIndicator(double laumSpd, double godSpd) {
        return laumSpd > godSpd;
    }
    public void laumHealthReducer(Laum laum, int damage, Gods god) {
        
        double defense = (laum.getDef() * 0.50) + 10;
        double actualDamage = damage - defense;
        
        if (laum.getHp() > 0) {
            if(damage > defense){
            laum.setHp(laum.getHp() - (actualDamage));
            if(critical){
                System.out.println("> A critical hit! It dealt " + Math.ceil(actualDamage) + " damage");
                laum.gotHit(true);
                critical = false;
            } else {
                System.out.println("> It dealt " + Math.ceil(actualDamage));
                laum.gotHit(true);
            }
            if(laum.getHp() < 0){
                laum.setHp(0);
            }
            }
        }else{
                laum.setHp(0);
            if(critical){
                System.out.println("> A critical hit! It dealt " + Math.ceil(actualDamage) + " damage");
                laum.gotHit(true);
                critical = false;
            } else {
                System.out.println("> It dealt " + Math.ceil(actualDamage));
                laum.gotHit(true);
            }

        }
        }
    public void godHealthReducer(Gods god, int damage, Laum laum) {

        double defense = (god.getDef() * 0.50) + 10;
        double actualDamage = damage - defense;

        if ((god.getHp()-actualDamage) > 0) {
            if(damage > defense){
                god.setHp(god.getHp() - (actualDamage));
                if(critical){
                    System.out.println("> A critical hit! It dealt " + actualDamage + " damage");
                    god.gotHit(hit);
                    critical = false;
                    hit = false;
                } else {
                    System.out.println("> It dealt " + actualDamage);
                    god.gotHit(hit);
                    critical = false;
                    hit = false;

                }
            }
        }else{
            god.setHp(0);
            if(critical){
                System.out.println("> A critical hit! It dealt " + actualDamage + " damage");
                god.gotHit(hit);
                critical = false;
                hit = false;

            } else {
                System.out.println("> It dealt " + actualDamage);
                god.gotHit(hit);
                critical = false;
                hit = false;

            }

        }
    }
    public void laumHealthAdder(Laum laum, double h, double x) {
        int heal = (int)(h);
        int maxHealth = (int)(x);

        if (laum.getHp() < maxHealth) {
            laum.setHp(laum.getHp() + heal);
            System.out.println("> [LA-UM]: Regained " + Math.ceil(heal) + " HP");
            if(laum.getHp() > maxHealth){
                laum.setHp(maxHealth);
            }
        }
    }
    public void laumManaAdder(Laum laum, double m, double x) {
        int mana = (int)(m);
        int maxMana = (int)(x);

        if (laum.getMana() < maxMana) {
            laum.setMana(laum.getMana() + mana);
            System.out.println("> [LA-UM]: Regained " + m + " Mana");
            if(laum.getMana() > maxMana){
                laum.setHp(maxMana);
            }
        }
    }
    public boolean moveHits(double acc, double luck){
        int indicator = random.nextInt(100);

        return hit = indicator <= (((acc * 100)) * luck);
    }
    public boolean escapes(double laumSpd, double godSpd){
        int indicator = random.nextInt(100);

        boolean escape = indicator <= ((((laumSpd)/godSpd * 100)));
        return escape;

    }
    }
class StatListOperation {
    private int laumTurns = 4;
    private int godTurns = 4;
    private int drownedTurns = 5;
    private boolean drowned = false;

    Random random = new Random();

    public void laumLvlUp(boolean lvlCleared, Laum laum) {
        if (lvlCleared) {
            laum.setLvl((laum.getLvl()) + 5);
            laum.setHp(laum.getHp() * 1.20);
            laum.setAtk(laum.getAtk() * 1.10);
            laum.setMana(laum.getMana() * 1.2);
            laum.setDef(laum.getDef() * 1.10);
            laum.setSpd(laum.getSpd() * 1.05);
            laum.setCrtRate(laum.getCrtRate() * 1.20);
            laum.setCrtDmg(laum.getCrtDmg() * 1.03);
            laum.setManaReg(laum.getManaReg() + 2);
        } else {
            laum.setHp(laum.getHp() * 0.99);
            laum.setAtk(laum.getAtk() * 0.99);
            laum.setMana(laum.getMana() * 0.99);
            laum.setDef(laum.getDef() * 0.99);
            laum.setCrtRate(laum.getCrtRate() * 0.99);
            laum.setCrtDmg(laum.getCrtDmg() * 0.99);
        }
    }
    public void laumStatusEffect(StatusEffects e,String element, Laum laum) {
        if (Objects.equals(element, "Natura")) {
            laum.setSpd(e.trapped(laum.getSpd()));
            laum.setStatus("TRAPPED");
        } else if (Objects.equals(element, "Pluvia")) {
            laum.setAcc(e.misted1(laum.getAcc()));
            laum.setStatus("MISTED");
        } else if (Objects.equals(element, "Populi")) {
            laum.setAtk(e.weaken1(laum.getAtk()));
            laum.setStatus("WEAKENED");
        } else if (Objects.equals(element, "Fulmen")) {
            laum.setSpd(e.prlyz1(laum.getSpd()));
            laum.setStatus("PARALYZED");
        } else if (Objects.equals(element, "Aequor")) {
            drowned = true;
            decayingHealth(laum);
            laum.setStatus("DROWNING");
        } else if (Objects.equals(element, "Divinus")) {
            int indicator = random.nextInt(5);

            switch (indicator) {
                case 1 -> {
                    laum.setSpd(e.trappedDivine(laum.getSpd()));
                    laum.setStatus("TRAPPED");
                }
                case 2 -> {
                    laum.setAcc(e.mistedDivine(laum.getAcc()));
                    laum.setStatus("MISTED");
                }
                case 3 -> {
                    laum.setAtk(e.weakenDivine(laum.getAtk()));
                    laum.setStatus("WEAKENED");
                }
                case 4 -> {
                    laum.setSpd(e.prlyzDivine(laum.getSpd()));
                    laum.setStatus("PARALYZED");
                }
                case 5 -> {
                    drowned = true;
                    decayingHealth(laum);
                    laum.setStatus("DROWNING");
                }
            }
        }
    }
    public void godStatusEffect(StatusEffects e, String element, Gods god, Gods statGod) {
        if (Objects.equals(element, "Populi")) {
            statGod.setAtk(statGod.getAtk());
            god.setAtk(e.weaken2(god.getAtk()));
            god.setStatus("WEAKENED");
        } else if (Objects.equals(element, "Fulmen")) {
            statGod.setSpd(god.getSpd());
            god.setSpd(e.prlyz2(god.getSpd()));
            god.setStatus("PARALYZED");
        } else if (Objects.equals(element, "Pluvia")) {
            statGod.setAcc(god.getAcc());
            god.setAcc(e.misted2(god.getAcc()));
            god.setStatus("MISTED");
        }
    }
    public void laumVitalize(StatusEffects e, Laum laum, Laum tempLaum, Operations op, double def, double hp){
        op.laumHealthAdder(laum, e.vitalize(def, hp),tempLaum.getHp());
    }
    public void laumShielded(StatusEffects e, Laum laum, Laum tempLaum, Operations op, double def, double hp){
        op.laumHealthAdder(laum, e.shielded(def, hp),tempLaum.getHp());
    }
    public void decayingHealth(Laum laum){
        if (drowned && (drownedTurns > 0)) {
            laum.setHp(laum.getHp() * 0.95);
            System.out.println("A sudden feeling of suffocation overwhelmed your senses. You are losing health.");
        } else {
            drownedTurns = 5;
            drowned = false;
        }
    }
    public void laumStatusTurns(Laum laum, Laum tempLaum) {
        if (!(laum.getStatus().equals(""))) {
            if (laumTurns > 0) {
                laumTurns--;
            } else {
                laumStatusReset(laum, tempLaum);
            }
        }
    }
    public void godStatusTurns(Gods god, Gods tempGod) {
        if (!(god.getStatus().equals(""))) {
            if (godTurns > 0) {
                godTurns--;
            } else {
                godStatusReset(god, tempGod);
            }
        }
    }
    public void laumStatusReset(Laum laum, Laum tempLaum){
        laumTurns = 3;
        laum.setStatus("");

        System.out.println("-----------------------\n[LAUM]: Your body regained its normal state...");

        laum.setAtk(tempLaum.getAtk());
        laum.setDef(tempLaum.getDef());
        laum.setMana(tempLaum.getMana());
        laum.setCrtRate(tempLaum.getCrtRate());
        laum.setCrtDmg(tempLaum.getCrtDmg());
        laum.setAcc(tempLaum.getAcc());
        laum.setSpd(tempLaum.getSpd());
    }
    public void godStatusReset(Gods god, Gods tempGod){
        godTurns = 3;
        god.setStatus("");

        god.setAtk(tempGod.getAtk());
        god.setDef(tempGod.getDef());
        god.setMana(tempGod.getMana());
        god.setCrtRate(tempGod.getCrtRate());
        god.setCrtDmg(tempGod.getCrtDmg());
        god.setAcc(tempGod.getAcc());
        god.setSpd(tempGod.getSpd());
    }
    public void laumResetStats(Laum laum, Laum tempLaum){
            laumTurns = 3;
            drownedTurns = 5;
            drowned = false;
            laum.setStatus("");

            System.out.println("-----------------------\n[LAUM]: Your body regained its normal state...");

            laum.setHp(tempLaum.getHp());
            laum.setAtk(tempLaum.getAtk());
            laum.setDef(tempLaum.getDef());
            laum.setMana(tempLaum.getMana());
            laum.setCrtRate(tempLaum.getCrtRate());
            laum.setCrtDmg(tempLaum.getCrtDmg());
            laum.setAcc(tempLaum.getAcc());
            laum.setSpd(tempLaum.getSpd());
            laum.setStatus(tempLaum.getStatus());
    }
    public void godResetStats(Gods god, Gods tempGod){
            godTurns = 3;
            god.setStatus("");
                god.setHp(tempGod.getHp());
                god.setAtk(tempGod.getAtk());
                god.setDef(tempGod.getDef());
                god.setMana(tempGod.getMana());
                god.setCrtRate(tempGod.getCrtRate());
                god.setCrtDmg(tempGod.getCrtDmg());
                god.setAcc(tempGod.getAcc());
                god.setSpd(tempGod.getSpd());
                god.setStatus(tempGod.getStatus());
    }
    public void laumStatusHit(StatusEffects e, double acc, double luck, String aff, Laum laum, Laum tempLaum) {
        Random random = new Random();
        int indicator = random.nextInt(100);

        if (indicator <= ((acc * 100) * luck)) {
            if (laum.getStatus() == "") {
                laumStatusEffect(e, aff, laum);
                laumStatusTurns(laum, tempLaum);
            } else {
                System.out.println("> Attack failed. Your vitality stopped the negative effect.");
            }
        } else {
            System.out.println("> Attack missed!");
        }
    }
    public void godStatusHit(StatusEffects e, double acc, double luck, String element, String status, Gods god, Gods statGod, boolean hit) {
        Random random = new Random();
        int indicator = random.nextInt(100);

        if (hit) {
            if (indicator <= ((acc * 100) * luck)) {
                if (status == "") {
                    godStatusEffect(e, element, god, statGod);
                    godStatusTurns(god, statGod);
                } else {
                    System.out.println("> Divinity did not allow the negative effects to pierce through the god's ethereal body again...");
                }
            }
        }
    }
    public void manaRegenerate(Laum laum, Laum tempLaum){
        if(laum.getMana() < tempLaum.getMana()){
            laum.setMana(laum.getMana() + laum.getManaReg());
            if (laum.getMana() > tempLaum.getMana()){
                laum.setMana(tempLaum.getMana());
            }
        }
    }
    public int getLaumTurns() {
        return laumTurns;
    }
}
class StatusEffects {

    public double trapped(double spd){
        System.out.println("You are trapped! Your Speed is lowered.");
        return spd * 0.85;
    }
    public double trappedDivine(double spd){
        System.out.println("You are trapped in divine chains! Your Speed has drastically lowered.");
        return spd * 0.70;
    }

    public double misted1(double acc){
        System.out.println("> Mist engulfed the area! Your vision is blurred.");
        return acc * 0.90;
    }

    public double mistedDivine(double acc){
        System.out.println("> Divine clouds engulfed the area! Your vision is extremely blurred.");
        return acc * 0.80;
    }
    public double misted2(double acc){
        System.out.println("> Mist engulfed the opponent's area! Their vision is blurred.");
        return acc * 0.90;
    }


    public double weaken1(double atk){
        System.out.println("> You feel a sudden deterioration of vitality! Your Attack is lowered.");
        return atk * 0.90;
    }

    public double weakenDivine(double atk){
        System.out.println("> You feel your muscles have been blown to the core! Your Attack is extremely lowered.");
        return atk * 0.70;
    }

    public double weaken2(double atk){
        System.out.println("> Your opponent felt a sudden deterioration of their vitality! Your opponent's Attack is lowered.");
        return atk * 0.90;
    }
    public double prlyz1(double spd){
        System.out.println("> Parts of your body became paralyzed! Your Speed is harshly lowered.");
        return spd * 0.70;
    }


    public double prlyzDivine(double spd){
        System.out.println("> Your whole body became paralyzed! Your Speed is extremely lowered.");
        return spd * 0.45;
    }

    public double prlyz2(double spd){
        System.out.println("> Parts of your opponent's body became paralyzed! Your opponent's Speed is harshly lowered.");
        return spd * 0.70;
    }


    public double shielded(double def, double hp){
        System.out.println("> You are ready to brace the attack!...");
        return ((((hp/2)*def)/50)+20);
    }
    public double vitalize(double def, double hp){
        System.out.println("> You feel the tranquil waters flow through your veins...");
        return ((((hp/2)*def)/50)+50);
    }

}
class Interface {
    public void displayHealthBar(double currentHealth, double maxHealth, String name, int lvl, String status) {
        int healthBarLength = 20;
        int filledLength = (int) Math.ceil(currentHealth / maxHealth * healthBarLength);

        System.out.print(name + " (LVL " + lvl + "): [");
        for (int i = 0; i < healthBarLength; i++) {
            if (i < filledLength) {
                System.out.print("#");
            } else {
                System.out.print("-");
            }
        }
        System.out.println("] " + Math.ceil(currentHealth) + "/" + Math.ceil(maxHealth) + " | [" + status + "]");
    }
}




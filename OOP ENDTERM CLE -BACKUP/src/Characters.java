import java.io.Serializable;

public class Characters {
    Laum laum = new Laum("La-um",5,200,100,150,110,110,1,0.15,1.12,1,10, "");

    Gods dumakulem = new Gods(
            "Dumakulem", "Forest Deity of the Mountains", "Natura",
            10,300,130,200,50,105,1,0.05,1.75, 0.90,
            "Timber's Quake","Foliage Frenzy","Overgrowth","Mountain Splitter",
            20, 18,1,75,
            "");
    Gods anitun = new Gods(
            "Anitun Tabu", "Goddess of Rain", "Pluvia",
            15, 450,120, 300,90,130,1,0.32, 1.25, 0.95,
            "Rain Fall","Raindrop Barrage","Mist","Typhoon Dance",
            40,60,0.75,85,
            "");
    Gods idianale = new Gods(
            "Idianale", "Civil Deity of Justice and Labor", "Populi",
            20,500,140,250,90,100,1.5, 0.26,1.40, 0.95,
            "Cries of Injustice","Appeal of the Marginalized","Deafening Outcries","Death Penalty",
            55,60,1,100,
            "");
    Gods habagat = new Gods(
            "Habagat", "God of the Northern Storm", "Fulmen",
            25, 550, 180,300,50,180,0.9, 0.15,1.5, 0.85,
            "Thunderbolt","Forked Lightning", "Thunder Wave","The Eye of the Storm",
            60, 65, 0.65,90,
            "");
    Gods sinaya = new Gods(
            "Aman Sinaya", "Primordial Deity of the Ocean", "Aequor",
            30,600,175,500,130,140,1,0.18,1.30, 0.98,
            "Crashing Waves","Abysmal Trench", "Drown", "Wrath of the Primordial Waters",
            65, 70, 0.6, 100,
            "");
    Gods bathala = new Gods(
            "Bathala", "Supreme God of the Amandig Pantheon", "Divinus",
            40,700,210, 350,100, 150,0.95,0.39,1.45, 95,
            "Divine Intervention", "Divine Retribution", "Karma","Wrath of an Omnipotent Being",
            65,75, 0.7,100,
            "");

}
class Laum extends CharacterStats implements BattleDialogue, Serializable {

    private double manaReg;
    Laum(String name, int lvl, double hp, double atk, double mana, double def, double spd, double luck, double crtRate, double crtDmg, double acc, double manaReg, String status) {
        super(name, lvl, hp, atk, mana, def, spd, luck, crtRate, crtDmg, acc, status);
        this.manaReg = manaReg;
    }

    Laum(){
        super();
    }

    public Laum(Laum e) {
        super(e.name, e.getLvl(), e.getHp(), e.getAtk(), e.getMana(), e.getDef(), e.getSpd(), e.getLuck(), e.getCrtRate(), e.getCrtDmg(), e.getAcc(), e.getStatus());
        this.manaReg = e.manaReg;
    }

    public double getManaReg() {
        return manaReg;
    }

    public void setManaReg(double manaReg) {
        this.manaReg = manaReg;
    }

    @Override
    public void encounter() {
        int indicator = random.nextInt(2);
        if (indicator == 1) {
            System.out.println("> LA-UM: You dare approach me? Let's see how long can you last...");
        } else {
            System.out.println("> LA-UM: Another burden encountered...");
        }

    }
    @Override
    public void gotHit(boolean hit) {
        if(hit) {
            int indicator = random.nextInt(2);
            if (indicator == 1) {
                System.out.println("> LA-UM: Hu-ahhh, t-that was weak...");
            } else {
                System.out.println("> LA-UM: ARGH!!");
            }
        }
    }
    @Override
    public void enemyDefeat() {
        int indicator = random.nextInt(2);
        if (indicator == 1) {
            System.out.println("> LA-UM: Just as I anticipated...");
        } else {
            System.out.println("> LA-UM: The gods are powerful and so am I...");        }

    }
    @Override
    public void death() {
        System.out.println("> LA-UM: N-no! The g-gods have w-w-won...");
    }

}
class Gods extends CharacterStats implements BattleDialogue, GodActions{
    String aff, element;
    String move1, move2, move3, move4;
    int power1, power2, power4;
    double acc;
    Gods(String name, String aff, String element,
         int lvl, double hp, double atk, double mana, double def, double spd, double luck, double crtRate, double crtDmg, double acc,
         String move1, String move2, String move3, String move4,
         int power1, int power2, double acc2, int power4,
         String status)
    {
        super(name, lvl, hp, atk, mana, def, spd, luck,crtRate,crtDmg, acc, status);
        this.aff = aff;
        this.element = element;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        this.power1 = power1;
        this.power2 = power2;
        this.acc = acc2;
        this.power4 = power4;
    }

    Gods(){
        super();
    };

    public Gods(Gods e) {
        super(e.name, e.getLvl(), e.getHp(), e.getAtk(), e.getMana(), e.getDef(), e.getSpd(), e.getLuck(), e.getCrtRate(), e.getCrtDmg(), e.getAcc(), e.getStatus());
        this.aff = e.aff;
        this.element = e.element;
    }

    @Override
    public void encounter() {
        int indicator = random.nextInt(2);
        if (indicator == 1) {
            System.out.println("> You are challenging " + name + ", the " + aff + "...");
        } else {
            System.out.println("> " + name + ", " + "the " + aff + ", has shown aggressiveness towards you...");
        }
    }

    @Override
    public void gotHit(boolean hit) {
        if(hit) {
            int indicator = random.nextInt(2);
            if (indicator == 1) {
                System.out.println("> " + name + " is unpleased with your actions. Beware...");
            } else {
                System.out.println("> " + name + " is shocked with your strength!..");
            }
        }
    }

    @Override
    public void enemyDefeat() {
        int indicator = random.nextInt(2);
        if (indicator == 1) {
            System.out.println("> " + name + " turned around and did not look back...");
        } else {
            System.out.println("> " + name + " is unfazed to see your failing body...");
        }

    }

    @Override
    public void death() {
        System.out.println("> " + name + "'s body crumbles into dust as their expression of disbelief is immobilized in their faces...");
    }

    @Override
    public int attackVar1(Operations op) {
        System.out.println("-----------------------\n> " + name + " unleashed " + move1);
        return op.damageCalculator(getAtk(),power1,getCrtRate(),getCrtDmg(),getLuck(), op.moveHits(getAcc(), getLuck()));
    }

    @Override
    public int attackVar2(Operations op) {
        System.out.println("-----------------------\n> " + name + " attacked with " + move2);
        return op.damageCalculator(getAtk(),power2,getCrtRate(),getCrtDmg(),getLuck(), op.moveHits(getAcc(), getLuck()));
    }

    @Override
    public void statusAtkVar1(StatusEffects e, StatListOperation stat, Laum laum, Laum tempLaum) {
        System.out.println("-----------------------\n> " + name + " used " + move3 );
        stat.laumStatusHit(e, acc, getLuck(), element, laum, tempLaum);
    }

    @Override
    public int ultAtk(Operations op) {
        System.out.println("-----------------------\n> " + name + " did not hesitate and revealed " + move4);
        return op.damageCalculator(getAtk(),power4,getCrtRate(),getCrtDmg(),getLuck(), op.moveHits(getAcc(), getLuck()));
    }
}




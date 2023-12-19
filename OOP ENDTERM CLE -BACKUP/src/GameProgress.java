import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameProgress {

    Scanner game = new Scanner(System.in);
    public void loadLaumStats(int lvl, Laum laum) {
        switch (lvl) {
            case 2 -> {
                laum.setLvl((laum.getLvl()) + 5);
                laum.setHp(laum.getHp() * 1.20);
                laum.setAtk(laum.getAtk() * 1.10);
                laum.setMana(laum.getMana() * 1.2);
                laum.setDef(laum.getDef() * 1.10);
                laum.setSpd(laum.getSpd() * 1.05);
                laum.setCrtRate(laum.getCrtRate() * 1.20);
                laum.setCrtDmg(laum.getCrtDmg() * 1.03);
                laum.setManaReg(laum.getManaReg() + 2);
            }
            case 3 -> {
                laum.setLvl((laum.getLvl()) + 10);
                laum.setHp(laum.getHp() * Math.pow(1.20, 2));
                laum.setAtk(laum.getAtk() * Math.pow(1.10, 2));
                laum.setMana(laum.getMana() * Math.pow(1.2, 2));
                laum.setDef(laum.getDef() * Math.pow(1.10, 2));
                laum.setSpd(laum.getSpd() * Math.pow(1.05, 2));
                laum.setCrtRate(laum.getCrtRate() * Math.pow(1.20, 2));
                laum.setCrtDmg(laum.getCrtDmg() * Math.pow(1.03, 2));
                laum.setManaReg(laum.getManaReg() + 4);
            }
            case 4 -> {
                laum.setLvl((laum.getLvl()) + 15);
                laum.setHp(laum.getHp() * Math.pow(1.20, 3));
                laum.setAtk(laum.getAtk() * Math.pow(1.10, 3));
                laum.setMana(laum.getMana() * Math.pow(1.2, 3));
                laum.setDef(laum.getDef() * Math.pow(1.10, 3));
                laum.setSpd(laum.getSpd() * Math.pow(1.05, 3));
                laum.setCrtRate(laum.getCrtRate() * Math.pow(1.20, 3));
                laum.setCrtDmg(laum.getCrtDmg() * Math.pow(1.03, 3));
                laum.setManaReg(laum.getManaReg() + 6);
            }
            case 5 -> {
                laum.setLvl((laum.getLvl()) + 20);
                laum.setHp(laum.getHp() * Math.pow(1.20, 4));
                laum.setAtk(laum.getAtk() * Math.pow(1.10, 4));
                laum.setMana(laum.getMana() * Math.pow(1.2, 4));
                laum.setDef(laum.getDef() * Math.pow(1.10, 4));
                laum.setSpd(laum.getSpd() * Math.pow(1.05, 4));
                laum.setCrtRate(laum.getCrtRate() * Math.pow(1.20, 4));
                laum.setCrtDmg(laum.getCrtDmg() * Math.pow(1.03, 4));
                laum.setManaReg(laum.getManaReg() + 8);
            }
            case 6 -> {
                laum.setLvl((laum.getLvl()) + 25);
                laum.setHp(laum.getHp() * Math.pow(1.20, 5));
                laum.setAtk(laum.getAtk() * Math.pow(1.10, 5));
                laum.setMana(laum.getMana() * Math.pow(1.2, 5));
                laum.setDef(laum.getDef() * Math.pow(1.10, 5));
                laum.setSpd(laum.getSpd() * Math.pow(1.05, 5));
                laum.setCrtRate(laum.getCrtRate() * Math.pow(1.20, 5));
                laum.setCrtDmg(laum.getCrtDmg() * Math.pow(1.03, 5));
                laum.setManaReg(laum.getManaReg() + 10);
            }
        }

    }

    public void loadGameProgress(int lvl, LoadSystemObjects sys, AccountOperations a) throws IOException, ClassNotFoundException {
        sys.b.victory = false;
        sys.b.gameExit = false;

        switch (lvl) {
            case 0 -> System.out.println("-----------------------\n> You must log in...\n-----------------------");
            case 1 -> {
                while (!sys.b.gameExit) {
                    gameIntroduction();
                    dumakulemArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.dumakulem, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    anitunArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.anitun, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    idianaleArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.idianale, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    habagatArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.habagat, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    sinayaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.sinaya, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    bathalaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.bathala, a);
                    }
                    gameEnding();
                    sys.b.gameExit = true;
                }
            }
            case 2 -> {
                while (!sys.b.gameExit) {
                    loadLaumStats(2, sys.k.laum);
                    anitunArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.anitun, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    idianaleArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.idianale, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    habagatArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.habagat, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    sinayaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.sinaya, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    bathalaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.bathala, a);
                    }
                    gameEnding();
                    sys.b.gameExit = true;
                }
            }
            case 3 -> {
                while (!sys.b.gameExit) {
                    loadLaumStats(3, sys.k.laum);
                    idianaleArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.idianale, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    habagatArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.habagat, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    sinayaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.sinaya, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    bathalaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.bathala, a);
                    }
                    gameEnding();
                    sys.b.gameExit = true;
                }
            }
            case 4 -> {
                while (!sys.b.gameExit) {
                    loadLaumStats(4, sys.k.laum);
                    habagatArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.habagat, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    sinayaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.sinaya, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    bathalaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.bathala, a);
                    }
                    gameEnding();
                    sys.b.gameExit = true;
                }
            }
            case 5 -> {
                while (!sys.b.gameExit) {
                    loadLaumStats(5, sys.k.laum);
                    sinayaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.sinaya, a);
                    }
                    if (sys.b.gameExit) {
                        break;
                    } else {
                        sys.b.victory = false;
                    }
                    bathalaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.bathala, a);
                    }
                    gameEnding();
                    sys.b.gameExit = true;
                }
            }
            case 6 -> {
                while (!sys.b.gameExit) {
                    loadLaumStats(6, sys.k.laum);
                    bathalaArc();
                    while (!sys.b.victory) {
                        sys.b.prepare();
                        sys.b.battle(sys.k.bathala, a);
                    }
                    gameEnding();
                    sys.b.gameExit = true;
                }
            }
        }
    }

    public void gameIntroduction() {

            slowPrint("""
                    -----------------------
                    [STORY]: After knowing the arcane secret of defeating the gods,
                            La-um, who is thirsty for glory, embarks himself to a journey to
                            defeat every god in Amandig and gain their powers...
                            """
            );
    }
    public void dumakulemArc(){
        System.out.print("Go through story? (y/n): ");
        String choice = game.nextLine();

        if(choice.equalsIgnoreCase("y")) {
            slowPrint("""
                    -----------------------
                    [STORY]: La-um entered the Great Narra Forest and witnessed the majestic
                            trees reaching the skies with the width so wide that the clearing is
                            basically invisible. However, dark scratches and withering were noticeable
                            with these trees — signs of negligence.
                            
                            He encountered Dumakulem, the Forest Deity of the Mountains...
                            
                    [DUMAKULEM]: What are you doing here, tao?
                            
                    [LA-UM]: I am here to defeat you, god!
                            
                    [DUMAKULEM]: Defeat me? Quit your foolishness, I have more important things to do...
                            
                    [LA-UM]: Funny, what are these important things and why are is it not the welfare
                            of this forest?
                            
                    [DUMAKULEM]: Excuse me?
                            
                    [LA-UM]: The trees everywhere are dying and you are not doing anything! If you are
                            such a powerful god and the protector of this realm then why are you helping?!
                            
                    [DUMAKULEM]: Challenging me is one thing, but insulting my divinity is another, boy! Prepare
                            to get punished by the wrath of the forest and mountains!
                    -----------------------""");
        }
    }
    public void anitunArc(){
        System.out.print("Go through story? (y/n): ");
        String choice = game.nextLine();

        if(choice.equalsIgnoreCase("y")) {
            slowPrint("""
                    -----------------------
                    [STORY]: La-um defeated the forest deity and successfully claimed the power of Natura in the form of an axe.
                            La-um swinged his axe to the ground creating a great shockwave. Right in the spot of impact, trickling foliage
                            starts to appear that seems to heal the forest. The Great Narra Forest is now healed.
                            
                            La-um headed north to the mountains. As he climbs higher to the mountain, rain starts to become stronger and
                            stronger. When La-um reached the plateau, he saw rain from all directions that covers the whole Ambong Valley.
                            
                            La-um yelled to the skies.
                            
                    [LA-UM]: What are you doing, Tabu?! Ambong Valley is your domain and you are flooding it!
                            
                    [LA-UM]: How can you be so incapable of your own people!?
                            
                            A sudden downpour of sharp raindrops suddenly dropped behind La-um as if it was a threat...
                            
                    [ANITUN TABU]: Human, how dare you insult me? I am the divine god of rain and rain is what I do. Do not question
                            my actions.
                            
                    [LA-UM]: You are killing everyone! What purpose does this yield?
                            
                    [ANITUN TABU]: I said DO NOT QUESTION ME!!
                    -----------------------""");
        }

    }
    public void idianaleArc(){
        System.out.print("Go through story? (y/n): ");
        String choice = game.nextLine();

        if(choice.equalsIgnoreCase("y")) {
            slowPrint("""
                    -----------------------
                    [STORY]: Anitun Tabu's dust suddenly collated in the air. Suddenly, it glowed
                             and turned into bubbles of water which merged into one. The bubble glowed
                             and a silhouette of a bow appeared. La-um received the divine manifestation
                             of Pluvia.
                                    
                             La-um used the bow and shot the sky which ironically stopped the rain. Ambong Valley
                             is now safe from inevitable flood.
                                    
                             La-um headed west. He encountered various terrains like jungles and mountains,
                             until he reached signs of agriculture. He passed by a farmer who is sweating from
                             labor which later on was getting screamed at by another person with high quality robes.
                                    
                    [LA-UM]: Huh? He looks like he's enslaving him...
                            
                             La-um whispered to himself. He continued on, as he approach the walls of a very
                             large and populated city, he witnessed more signs of abuse from employers.
                                    
                             An employer with luscious green robes is arguing with a lady who managed to spill
                             water to his pants which appears to be gray from being wet compared to its bright white
                             fabric.
                                    
                    [EMPLOYER]: YOU BETTER DO YOU JOB RIGHT! LOOK AT WHAT YOU'VE DONE!
                            
                    [LADY]: I-I'm sorry sir, let me f-fix that f-for you...
                            
                             As the lady approach, the employer lifted his hand which looks like he is about to hit the
                             lady. La-um, already sick with the mistreatment, decided to jump in and hold the employer's
                             hand.
                                    
                    [LA-UM]: Drop the hand.
                            
                    [EMPLOYER]: WHAT?! WHO ARE YOU?
                            
                    [LA-UM]: Why are you treating this lady like she's some slave?
                            
                    [EMPLOYER]: Some slave? SHE IS A SLAVE! And who are you?? Don't you know you are in the Great City
                                of Indapo?
                                        
                    [LA-UM]: Indapo? The Central City of Justice? Idianale is closely monitoring this place. This is her
                             domain.
                                    
                    [EMPLOYER]: Well boohoo! The "Idianale" that you mentioned has been long gone! We haven't seen her
                                for decades! We can do whatever we want!
                                        
                             Upon hearing those words, La-um fell silent. Frustration built up in his face.
                             He punched the employer and rushed off deeper into the city. The employer's personal
                             guards chased La-um. La-um entered what seems to be a temple.
                                        
                             La-um entered Indapo Temple, the most sacred hall of justice and labor.
                                        
                    [LA-UM]: WHAT IS WRONG WITH YOU GODS?!
                            
                             La-um yelled at the empty hall.
                            
                    [LA-UM]: First, Dumakulem who was abandoning the withering trees of the enchanted narra forest.
                             Then Anitun Tabu, who was purposely allowing her domain to rain endlessly without a care if
                             it will flood. Then YOU! Out of all gods, I thought you would be reasonable.
                             The Goddess of Justice! Abandoning her people to anarchy. Tell me?! What are you doing?!
                                    
                             The hall allowed the echoes of La-um's voice to bounce around the walls...
                                        
                    [LA-UM]: I was challenging the gods for glory but now that I've seen the gods being so blinded
                             and incompetent... I think it's better that I finished them off... From here then on, I
                             will serve the people that the gods have chosen to abandon...
                                    
                    [IDIANALE]: How cute... Too bad you're being a hypocrite...
                            
                             Idianale suddenly appeared from the shadows of the hall.
                                        
                    [IDIANALE]: Serving the people yet killing their gods? The gods that provide them sustenance? Tsk...
                            
                    [LA-UM]: AND A GOD WHO'S LETTING THEIR PEOPLE RUIN THEIR DOMAIN? HOW BETTER CAN YOU BE?!
                            
                    [IDIANALE]: Boy, I do not want this. Yet, I have to for the greater good which you don't understand
                            
                    [LA-UM]: DECADES! Decades of absence! How great can this greater good be?!
                            
                    [IDIANALE]: SILENCE!
                            
                            Idianale rung her bell and silence fell to the hall.
                                        
                    [LA-UM]: TSK! Eat your greater good! I'll just finish you off!!
                    -----------------------""");
        }
    }
    public void habagatArc(){
        System.out.print("Go through story? (y/n): ");
        String choice = game.nextLine();

        if(choice.equalsIgnoreCase("y")) {
            slowPrint("""
                    -----------------------
                    [STORY]: As Idianale turns into the dust, the sacred bell that she proudly carry fell on to the ground
                            The god's dust remains collated and turned into a ball of light. The bell suddenly floated which
                            the two merged becoming a whip. La-um received the Populi's manifestation of power.
                            
                            La-um leaves the temple. As he steps outside, he witnessed the chaos that started earlier. Various
                            guards searching for La-um. A guard saw him which he alerted the other guards.
                            
                            When they came running to La-um, La-um used the whip to ring the bell which echoed throughout the
                            city. The people start to behave calmly and started to arrange themselves. The employers started
                            to gather in the center of city which chains magically appeared around their hands. They are now
                            arrested by the people for abuse.
                            
                            La-um left the city as noise of rejoice became background echoes.
                            
                            La-um wishes to go to the oceans but the shortest way was to go over the mountains
                            
                            La-um climbed the Umaambong Peaks and as he reached the summit. He decided to rest for the night.
                            
                            La-um woke up from the crackles of thunder and lightning that surrounds the mountain
                            
                    [HABAGAT]: SHOW YOURSELF, COWARD!
                            
                            Habagat, the God of the Northern Storms, appeared in the vicinity.
                            
                    [HABAGAT]: You have killed my wife, Anitun Tabu! And other gods! Prepare for vengeance you blasphemous
                            dog!
                            
                            La-um, startled with the sudden presence of another god, prepared himself. He stepped outside and yelled
                            
                    [LA-UM]: I am he--
                            
                            A suddent bolt of lightning hit the spot 3 feet beside La-um which startled him...
                            
                    [HABAGAT]: QUIT YOUR YAPPING AND PREPARE TO FACE THE CONSEQUENCES OF MESSING THE SKY GODS!
                    -----------------------"""
            );
        }
    }
    public void sinayaArc(){
        System.out.print("Go through story? (y/n): ");
        String choice = game.nextLine();

        if(choice.equalsIgnoreCase("y")) {
            slowPrint("""
                    -----------------------
                    [STORY]: Habagat managed to delay his disappearance.
                            
                    [HABAGAT]:H-how can I be defeated... I will find you a-again!
                            
                            Habagat continued to disintegrate. His dust suddenly joined one another which it was struck by lightning
                            The dust glowed into a shape of a trident. La-um received the weapon that embodies Fulmen.
                            
                            La-um raised the trident when a burst of lightning struck it. The storm clouds suddenly disappeared.
                            
                            La-um went down the mountain. As he trekked down, he keeps thinking the final words of Habagat.
                            
                    [LA-UM]: I will find you... But aren't they dead?
                            
                            La-um whispered to himself...
                            
                            La-um arrived to the Kadaragtang Shores. He discovered the rough waves and treacherous waters crashing
                            down the shoreline. The residents look like they're starving and struggling from the condition. La-um
                            approached one man.
                            
                    [LA-UM]: Sir, are your people alright?
                            
                    [MAN]: We're barely surviving. Food has never been this hard to get ever since the waves started to become cruel.
                            
                    [LA-UM]:This is the seas... This is supposed to be Aman Sinaya's domain... She shouldn't allow this...
                            
                    [MAN]: Alas she did... We are offering her various sacrifices and gifts to calm the seas but to no avail...
                            
                    [LA-UM]: Don't worry, I'll fix this.
                            
                            La-um swiftly ran to the port. He rented a boat which he used to embark to the middle of the Kadaragtang
                            Seas. Fortunately, with the power of Natura, Pluvia, and Fulmen, he was able to venture with no problems
                            from the unkind waves.
                            
                            He arrived to shallow waters in the middle of the sea. There seems to be a pool of pristine water in the
                            middle.
                            
                    [LA-UM]: Sinaya's Trove... The legends are true...
                            
                            La-um approaches the pool of water
                            
                    [LA-UM]: Primordial God of the Oceans. Here my call, I challenge you to skirmish that will make you fall!
                            
                            After a short while, the waves around the area grew bigger yet the shallow waters were left untouched.
                            Chaos surfaced to the ocean. Despite this, a sudden calm voide appeared.
                            
                    [AMAN SINAYA]: Why so formal after insulting the other gods before sealing them?
                            
                    [LA-UM]: Aman Sinaya... Wait, sealing them?
                            
                    [AMAN SINAYA]: Oh do you really think that the gods you defeat are dead? They are merely sealed inside your
                                    weapons.
                                    
                    [LA-UM]: What? After all this time, they still exist?
                            
                    [AMAN SINAYA]: And they are still using their powers to assist you. They are still doing their godly duties to
                                the people. You are just a mere instrument...
                            
                    [LA-UM]: What...? LIES! I saw with my own two eyes that they are being negligent! Abandoning their own domains!
                            For I don't even know how long!
                            
                    [AMAN SINAYA]: Oh dear, you don't know what's happening...
                            
                    [LA-UM]: THEN TELL ME YOU USELESS GOD! YOU, YOURSELF, A PRIMORDIAL GOD, ARE ALSO ABANDONING YOUR OWN DOMAIN!
                            
                    [AMAN SINAYA]: So feisty. I'll tell you when you defeat me.
                            
                    [LA-UM]: That better be a worthy explanation!
                    -----------------------
                            """);
        }
    }
    public void bathalaArc(){
        System.out.print("Go through story? (y/n): ");
        String choice = game.nextLine();

        if(choice.equalsIgnoreCase("y")) {
            slowPrint("""
                    -----------------------
                    [STORY]: Aman Sinaya disintegrated into dust, however, the dust gathered and turned into glowing water. The
                             water floated around La-um and formed into Aman Sinaya once again.
                             
                    [AMAN SINAYA]: Well you did defeat me, I must tell the truth then.
                             
                    [LA-UM]: How did you turn back? What is this trick??
                             
                    [AMAN SINAYA]: To clarify certain facts, when gods are defeated in a battle, they do not die. Instead, they
                                   manifest into weapons with the certain element that they embody. Gods instantly turn into these
                                   unless they have enough divine energy to retain their bodies for a while. I, for one, am a
                                   primordial god. I have more than enough divine energy to return back. However, since we are
                                   bounded by the Divine Creed, I must seal myself for you as the victor. Those who are sealed, will
                                   only be released once the vessel is broken, or the host died.
                                   
                    [LA-UM]: So these weapons, are still gods...?
                             
                    [AMAN SINAYA]: Indeed. Now, I must tell you the truth...
                             
                    [AMAN SINAYA]: Bathala are punishing the gods. Including other primordial gods. They cannot pamper their domains
                                   for 100 years and they are not allowed to say a word about this punishment. They have also been
                                   cursed to act as if nothing is wrong. This curse does not affect other primordial gods which allow
                                   me to tell you this.
                                   
                    [LA-UM]: T-This doesn't make sense! Then why are the gods allow themselves to get sealed?
                             
                    [AMAN SINAYA]: Child, you are actually helping them. They see this an opportunity to finally help their domains.
                                   They can't directly help their domains because of the punishment but the curse does not state that
                                   it includes being sealed. By being sealed, they are lending their powers to you which you use for
                                   the greater good.
                                   
                    [LAUM]: Greater good...
                             
                    [AMAN SINAYA]: That's the truth. Now I must honor the creed and seal myself for you...
                             
                                   Aman Sinaya glowed and turned into a shield. La-um held shield with gentleness. He raised the
                                   shield and the waves suddenly calmed down...
                                   
                                   La-um went back to shore and he is suddenly surrounded by residents rejoicing.
                                   
                                   However, La-um face is evidently puzzled, his mind filled with questions but one things is
                                   certain. La-um will seal Bathala.
                                   
                                   He returned to the City of Indapo. He went back to Indapo temple and sat down recalling the
                                   hidden knowledge he acquired before.
                                   
                                   He recalled the line in the book that allows him to connect with Bathala. It states "With the
                                   presence of five elements of the world, one can only strike the shield to call the last divine
                                   element."
                                   
                    [LA-UM]: Call the last divine element...
                             
                                   Natura. Pluvia. Populi. Fulmen. Aequor.
                                   
                                   Nature. Rain. People. Energy. Ocean.
                                   
                                   Axe. Bow. Whipe. Trident. Shield.
                                   
                                   Five Elements and a Shield.
                                   
                                   La-um arranged his divine weapons wherein the shield at the center while the others surrounds it.
                                   
                                   La-um hits the shield and a sudden glow emerged from all of the weapons. La-um got engulfed by
                                   light.
                                   
                                   La-um opened his eyes and he discovered himself inside Bathala's Trove, or the Sansinukob Domain.
                                   
                    [BATHALA]: I've been observing you. You've really caused quite the ruckus...
                             
                    [LA-UM]: With all due respect, I don't wanna hear what you have to say!
                             
                    [BATHALA]: Quite blasphemous, huh? Well you're quite straightforward.
                             
                    [LA-UM]: Do you think I care about blasphemy? You have chosen your own pride over the welfare of the world!
                           Punishing the gods into not caring of their own domains? What kind of foolishness is that?
                           
                    [BATHALA]: They did not tell you the whole story now did they? Not only they almost destroyed my creation, but
                               they also tried to overthrow me! After all I've done for them!
                               
                    [LA-UM]: What? Are you serious? You will compromise the world as a punishment? BULLSHIT! WE ARE NOT SOME TOY
                           THAT YOU CAN JUST GRAB AND DO WHATEVER YOU WANT?
                           
                    [BATHALA]: What? Don't you see the weight of this problem??? ha... HAHHAHAHA PUNY HUMAN?! TREAD CAREFULLY 
                               BECAUSE YOU ARE ON A VERY THIN TIGHTROPE!!
                               
                    [LA-UM]: SHUT UP SELF-CENTERED FOOL!!
                    -----------------------
                    """);
        }
    }
    public void gameEnding(){

        slowPrint("""
        -----------------------
        [STORY]: Bathala's dust instantly glowed and reformed.
        
        [BATHALA]: YOU UNGRATEFUL RAT! I HAVE ONLY DONE WHAT IS RIGHT!
        
                The weapons behind La-um's back glowed and starts to transform into their corresponding bodies
                
        [DUMAKULEM]: What is right? Letting the enchanted forest wither is right??
        
        [ANITUN TABU]: Allowing my valley to get flooded by my own rain? That seems right to you?
        
        [IDIANALE]: Getting manipulated by your lies and letting my domain turn into an anarchical dystopia?
        
        [HABAGAT]: Uncontrollable anger???
        
        [AMAN SINAYA]: Dear brother in divinity... You have proved yourself to be too prideful for your own good.
                        It is time to let the gods of their own domain, rule their domains. The age of Bathala will
                        be no more!
        
        [BATHALA]: NO! NO!!!
                    
                    Bathala suddenly glowed and turned into two blades. One representing goodness in Divinus while
                    the other represents the evil in the divine.
                    
        [AMAN SINAYA]: We thank you, La-um, for breaking us from our curse and punishment...
        
        [DUMAKULEM]: We really owe you a lot, tao
        
        [HABAGAT]: I did not expect you challenging the Supreme God
        
        [LA-UM]: It was not possible without everyone here. I apologize for all the insults and pain I've caused you...
        
        [IDIANALE]: It is mere fate. I told you, it was all for the greater good...
        
        [LA-UM]: I will now release you all!
         
                As La-um finishes his sentence, the gods glowed so brightly that it almost blinded La-um.
                The gods became their own again, however, the weapons remain.
                
        [LA-UM]: Huh? The weapons are still here...
        
        [AMAN SINAYA]: I may have hidden one detail from you. Releasing a god from a weapon with mutual understanding and
                    respect towards each other will not only release the god but it will also retain the weapon with the 
                    same power of the god that was once sealed.
                    
        [LA-UM]: I get to keep these weapons with their powers?
        
        [AMAN SINAYA]: That will make a fine trinket as a proof of glory won't it?
        
        [LA-UM]: No... The people need this... For the greater good...
        -----------------------
        PRODUCED BY:
        WAELAND STUDIOS
               
         """);
    }

    public void slowPrint(String output) {
        for (int i = 0; i<output.length(); i++) {
            char c = output.charAt(i);
            System.out.print(c);
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }
            catch (Exception e) {
            }
        }
    }

}

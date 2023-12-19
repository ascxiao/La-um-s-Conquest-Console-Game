import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AccountOperations a = new AccountOperations();
        CreateSystemObjects sys = new CreateSystemObjects();
        a.accountMenu(sys);
        LoadSystemObjects loadedSys = new LoadSystemObjects(a.username);

        sys.game.loadGameProgress(a.load(a.currentFile), loadedSys, a);
        }
    }

/*
    12/12/23 - 10:36PM
    >>> FIGURE OUT SAVE INVENTORY


    PROGRESS TRACKER:
    - Damage Calculator done
    - Status done
    - Characters instantiation done
    - Status Operations Done
    - Regular Weapons DONE
    - Inventory System DONE
        - Potions DONE
    - Armor System (Elemental Bonuses)
        - Program Flow
 */


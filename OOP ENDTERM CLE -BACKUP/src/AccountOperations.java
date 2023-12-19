import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountOperations {
    Scanner fileInput = new Scanner(System.in);
    Scanner input = new Scanner(System.in);
    File currentFile;
    Account currentAccount;
    String username;
    boolean menuExit = false;
    static class Account implements Serializable {

        private String name, username, password;
        private int lvl;

        Account(String name, String username, String password, int lvl) {
            this.name = name;
            this.username = username;
            this.password = password;
            this.lvl = lvl;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getLvl() {
            return lvl;
        }

    }
    // A serializable class that defines an account. This class is able to be stored inside a file

    public void logIn() throws IOException, ClassNotFoundException {

        Scanner loginInput = new Scanner(System.in);

        System.out.println("""
                -----------------------
                LOG IN
                -----------------------""");

        System.out.print("> Username: ");
        String username = loginInput.nextLine();
        System.out.print("> Password: ");
        String password = loginInput.nextLine();
        System.out.println("-----------------------");
        File login = new File(username);

        if (login.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(login));
            ArrayList <Object> accountArray = new ArrayList<>();
            accountArray = (ArrayList<Object>) ois.readObject();

            Account logAccount = (Account) accountArray.get(0);

            ois.close();
            if ((username.equals(logAccount.getUsername()) && (password.equals(logAccount.getPassword())))) {
                System.out.println("> Welcome back '" + username + "'!");

                this.currentFile = login;
                this.currentAccount = logAccount;
                this.username =  logAccount.getUsername();

                System.out.print("""
                        -----------------------
                        LA-UM'S CONQUEST:
                        Conflict of the Gods
                        -----------------------
                        > ENTER ANY LETTER TO START THE GAME: """);
                String anything = loginInput.nextLine();
                if (anything != null){
                    menuExit = true;
                }

            } else {
                System.out.println("> INVALID LOGIN. PLEASE TRY AGAIN!");
            }
        } else {
            System.out.println("> ACCOUNT NOT FOUND!");
        }

        System.out.println("-----------------------");
    }
    //A method that logs in the user that checks all files if it exists

    public void createAccount(CreateSystemObjects sys) throws IOException, ClassNotFoundException {
        boolean exit = false;

        System.out.println("""
                -----------------------
                CREATE ACCOUNT
                -----------------------""");
        while (!exit) {
        System.out.print("> Enter Username: ");
        String username = fileInput.nextLine();

        File file = new File(username);

            if (file.exists()) {
                System.out.println("> Username " + username + " already exists.");
                System.out.print("> Do you want continue? (y/n): ");
                String choice = input.next();
                if(choice.equalsIgnoreCase("n")){
                    exit = true;
                    menuExit = true;
                    input.reset();
                    break;
                }
            } else {
                System.out.print("> Enter Full Name: ");
                String name = input.nextLine();
                System.out.print("> Create Password: ");
                String password = input.nextLine();

                Account b = new Account(name, username, password, 1);
                Inventory i = new Inventory(sys.op, sys.k.laum, sys.weapon);

                ArrayList <Object> accountData = new ArrayList<>();

                accountData.add(b);
                accountData.add(i);

                writeFile(file, "");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

                oos.writeObject(accountData);

                System.out.println("> Account " + username + " created!");
                oos.close();
                exit = true;

                System.out.print("> Proceed to log in? (y/n): ");
                String choice = input.next();
                if(choice.equalsIgnoreCase("y")){
                    logIn();
                    input.reset();
                } else {
                    input.reset();
                    exit = true;
                }
            }
        }
    }
    //A method that creates an account or a new file

    public void save(File username, int lvl, Account login, Inventory i) throws IOException {
        System.out.println("-----------------------");
        System.out.print(">DO YOU WANT TO SAVE YOUR PROGRESS? (y/n): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(username));
            oos.reset();

            Account a = new Account(login.getName(), login.getUsername(), login.getPassword(), lvl);

            ArrayList <Object> saveAccount= new ArrayList<>();

            saveAccount.add(a);
            saveAccount.add(i);

            oos.writeObject(saveAccount);

            System.out.println("-----------------------");
            System.out.println("> PROGRESS SAVED");
            oos.close();
        } else {
            System.out.println("-----------------------");
            System.out.println("> DID NOT SAVE PROGRESS...");
        }
    }
    // A method to save the progress of the player to the account's file using file input

    public int load(File username) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username));
        ArrayList <Object> accountArray = new ArrayList<>();
        accountArray = (ArrayList<Object>) ois.readObject();

        Account logAccount = (Account) accountArray.get(0);
        return logAccount.getLvl();
    }
    // A method to load the level of progress of the player's account using file output

    public void logOut() throws IOException, ClassNotFoundException {
        try {
            File accountOut = new File("logged out");
            ObjectInputStream logOut = new ObjectInputStream(new FileInputStream(accountOut));
            Account loggedOut = (Account) logOut.readObject();

            this.currentFile = accountOut;
            this.currentAccount = loggedOut;

            System.out.println("> LOGGED OUT SUCCESSFULLY");
        } catch (Exception e){
            System.out.println("> ERROR IN LOGGING OUT");
        }
    }

    public static void writeFile(File file, String text) {
        try {
            // Create a PrintWriter object to write to the file
            PrintWriter writer = new PrintWriter(new FileWriter(file));

            // Write the text to the file
            writer.println(text);

            // Close the PrintWriter object
            writer.close();
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    // A method to append some text to a file

    public void accountMenu(CreateSystemObjects sys) throws IOException, ClassNotFoundException {
        int choice;
        Scanner input1 = new Scanner(System.in);

        System.out.println("""
                -----------------------
                WELCOME TO THE WAELAND GAMES
                -----------------------""");

        while(!menuExit){
            menuExit = false;

            System.out.print("""
                ------------------------
                Do you want to log in or create account?
                1. LOG IN
                2. CREATE ACCOUNT
                3. EXIT
                ------------------------
                Input: """);
        choice = input1.nextInt();
        switch(choice){
            case 1:
                logIn();
                break;
            case 2:
                createAccount(sys);
                break;
            case 3:
                menuExit = true;
                break;
        }
        }

    }


}


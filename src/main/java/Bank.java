import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank (String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    // generate a unique ID for the user
    public String getNewUserUUID(){
        String uuid;
        Random random = new Random();
        int len = 6;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {

            //generate the number
            uuid = "";
            for (int i = 0; i < len; i++){
                uuid += ((Integer) random.nextInt(10)).toString();
            }

            //check to make sure is unique
            nonUnique = false;
            for (User u : this.users){
                if (uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    public String getNewAccountUUID(){
        String uuid;
        Random random = new Random();
        int len = 10;
        boolean nonUnique;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10)).toString();
            }
            nonUnique = false;
            for (Account u : this.accounts){
                if (uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    public User addUser (String firstName, String lastName, String pin){

        // create a new user and add it to the users array
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        // create a savings account for the new user and add the account to the bank account list
        // add the account to the user account list
        Account newAccount = new Account("Savings",newUser,this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin){
        // search through list of users
        for (User u : users){

            // check if id and pass are correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        //if we havent found the user return null
        return null;
    }

    public void addAccount(Account aAccount){
        this.accounts.add(aAccount);
    }

    public String getName(){
        return this.name;
    }
}

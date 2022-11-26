import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;

    private String lastName;

    // the id number of the user
    private String uuid;

    // MD5 hash of the users pin number
    private byte[] pinHash;

    // the list of account of this user
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank){
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pins MD5 hash, rather than the original value, for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // getting the bytes of our pin object and digesting through our md5 algo and return a
            // different array of bytes that we are going to store in pinHash
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("error, caught NoSuchAlgorithmException");
        }

        // get a new unique universal id for the user
        this.uuid = theBank.getNewUserUUID();

        // create empty list of account;
        this.accounts = new ArrayList<Account>();

        // print a log message
        System.out.printf("New user %s %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    // add an account for the user
    // anAccount - the account to add
    public void addAccount(Account anAccount){
        this.accounts.add(anAccount);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin (String pin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("error, caught NoSuchAlgorithmException");
        }
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void printAccounstSummary(){
        System.out.printf("\n%s's accounts summary:\n", this.firstName);
        for (int i = 0; i < accounts.size(); i++){
            System.out.printf("%d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public int numsAccounts(){
        return this.accounts.size();
    }

    public void printAcctTransHistory (int index){
        this.accounts.get(index).printTransHistory();
    }

    public double getAcctBalance(int index){
        return this.accounts.get(index).getBalance();
    }

    public String getAcctUUID(int index){
        return this.accounts.get(index).getUUID();
    }

    public void addAcctTransaction(int index, double amount, String memo){
        this.accounts.get(index).addTransaction(amount,memo);
    }





}

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Account {

    private String name;

    private String uuid;

    private User holder;

    private ArrayList<Transaction> transactions;

    // name  - the name of the account
    // holder - the User object that holds this account
    // theBank - the bank that issued this account
    public Account (String name, User holder, Bank theBank){

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        // get new accound UUID
        this.uuid = theBank.getNewAccountUUID();

        // initialize the tranzations
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID(){
        return this.uuid;
    }

    public String getSummaryLine(){

        // get the balance account
        double balance = this.getBalance();

        if (balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance(){
        double balance = 0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory(){
        System.out.printf("Transaction history for account %s\n", this.uuid);
        for (int i = this.transactions.size()-1; i >= 0; i--){
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo){
        Transaction transaction = new Transaction(amount, this, memo);
        this.transactions.add(transaction);
    }
}

import java.util.Date;

public class Transaction {

    private double amount;

    private Date timestamp;

    private String memo;

    private Account inAccount;

    // create a transaction with the ammount and with the account the transaction belongs
    public Transaction(double ammount,Account inAccount){
        this.amount = ammount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double ammount,Account inAccount, String memo){
        this(ammount,inAccount);
        this.memo = memo;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getSummaryLine(){
        if (this.amount >= 0){
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }


}

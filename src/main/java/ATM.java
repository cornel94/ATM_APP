import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Cornel");

        User aUser = theBank.addUser("Cornel","Stefan","1234");

        Account newAccount = new Account("Checking",aUser,theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){

            // stay in login prompt untill succesfull login
            curUser = ATM.mainMenuPrompt(theBank,sc);

            // stay in main menu untill user quits
            ATM.printUserMenu(curUser,sc);
        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc){
        //inits
        String userID;
        String pin;
        User authUser;

        // serach the user for the userID an pin combination
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter the userID: ");
            userID = sc.nextLine();
            System.out.print("Enter the pin: ");
            pin = sc.nextLine();
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null){
                System.out.println("Incorrect pin or userID combination. Please try again.");
            }
        } while (authUser == null);

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc){

        // print a summary of the users accounts
        theUser.printAccounstSummary();

        int choice;

        //user menu
        do{
            System.out.printf("Hey %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Withdraw");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5.");
            }
        } while (choice < 1 || choice > 5);

        // process the choise
        switch (choice){
            case 1:
                ATM.showTransHistory(theUser,sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser,sc);
                break;
            case 3:
                ATM.depositFunds(theUser,sc);
                break;
            case 4:
                ATM.transferFunds(theUser,sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        // redesplay the menu until the user want to quit
        if (choice != 5){
            printUserMenu(theUser,sc);
        }
    }

    public static void showTransHistory(User theUser, Scanner sc){
        int theAcct;

        // get account whose transaction history to look at
        do {
            System.out.printf("Enter the number 1-%d, of the account " +
                    " whose transactions you want to see: ", theUser.numsAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numsAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numsAccounts());

        // print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner sc){

        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ",
                    theUser.numsAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numsAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numsAccounts());
        acctBalance = theUser.getAcctBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer into: ",theUser.numsAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numsAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numsAccounts());

        // get amount to transfer
        do {
            System.out.printf("Please enter the amount to transfer (max $%.02f): $", acctBalance);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than 0.");
            } else if (amount > acctBalance){
                System.out.printf("Amount must not be greater than balance of $%.02f.", acctBalance);
            }

        } while (amount < 0 || amount > acctBalance);

        // doing the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s.", theUser.getAcctUUID(toAcct)));

        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer from account %s.", theUser.getAcctUUID(fromAcct)));

    }

    public static void withdrawFunds(User theUser, Scanner sc){

        int fromAcct;
        double amount;
        double acctBalance;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ",theUser.numsAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numsAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numsAccounts());
        acctBalance = theUser.getAcctBalance(fromAcct);

        // get amount to transfer
        do {
            System.out.printf("Please enter the amount to transfer (max $%.02f): $", acctBalance);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than 0.");
            } else if (amount > acctBalance){
                System.out.printf("Amount must not be greater than balance of $%.02f.", acctBalance);
            }

        } while (amount < 0 || amount > acctBalance);

        // get a memo
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    public static void depositFunds(User theUser, Scanner sc){

        int toAcct;
        double amount;
        double acctBalance;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer into:",theUser.numsAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numsAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numsAccounts());
        acctBalance = theUser.getAcctBalance(toAcct);

        // get amount to transfer
        do {
            System.out.printf("Please enter the amount to transfer (max $%.02f): $", acctBalance);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than 0.");
            }
        } while (amount < 0);

        System.out.print("Enter a memo:");
        sc.nextLine();
        // get a memo
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

}

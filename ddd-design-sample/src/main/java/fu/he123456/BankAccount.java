package fu.he123456;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/*
Aggregate root

 */

public class BankAccount {
    private String accountNumber;
    private BigDecimal balance;
    private List<Transaction> transactions = new ArrayList<>();
    private Status status;

    private BankAccount() {

    }

    public static BankAccount openNewAccount(String accountNumber) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.accountNumber = accountNumber;
        bankAccount.balance = new BigDecimal(50000);
        bankAccount.status = Status.ACTIVE;

        return bankAccount;

    }

    public void withdraw(BigDecimal amount) {

        if (status == Status.FROZEN) {
            throw new IllegalArgumentException("Your account is frozen");
        }

        if (amount.compareTo(balance) > 50000) {
            throw new IllegalArgumentException("Balance - amount >=50000");

        }


    }
}


enum Status {
    ACTIVE,
    FROZEN,
    CLOSED
}

class Transaction {

}



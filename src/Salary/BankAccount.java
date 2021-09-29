package Salary;

import Salary.Transaction;

import java.math.BigDecimal;

public class BankAccount implements Transaction {
    public String name, address, accountType, branchName, bankName, accountNumber;
    private BigDecimal currentBalance;

    /**
     * Constructor for Employees
     * @param name name of account holder/company
     * @param address address of account holder/company
     * @param accountType account type ie: savings
     * @param branchName branch name of bank
     * @param bankName bank name
     * @param accountNumber account number
     */
    public BankAccount(String name, String address, String accountType,
                       String branchName, String bankName, String accountNumber) {
        this.name = name;
        this.address = address;
        this.accountType = accountType;
        this.branchName = branchName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.currentBalance = BigDecimal.ZERO;
    }

    /**
     * Constructor for bank Account with given deposit
     * @param name name of account holder/company
     * @param address address of account holder/company
     * @param accountType account type of employee ie: savings
     * @param branchName branch name of bank
     * @param bankName bank name
     * @param accountNumber account number
     * @param currentBalance current balance of the company
     */
    public BankAccount(String name, String address, String accountType,
                       String branchName, String bankName, String accountNumber, BigDecimal currentBalance) {
        this.name = name;
        this.address = address;
        this.accountType = accountType;
        this.branchName = branchName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
    }

    /**
     * Transfer balance from an account to another account.
     *
     * @param destinationAccount BankAccount object
     * @param amount Requested amount to transfer.
     * @param <T> BankAccount class Type of object
     * @param <U> BigDecimal class Type of object
     */
    public <T extends BankAccount, U extends BigDecimal> void transferFund( T destinationAccount, U amount) throws BalanceInsufficientException {
        this.currentBalance.subtract(amount);
        destinationAccount.addBalance(amount);
    }

    @Override
    public synchronized BigDecimal queryBalance() {
        return currentBalance;
    }

    @Override
    public synchronized BigDecimal addBalance(BigDecimal balance) {
        currentBalance = currentBalance.add(balance);
        return currentBalance;
    }

    @Override
    public synchronized BigDecimal subtract(BigDecimal amount) throws BalanceInsufficientException {
        if (currentBalance.compareTo(amount) >= 0){
            currentBalance = currentBalance.subtract(amount);
        }else{
            throw new BalanceInsufficientException(
                    "Insufficient Balance terminated the proposed transaction with "+ amount
            );
        }
        return currentBalance;
    }

}

/**
 * Custom Exception for insufficient bank balance
 */
class BalanceInsufficientException extends Exception{
    public BalanceInsufficientException(String message) {
        super(message);
    }
}
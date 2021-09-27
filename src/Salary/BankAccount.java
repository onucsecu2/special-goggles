package Salary;

import java.math.BigDecimal;

public class BankAccount implements Transaction{
    public String name, address, accountType, branchName, bankName, accountNumber;
    private BigDecimal currentBalance;

    /**
     * Constructor for Employees
     * @param name name of employee
     * @param address address of employee
     * @param accountType account type of employee ie: savings
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
     * Constructor for company bank Account
     * @param currentBalance current balance of the company
     */
    public BankAccount(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public BigDecimal queryBalance() {
        return currentBalance;
    }

    @Override
    public BigDecimal addBalance(BigDecimal balance) {
        currentBalance = currentBalance.add(balance);
        return currentBalance;
    }

    @Override
    public BigDecimal subtract(BigDecimal amount) throws BalanceInsufficientException {
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
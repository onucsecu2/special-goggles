package Salary;

import java.math.BigDecimal;

public class BankAccount implements Transaction{
    public String name, address, accountType, branchName, bankName, accountNumber;
    private BigDecimal currentBalance;

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

class BalanceInsufficientException extends Exception{
    public BalanceInsufficientException(String message) {
        super(message);
    }
}
package Salary;

import java.math.BigDecimal;

interface Transaction {
    BigDecimal queryBalance();
    BigDecimal addBalance(BigDecimal balance);
    BigDecimal subtract(BigDecimal balance) throws BalanceInsufficientException;
}

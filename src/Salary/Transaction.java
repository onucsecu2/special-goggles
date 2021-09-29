package Salary;


import java.math.BigDecimal;

interface Transaction <T extends BigDecimal> {
    /**
     * show current balance
     *
     * @return current balance from the account
     */
    BigDecimal  queryBalance();

    /**
     * add a amount to the account
     *
     * @param amount add requested amount to the account
     * @return current balance after adding the amount
     */
    BigDecimal addBalance(T amount);

    /**
     * deduct an amount from the account.
     *
     * @param amount deduct requested amount from the account
     * @return current balance after deducting the amount
     * @throws BalanceInsufficientException throws the exception when deducting from the account causes negative
     *                                      value in the current balance. i.e : in subtracting operation,
     *                                      requested amount is greater than current balance.
     */
    BigDecimal subtract(T amount) throws BalanceInsufficientException;
}

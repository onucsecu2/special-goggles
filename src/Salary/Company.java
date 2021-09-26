package Salary;

import java.math.BigDecimal;

public class Company {
    public BigDecimal basic;
    public BankAccount bankAccount;

    public Company(BigDecimal basic, BankAccount bankAccount) {
        this.basic = basic;
        this.bankAccount = bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public static final BigDecimal INCREMENT_PER_GRADE = new BigDecimal(5000);
    /**
     * House rent is 20% of the basic
     */
    public static final BigDecimal HOUSE_RENT_PERCENTAGE = new BigDecimal("0.20");
    /**
     * Medical allowance is 15% of the basic
     */
    public static final BigDecimal MEDICAL_ALLOWANCE_PERCENTAGE = new BigDecimal("0.15");

}

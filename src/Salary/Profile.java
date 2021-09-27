package Salary;

/**
 *  Profile class to make an object with the Employee object and its corresponding BankAccount object
 */
class Profile{
    Employee employee;
    BankAccount bankAccount;

    /**
     * Profile object constructor
     * @param employee Employee Object
     * @param bankAccount BankAccount Object
     */
    public Profile(Employee employee, BankAccount bankAccount) {
        this.employee = employee;
        this.bankAccount = bankAccount;
    }

    /**
     *  employee object extract
     * @return Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Extract BankAccount object
     * @return BankAccount Object
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }
}

package Salary;

class Profile{
    Employee employee;
    BankAccount bankAccount;
    public Profile(Employee employee, BankAccount bankAccount) {
        this.employee = employee;
        this.bankAccount = bankAccount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}

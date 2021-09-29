package Salary;
import Salary.EmployeeSalaryPayment;
import Salary.Grade;

import java.math.BigDecimal;

public class GeneralEmployee extends Employee {
    public Grade grade;
    public BigDecimal salary;

    /**
     *
     * @param name string name of the employee
     * @param address string address of the employee
     * @param mobile string mobile number of employee
     * @param grade enum grade of employee
     */
    public GeneralEmployee(String name, String address, String mobile, Grade grade) {
        super(name, address, mobile);
        this.grade = grade;
        this.salary =  calculateSalary(grade);
    }

    /**
     * Grade of the employee
     * @return enum grade of the employee.
     */
    @Override
    public Grade getGrade() {
        return grade;
    }

    @Override
    BigDecimal getSalary() {
        return this.salary;
    }

    /**
     * calculate the salary of the employee according to the basic, grade, allowances etc.
     * @param grade enum grade of the employee
     * @return Bigdecimal salary of the employee
     */
    private BigDecimal calculateSalary(Grade grade) {
        BigDecimal basic = EmployeeSalaryPayment.company.basic.add(Company.INCREMENT_PER_GRADE
                .multiply(new BigDecimal(grade.getGradeValue())));
        return basic.add(basic.multiply(Company.HOUSE_RENT_PERCENTAGE)).add(basic.multiply(Company.MEDICAL_ALLOWANCE_PERCENTAGE));
    }
}

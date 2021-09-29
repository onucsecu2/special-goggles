package Salary;

import Salary.Grade;

import java.math.BigDecimal;

abstract class Employee {
    private String name, address, mobile;

    public Employee(String name, String address, String mobile) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;

    }
    public String getName() {
        return name;
    }
    abstract Grade getGrade();
    abstract  BigDecimal getSalary();
}

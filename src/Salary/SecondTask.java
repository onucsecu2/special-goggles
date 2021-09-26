package Salary;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class
 */

public class SecondTask {

    private static List<Profile> profileList = new ArrayList<>();
    private static Company company;
    public static void main(String[] args) {
        readBasicData();
        loadEmployees();
        while(true) {
            ShowInteractiveMenu();
        }
    }

    private static void loadEmployees() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/Salary/employees.txt"));
            String line = "";
            String splitBy = ";";
            while ((line = br.readLine()) != null)
            {
                String[] employee = line.split(splitBy);
                String name = employee[0];
                String address = employee[1];
                String mobile =  employee[2];
                Grade grade =  Grade.valueOf(employee[3]);
                String accountType = employee[4];
                String branchName = employee[5];
                String bankName = employee[6];
                String accountNumber = employee[7];

                BankAccount bankAccount = new BankAccount(name, address, accountType, branchName, bankName, accountNumber);
                Employee employeeObj = new GeneralEmployee(name, address, mobile, grade);

                Profile profile = new Profile(employeeObj, bankAccount);
                profileList.add(profile);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println(e);
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void ShowInteractiveMenu() {
        System.out.println("______________________________________________");
        System.out.println("Enter the following key:");
        System.out.println("L : to see the list of the employees");
        System.out.println("T : to transact salary to all employees");
        System.out.println("S : to transact salary to specific employee");
        System.out.println("G : to generate employees report");
        System.out.println("P : to generate payroll report");
        Scanner input = new Scanner(System.in);
        char ch = input.next().charAt(0);
        ch = Character.toUpperCase(ch);
        switch (ch){
            case 'L':
                showEmployeesList();
                break;
            case 'T':
                transferFundAll();
                break;
            case 'S':
                transferFunds();
                break;
            case 'G':
                generateReport();
                break;
            case 'P':
                generatePayrollReport();
                break;
            case 'Q':
                break;
        }
    }

    private static void generatePayrollReport() {
        StringBuffer sb = new StringBuffer();
        String separator = ";";
        String lineSeparator = "\n";
        BigDecimal total = BigDecimal.ZERO;
        for(Profile profile : profileList){
            BankAccount account = profile.getBankAccount();
            total = total.add(account.queryBalance());
        }

        sb.append("Total Paid");
        sb.append(separator);
        sb.append(total);
        sb.append(lineSeparator);
        sb.append("Remaining Balance");
        sb.append(separator);
        sb.append(company.getBankAccount().queryBalance());
        sb.append(lineSeparator);

        String path = "src/Salary/total.csv";
        writeToFile(path, sb);
    }

    private static void generateReport() {
        StringBuffer sb = new StringBuffer();
        String separator = ";";
        String lineSeparator = "\n";
        for(Profile profile : profileList){
            Employee employee = profile.getEmployee();
            sb.append(employee.getName());
            sb.append(separator);
            sb.append(employee.getGrade());
            sb.append(separator);
            sb.append(employee.getSalary());
            sb.append(lineSeparator);
        }
        String path = "src/Salary/Name_of_employee.csv";
        writeToFile(path, sb);
    }

    private static void writeToFile(String path, StringBuffer sb) {
        Charset charset = StandardCharsets.US_ASCII;
        BufferedWriter writer =null;
        try {
            writer = Files.newBufferedWriter(Path.of(path), charset);
            writer.write(String.valueOf(sb), 0, sb.length());
        } catch (FileAlreadyExistsException e){
            System.err.format("FileAlreadyExistsException: %s%n", e);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Content of StringBuffer written to File.");
    }

    private static void transferFunds () {

        try {
            Profile profile = getSpecificProfile();
            transferFund(company.getBankAccount(), profile.getBankAccount(), profile.getEmployee().getSalary());
        }catch (IndexOutOfBoundsException e){
            System.err.println(e);
        }
        catch (InputMismatchException e){
            System.err.println(e);
        }finally {
            showCompanyBalance();
        }
    }

    private static void showCompanyBalance() {
        System.out.println("Remaining Balance : "+company.getBankAccount().queryBalance());
        System.out.println("----Transfer Completed----");
    }

    private static Profile getSpecificProfile() throws InputMismatchException,IndexOutOfBoundsException  {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Index Number from the Employee List :");
        int index = input.nextInt();
        return profileList.get(index-1);
    }

    private static void transferFundAll() {
        for(Profile profile : profileList){
            transferFund(company.getBankAccount(), profile.getBankAccount(), profile.getEmployee().getSalary());
        }
        showCompanyBalance();
    }

    private static void showEmployeesList() {
        int index = 0;
        for(Profile profile : profileList){
            System.out.println(index+". " + profile.getEmployee().getName());
            index++;
        }
    }

    private static void readBasicData() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Basic Salary of the Lowest Grade: ");
        BigDecimal basicSalary = input.nextBigDecimal();
        System.out.println("Company Bank Balance: ");
        BigDecimal companyBankBalance = input.nextBigDecimal();

        BankAccount bankAccount = new BankAccount(companyBankBalance);

        company = new Company(basicSalary, bankAccount);

    }

    public static <T extends BankAccount, U extends BigDecimal> void transferFund(T companyAccount, T employeeAccount, U amount){
        try {
            companyAccount.subtract(amount);
            employeeAccount.addBalance(amount);
        } catch (BalanceInsufficientException e) {
            System.err.println(e);
        }

    }
}
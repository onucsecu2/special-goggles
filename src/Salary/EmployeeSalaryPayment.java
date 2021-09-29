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

public class EmployeeSalaryPayment {

    private static List<Profile> profileList = new ArrayList<>();
    public static Company company;
    public static void main(String[] args) {
        readBasicData();
        loadEmployees();
        while(true) {
            showInteractiveMenu();
        }
    }

    /**
     * read the basic inputs (Basic salary  and the initial bank balance of company from the console
     */
    private static void readBasicData() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Basic Salary of the Lowest Grade: ");
        BigDecimal basicSalary = input.nextBigDecimal();
        System.out.println("Company Bank Balance: ");
        BigDecimal companyBankBalance = input.nextBigDecimal();

        BankAccount bankAccount = new BankAccount("Company.LLC", "Dhaka-1207", "Business",
                                                "Agargaon", "Standard Chartered",
                                    "19029394762834", companyBankBalance);

        company = new Company(basicSalary, bankAccount);

    }

    /**
     * load 10 employees data as described from a file for convenient
     */
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
        }catch (FileNotFoundException e) {
            ExceptionHandler.displayExceptionAbstractLabel(e);
        }
        catch (IndexOutOfBoundsException e) {
            ExceptionHandler.displayExceptionAbstractLabel(e);
        }
        catch (IOException e) {
            ExceptionHandler.displayExceptionAbstractLabel(e);
        }
        finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                ExceptionHandler.displayExceptionAbstractLabel(e);
            } catch (NullPointerException e) {
                ExceptionHandler.displayExceptionAbstractLabel(e);
            }
        }

    }

    /**
     *  it's an interactive menu displaying in the console with command list and take input command and execute
     */
    private static void showInteractiveMenu() {
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
                generateEmployeeReport();
                break;
            case 'P':
                generateBalanceReport();
                break;
            case 'Q':
                break;
        }
    }

    /**
     *  show the list of the employees
     */
    private static void showEmployeesList() {
        int index = 0;
        for(Profile profile : profileList) {
            System.out.println(index+". " + profile.getEmployee().getName());
            index++;
        }
    }

    /**
     * Get the profile of specific employee according to the index number taking from the console
     *  while displaying the employee list
     * @return Profile object of the employee of the index.
     * @throws InputMismatchException Console read expect an int input otherwise can't proceed
     * @throws IndexOutOfBoundsException if the provided index number is out of range of the employee list
     */
    private static Profile getSpecificProfile() throws InputMismatchException,IndexOutOfBoundsException  {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Index Number from the Employee List :");
        int index = input.nextInt();
        return profileList.get(index-1);
    }

    /**
     *  Display company's current balance in the console after performing transaction operation
     */
    private static void showCompanyBalance() {
        System.out.println("Remaining Balance : "+company.getBankAccount().queryBalance());
        System.out.println("----Transfer Completed----");
    }

    /**
     * Transfer balance from an account to another account.
     *
     * @param companyAccount  From BankAccount object
     * @param employeeAccount To BankAccount object
     * @param amount Requested amount to transfer.
     * @param <T> BankAccount class Type of object
     * @param <U> BigDecimal class Type of object
     */
    public static <T extends BankAccount, U extends BigDecimal> void transferFund(T companyAccount, T employeeAccount, U amount){
        try {
            companyAccount.subtract(amount);
            employeeAccount.addBalance(amount);
        } catch (BalanceInsufficientException e) {
            ExceptionHandler.displayExceptionAbstractLabel(e);
        }

    }

    /**
     * transfer amount from company bank account to employee bank account
     */
    private static void transferFunds () {

        try {
            Profile profile = getSpecificProfile();
            transferFund(company.getBankAccount(), profile.getBankAccount(), profile.getEmployee().getSalary());
        }catch (IndexOutOfBoundsException e){
            ExceptionHandler.displayExceptionAbstractLabel(e);
        }
        catch (InputMismatchException e) {
            ExceptionHandler.displayExceptionAbstractLabel(e);
        }finally {
            showCompanyBalance();
        }
    }

    /**
     * transfer salary from the company bank account to all employees account once;
     */
    private static void transferFundAll() {
        for(Profile profile : profileList) {
            transferFund(company.getBankAccount(), profile.getBankAccount(), profile.getEmployee().getSalary());
        }
        showCompanyBalance();
    }


    /**
     * generate report file of company's current balance and total paid
     */
    private static void generateBalanceReport() {
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

    /**
     *  generate report file of the employees information
     */
    private static void generateEmployeeReport() {
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
        String path = "src/Salary/employee_report.csv";
        writeToFile(path, sb);
    }

    /**
     * write to a file
     * @param path path of the file
     * @param sb what to write in the file
     */
    private static void writeToFile(String path, StringBuffer sb) {
        Charset charset = StandardCharsets.US_ASCII;
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(Path.of(path), charset);
            writer.write(String.valueOf(sb), 0, sb.length());
        } catch (FileAlreadyExistsException e){
            ExceptionHandler.displayExceptionAbstractLabel(e);
        } catch (IOException e) {
            ExceptionHandler.displayExceptionAbstractLabel(e);
        } finally {
            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e) {
                ExceptionHandler.displayExceptionAbstractLabel(e);
            }
        }
        System.out.println("Content of StringBuffer written to File.");
    }
}


// TODO : unified bank account for company and employee
// TODO : add synchronization in transaction
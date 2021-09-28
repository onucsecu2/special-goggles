package Salary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.InputMismatchException;

public class ExceptionHandler<T extends Exception> {

    public static <T extends Exception> void displayExceptionAbstractLabel(T exception){
        if(exception instanceof InputMismatchException) {
            System.err.println("Re type the input in correct manner");
        } else if(exception instanceof IndexOutOfBoundsException) {
            System.err.println("Requested information is either not found or is out of the collection");
        } else if(exception instanceof BalanceInsufficientException) {
            System.err.println("The requested amount failed to transact due to insufficient balance");
        } else if(exception instanceof FileNotFoundException) {
            System.err.format("Requested file not found in the location %s%n", exception.getLocalizedMessage());
        } else if(exception instanceof FileAlreadyExistsException) {
            System.err.println("File Already exist in the location");
        } else if(exception instanceof IOException) {
            System.err.println("Requested command cant be proceeded");
        } else {
            System.err.println("Something went wrong");
        }
    }
}

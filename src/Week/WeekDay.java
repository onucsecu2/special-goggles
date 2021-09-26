package Week;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeekDay {
    public static void main(String[] args) {

        String[] days = { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };

        while(true){
            Scanner input = new Scanner(System.in);
            try {
                short day = input.nextShort();
                if(validate(day)){
                    System.out.println(days[day%7]);
                }else{
                    System.out.println("Invalid Date");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid Input");
            }catch (IndexOutOfBoundsException e){
                System.out.println("Something went wrong");
            }
        }
    }
    public static boolean validate(short n){
        return n >= 1 && n <= 31;
    }

}

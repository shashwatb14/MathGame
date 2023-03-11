import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // menu choices
        String[] menu = {
                "[1] for addition",
                "[2] for subtraction",
                "[3] for multiplication",
                "[4] for division",
                "[5] to quit"
        };

        // printing the menu
        System.out.println("Choose: " + menu[0]);
        for (int i = 1, n = menu.length; i < n; i++) {
            System.out.println("        " + menu[i]);
        }

        // input validation
        int option = 0;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">>> ");
            // first check if it is integer
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                // check if it is a valid choice
                if (option < 1 || option > 5) {
                    System.out.println("Error - please choose a valid option");
                }
            }
            else System.out.println("Error - please provide valid input");
        } while (option < 1 || option > 5);
    }

    // returns random number from a specific range
    // doesn't include the last number
    public static int generate_num(int start, int range) {
        return (int) (Math.random() * range) + start;
    }

    // todo - returns number of points scored
    public static int play_addition(int level) {
        Scanner scanner = new Scanner(System.in);
        return 0;
    }
}
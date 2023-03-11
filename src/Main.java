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
            Scanner getin = new Scanner(System.in);
            System.out.print(">>> ");
            // first check if it is integer
            if (getin.hasNextInt()) {
                option = getin.nextInt();
                // check if it is a valid choice
                if (option < 1 || option > 5) {
                    System.out.println("Error - please choose a valid option");
                }
            }
            else System.out.println("Error - please provide valid input");
        } while (option < 1 || option > 5);

        if (option == 1) {
            while (true) {
                play_addition(4);
            }
        }
    }

    /*
     * returns random number from a specific range
     * doesn't include the last number
     */
    public static int generate_num(int start, int range) {
        return (int) (Math.random() * range) + start;
    }

    // todo - returns number of points scored
    public static int play_addition(int level) {
        Scanner getin;
        int[] numbers = new int[level];

        // generate numbers based on level
        int start = (int) Math.pow(10, level);
        int range = (int) Math.pow(10, level + 1) - start;
        int min = generate_num(start, range);
        int max = generate_num(start, range);

        for (int i = 0; i < 10000000; i++) {
            // numbers[i] = generate_num(start, range);
            int x = generate_num(start, range);
            if (x < min) min = x;
            if (x > max) max = x;
        }
        System.out.println("min = " + min + "\nmax = " + max);

        int answer;
        do {
            getin = new Scanner(System.in);
            System.out.print("> ");
            // first check if it is integer
            if (getin.hasNextInt()) answer = getin.nextInt();
            else System.out.println("Error - please provide valid input");
        } while (!getin.hasNextInt());
        return 0;
    }
}
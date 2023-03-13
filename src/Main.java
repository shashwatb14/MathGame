import java.util.Scanner;
import java.util.Date;

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
        int score = 0;

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
                // capture initial timing
                Date d1 = new Date();
                double start = d1.getTime();

                int result = play_addition(1);

                // capture final timing
                Date d2 = new Date();
                double end = d2.getTime();

                // rogue value
                if (result == -6) break;
                score += result;
                System.out.printf("Score: %6d\n", score);
                System.out.println("\nTime taken: " + ((end - start) / 1000));
            }
        }
        System.out.println("Your final score: " + score);
    }

    /*
     * returns random number from a specific range
     * doesn't include the last number
     */
    public static int generate_num(int start, int range) {
        return (int) (Math.random() * range) + start;
    }

    // returns number of points scored
    public static int play_addition(int level) {
        Scanner getin;
        int[] numbers = new int[level + 1];

        // generate numbers based on level
        int start = (int) Math.pow(10, level);
        int range = (int) Math.pow(10, level + 1) - start;
        int sum = 0;

        System.out.println();
        for (int i = 0; i < (level + 1) * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println("\n");

        for (int i = 0; i < level + 1; i++) {
            numbers[i] = generate_num(start, range);
            sum += numbers[i];
            if (i == level) System.out.println(numbers[i]);
            else System.out.print(numbers[i] + " + ");
        }
        System.out.println(sum);

        int answer;
        do {
            getin = new Scanner(System.in);
            System.out.print("> ");
            // first check if it is integer
            if (getin.hasNextInt()) {
                answer = getin.nextInt();
                break;
            }
            // rogue value
            else if (getin.hasNext("q") || getin.hasNext("exit")) return -6;
            else {
                System.out.println("Error - please provide valid input");
                System.out.println("Tip: type 'q' or \"exit\" return to the menu.");
            }

        } while (true);

        if (answer == sum) {
            int points = generate_num((level - 1) * 10, level * 10);
            System.out.printf("\nCorrect! You got %d point(s)!\n", points);
            return points;
        } else {
            int loss = generate_num((((level - 1) * 10) / 2) + 1, (level * 10) / 2);
            System.out.printf("\nWrong! The answer was %d, you lost %d point(s).\n", sum, loss);
            return -loss;
        }
    }
}
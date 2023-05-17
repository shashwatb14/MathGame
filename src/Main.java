import java.util.Scanner;
import java.util.Date;
import java.io.*;

public class Main {

    // keeps track of points
    static int score = 0;

    public static void main(String[] args) throws IOException {

        File file = new File("src/score.txt");

        // for reading file and making local saves
        FileReader fr = new FileReader(file);
        BufferedReader load = new BufferedReader(fr);

        String name = load.readLine();
        String scoreStr = load.readLine();

        int highScore = Integer.parseInt(scoreStr);

        while (true) {

            // menu choices
            String[] menu = {
                    "[1] for addition",
                    "[2] for subtraction",
                    "[3] for multiplication",
                    "[4] for division",
                    "[5] for random operations",
                    "[6] to quit"
            };

            System.out.printf("High-score: %d by %s\n", highScore, name);

            // printing the menu
            System.out.println("\nChoose: " + menu[0]);
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
                    if (option < 1 || option > 6) {
                        System.out.println("Error - please choose a valid option");
                    }
                } else System.out.println("Error - please provide valid input");
            } while (option < 1 || option > 6);

            if (option == 6) break;
            else {
                int level = difficulty();
                if (option == 1) play(level, '+');
                else if (option == 2) play(level, '-');
                else if (option == 3) play(level, '*');
                else if (option == 4) play(level, '/');
                else play(level, 'r');
            }
        }

        System.out.println("\nYour final score: " + score);

        // write to file only if current score > highScore
        if (score >= highScore) {
            System.out.println("\nCongratulations! New high-score!");
            Scanner in = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String winner = in.nextLine();

            // write to file
            FileWriter fw = new FileWriter(file);
            PrintWriter save = new PrintWriter(fw);
            save.write(winner + "\n");
            save.println(score);

            // always close files
            save.close();
            System.out.println("High-scored saved!");
        }

        load.close();
    }

    /*
     * returns random number from a specific range
     * doesn't include the last number
     */
    public static int generate_num(int start, int range) {
        return (int) (Math.random() * range) + start;
    }

    // allow for custom difficulty level
    public static int difficulty() {
        System.out.println("Choose the difficulty level (1 - 5): ");
        int level = 0;
        do {
            Scanner getin = new Scanner(System.in);
            System.out.print(">>> ");
            // first check if it is integer
            if (getin.hasNextInt()) {
                level = getin.nextInt();

                // check if it is within a valid range
                if (level < 1 || level > 5) System.out.println("Error - difficulty out of range (1 - 5)");

            } else System.out.println("Error - please provide valid input");
        } while (level < 1 || level > 5);

        return level;
    }

    // captures timing and updates score
    public static void play(int level, char operator) {
        while (true) {
            int result;

            // capture initial timing
            Date d1 = new Date();
            double start = d1.getTime();

            if (operator == '+' || operator == '-') result = arithmetic(level, operator);
            else if (operator == '*') result = multiplication(level);
            else if (operator == '/') result = division(level);
            else result = random(level);

            // capture final timing
            Date d2 = new Date();
            double end = d2.getTime();

            // rogue value
            if (result == -6) break;
            score += result;
            System.out.printf("Score: %6d\n", score);
            System.out.println("\nTime taken: ~" + ((end - start) / 1000) + "s");
        }
    }

    // validate and check input
    public static int check(long result, int level) {
        Scanner getin;
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
            else if (getin.hasNext("q") || getin.hasNext("l")) return -6;

            else {
                System.out.println("Error - please provide valid input");
                System.out.println("Tip: type 'q' to return to the menu.");
            }
        } while (true);

        // check answer and award points according to level
        if (answer == result) {
            int points = generate_num((level - 1) * 10 + 1, level * 10);
            System.out.printf("\nCorrect! You got %d point(s)!\n", points);
            return points;
        } else {

            // loss points are half the range of win points
            int loss = generate_num((((level - 1) * 10) / 2) + 1, (level * 10) / 2);

            // rogue value
            if (loss == -6) loss = generate_num((((level - 1) * 10) / 2) + 1, (level * 10) / 2);
            System.out.printf("\nWrong! The answer was %d, you lost %d point(s).\n", result, loss);
            return -loss;
        }
    }

    // returns number of points scored in arithmetic (only addition and subtraction)
    public static int arithmetic(int level, char operator) {
        int[] numbers = new int[level + 1];

        // generate numbers based on level
        int start = (int) Math.pow(10, level);
        int range = (int) Math.pow(10, level + 1) - start;

        int sum = 0, difference = 0;

        System.out.println();
        for (int i = 0; i < (level + 1) * 2 + 3; i++) System.out.print("-");
        System.out.println("\n");

        // generate numbers
        for (int i = 0; i < level + 1; i++) {
            numbers[i] = generate_num(start, range);
            sum += numbers[i];

            if (i == 0) difference = numbers[i];
            else difference -= numbers[i];

            if (i == level) System.out.println(numbers[i]);
            else System.out.print(numbers[i] + " " + operator + " ");
        }

        // check answer
        if (operator == '-') return check(difference, level);
        else return check(sum, level);
    }

    // method for multiplication
    public static int multiplication(int level) {
        // generate numbers based on level
        int start = (int) Math.pow(10, level - 1);
        int range = (int) Math.pow(10, level);

        int num1 = generate_num(start, range), num2 = generate_num(start, range);
        int product = num1 * num2;

        System.out.println();
        for (int i = 0; i < (level + 1) * 2 + 3; i++) System.out.print("-");
        System.out.printf("\n\n%d * %d\n", num1, num2);

        return check(product, level);
    }

    // method for division
    public static int division(int level) {
        // generate numbers based on level
        int start = (int) Math.pow(10, level);
        int range = (int) Math.pow(10, level + 2) - start;

        int num1 = generate_num(start, range), num2 = generate_num(start, range);

        // generate until it's perfectly divisible
        while (num1 % num2 != 0 || num1 == num2) {
            num1 = generate_num(start, range);
            num2 = generate_num(start, range);
        }

        int quotient = num1 / num2;

        System.out.println();
        for (int i = 0; i < (level + 1) * 2 + 3; i++) System.out.print("-");
        System.out.printf("\n\n%d / %d\n", num1, num2);

        return check(quotient, level);
    }

    // prime number check for random division
    public static boolean isPrime(int number) {

        if (number == 2) return true;
        if (number < 2 || number % 2 == 0) return false;

        for (int i = 3; i < (int) Math.sqrt(number) + 1; i += 2) if (number % i == 0) return false;
        return true;
    }

    // method for mixed operations
    public static int random(int level) {

        int[] numbers = new int[generate_num(level + 1, level)];
        char[] operations = {'+', '-', '/', '*'};
        long result = 0;
        int prev = 1;

        for (int i = 0, n = numbers.length; i < n; i++) {
            char operator = operations[generate_num(0, 4)];
            if (operator == '+' || operator == '-' || i == 0) {

                // generate numbers for arithmetic based on level
                int start = (int) Math.pow(10, level);
                int range = (int) Math.pow(10, level + 1) - start;

                int num = generate_num(start, range);

                if (i == 0) result = num;
                else if (operator == '-') {
                    System.out.print(" - ");
                    result -= num;
                } else {
                    System.out.print(" + ");
                    result += num;
                }
                System.out.print(num);
                prev = num;
            } else if (operator == '*') {

                // generate smaller numbers for multiplication based on level
                int start = (level - 1) * 10 + 1;
                int range = level * 10;

                int num = generate_num(start, range);
                System.out.print(" * " + num);
                result *= num;
                prev = num;
            } else {

                // generate smaller numbers for division based on level
                int num = generate_num(2, prev);

                if (isPrime(prev)) num = prev;
                else {
                    while (prev % num != 0 || prev == num) {
                        num = generate_num(2, prev);
                    }
                }

                System.out.print(" / " + num);
                result /= num;
                prev = num;
            }
        }

        System.out.println();
        return check(result, level);
    }
}
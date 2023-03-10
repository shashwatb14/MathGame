import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Choose: [1] for addition");
        System.out.println("        [2] for subtraction");
        System.out.println("        [3] for multiplication");
        System.out.println("        [4] for division");
        System.out.println("        [5] to quit");

        int option = 0;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">>> ");
            if (validate_option(scanner)) {
                option = scanner.nextInt();
                if (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
                    System.out.println("Error - please choose a valid option");
                }
            }
            else {
                System.out.println("Error - please provide valid input");
            }

        } while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5);
    }

    public static int generate_num(int start, int range) {
        return (int) (Math.random() * range) + start;
    }

    public static boolean validate_option(Scanner check) {
        return check.hasNextInt();
    }
}
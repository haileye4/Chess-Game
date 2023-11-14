import com.google.gson.Gson;
import java.sql.Connection;
import java.util.*;

import static ChessUI.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_BOLD);

        System.out.print("\nWelcome to the wonderful game of Chess!\n" +
                "A CS240 project created by Hailey Johnson\n\n");

        System.out.print(RESET_TEXT_BOLD_FAINT);
        System.out.print(RESET_TEXT_ITALIC);

        // Get the clients option
        Scanner scanner = new Scanner(System.in);

        int option;
        do {
            //output the menu
            displayMenu();
            System.out.print("Enter an option (1-4): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                System.out.print("Enter an option (1-4): ");
                scanner.next();
            }

            option = scanner.nextInt();

            // Process user choice
            //switch statement looks at what user enters
            switch (option) {
                case 1:
                    System.out.println("You selected Register.");
                    System.out.println("\n");
                    register(scanner);
                    break;
                case 2:
                    System.out.println("You selected Login.");
                    // Add your Login logic here
                    break;
                case 3:
                    System.out.println("You selected Help.");
                    // Add your Help logic here
                    break;
                case 4:
                    System.out.println("You selected Quit. Exiting...");
                    // Add your Quit logic here or simply break out of the loop
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        } while (option != 4);

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Help");
        System.out.println("4. Quit");
    }

    public static void register(Scanner scanner) {
        scanner.nextLine();
        // Get username
        System.out.println("Registering a new user...");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // Get password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Get email
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Display the entered information
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("\n");
        System.out.print(SET_TEXT_ITALIC);
        System.out.println("Registered!");
        System.out.print(RESET_TEXT_ITALIC);
    }
}

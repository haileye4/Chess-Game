import com.google.gson.Gson;
import java.sql.Connection;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        System.out.print("Welcome to the wonderful game of Chess!\n" +
                "A CS240 project created by Hailey Johnson\n\n");

        //output a menu
        displayMenu();

        // Get the clients option
        Scanner scanner = new Scanner(System.in);

        int option;
        do {
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
                    // Add your Register logic here
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
}

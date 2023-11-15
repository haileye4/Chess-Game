import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.*;
import request.LoginRequest;
import request.RegisterRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static ChessUI.EscapeSequences.*;

public class Main {
    //make a local class variable scanner
    static Scanner scanner = new Scanner(System.in);
    static ServerFacade server = new ServerFacade();

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_BOLD);

        System.out.print("\nWelcome to the wonderful game of Chess!\n" +
                "A CS240 project created by Hailey Johnson\n\n");

        System.out.print(RESET_TEXT_BOLD_FAINT);
        System.out.print(RESET_TEXT_ITALIC);

        // Get the clients option
        //Scanner scanner = new Scanner(System.in);

        preLoginUI();

        scanner.close();
    }

    public static void preLoginUI() throws IOException, URISyntaxException, SQLException {
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
                    register();
                    break;
                case 2:
                    System.out.println("You selected Login.");
                    login();
                    break;
                case 3:
                    System.out.println("You selected Help.");
                    help();
                    break;
                case 4:
                    System.out.println("You selected Quit. Exiting...");
                    // Add your Quit logic here or simply break out of the loop
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        } while (option != 4);
    }

    public static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Help");
        System.out.println("4. Quit");
    }

    public static void register() throws IOException, URISyntaxException, SQLException {
        System.out.println("\n");
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

        RegisterRequest request = new RegisterRequest(username, password, email);
        RegisterResponse response = server.Register(request);

        // Display the entered information
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("\n");
        System.out.print(SET_TEXT_ITALIC);
        System.out.println("Registered! Welcome.");
        System.out.print(RESET_TEXT_ITALIC);

        postLoginUI(response.getAuthToken());
    }

    public static void login() throws IOException, URISyntaxException, SQLException {
        System.out.println("\n");
        scanner.nextLine();
        // Get username
        System.out.println("Logging in user...");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // Get password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = server.Login(request);

        // Display info
        System.out.println("\n");
        System.out.print(SET_TEXT_ITALIC);
        System.out.println("Welcome " + username + "!");
        System.out.print(RESET_TEXT_ITALIC);

        postLoginUI(response.getAuthToken());
    }

    public static void help() {
        System.out.println("\n");
        displayMenu();
    }

    public static void postLoginUI(String authToken) throws SQLException, IOException, URISyntaxException {
        System.out.println("\n");
        //scanner.nextLine();

        int option;
        do {
            //output the menu
            displayPostLoginMenu();
            System.out.print("Enter an option (1-6): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                System.out.print("Enter an option (1-6): ");
                scanner.next();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            // Process user choice
            switch (option) {
                case 1:
                    System.out.println("You selected List Games.");
                    break;
                case 2:
                    System.out.println("You selected Create Game.");
                    break;
                case 3:
                    System.out.println("You selected Join Game.");
                    break;
                case 4:
                    System.out.println("You selected Join Observer.");
                    // Add your Quit logic here or simply break out of the loop
                    break;
                case 5:
                    System.out.println("You selected Help....");
                    displayPostLoginMenu();
                    break;
                case 6:
                    System.out.println("You selected Logout. Logging out...");
                    logout(authToken);
                    // Add your Quit logic here or simply break out of the loop
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        } while (option != 6);
    }

    public static void displayPostLoginMenu() {
        System.out.println("\n");
        System.out.println("Menu:");
        System.out.println("1. List Games");
        System.out.println("2. Create Game");
        System.out.println("3. Join Game");
        System.out.println("4. Join Observer");
        System.out.println("5. Help");
        System.out.println("6. Logout");
    }

    public static void logout(String authToken) throws SQLException, IOException, URISyntaxException {
        LogoutResponse response = server.Logout(authToken);
    }
}

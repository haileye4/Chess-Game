import ChessUI.DrawBoard;
import chess.ChessGame;
import models.Game;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import responses.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

import static ChessUI.EscapeSequences.*;

public class Main {
    //make a local class variable scanner
    static Scanner scanner = new Scanner(System.in);
    static ServerFacade server = new ServerFacade();
    private static ArrayList<Game> allGames; //so we know which game to join

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
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Register.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    register();
                    break;
                case 2:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Login.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    login();
                    break;
                case 3:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Help.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    help();
                    break;
                case 4:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Quit. Exiting...");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
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

        try {
            RegisterResponse response = server.Register(request);
            // Display the entered information
            System.out.print(SET_TEXT_ITALIC);
            System.out.print(SET_TEXT_BOLD);
            // Display the entered information
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("Email: " + email);
            System.out.println("\n");
            System.out.println("Registered! Welcome.");
            System.out.print(RESET_TEXT_ITALIC);
            System.out.print(RESET_TEXT_BOLD_FAINT);
            postLoginUI(response.getAuthToken());
        } catch (RuntimeException ex) {
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_RED);
            System.out.println("Error: username already taken.");
            System.out.print(RESET_TEXT_BOLD_FAINT);
            System.out.print(RESET_TEXT_COLOR);
        }
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

        try {
            LoginResponse response = server.Login(request);

            // Display info
            System.out.println("\n");
            System.out.print(SET_TEXT_ITALIC);
            System.out.println("Welcome " + username + "!");
            System.out.print(RESET_TEXT_ITALIC);

            postLoginUI(response.getAuthToken());

        } catch (RuntimeException ex) {
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_RED);
            System.out.println("Error: Incorrect password or username.");
            System.out.print(RESET_TEXT_BOLD_FAINT);
            System.out.print(RESET_TEXT_COLOR);
        }
    }

    public static void help() {
        System.out.println("\n");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_BLUE);
        System.out.println("Welcome to the game of chess!");
        System.out.print(RESET_TEXT_BOLD_FAINT);

        System.out.println("Select one of 4 options from the pre-login menu to begin.");
        System.out.println("First, register a new user with an original username, password, and email.");

        System.out.print(SET_TEXT_ITALIC);
        System.out.println("An existing username may not be taken.");
        System.out.print(RESET_TEXT_ITALIC);

        System.out.println("If already registered, login with an existing username and password.");
        System.out.println("Once logged in, you may play and enjoy!");
        System.out.println("When you are done, select quit to end your session.");

        System.out.print(RESET_TEXT_COLOR);
        System.out.println("\n");
    }

    public static void postLoginUI(String authToken) throws SQLException, IOException, URISyntaxException {
        System.out.println("\n");
        //scanner.nextLine();
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_RED);
        System.out.println("Please list games before joining to play or observe.");
        System.out.print(RESET_TEXT_BOLD_FAINT);
        System.out.print(RESET_TEXT_COLOR);

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
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected List Games.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
                    listGames(authToken);
                    break;
                case 2:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Create Game.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
                    createGame(authToken);
                    break;
                case 3:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Join Game.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
                    joinGame(authToken);
                    break;
                case 4:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Join Observer.");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
                    joinGameObserver(authToken);
                    // Add your Quit logic here or simply break out of the loop
                    break;
                case 5:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Help....");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
                    postLoginHelp();
                    break;
                case 6:
                    System.out.print(SET_TEXT_BOLD);
                    System.out.print(SET_TEXT_COLOR_MAGENTA);
                    System.out.println("You selected Logout. Logging out...");
                    System.out.print(RESET_TEXT_BOLD_FAINT);
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("\n");
                    logout(authToken);
                    // Add your Quit logic here or simply break out of the loop
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        } while (option != 6);
    }

    public static void displayPostLoginMenu() {
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
        System.out.println("\n");
    }

    public static void listGames(String authToken) throws SQLException, IOException, URISyntaxException {
        ListGamesResponse response = server.ListGames(authToken);
        System.out.print(SET_TEXT_BOLD);
        if (response.gamesToString() == "") {
            //There are no games
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_GREEN);
            System.out.println("There are no games on the server. Create a new game!");
            System.out.print(RESET_TEXT_BOLD_FAINT);
            System.out.print(RESET_TEXT_COLOR);
            System.out.println("\n");
        } else {
            System.out.println(response.gamesToString());
        }
        System.out.print(RESET_TEXT_BOLD_FAINT);
        allGames = response.getGames();
        System.out.println("\n");
    }

    public static void createGame(String authToken) throws IOException, URISyntaxException {
        System.out.println("\n");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_MAGENTA);
        System.out.println("Creating a new game...");
        System.out.print(RESET_TEXT_BOLD_FAINT);
        System.out.print(RESET_TEXT_COLOR);

        System.out.println("\n");
        System.out.print("Enter your new game name: ");
        String gameName = scanner.nextLine();

        CreateGameRequest request = new CreateGameRequest(gameName);
        CreateGameResponse response = server.CreateGame(request, authToken);

        // Display the entered information
        System.out.println("Game Name: " + gameName);
        System.out.println("\n");
        System.out.print(SET_TEXT_ITALIC);
        System.out.println("New Game Created!");
        System.out.print(RESET_TEXT_ITALIC);
    }

    public static void joinGame(String authToken) throws IOException, URISyntaxException {
        boolean validInput = false;
        int selectedGame;
        ChessGame.TeamColor playerColor = null;

        do {System.out.println("\n");
            System.out.println("Joining a game...");
            System.out.print("Select a game number from the list of games: ");
            selectedGame = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Select the team color you wish to play: ");
            System.out.println("Options: black or white\n");
            String teamColor = scanner.nextLine();

            if (Objects.equals(teamColor, "black")) {
                validInput = true;
                playerColor = ChessGame.TeamColor.BLACK;
            } else if (Objects.equals(teamColor, "white")) {
                validInput = true;
                playerColor = ChessGame.TeamColor.WHITE;
            } else {
                System.out.println("ERROR: Invalid team option. Enter a valid team color: ");
            }
        } while (!validInput);

        JoinGameRequest request = new JoinGameRequest(playerColor, allGames.get(selectedGame - 1).getGameID());
        //JoinGameResponse response = server.JoinGame(request, authToken);

        try {
            JoinGameResponse response = server.JoinGame(request, authToken);
            // Display the entered information
            System.out.print(SET_TEXT_ITALIC);
            System.out.print(SET_TEXT_BOLD);
            System.out.println("Game number " + selectedGame + " Joined as " + playerColor + " player!");
            System.out.print(RESET_TEXT_ITALIC);
            System.out.print(RESET_TEXT_BOLD_FAINT);
            DrawBoard.printBoards();
            System.out.println("\n");
        } catch (RuntimeException ex) {
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_RED);
            System.out.println("Error: team color already taken.");
            System.out.print(RESET_TEXT_BOLD_FAINT);
            System.out.print(RESET_TEXT_COLOR);
        }
    }

    public static void joinGameObserver(String authToken) throws IOException, URISyntaxException, SQLException {
        boolean validInput = false;
        int selectedGame;

        System.out.println("\n");
        System.out.println("Observing a game...");
        System.out.print("Select a game number from the list of games: ");
        selectedGame = scanner.nextInt();
        scanner.nextLine();

        //join a game as "null" to be a watcher
        JoinGameRequest request = new JoinGameRequest(null, allGames.get(selectedGame - 1).getGameID());
        JoinGameResponse response = server.JoinGame(request, authToken);

        // Display the entered information
        System.out.print(SET_TEXT_ITALIC);
        System.out.print(SET_TEXT_BOLD);
        System.out.println("Game number " + selectedGame + " Joined as an observer!");
        System.out.print(RESET_TEXT_ITALIC);
        System.out.print(RESET_TEXT_BOLD_FAINT);

        //DrawBoard.drawChessboard(System.out, allGames.get(selectedGame - 1).getGameBoard());
        DrawBoard.printBoards();
        System.out.println("\n");
    }

    public static void postLoginHelp(){
        System.out.println("\n");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_BLUE);
        System.out.println("Welcome to the game of chess!");
        System.out.print(RESET_TEXT_BOLD_FAINT);
        System.out.println("Select one of 6 options from the menu to begin.");
        System.out.println("First, list games to see if any are available.");
        System.out.println("If no games are available, create a new one with an original game name.");
        System.out.println("Else, join a game from the list as a player or an observer. Play and enjoy!");
        System.out.println("When you are done, logout.");
        System.out.print(RESET_TEXT_COLOR);
        System.out.println("\n");
    }
}

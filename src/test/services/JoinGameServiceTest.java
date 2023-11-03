package services;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.JoinGameRequest;
import responses.JoinGameResponse;
import responses.ListGamesResponse;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameServiceTest {
    ArrayList<AuthToken> testTokens = new ArrayList<AuthToken>();

    ArrayList<Game> testGames = new ArrayList<Game>();
    JoinGameService service = new JoinGameService();

    @Test
    void joinGame() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");

        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        GameDAO games = new GameDAO();
        Game myGame = new Game();
        myGame.setGameID(1234);

        games.create(myGame);

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, myGame.getGameID());
        JoinGameResponse response = service.joinGame(request, "fancyToken");

        String player = null;
        for (Game game : games.read()) {
            if (game.getGameID() == 1234) {
                player = game.getWhiteUsername();
            }
        }

        Assertions.assertEquals("Billy Jean", player,
                "Valid player could not join valid game");

    }

    @Test
    void unauthorizedRequest() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        GameDAO games = new GameDAO();
        Game myGame = new Game();
        myGame.setGameID(1234);

        games.create(myGame);

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, myGame.getGameID());
        JoinGameResponse response = service.joinGame(request, "fakeToken");

        Assertions.assertEquals("Error: unauthorized", response.getMessage(),
                "Invalid user joined game");
    }

    @Test
    void gameDoesntExist() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        GameDAO games = new GameDAO();
        Game myGame = new Game();
        myGame.setGameID(1234);

        games.create(myGame);

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, 4567); //gameID doesn't exist!
        JoinGameResponse response = service.joinGame(request, "fancyToken");

        Assertions.assertEquals("Error: game doesn't exist", response.getMessage(),
                "User joined game that doesn't exist");
    }

    @Test
    void teamAlreadyTaken() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        GameDAO games = new GameDAO();
        Game myGame = new Game();
        myGame.setGameID(1234);
        myGame.setWhiteUsername("evilSam"); //someone has already joined the game as the white team

        games.create(myGame);

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, myGame.getGameID());
        JoinGameResponse response = service.joinGame(request, "fancyToken");

        Assertions.assertEquals("Error: already taken", response.getMessage(),
                "User joined a team that was already taken");
    }

    public void clearEverything() throws SQLException, DataAccessException {
        UserDAO users = new UserDAO();
        GameDAO games = new GameDAO();
        AuthDAO tokens = new AuthDAO();

        users.clear();
        games.clear();
        tokens.clear();
    }
}
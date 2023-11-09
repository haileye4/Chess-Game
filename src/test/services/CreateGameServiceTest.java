package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import responses.CreateGameResponse;

import java.sql.SQLException;

class CreateGameServiceTest {
    CreateGameService service = new CreateGameService();

    @Test
    void createGame() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        CreateGameRequest request = new CreateGameRequest("newFavoriteGame");
        CreateGameResponse response = service.createGame(request, "fancyToken");

        GameDAO games = new GameDAO();
        System.out.println(response.getGameID());
        Assertions.assertEquals(1, games.read().size(),
                "Game was not created");

    }

    @Test
    void badRequest() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        CreateGameRequest request = new CreateGameRequest(null); //bad request
        CreateGameResponse response = service.createGame(request, "fancyToken");

        GameDAO games = new GameDAO();
        System.out.println(response.getGameID());
        Assertions.assertEquals("Error: bad request (no game name included)", response.getMessage(),
                "Error message not received");
    }

    @Test
    void unauthorized() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        CreateGameRequest request = new CreateGameRequest("newFavoriteGame");
        CreateGameResponse response = service.createGame(request, "fakeToken");

        GameDAO games = new GameDAO();
        System.out.println(response.getGameID());
        Assertions.assertEquals(0, games.read().size(),
                "Unauthorized game was created");
        Assertions.assertEquals("Error: unauthorized", response.getMessage(),
                "Error message not received");
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
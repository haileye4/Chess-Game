package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import responses.ListGamesResponse;

import java.sql.SQLException;
import java.util.ArrayList;

class ListGamesServiceTest {

    ArrayList<AuthToken> testTokens = new ArrayList<AuthToken>();

    ArrayList<Game> testGames = new ArrayList<Game>();
    ListGamesService service = new ListGamesService();

    @Test
    void listGames() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");

        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);
        testTokens.add(testUser);

        GameDAO games = new GameDAO();
        Game myGame = new Game();

        games.create(myGame);
        testGames.add(myGame);

        ListGamesResponse response = service.listGames(testUser.getAuthToken());

        Assertions.assertEquals(testGames, games.read(),
                "Incorrect games listed");
        Assertions.assertNull(response.getMessage(),
                "Error message given");
    }

    @Test
    void invalidListGames() throws SQLException, DataAccessException {
        clearEverything();
        AuthToken testUser = new AuthToken("fancyToken", "Billy Jean");
        AuthToken fakeUser = new AuthToken("fakeToken", "Silly Sally");

        AuthDAO tokens = new AuthDAO();
        tokens.create(testUser);

        GameDAO games = new GameDAO();
        Game myGame = new Game();

        games.create(myGame);
        testGames.add(myGame);

        ListGamesResponse response = service.listGames(fakeUser.getAuthToken());

        Assertions.assertNull(response.getGames(),
                "Invalid user able to list games");
        Assertions.assertEquals(response.getMessage(), "Error: unauthorized",
                "Error message not given");
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
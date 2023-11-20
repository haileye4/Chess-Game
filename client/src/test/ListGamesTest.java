import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import responses.ListGamesResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ListGamesTest {
    ServerFacade server = new ServerFacade();
    GameDAO games = new GameDAO();
    AuthDAO tokens = new AuthDAO();

    @Test
    void listGames() throws SQLException, DataAccessException, IOException, URISyntaxException {
        games.create(new Game(1234, "whiteUser", "blackUser",
                "myCoolGame", new chess.Game()));
        games.create(new Game(5678, "whiteUser", "blackUser",
                "myCoolGame2", new chess.Game()));

        tokens.create(new AuthToken("fancyToken", "user"));

        ListGamesResponse response = server.ListGames("fancyToken");

        Assertions.assertEquals(2, response.getGames().size(), "Not all games were listed.");

        games.clear();
        tokens.clear();
    }

    @Test
    void unauthorizedListGames() throws SQLException, DataAccessException, IOException, URISyntaxException {
        games.create(new Game(1234, "whiteUser", "blackUser",
                "myCoolGame", new chess.Game()));
        games.create(new Game(5678, "whiteUser", "blackUser",
                "myCoolGame2", new chess.Game()));

        tokens.create(new AuthToken("fancyToken", "user"));

        ListGamesResponse response = server.ListGames("fancyToken");

        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.ListGames("wrongToken");
        }, "Expected RunTimeException for listing games with an unauthorized authToken.");


        games.clear();
        tokens.clear();
    }
}

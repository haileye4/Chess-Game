import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import responses.CreateGameResponse;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class CreateGameTest {
    ServerFacade server = new ServerFacade();
    GameDAO games = new GameDAO();
    AuthDAO tokens = new AuthDAO();

    @Test
    void createGame() throws SQLException, DataAccessException, IOException, URISyntaxException {
        games.clear();
        tokens.clear();

        tokens.create(new AuthToken("specialToken", "user"));

        CreateGameRequest request = new CreateGameRequest("gameName");

        CreateGameResponse response = server.CreateGame(request, "specialToken");

        Assertions.assertEquals("gameName", games.read().get(0).getGameName(), "Game was not added to database");

        games.clear();
        tokens.clear();

    }

    @Test
    void badCreateGame() throws SQLException, DataAccessException, IOException, URISyntaxException {
        //with a bad authToken
        games.clear();
        tokens.clear();

        tokens.create(new AuthToken("specialToken", "user")); //add user and auhtToken into data

        CreateGameRequest request = new CreateGameRequest("gameName");

        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.CreateGame(request, "badToken");
        }, "Expected RunTimeException for creating a game with an incorrect authToken.");

        games.clear();
        tokens.clear();
    }
}

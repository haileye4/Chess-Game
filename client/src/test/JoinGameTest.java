import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.JoinGameRequest;
import responses.JoinGameResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class JoinGameTest {
    ServerFacade server = new ServerFacade();
    GameDAO games = new GameDAO();
    AuthDAO tokens = new AuthDAO();

    @Test
    void joinGame() throws SQLException, DataAccessException, IOException, URISyntaxException {
        games.clear();
        tokens.clear();

        //create authToken and game
        games.create(new Game(1234, null, "blackUser",
                "myCoolGame", new chess.Game()));
        tokens.create(new AuthToken("fancyToken", "user"));

        //make request and response
        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, 1234);
        JoinGameResponse response = server.JoinGame(request, "fancyToken");

        Assertions.assertEquals("user", games.find(1234).getWhiteUsername(), "" +
                "User did not join game as the white player.");

        games.clear();
        tokens.clear();
    }

    @Test
    void badJoinGame() throws SQLException, DataAccessException, IOException, URISyntaxException {
        games.clear();
        tokens.clear();

        //create authToken and game
        games.create(new Game(1234, "takenUser", "blackUser",
                "myCoolGame", new chess.Game()));
        tokens.create(new AuthToken("fancyToken", "user"));

        //make request to join as a team color which is already taken
        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, 1234);

        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.JoinGame(request, "fancyToken");
        }, "Expected RunTimeException for joining a game color which is already taken.");

        games.clear();
        tokens.clear();
    }
}

package database;

import chess.Board;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class GameDAOTest {
    GameDAO games = new GameDAO();
    @Test
    public void insertGame() throws SQLException, DataAccessException {
        games.clear();
        //create a new game and insert into database
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());

        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);
        games.create(game);
    }

    @Test
    public void read() throws SQLException, DataAccessException {
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        ArrayList<Game> expectedGames = new ArrayList<>();
        expectedGames.add(game);

        ArrayList<Game> actualGames = games.read();

        boolean areEqual = Objects.equals(expectedGames.size(), actualGames.size());

        Assertions.assertTrue(areEqual, "Did not read database correctly");
        Assertions.assertEquals(expectedGames.get(0).getGameID(), actualGames.get(0).getGameID(),
                "incorrect game was read");

    }
}

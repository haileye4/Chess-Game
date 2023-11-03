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

public class GameDAOTest {
    GameDAO games = new GameDAO();
    @Test
    public void insertGame() throws SQLException, DataAccessException {
        games.clear();
        //create a new game and insert into database
        ArrayList<String> watchers = new ArrayList<>();
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());

        Game game = new Game(1234, "white", "black", watchers, "myCoolGame", myGame);
        games.create(game);
    }
}

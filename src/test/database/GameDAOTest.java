package database;

import chess.Board;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class GameDAOTest {
    GameDAO games = new GameDAO();

    @Test
    public void clear() throws SQLException, DataAccessException {
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        games.create(game);

        games.clear();
        ArrayList<Game> shouldBeEmpty = games.read();

        Assertions.assertTrue(shouldBeEmpty.isEmpty(), "Didn't clear games correctly");
    }

    @Test
    public void insertGame() throws SQLException, DataAccessException {
        games.clear();
        //create a new game and insert into database
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());

        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);
        games.create(game);

        //see if new user is found in the database
        Game gameFound = games.find(1234);
        Boolean found = gameFound.equals(game);

        Assertions.assertEquals(true, found,
                "Game inserted was not found");

        games.clear();
    }

    @Test
    public void badInsert() throws SQLException, DataAccessException {
        games.clear();
        //create a new game and insert into database
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());

        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);
        games.create(game);

        Game duplicateGame = new Game(1234, "white", "black", "myCoolGame", myGame);
        Assertions.assertThrows(DataAccessException.class, () -> games.create(duplicateGame));
        games.clear();
    }

    @Test
    public void find() throws SQLException, DataAccessException {
        //create a new game and insert into database
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());

        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);
        games.create(game);

        //see if authToken is found in the database
        Game gameFound = games.find(1234);

        Boolean areEqual = Objects.equals(gameFound, game);
        Assertions.assertEquals(true, areEqual,
                "game was not found");

        games.clear();
    }

    @Test
    public void badFind() throws SQLException, DataAccessException {
        //try to find a user that is not there
        games.clear();
        //create a new game and insert into database
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());

        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);
        games.create(game);

        //see if invalid user is found in the database
        Game gameFound = games.find(5678);

        Assertions.assertNull(gameFound, "User not inserted was still found");

        games.clear();
    }

    @Test
    //This test is not needed for the assignment. Just making sure my personal read() function
    // lists out all the games correctly
    public void readGames() throws SQLException, DataAccessException {
        games.clear();

        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        games.create(game);

        ArrayList<Game> expectedGames = new ArrayList<>();
        expectedGames.add(game);

        ArrayList<Game> actualGames = games.read();

        boolean areEqual = Objects.equals(expectedGames.size(), actualGames.size());

        Assertions.assertTrue(areEqual, "Did not read database correctly");
        Assertions.assertEquals(expectedGames.get(0).getGameID(), actualGames.get(0).getGameID(),
                "incorrect game was read");

    }

    @Test
    public void deleteGame() throws SQLException, DataAccessException {
        //create a new game and insert into database
        games.clear();

        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        games.create(game);

        //delete new user
        games.delete(game);
        Game gameFound = games.find(1234);

        // LOOK AT INVALID MOVES TEST Assertions.assertThrows(DataAccessException.class, users.read() )
        Assertions.assertNull(gameFound,
                "Game was not deleted from database");

        games.clear();
    }

    @Test
    public void badDelete() throws SQLException, DataAccessException {
        //delete a user which does not exist
        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        games.create(game);

        Game nonexistentGame = new Game(7777, "PapaElf", "milkAndCookies", "christmasGame", myGame);
        Assertions.assertThrows(DataAccessException.class, () -> games.delete(nonexistentGame));

        games.clear();
    }

    @Test
    public void updateGame() throws SQLException, DataAccessException {
        games.clear();

        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        games.create(game);

        //try to update game now...
        game.setGameName("newGameNameToUpdate");
        games.update(game);

        Assertions.assertEquals("newGameNameToUpdate", games.find(1234).getGameName(),
                "Game was not updated correctly");

        games.clear();
    }

    @Test
    public void badUpdate() throws SQLException, DataAccessException {
        games.clear();

        chess.Game myGame = new chess.Game();
        myGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        myGame.setBoard(new Board());
        Game game = new Game(1234, "white", "black", "myCoolGame", myGame);

        games.create(game);

        //try to update a game NOT in the database now...
        Game fakeGame = new Game(5678, "white", "black", "myDifferentCoolGame", myGame);
        Assertions.assertThrows(DataAccessException.class, () -> games.update(fakeGame));

        games.clear();
    }
}

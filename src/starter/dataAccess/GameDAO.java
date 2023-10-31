package dataAccess;

import chess.ChessGame;
import models.Game;

import java.util.ArrayList;

/**
 * stores games in the database
 */
public class GameDAO {
    /**
     * list of all our games (for now)
     */
    static ArrayList<Game> games = new ArrayList<Game>();

    /**
     * create a new game
     * @param game
     * @throws DataAccessException
     */
    public void create(Game game) /*throws DataAccessException */{
        /*if (games.contains(game)) {
            throw new DataAccessException("Game already in memory");
        }*/

        games.add(game);
    }

    /**
     * read all games in database
     * @return list of games
     * @throws DataAccessException
     */
    public ArrayList<Game> read() /*throws DataAccessException*/{
        return games;
    }

    /**
     * update a game
     * @param game
     * @throws DataAccessException
     */
    public void update(Game game) throws DataAccessException{
        if (!games.contains(game)) {
            throw new DataAccessException("Nonexistent game");
        }
    }

    /**
     * delete a game
     * @param game
     * @throws DataAccessException
     */
    public void delete(Game game) throws DataAccessException {
        games.remove(game);
    }

    public void clear() {
        games.clear();
    }
}


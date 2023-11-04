package dataAccess;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Game;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

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
    public void create(Game game) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        //how do I use connection now?
        String sql = "insert into game (gameID, whiteUsername, blackUsername, gameName, game) " +
                "values (?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, game.getGameID());
            stmt.setString(2, game.getWhiteUsername());
            stmt.setString(3, game.getBlackUsername());
            stmt.setString(4, game.getGameName());

            // Serialize and store the chess game JSON.
            var json = new Gson().toJson(game.getGame());
            stmt.setString(5, json);

            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Inserting game failed, no rows affected.");
            }

            // Log or handle the result as needed
            System.out.println("Added game into user table");
        } catch (SQLException e) {
            throw new DataAccessException("Could not insert user into the database");
        }

        //NEED TO DELETE CONNECTION!
        database.closeConnection(connection);
    }

    /**
     * read all games in database
     * @return list of games
     * @throws DataAccessException
     */
    public ArrayList<Game> read() throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        ArrayList<Game> games = new ArrayList<>();

        String sql = "select gameID, whiteUsername, blackUsername, gameName, game from game";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int gameID = rs.getInt(1);
                String whiteUsername = rs.getString(2);
                String blackUsername = rs.getString(3);
                String gameName = rs.getString(4);

                //deserialize the JSON of the game data
                var json = rs.getString(5);

                //var game = new Gson().fromJson(json, chess.Game.class);
                var builder = new GsonBuilder();

                Gson gson = builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter())
                        .registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter())
                        .registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter())
                        .create();

                var game = gson.fromJson(json, chess.Game.class);

                games.add(new Game(gameID, whiteUsername, blackUsername, gameName, game));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Can not read out games");
        }

        database.closeConnection(connection);
        return games;
    }

    /**
     * find a game in the database
     * @param gameID
     * @return
     * @throws DataAccessException
     * @throws SQLException
     */
    public Game find(int gameID) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "select gameID, whiteUsername, blackUsername," +
                " gameName, game from game where gameID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, gameID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");

                    //deserialize the JSON of the game data
                    var json = rs.getString("game");

                    //var game = new Gson().fromJson(json, chess.Game.class);
                    var builder = new GsonBuilder();

                    Gson gson = builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter())
                            .registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter())
                            .registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter())
                            .create();

                    var game = gson.fromJson(json, chess.Game.class);

                    return new Game(gameID, whiteUsername, blackUsername, gameName, game);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding authToken");
        } finally {
            // Close the connection
            database.closeConnection(connection);
        }

        // If no matching token is found, return null or handle appropriately
        return null;
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
    public void delete(Game game) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "delete from game where gameID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, game.getGameID());

            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Deleting game failed, no matching rows found.");
            }

            // Log or handle the result as needed
            System.out.println("Deleted game from game table");
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting game");
        }

        database.closeConnection(connection);
    }

    public void clear() throws SQLException, DataAccessException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "delete from game";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Log or handle the result as needed
            System.out.println("Cleared " + affectedRows + " records from user table");
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing game table");
        } finally {
            // Close the connection
            database.closeConnection(connection);
        }
    }
}


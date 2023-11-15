package dataAccess;

import models.AuthToken;

import java.sql.*;
import java.util.ArrayList;

/**
 * stores authTokens in the database
 */
public class AuthDAO {

    /**
     * create a new authentication token
     * @param token
     * @throws DataAccessException
     */
    public void create(AuthToken token) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        //how do I use connection now?
        String sql = "insert into authToken (authToken, username) values (?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthToken());
            stmt.setString(2, token.getUsername());

            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Inserting token failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not insert new authToken into the database");
        }

        //NEED TO DELETE CONNECTION!
        database.closeConnection(connection);
    }

    /**
     * read all authetication tokens
     * @return list of authTokens
     * @throws DataAccessException
     */
    public ArrayList<AuthToken> read() throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        ArrayList<AuthToken> tokens = new ArrayList<>();

        String sql = "select authToken, username from authToken";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                String authToken = rs.getString(1);
                String username = rs.getString(2);

                tokens.add(new AuthToken(authToken, username));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Can not read out authTokens");
        }

        database.closeConnection(connection);
        return tokens;
    }

    /**
     * find a certain authToken in the database
     * @param authToken
     * @return
     */
    public AuthToken find(String authToken) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "select authToken, username from authToken where authToken = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authToken);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    return new AuthToken(authToken, username);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding AuthToken");
        } finally {
            // Close the connection
            database.closeConnection(connection);
        }

        // If no matching token is found, return null or handle appropriately
        return null;
    }

    /**
     * delete an authentication token from our database
     * @param token
     * @throws DataAccessException
     */
    public void delete(AuthToken token) throws SQLException, DataAccessException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "delete from authToken where authToken = ? and username = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthToken());
            stmt.setString(2, token.getUsername());

            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Deleting token failed, no matching rows found.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting AuthToken");
        }

        database.closeConnection(connection);
    }

    /**
     * clear the database of all instances
     * @throws DataAccessException
     * @throws SQLException
     */
    public void clear() throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "DELETE FROM authToken";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Log or handle the result as needed
            System.out.println("Cleared " + affectedRows + " records from authToken table");
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing authToken table");
        } finally {
            // Close the connection
            database.closeConnection(connection);
        }
    }
}

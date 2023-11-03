package dataAccess;

import models.AuthToken;

import java.sql.*;
import java.util.ArrayList;

/**
 * stores authTokens in the database
 */
public class AuthDAO {
    /**
     * list that holds authTokens (for now)
     */
    static ArrayList<AuthToken> tokens = new ArrayList<AuthToken>();

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
        }

        tokens.add(token);

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
            throw new RuntimeException(e);
        }

        database.closeConnection(connection);
        return tokens;
    }

    /**
     * update an authToken
     * @param token
     * @throws DataAccessException
     */
    public void update(AuthToken token) throws DataAccessException {

    }

    /**
     * delete an authentication token from our database
     * @param token
     * @throws DataAccessException
     */
    public void delete(AuthToken token) /*throws DataAccessException*/ {
        tokens.remove(token);
    }

    public void clear() {
        tokens.clear();
    }
}

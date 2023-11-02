package dataAccess;

import models.AuthToken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        /*String connectionURL = "jdbc:mysql://localhost:3306/chess?" +
                "user=root&password=mypassword";

        Connection connection = null;
        try(Connection c = DriverManager.getConnection(connectionURL)) {
            connection = c;

            // Start a transaction
            connection.setAutoCommit(false);
        } catch(SQLException ex) {
            // ERROR
        }*/
        Database database = new Database();
        Connection connection = database.getConnection();

        //how do I use connection now?

        tokens.add(token);

        //NEED TO DELETE CONNECTION!
        database.closeConnection(connection);
    }

    /**
     * read all authetication tokens
     * @return list of authTokens
     * @throws DataAccessException
     */
    public ArrayList<AuthToken> read() {
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

package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.User;
import responses.ClearApplicationResponse;

import java.sql.SQLException;

/**
 * clear application service
 */
public class ClearApplicationService extends Service {
    /**
     * clear application
     * @return response
     */
    public ClearApplicationResponse clearApplication() throws SQLException, DataAccessException {
        //Clears the database. Removes all users, games, and authTokens.
        ClearApplicationResponse response = new ClearApplicationResponse();

        AuthDAO tokens = new AuthDAO();
        GameDAO games = new GameDAO();
        UserDAO users = new UserDAO();

        tokens.clear();
        games.clear();
        users.clear();

        return response;
    }
}

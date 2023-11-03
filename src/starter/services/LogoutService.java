package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import responses.LogoutResponse;

import java.sql.SQLException;
import java.util.Objects;

/**
 * logout service
 */
public class LogoutService extends Service {
    /**
     * logout of server
     * @return response
     */
    public LogoutResponse logout(String authToken) throws SQLException, DataAccessException {
        LogoutResponse response = new LogoutResponse();
        AuthDAO tokens = new AuthDAO();

        //if authToken is not in the database...
        boolean isAuthorized = isAuthorized(authToken);

        if (!isAuthorized) {
            response.setMessage("Error: unauthorized");
            return response;
        }

        //else if auth token is in, and we can actually log out...
        //remove auth token from database
        AuthToken tokenToDelete = null;
        for (AuthToken token : tokens.read()) {
            if (Objects.equals(token.getAuthToken(), authToken)) {
                tokenToDelete = token;
            }
        }
        tokens.delete(tokenToDelete);

        //no need for a message or anything.
        return response;
    }
}

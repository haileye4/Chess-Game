package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;

import java.sql.SQLException;
import java.util.Objects;

public class Service {
    public boolean isAuthorized(String authToken) throws SQLException, DataAccessException {
        AuthDAO tokens = new AuthDAO();

        boolean isAuthorized = false;
        for (AuthToken token : tokens.read()) {
            if (Objects.equals(token.getAuthToken(), authToken)) {
                isAuthorized = true;
                break;
            }
        }

        return isAuthorized;
    }
}

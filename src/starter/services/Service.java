package services;

import dataAccess.AuthDAO;
import models.AuthToken;

import java.util.Objects;

public class Service {
    public boolean isAuthorized(String authToken) {
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

package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import request.LoginRequest;
import responses.LoginResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * login service
 */
public class LoginService extends Service {
    /**
     * login
     * @param request
     * @return response
     */
    public LoginResponse login(LoginRequest request) throws SQLException, DataAccessException {
        String username = request.getUsername();
        String password = request.getPassword();

        LoginResponse response = new LoginResponse();
        UserDAO users = new UserDAO();

        boolean userFound = false;
        for (User user : users.read()) {
            if (Objects.equals(user.getUsername(), username)) {
                if (Objects.equals(user.getPassword(), password)) {
                    userFound = true;
                }
            }

        }

        if (!userFound) {
            response.setMessage("Error: user not found");
            return response;
        }

        //if user is found, set the response to include their username and authtoken.
        // Add the new authToken into the DAO.
        response.setUsername(username);

        AuthToken token = new AuthToken();
        token.setAuthToken(UUID.randomUUID().toString());
        token.setUsername(username);
        response.setAuthToken(token.getAuthToken());

        AuthDAO authData = new AuthDAO();
        authData.create(token);

        return response;
    }
}

package services;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import request.RegisterRequest;
import responses.RegisterResponse;

import java.util.Objects;
import java.util.UUID;

/**
 * register service
 */
public class RegisterService extends Service {
    /**
     * register a user
     * @param request
     * @return response
     */
    public RegisterResponse register(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        RegisterResponse response = new RegisterResponse();

        if (username == null || password == null || email == null) {
            response.setMessage("Error: bad request");
            return response;
        }

        //check to see if username or email is already in system
        UserDAO users = new UserDAO();

        boolean alreadyIn = false;
        if (users.read() != null) {
            for (User user : users.read()) {
                if (Objects.equals(user.getUsername(), username) || Objects.equals(user.getEmail(), email)) {
                    alreadyIn = true;
                }
            }
        }


        if (alreadyIn) {
            response.setMessage("Error: already taken");
            return response;
        }

        //else, it's not a bad request and the username or email is not already in the system, then lets make a new user.
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        users.create(newUser);

        //add username to response
        response.setUsername(username);

        //add auth token to response and to database
        AuthToken token = new AuthToken();
        token.setAuthToken(UUID.randomUUID().toString());
        token.setUsername(username);
        response.setAuthToken(token.getAuthToken());

        AuthDAO authData = new AuthDAO();
        authData.create(token);

        //return response
        return response;
    }
}

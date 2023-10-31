package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import responses.LoginResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    ArrayList<User> testUsers = new ArrayList<User>();
    LoginService service = new LoginService();

    @Test
    void normalLogin() {
        clearEverything();
        User user1 = new User("noah","1234", "coolGuy@gmail.com");
        User user2 = new User("hailey", "love00", "happy.email.com");

        testUsers.add(user1);
        testUsers.add(user2);

        LoginRequest request = new LoginRequest("noah", "1234");
        //requesting to log in existing user

        UserDAO users = new UserDAO();
        users.create(user1);
        users.create(user2);
        //add users into database...

        LoginResponse response = service.login(request);
        //attempt login with existing user
        AuthDAO tokens = new AuthDAO();
        //see if the user was given an authToken

        Assertions.assertEquals(tokens.read().size(), 1,
                "Existing user could not log in");
        Assertions.assertEquals(tokens.read().get(0).getUsername(), "noah",
                "Incorrect user put in database");
        Assertions.assertNull(response.getMessage(),
                "Error message detected");
        //check to see if they are in auth token DAO
    }

    @Test
    void invalidLogin() {
        clearEverything();
        User user1 = new User("noah","1234", "coolGuy@gmail.com");
        testUsers.add(user1);

        LoginRequest request = new LoginRequest("noah", "wrongPassword");
        //requesting to log in existing user with the wrong password

        UserDAO users = new UserDAO();
        users.create(user1);
        //add users into database...

        LoginResponse response = service.login(request);
        //attempt login with existing user
        AuthDAO tokens = new AuthDAO();
        //see if the user was given an authToken

        Assertions.assertEquals(tokens.read().size(), 0,
                "Invalid login worked");
        Assertions.assertEquals(response.getMessage(), "Error: user not found",
                "No error message detected");
        //check to see if they are in auth token DAO
    }

    public void clearEverything() {
        UserDAO users = new UserDAO();
        GameDAO games = new GameDAO();
        AuthDAO tokens = new AuthDAO();

        users.clear();
        games.clear();
        tokens.clear();
    }
}
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import responses.LoginResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class LoginTest {
    ServerFacade server = new ServerFacade();
    UserDAO users = new UserDAO();
    AuthDAO tokens = new AuthDAO();

    @Test
    void login() throws SQLException, DataAccessException, IOException, URISyntaxException {
        users.clear();
        tokens.clear();

        users.create(new User("myUsername", "secretPassword", "fancyEmail@gmail.com"));

        LoginRequest request = new LoginRequest("myUsername", "secretPassword");
        LoginResponse response = server.Login(request);

        Assertions.assertEquals("myUsername", tokens.read().get(0).getUsername(), "User was not logged in " +
                "and given an authToken.");

        users.clear();
        tokens.clear();
    }

    @Test
    void badLogin() throws SQLException, DataAccessException, IOException, URISyntaxException {
        users.clear();
        tokens.clear();

        users.create(new User("myUsername", "secretPassword", "fancyEmail@gmail.com"));

        LoginRequest request1 = new LoginRequest("wrongUsername", "secretPassword");
        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.Login(request1);
        }, "Expected RunTimeException for logging in with the wrong username.");


        LoginRequest request2 = new LoginRequest("myUsername", "wrongPassword");
        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.Login(request2);
        }, "Expected RunTimeException for logging in with the wrong password.");

        users.clear();
        tokens.clear();
    }
}

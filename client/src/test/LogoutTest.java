import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import responses.LoginResponse;
import responses.LogoutResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class LogoutTest {
    ServerFacade server = new ServerFacade();
    UserDAO users = new UserDAO();
    AuthDAO tokens = new AuthDAO();

    @Test
    void logout() throws SQLException, DataAccessException, IOException, URISyntaxException {
        users.clear();
        tokens.clear();

        users.create(new User("myUsername", "secretPassword", "fancyEmail@gmail.com"));
        tokens.create(new AuthToken("fancytoken", "myUsername"));

        LogoutResponse response = server.Logout("fancytoken");

        Assertions.assertEquals(0, tokens.read().size(), "User was not logged out.");

        users.clear();
        tokens.clear();
    }

    @Test
    void badLogout() throws SQLException, DataAccessException, IOException, URISyntaxException {
        users.clear();
        tokens.clear();

        users.create(new User("myUsername", "secretPassword", "fancyEmail@gmail.com"));
        tokens.create(new AuthToken("fancytoken", "myUsername"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.Logout("wrongtoken");
        }, "Expected RunTimeException for logging out with the wrong authToken.");

        users.clear();
        tokens.clear();
    }
}

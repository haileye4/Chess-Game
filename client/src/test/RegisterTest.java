import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import responses.RegisterResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class RegisterTest {
    ServerFacade server = new ServerFacade();
    UserDAO users = new UserDAO();
    AuthDAO tokens = new AuthDAO();

    @Test
    void registerNewUser() throws SQLException, DataAccessException, IOException, URISyntaxException {
        users.clear();
        tokens.clear();

        RegisterRequest request = new RegisterRequest("turkey", "gravy", "email@thanksgiving");
        RegisterResponse response = server.Register(request);

        Assertions.assertEquals("turkey", users.read().get(0).getUsername(), "User was not added to database");

        users.clear();
        tokens.clear();
    }

    @Test
    void badRegister() throws SQLException, DataAccessException, IOException, URISyntaxException {
        users.clear();
        tokens.clear();

        RegisterRequest request = new RegisterRequest("turkey", "gravy", "email@thanksgiving");
        RegisterResponse response = server.Register(request);
        
        RegisterRequest badRequest = new RegisterRequest("turkey", "gravy", "email");

        RegisterResponse badResponse;
        Assertions.assertThrows(RuntimeException.class, () -> {
            // Call the method that should throw a RuntimeException
            server.Register(badRequest);
        }, "Expected RunTimeException for registering a user with an already existing username.");

        System.out.println("Correctly threw a runtime exception!");

        users.clear();
        tokens.clear();
    }
}

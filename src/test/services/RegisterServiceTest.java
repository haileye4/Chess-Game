package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import responses.RegisterResponse;

import java.sql.SQLException;
import java.util.ArrayList;

class RegisterServiceTest {

    ArrayList<User> users = new ArrayList<User>();
    RegisterService service = new RegisterService();

    @Test
    void register() throws SQLException, DataAccessException {
        clearEverything();
        //test a basic register
        User user1 = new User("noah","1234", "coolGuy@gmail.com");
        User user2 = new User("hailey", "love00", "happy.email.com");

        users.add(user2);
        users.add(user1);

        RegisterRequest request1 = new RegisterRequest("noah","1234", "coolGuy@gmail.com");
        RegisterRequest request2 = new RegisterRequest("hailey", "love00", "happy.email.com");

        RegisterResponse response1 = service.register(request1);
        RegisterResponse response2 = service.register(request2);

        //make sure users are in DAO...
        UserDAO allUsers = new UserDAO();

        Assertions.assertEquals(users, allUsers.read(),
                "users not found in DAO");
        Assertions.assertEquals(response1.getMessage(), response2.getMessage(),
                "response messages do not match");
    }

    @Test
    void registerBadRequest() throws SQLException, DataAccessException {
        clearEverything();
        //register someone with no username
        RegisterRequest badRequest = new RegisterRequest(null,"1234", "coolGuy@gmail.com");
        RegisterResponse response1 = service.register(badRequest);

        RegisterRequest badRequest2 = new RegisterRequest("joe",null, "coolGuy@gmail.com");
        RegisterResponse response2 = service.register(badRequest2);

        //make sure users are not in DAO...
        UserDAO allUsers = new UserDAO();

        Assertions.assertEquals(0, allUsers.read().size(),
                "Bad users were put into the database");
        Assertions.assertEquals(response1.getMessage(), "Error: bad request",
                "Did not get correct error message");
        Assertions.assertEquals(response2.getMessage(), "Error: bad request",
                "Did not get correct error message");
    }

    @Test
    void registerExistingUser() throws SQLException, DataAccessException {
        clearEverything();
        //register someone who is already in the database
        User noah = new User("noah","1234", "coolGuy@gmail.com");
        User hailey = new User("hailey", "love00", "happy@email.com");
        users.add(hailey);
        users.add(noah);

        RegisterRequest request1 = new RegisterRequest("noah","1234", "coolGuy@gmail.com");
        RegisterRequest request2 = new RegisterRequest("hailey", "love00", "happy@email.com");

        RegisterResponse response1 = service.register(request1);
        RegisterResponse response2 = service.register(request2);

        //try to register an existing user with same email
        RegisterRequest badRequest = new RegisterRequest("fakeNoah","5678", "coolGuy@gmail.com");
        RegisterResponse badRegister = service.register(badRequest);

        //make sure users are in DAO...
        UserDAO allUsers = new UserDAO();

        Assertions.assertEquals(users, allUsers.read(),
                "Incorrect users found in DAO");
        Assertions.assertEquals(badRegister.getMessage(), "Error: already taken",
                "Did not get correct error message");
    }

    public void clearEverything() throws SQLException, DataAccessException {
        UserDAO users = new UserDAO();
        GameDAO games = new GameDAO();
        AuthDAO tokens = new AuthDAO();

        users.clear();
        games.clear();
        tokens.clear();
    }
}
package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ClearApplicationServiceTest {
    ClearApplicationService service = new ClearApplicationService();

    @Test
    void clearApplication() throws SQLException, DataAccessException {
        //make sure this service clears the databases...
        UserDAO users = new UserDAO();
        AuthDAO tokens = new AuthDAO();
        GameDAO games = new GameDAO();

        users.create(new User("TimmyJ", "coolestPassword", "tim@happy.com"));
        tokens.create(new AuthToken("happyKid1234", "TimmyJ"));
        games.create(new Game());

        service.clearApplication();

        Assertions.assertEquals(0, games.read().size(),
                "Game was not deleted");
        Assertions.assertEquals(0, tokens.read().size(),
                "Auth token was not deleted");
        Assertions.assertEquals(0, users.read().size(),
                "User was not deleted");
    }
}
package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import responses.LogoutResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {
    ArrayList<AuthToken> testTokens = new ArrayList<AuthToken>();
    LogoutService service = new LogoutService();

    @Test
    void logoutTest() {
        clearEverything();
        AuthDAO tokens = new AuthDAO();

        AuthToken user1 = new AuthToken("bobToken", "bob");
        AuthToken user2 = new AuthToken("sallyToken", "sally");

        tokens.create(user1);
        tokens.create(user2);

        testTokens.add(user1);
        testTokens.add(user2);

        //fake "log out" of server:
        testTokens.remove(user2);
        service.logout(user2.getAuthToken());

        Assertions.assertEquals(tokens.read(), testTokens,
                "DAO does not match what should be there");
    }

    @Test
    void invalidLogout() {
        clearEverything();
        //logout with an auth token that is not in the database
        AuthToken user1 = new AuthToken("bobToken", "bob");
        AuthToken user2 = new AuthToken("sallyToken", "sally");

        AuthDAO tokens = new AuthDAO();
        tokens.create(user1);
        tokens.create(user2);

        AuthToken fakeUser = new AuthToken("fakeToken", "mr.FakeMan");

        LogoutResponse response = service.logout(fakeUser.getAuthToken());

        Assertions.assertEquals(response.getMessage(), "Error: unauthorized",
                "Fake authToken was able to logout");
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
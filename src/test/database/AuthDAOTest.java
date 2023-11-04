package database;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AuthDAOTest {
    AuthDAO tokens = new AuthDAO();

    @Test
    public void clear() throws SQLException, DataAccessException {
        AuthToken token = new AuthToken("fancyToken", "Mr.Fancy1234");
        tokens.create(token);

        tokens.clear();
        ArrayList<AuthToken> shouldBeEmpty = tokens.read();

        Assertions.assertTrue(shouldBeEmpty.isEmpty(), "Didn't clear tokens correctly");
    }

    @Test
    public void insert() throws SQLException, DataAccessException {
        //create an authToken to test insert
        AuthToken fancy = new AuthToken("fancyToken", "Mr.Fancy1234");
        tokens.create(fancy);

        //see if token inserted was found
        AuthToken tokenFound = tokens.find("fancyToken");
        Boolean found = tokenFound.equals(fancy);

        Assertions.assertEquals(true, found,
                "Token inserted was not found");

        tokens.clear();
    }

    @Test
    public void badInsert() throws SQLException, DataAccessException {
        tokens.clear();
        //attempt to insert someone with an already used username
        AuthToken token = new AuthToken("specialToken", "happyCow4");
        tokens.create(token);

        AuthToken duplicateToken = new AuthToken("specialToken", "happyCow4");
        Assertions.assertThrows(DataAccessException.class, () -> tokens.create(duplicateToken));

    }

    @Test
    public void find() throws SQLException, DataAccessException {
        //try to find an authToken
        AuthToken token1 = new AuthToken("cookiesAndMilk", "SantaClaus");
        tokens.create(token1);

        //see if authToken is found in the database
        AuthToken tokenFound = tokens.find("cookiesAndMilk");

        Boolean areEqual = Objects.equals(tokenFound, token1);
        Assertions.assertEquals(true, areEqual,
                "token was not found");

        tokens.clear();
    }
    @Test
    public void badFind() throws SQLException, DataAccessException {
        //try to find a token that is not there
        AuthToken token1 = new AuthToken("cookiesAndMilk", "SantaClaus");
        tokens.create(token1);

        //see if invalid authToken is found in the database
        AuthToken tokenFound = tokens.find("milkAndCookies");

        Assertions.assertNull(tokenFound, "AuthToken not inserted was still found");

        tokens.clear();
    }

    @Test
    //This test is not needed for the assignment. Just making sure my personal read() function
    // lists out all the games correctly
    public void read() throws SQLException, DataAccessException {
        tokens.clear();
        AuthToken token1 = new AuthToken("fancyToken", "Mr.Fancy1234");
        AuthToken token2 = new AuthToken("superSecretStuff", "JamesBond");
        AuthToken token3 = new AuthToken("topSecret", "JohnSmith");
        tokens.create(token1);
        tokens.create(token2);
        tokens.create(token3);

        ArrayList<AuthToken> expected = new ArrayList<>();
        expected.add(token1);
        expected.add(token2);
        expected.add(token3);

        ArrayList<AuthToken> actual = tokens.read();

        // Check if the ArrayLists are equal
        boolean areEqual = Objects.equals(expected, actual);

        Assertions.assertTrue(areEqual, "Did not read database correctly");

        tokens.clear();
    }

    @Test
    public void delete() throws SQLException, DataAccessException {
        AuthToken token = new AuthToken("fancyToken", "Mr.Fancy1234");
        tokens.create(token);

        tokens.delete(token);
        ArrayList<AuthToken> shouldBeEmpty = tokens.read();

        Assertions.assertTrue(shouldBeEmpty.isEmpty(), "Didn't delete authToken correctly");
        tokens.clear();
    }

    @Test
    public void badDelete() throws SQLException, DataAccessException {
        //try to delete a token that's not there
        AuthToken token = new AuthToken("fancyToken", "Mr.Fancy1234");
        tokens.create(token);

        AuthToken nonexistentToken = new AuthToken("notFound", "helloWorld");

        Assertions.assertThrows(DataAccessException.class, () -> tokens.delete(nonexistentToken));
        tokens.clear();
    }

}

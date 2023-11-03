package database;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AuthDAOTest {
    //make sure to clear once you are done with test
    AuthDAO tokens = new AuthDAO();
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
    public void read() throws SQLException, DataAccessException {
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

}

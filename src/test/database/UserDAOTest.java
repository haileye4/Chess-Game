package database;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UserDAOTest {
    UserDAO users = new UserDAO();
    //try to insert a user with the same username
    //try to find something that's not there
    @Test
    public void insertUser() throws SQLException, DataAccessException {
        //create a new user and insert into database
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        //see if new user is found in the database
        User userFound = users.find("SantaClaus");
        Boolean found = userFound.equals(newUser);

        Assertions.assertEquals(true, found,
                "User inserted was not found");

        users.clear();
    }

    @Test
    public void readUsers() throws SQLException, DataAccessException {
        users.clear();
        //create a new user and insert into database
        User user1 = new User("happySally", "smile1234", "rainbows@gmail.com");
        User user2 = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");

        users.create(user1);
        users.create(user2);

        //create  list of the users you expect to be in the database
        ArrayList<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);

        //see if function reads users correctly
        ArrayList<User> actualUsers = users.read();

        // Check if the ArrayLists are equal
        //do .contains each user and work on size of ordering
        boolean areEqual = Objects.equals(expectedUsers, actualUsers);

        Assertions.assertTrue(areEqual, "Did not read database correctly");

        users.clear();
    }
    @Test
    public void deleteUser() throws SQLException, DataAccessException {
        users.clear();
        //create a new user and insert into database
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        //see if new user is found in the database
        User userFound = users.find("SantaClaus");
        Boolean found = userFound.equals(newUser);

        // LOOK AT INVALID MOVES TEST Assertions.assertThrows(DataAccessException.class, users.read() )
        Assertions.assertEquals(true, found,
                "User inserted was not found");

        users.clear();
    }
}

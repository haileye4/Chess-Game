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

    @Test
    public void clear() throws SQLException, DataAccessException {
        //create a new user and insert into database
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        users.clear();
        ArrayList<User> shouldBeEmpty = users.read();

        Assertions.assertTrue(shouldBeEmpty.isEmpty(), "Didn't clear users correctly");
    }

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
    public void badInsert() throws SQLException, DataAccessException {
        users.clear();
        //attempt to insert someone with an already used username
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        User duplicateUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        Assertions.assertThrows(DataAccessException.class, () -> users.create(duplicateUser));
        users.clear();
    }

    @Test
    public void find() throws SQLException, DataAccessException {
        //try to find a user
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        //see if authToken is found in the database
        User userFound = users.find("SantaClaus");

        Boolean areEqual = Objects.equals(userFound, newUser);
        Assertions.assertEquals(true, areEqual,
                "user was not found");

        users.clear();
    }
    @Test
    public void badFind() throws SQLException, DataAccessException {
        //try to find a user that is not there
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        User newUser2 = new User("PapaElf", "milkAndCookies", "headElf@gmail.com");

        users.create(newUser);
        users.create(newUser2);

        //see if invalid user is found in the database
        User userFound = users.find("reindeerDasher");

        Assertions.assertNull(userFound, "User not inserted was still found");

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
        //create a new user and insert into database
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        //delete new user
        users.delete(newUser);
        User userFound = users.find("SantaClaus");

        // LOOK AT INVALID MOVES TEST Assertions.assertThrows(DataAccessException.class, users.read() )
        Assertions.assertNull(userFound,
                "User was not deleted from database");

        users.clear();
    }

    @Test
    public void badDelete() throws SQLException, DataAccessException {
        //delete a user which does not exist
        User newUser = new User("SantaClaus", "cookiesAndMilk", "santa@gmail.com");
        users.create(newUser);

        User nonexistentUser = new User("PapaElf", "milkAndCookies", "headElf@gmail.com");
        Assertions.assertThrows(DataAccessException.class, () -> users.delete(nonexistentUser));

        users.clear();
    }
}

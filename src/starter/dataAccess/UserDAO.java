package dataAccess;

import chess.ChessGame;
import models.User;

import java.util.ArrayList;

/**
 * stores a user in the database
 */
public class UserDAO {
    /**
     * list of users (for now)
     */
    static ArrayList<User> users = new ArrayList<User>();

    /**
     * create a new user
     * @param user
     * @throws DataAccessException
     */
    public void create(User user) /*throws DataAccessException*/{

        /*if (usernames.contains(user)) {
            throw new DataAccessException("Username already taken");
        }*/

        users.add(user);
    }

    /**
     * read list of users
     * @return list of users in our server
     */
    public ArrayList<User> read() {
        return users;
    }

    /**
     * update a user in the database
     * @param user
     * @throws DataAccessException
     */
    public void update(User user) throws DataAccessException {

    }

    /**
     * delete a user
     * @param user
     * @throws DataAccessException
     */
    public void delete(User user) throws DataAccessException{
        users.remove(user);
    }

    public void clear() {
        users.clear();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

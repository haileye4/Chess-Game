package dataAccess;

import chess.ChessGame;
import models.AuthToken;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * stores a user in the database
 */
public class UserDAO {
    /**
     * create a new user
     * @param user
     * @throws DataAccessException
     */
    public void create(User user) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        //how do I use connection now?
        String sql = "insert into user (username, password, email) values (?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }

            // Log or handle the result as needed
            System.out.println("Added user into user table");
        } catch (SQLException e) {
            throw new DataAccessException("Could not insert user into the database");
        }

        //NEED TO DELETE CONNECTION!
        database.closeConnection(connection);
    }

    /**
     * read list of users
     * @return list of users in our server
     */
    public ArrayList<User> read() throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        ArrayList<User> users = new ArrayList<>();

        String sql = "select username, password, email from user";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);

                users.add(new User(username, password, email));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Can not read out users");
        }

        database.closeConnection(connection);
        return users;
    }

    public User find(String username) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "select username, password, email from user where username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    return new User(username, password, email);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding authToken");
        } finally {
            // Close the connection
            database.closeConnection(connection);
        }

        // If no matching token is found, return null or handle appropriately
        return null;
    }

    /**
     * delete a user
     * @param user
     * @throws DataAccessException
     */
    public void delete(User user) throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "delete from user where username = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());

            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no matching rows found.");
            }

            // Log or handle the result as needed
            System.out.println("Deleted user from user table");
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting user");
        }

        database.closeConnection(connection);
    }

    public void clear() throws DataAccessException, SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();

        String sql = "delete from user";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Execute the SQL statement
            int affectedRows = stmt.executeUpdate();

            // Log or handle the result as needed
            System.out.println("Cleared " + affectedRows + " records from user table");
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing user table");
        } finally {
            // Close the connection
            database.closeConnection(connection);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

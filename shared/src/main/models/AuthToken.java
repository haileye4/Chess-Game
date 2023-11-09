package models;

import java.util.Objects;

/**
 * represents authentication token
 */
public class AuthToken {
    /**
     * authentication token
     */
    private String authToken;
    /**
     * username
     */
    private String username;

    public AuthToken() {

    }

    public AuthToken(String token, String user) {
        authToken = token;
        username = user;
    }

    /**
     * get auth token
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * set the authentication token
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * equals method to decide if an object is equal to the authToken
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the object is an instance of AuthToken
        if (!(obj instanceof AuthToken)) {
            return false;
        }

        AuthToken token = (AuthToken) obj;

        // Compare authToken and username
        return Objects.equals(token.getAuthToken(), authToken) &&
                Objects.equals(token.getUsername(), username);
    }
}

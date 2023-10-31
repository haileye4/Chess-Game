package models;

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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

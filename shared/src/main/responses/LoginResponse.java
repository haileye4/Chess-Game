package responses;

/**
 * login service response
 */
public class LoginResponse extends Response {
    /**
     * authentication token
     */
    private String authToken;
    /**
     * username
     */
    private String username;

    /**
     * constructor
     * @param message
     * @param authToken
     * @param username
     */
    public LoginResponse(String message, String authToken, String username) {
        this.message = message;
        this.authToken = authToken;
        this.username = username;
    }

    public LoginResponse() {

    }

    /**
     * get authentication token
     * @return
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * set authentication token
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * get username
     * @return
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
}

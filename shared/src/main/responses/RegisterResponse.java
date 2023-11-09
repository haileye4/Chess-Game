package responses;

/**
 * register service response
 */
public class RegisterResponse extends Response{
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
    public RegisterResponse(String message, String authToken, String username) {
        this.message = message;
        this.authToken = authToken;
        this.username = username;
    }

    public RegisterResponse() {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    /**
     * get authentication token
     * @return authentication token
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
}

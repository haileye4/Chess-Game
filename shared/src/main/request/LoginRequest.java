package request;

/**
 * login service request
 */
public class LoginRequest {
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;

    /**
     * constructor: set username and password in the request
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {

    }

    /**
     * get username in request
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username in the request
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get password in the request
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password in the request
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

package request;

/**
 * register service request
 */
public class RegisterRequest {
    /**
     * username
     */
    private String username;
    /**
     * user password
     */
    private String password;
    /**
     * user email
     */
    private String email;

    /**
     * basic constructor
     */
    public RegisterRequest() {}

    /**
     * constructor: set username, password, and email in the request
     * @param username
     * @param password
     * @param email
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * get username in the request
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set the usernme in the request
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get the password in the request
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set the password in the request
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get email in the request
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set the email in the request
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

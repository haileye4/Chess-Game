package request;

import java.nio.charset.Charset;

/**
 * createGame service request
 */
public class CreateGameRequest {
    /**
     * game name
     */
    private String gameName;
    /**
     * white team username
     */
    private String whiteUsername = null;
    /**
     * black team username
     */
    private String blackUsername = null;

    /**
     * constructor: set gameName you want to request to create
     * @param gameName
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    /**
     * get the game name in the request
     * @return
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * set the game name in the request
     * @param gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }
}

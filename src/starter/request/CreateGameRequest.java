package request;

/**
 * createGame service request
 */
public class CreateGameRequest {
    /**
     * game name
     */
    private String gameName;

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
}

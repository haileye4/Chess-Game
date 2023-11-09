package responses;

/**
 * create game service response
 */
public class CreateGameResponse extends Response{
    /**
     * game ID
     */
    private Integer gameID;

    /**
     * constructor
     * @param message
     * @param gameID
     */
    public CreateGameResponse(String message, int gameID) {
        this.message = message;
        this.gameID = gameID;
    }

    public CreateGameResponse() {

    }

    /**
     * game game ID
     * @return
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * set game ID
     * @param gameID
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}

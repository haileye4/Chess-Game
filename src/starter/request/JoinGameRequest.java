package request;

import chess.ChessGame;
import chess.ChessPiece;

/**
 * join game service request
 */
public class JoinGameRequest {
    /**
     * player color
     */
    private ChessGame.TeamColor playerColor = null;
    /**
     * game ID
     */
    private int gameID;

    public JoinGameRequest() {

    }
    /**
     * constructor: set the player color and gam eID in the request
     * @param playerColor
     * @param gameID
     */
    public JoinGameRequest(ChessGame.TeamColor playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    /**
     * get player color in the request
     * @return player color
     */
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    /**
     * set player color in the request
     * @param playerColor
     */
    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * get the game ID in the request
     * @return game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * set the game ID in the request
     * @param gameID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}

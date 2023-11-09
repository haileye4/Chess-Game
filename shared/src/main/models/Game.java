package models;

import chess.ChessGame;

import java.util.ArrayList;
import java.util.Objects;

/**
 * represents the game
 */
public class Game {
    /**
     * gameID
     */
    private int gameID;
    /**
     * white team username
     */
    private String whiteUsername;
    /**
     * black team username
     */
    private String blackUsername;

    private ArrayList<String> watchers = new ArrayList<String>();
    /**
     * game name
     */
    private String gameName;
    /**
     * game
     */
    private ChessGame game;

    /**
     * constructor with all parameters
     * @param id
     * @param white
     * @param black
     * @param watchers
     * @param gameName
     * @param game
     */
    public Game(int id, String white, String black, ArrayList<String> watchers,
                String gameName, chess.Game game) {
        gameID = id;
        whiteUsername = white;
        blackUsername = black;
        this.watchers = watchers;
        this.gameName = gameName;
        this.game = game;
    }

    /**
     * another constructor for JSON deserialization in database
     * @param id
     * @param white
     * @param black
     * @param gameName
     * @param game
     */
    public Game(int id, String white, String black,
                String gameName, ChessGame game) {
        gameID = id;
        whiteUsername = white;
        blackUsername = black;
        this.gameName = gameName;
        this.game = game;
    }

    /**
     * basic constructor
     */
    public Game() {

    }

    /**
     * get the gameID
     * @return ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * set gameID
     * @param gameID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * get white team username
     * @return white team username
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * set white teams username
     * @param whiteUsername
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * get black team username
     * @return black team username
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * set black team username
     * @param blackUsername
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    /**
     * get game name
     * @return game name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * set the game name
     * @param gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * get chess game
     * @return chess game
     */
    public ChessGame getGame() {
        return game;
    }

    /**
     * set the chess game
     * @param game
     */
    public void setGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }

        Game game = (Game) obj;

        // Compare authToken and username
        return Objects.equals(game.getGameID(), gameID) &&
                Objects.equals(game.getWhiteUsername(), whiteUsername) &&
                Objects.equals(game.getBlackUsername(), blackUsername) &&
                Objects.equals(game.getGameName(), gameName);
    }

    public ArrayList<String> getWatchers() {
        return watchers;
    }

    public void setWatchers(ArrayList<String> watchers) {
        this.watchers = watchers;
    }

    public void addWatcher(String username) {
        watchers.add(username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, watchers, gameName, game);
    }
}

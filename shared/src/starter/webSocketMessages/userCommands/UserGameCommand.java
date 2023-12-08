package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(CommandType type, String authToken, Integer game, String username, ChessGame.TeamColor team) {
        this.authToken = authToken;
        this.gameID = game;
        this.username = username;
        this.commandType = type;
        this.playerColor = team;
    }

    public UserGameCommand(CommandType type, String authToken, Integer game, String username) {
        this.authToken = authToken;
        this.gameID = game;
        this.username = username;
        this.commandType = type;
    }

    public UserGameCommand(CommandType type, String authToken, Integer game, String username,
                           ChessGame.TeamColor team, ChessMove move) {
        this.authToken = authToken;
        this.gameID = game;
        this.username = username;
        this.commandType = type;
        this.playerColor = team;
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;

    private final Integer gameID;

    private final String username;

    private ChessGame.TeamColor playerColor;

    private ChessMove move;

    public String getAuthString() {
        return authToken;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getUsername(){return username;}

    public CommandType getCommandType() {
        return this.commandType;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}

package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(CommandType type, String authToken, Integer game, ChessGame.TeamColor team) {
        this.authToken = authToken;
        this.gameID = game;
        this.commandType = type;
        this.playerColor = team;
    }

    public UserGameCommand(CommandType type, String authToken, Integer game, String username) {
        this.authToken = authToken;
        this.gameID = game;
        this.username = username;
        this.commandType = type;
    }

    public UserGameCommand(CommandType type, String authToken, Integer game,
                           ChessGame.TeamColor team, ChessMove move) {
        this.authToken = authToken;
        this.gameID = game;
        this.username = username;
        this.commandType = type;
        this.playerColor = team;
        this.move = move;
    }

    public UserGameCommand(CommandType commandType, String authToken, int gameID) {
        this.commandType = commandType;
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public UserGameCommand(CommandType commandType, String authToken, int gameID, ChessPosition piecePosition, ChessGame.TeamColor team) {
        this.commandType = commandType;
        this.gameID = gameID;
        this.piecePosition = piecePosition;
        this.playerColor = team;
        this.authToken = authToken;
    }

    public ChessMove getMove() {
        return move;
    }

    public ChessPosition getPiecePosition() {
        return piecePosition;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN,
        HIGHLIGHT_MOVES,
        LOAD_GAME
    }

    protected CommandType commandType;

    private String authToken;

    private final Integer gameID;

    private String username;

    private ChessGame.TeamColor playerColor;

    private ChessMove move;
    private ChessPosition piecePosition;

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

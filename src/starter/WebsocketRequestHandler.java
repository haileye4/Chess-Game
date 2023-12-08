import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import models.User;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebSocket
public class WebsocketRequestHandler {
    Map<String, Connection> connectionsByAuthToken = new HashMap<>();
    Map<Integer, List<Connection>> connectionsByGameId = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        //if session is not in list, MAKE A CONNECTION!
        var builder = new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(ChessGame.class, new typeAdapters.ChessGameAdapter())
                .registerTypeAdapter(ChessBoard.class, new typeAdapters.ChessBoardAdapter())
                .registerTypeAdapter(ChessPiece.class, new typeAdapters.ChessPieceAdapter())
                .registerTypeAdapter(ChessMove.class, new typeAdapters.ChessMoveAdapter())
                .registerTypeAdapter(ChessPosition.class, new typeAdapters.ChessPositionAdapter())
                .create();


        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        //turn back into userGameCommand

        var connection = getConnection(command.getAuthString(), session);

        System.out.println("determining command type...");

        if (connection!= null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(connection, command);
                case JOIN_OBSERVER -> observe(connection, command);
                case MAKE_MOVE -> move(connection, command);
                case LEAVE -> leave(connection, command);
                case RESIGN -> resign(connection, message);
            }
        } else {
            System.out.println("error bro...");
            Connection.sendError(session.getRemote(), "unknown user");
        }
    }

    private Connection getConnection(String authToken, Session session) throws SQLException, DataAccessException, IOException {
        Connection connection = connectionsByAuthToken.get(authToken);

        if(connection == null) {
            //validate auth token
            boolean valid = false;

            AuthDAO tokens = new AuthDAO();
            if (tokens.find(authToken) != null) {
                valid = true;
            }

            if(valid) {
                connection = new Connection(authToken, session);
                connectionsByAuthToken.put(authToken, connection);
            } else {
                System.out.println("AuthToken is not valid.");
                ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                serverMessage.setErrorMessage("Bad authToken");
                serverMessage.setMessage("Bad authToken");
                String stringMessage = new Gson().toJson(serverMessage);

                session.getRemote().sendString(stringMessage);
            }
        }

        return connection;
    }

    @OnWebSocketConnect
    public void test(Session session) {
        System.out.println("A session has connected...");
    }

    private void join(Connection connection, UserGameCommand command) throws IOException, SQLException, DataAccessException {
        //ACCESS DAO FROM SERVER
        System.out.println("entered join function in server...");
        System.out.println(command.getGameID());
        int gameID = command.getGameID();
        String authToken = command.getAuthString();

        AuthDAO tokens = new AuthDAO();
        String username = tokens.findUsername(authToken);

        GameDAO games = new GameDAO();
        Game myGame = games.find(gameID);

        if (myGame == null) {
            System.out.println("GAME IS NULL");
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            serverMessage.setErrorMessage("Game does not exist");
            String stringMessage = new Gson().toJson(serverMessage);
            connection.send(stringMessage);
            return;
        }

        //if trying to join an already taken team TEST CASES ONLY USE AUTHTOKEN
        if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && !Objects.equals(myGame.getWhiteUsername(), username)) {
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            serverMessage.setErrorMessage("Team already taken");
            String stringMessage = new Gson().toJson(serverMessage);
            connection.send(stringMessage);
            return;
        } else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && !Objects.equals(myGame.getBlackUsername(), username)) {
            System.out.println("BLACK TEAM ERROR");
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            serverMessage.setErrorMessage("Team already taken");
            String stringMessage = new Gson().toJson(serverMessage);
            connection.send(stringMessage);
            return;
        }

        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.setMessage("\n" + username + " has joined the game!");

        String message = new Gson().toJson(serverMessage);

        //if the game is not in the list, put it in the list.
        if (connectionsByGameId.get(gameID) == null) {
            connectionsByGameId.put(gameID, new ArrayList<>());
            connectionsByGameId.get(gameID).add(connection);
        } else {
            connectionsByGameId.get(gameID).add(connection);
        }

        System.out.println("New connection size now that they've joined game: " + connectionsByGameId.get(gameID).size());

        //serialize it into JSON form
        for (Connection user: connectionsByGameId.get(gameID)) {
            System.out.println("sending messages to all users...");
            if (!user.getAuthToken().equals(connection.getAuthToken())) {
                user.send(message);
            } else { //send a load game to the user who just joined...

                ChessGame chessGame = myGame.getGame();
                ChessBoard board = chessGame.getBoard();

                ServerMessage rootMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                rootMessage.setGame(chessGame);

                if (Objects.equals(myGame.getWhiteUsername(), username)) {
                    rootMessage.setTeamColor(ChessGame.TeamColor.WHITE);
                } else if (Objects.equals(myGame.getBlackUsername(), username)) {
                    rootMessage.setTeamColor(ChessGame.TeamColor.BLACK);
                }

                String rootStringMessage = new Gson().toJson(rootMessage);
                user.send(rootStringMessage);
            }
        }
    }

    private void observe(Connection connection, UserGameCommand command) throws SQLException, DataAccessException, IOException {
        int gameID = command.getGameID();
        String authToken = command.getAuthString();
        AuthDAO tokens = new AuthDAO();
        String username = tokens.findUsername(authToken);

        GameDAO games = new GameDAO();
        Game myGame = games.find(gameID);

        if (myGame == null) {
            System.out.println("GAME IS NULL");
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            serverMessage.setErrorMessage("Game does not exist");
            String stringMessage = new Gson().toJson(serverMessage);
            connection.send(stringMessage);
            return;
        }

        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.setMessage("\n" + username + " has joined the game as an observer!");

        String message = new Gson().toJson(serverMessage);

        //if the game is not in the list, put it in the list.
        if (connectionsByGameId.get(gameID) == null) {
            connectionsByGameId.put(gameID, new ArrayList<>());
            connectionsByGameId.get(gameID).add(connection);
        } else {
            connectionsByGameId.get(gameID).add(connection);
        }

        System.out.println("New connection size now that they've joined as observer: " + connectionsByGameId.get(gameID).size());

        //serialize it into JSON form
        for (Connection user: connectionsByGameId.get(gameID)) {
            System.out.println("sending messages to all users...");
            if (!user.getAuthToken().equals(connection.getAuthToken())) {
                user.send(message);
            } else { //send a load game to the user who just joined...

                ChessGame chessGame = myGame.getGame();
                ChessBoard board = chessGame.getBoard();

                ServerMessage rootMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                rootMessage.setGame(chessGame);

                rootMessage.setTeamColor(ChessGame.TeamColor.BLACK);

                String rootStringMessage = new Gson().toJson(rootMessage);
                user.send(rootStringMessage);
            }
        }
    }

    private void move(Connection connection, UserGameCommand command) throws SQLException, DataAccessException, IOException {
        ChessMove chessMove = command.getMove();
        int gameID = command.getGameID();

        GameDAO games = new GameDAO();
        Game myGame = games.find(gameID);
        ChessGame chessGame = myGame.getGame();

        try {
            chessGame.makeMove(chessMove);
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move!");
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            serverMessage.setErrorMessage("Invalid chess move");
            String stringMessage = new Gson().toJson(serverMessage);
            connection.send(stringMessage);
            return;
        }

        //else, valid move, and let's update it to the DAO.
        myGame.setGame(chessGame);
        games.update(myGame);

        for (var c : connectionsByGameId.get(gameID)) {
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            serverMessage.setMessage(command.getUsername() + " just made a move.");
            serverMessage.setGame(chessGame);
            String stringMessage = new Gson().toJson(serverMessage);
            c.send(stringMessage);
        }

    }

    private void leave(Connection connection, UserGameCommand command) throws IOException {
        int gameID = command.getGameID();
        String username = command.getUsername();

        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.setMessage(username + " has left the game.");
        String message = new Gson().toJson(serverMessage);

        for (var c : connectionsByGameId.get(gameID)) {
            if (!c.getAuthToken().equals(connection.getAuthToken())) {
                c.send(message);
            }
        }

        // Remove the connection that is about to leave
        connectionsByGameId.get(gameID).remove(connection);
        connectionsByAuthToken.remove(connection.getAuthToken());

        System.out.println("New connection size in game: " + connectionsByGameId.get(gameID).size());
        System.out.println("Connections total now: " + connectionsByAuthToken.size());
    }

    private void resign(Connection connection, String message) {

    }

    private record Connection(String authToken, Session session) {
        public void send(String message) throws IOException {
            session.getRemote().sendString(message);
        }
        public static void sendError(RemoteEndpoint Remote, String unknownUser) throws IOException {
            Remote.sendString(unknownUser);
        }

        public String getAuthToken() {
            return authToken;
        }
    }

}

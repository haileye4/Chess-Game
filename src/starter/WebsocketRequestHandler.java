import com.google.gson.Gson;
import models.AuthToken;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebSocket
public class WebsocketRequestHandler {
    Map<String, Connection> connectionsByAuthToken = new HashMap<>();
    Map<Integer, List<Connection>> connectionsByGameId = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {

        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        //turn back into userGameCommand

        var connection = getConnection(command.getAuthString(), session);

        if (connection!= null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(connection, command);
                case JOIN_OBSERVER -> observe(connection, message);
                case MAKE_MOVE -> move(connection, message);
                case LEAVE -> leave(connection, command);
                case RESIGN -> resign(connection, message);
            }
        } else {
            Connection.sendError(session.getRemote(), "unknown user");
        }
    }

    private Connection getConnection(String authToken, Session session) {
        Connection connection = connectionsByAuthToken.get(authToken);

        if(connection == null) {
            //validate auth token
            boolean valid = false;

            if(valid) {
                connection = new Connection(authToken, session);
                connectionsByAuthToken.put(authToken, connection);
            }
        }

        return connection;
    }

    @OnWebSocketConnect
    public void test(Session session) {
        System.out.println("A session has connected...");
    }

    private void join(Connection connection, UserGameCommand command) throws IOException {
        int gameID = command.getGameID();
        String username = command.getUsername();

        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.setMessage(username + " has joined the game!");

        String message = new Gson().toJson(serverMessage);

        //if the game is not in the list, put it in the list.
        if (connectionsByGameId.get(gameID) == null) {
            connectionsByGameId.put(gameID, new ArrayList<>());
            connectionsByGameId.get(gameID).add(connection);
        }

        //serialize it into JSON form
        for (Connection user: connectionsByGameId.get(gameID)) {
            user.send(message);
        }
    }

    private void observe(Connection connection, String message) {

    }

    private void move(Connection connection, String message) {

    }

    private void leave(Connection connection, UserGameCommand command) throws IOException {
        int gameID = command.getGameID();
        String username = command.getUsername();

        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.setMessage(username + " has left the game.");

        String message = new Gson().toJson(serverMessage);

        /*var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.visitorName.equals(excludeVisitorName)) {
                    c.send(notification.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.visitorName);
        }*/
        connectionsByGameId.get(gameID).remove(connection);

        //serialize it into JSON form
        for (Connection user: connectionsByGameId.get(gameID)) {
            user.send(message);
        }
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
    }

}

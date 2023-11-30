import com.google.gson.Gson;
import models.AuthToken;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebSocket
public class WebsocketRequestHandler {
    Map<String, Connection> connectionsByAuthToken = new HashMap<>();
    Map<Integer, List<Connection>> connectionsByGameId = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = readJson(message, UserGameCommand.class);
        //what is this? ^^

        var connection = getConnection(command.getAuthString(), session);
        if (connection!= null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(connection, message);
                case JOIN_OBSERVER -> observe(connection, message);
                case MAKE_MOVE -> move(connection, message);
                case LEAVE -> leave(connection, message);
                case RESIGN -> resign(connection, message);
            }
        } else {
            Connection.sendError(session.getAsyncRemote(), "unknown user");
            //IS IT ASYNC?
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

    private void join(Connection connection, String message) {

    }

    private void observe(Connection connection, String message) {

    }

    private void move(Connection connection, String message) {

    }

    private void leave(Connection connection, String message) {

    }

    private void resign(Connection connection, String message) {

    }

    private record Connection(String authToken, Session session) {
        public static void sendError(RemoteEndpoint.Async asyncRemote, String unknownUser) {
        }
    }

}

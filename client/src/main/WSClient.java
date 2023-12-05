import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

@ClientEndpoint
public class WSClient extends Endpoint {
    public Session session;

    public WSClient() throws DeploymentException, IOException, URISyntaxException {
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);*/

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) { //server message
                //deserialize JSON back to a server message object type and do something about it
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);

                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> WSClient.this.notify(serverMessage.getMessage());
                    case ERROR -> WSClient.this.error();
                    case LOAD_GAME -> WSClient.this.loadGame();
                }
                //switch statement here

                //ex. print out notification
                //ex. if you have a load game, draw board with old function I made
                //if trying ot load game, like get a board to draw it, here I will need type adapters again
            }
        });
    }

    public void send(UserGameCommand msg) throws Exception {
        System.out.println("about to send command to server...");
        this.session.getBasicRemote().sendText(new Gson().toJson(msg));

        if (msg.getCommandType() == UserGameCommand.CommandType.LEAVE) {
            session.close();
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void notify(String message) {
        System.out.println(message);
    }

    public void error(){

    }

    public void loadGame(){

    }
}
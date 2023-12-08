import ChessUI.DrawBoard;
import chess.Board;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import static ChessUI.EscapeSequences.RESET_TEXT_COLOR;
import static ChessUI.EscapeSequences.SET_TEXT_COLOR_MAGENTA;

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
                var builder = new GsonBuilder();
                Gson gson = builder.registerTypeAdapter(ChessGame.class, new typeAdapters.ChessGameAdapter())
                        .registerTypeAdapter(ChessBoard.class, new typeAdapters.ChessBoardAdapter())
                        .registerTypeAdapter(ChessPiece.class, new typeAdapters.ChessPieceAdapter())
                        .create();

                //var game = gson.fromJson(json, chess.Game.class);
                ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);

                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> WSClient.this.notify(serverMessage.getMessage());
                    case ERROR -> WSClient.this.error(serverMessage.getErrorMessage());
                    case LOAD_GAME -> WSClient.this.loadGame(serverMessage.getGame(), serverMessage.getTeamColor(), serverMessage.getMessage());
                }
                //switch statement here

                //ex. print out notification
                //ex. if you have a load game, draw board with old function I made
                //if trying ot load game, like get a board to draw it, here I will need type adapters again
            }
        });
    }

    public void send(UserGameCommand msg) throws Exception {
        //System.out.println("about to send command to server...");
        //Is there any way for me to do this and WAIT? until it is done?? ! this one beloe
        this.session.getBasicRemote().sendText(new Gson().toJson(msg));

        if (msg.getCommandType() == UserGameCommand.CommandType.LEAVE) {
            session.close();
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void notify(String message) {
        System.out.print(SET_TEXT_COLOR_MAGENTA);
        System.out.println(message);
        System.out.print(RESET_TEXT_COLOR);
    }

    public void error(String message){
        System.out.print(SET_TEXT_COLOR_MAGENTA);
        System.out.println(message);
        System.out.print(RESET_TEXT_COLOR);
    }

    public void loadGame(ChessGame game, ChessGame.TeamColor team, String message){
        //System.out.println("Load Game Message Received...");

        ChessBoard board = game.getBoard();

        if (team == ChessGame.TeamColor.WHITE) {
            DrawBoard.drawChessboardWhite(System.out, board);
        } else if (team == ChessGame.TeamColor.BLACK) {
            DrawBoard.drawChessboard(System.out, board);
            //means just draw a black chessboard...
        }

        System.out.println(message);
        //get game out of message
    }
}
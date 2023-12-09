import ChessUI.DrawBoard;
import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import static ChessUI.EscapeSequences.*;

@ClientEndpoint
public class WSClient extends Endpoint {
    public Session session;
    //make an object called mygame

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
                        .registerTypeAdapter(ChessPosition.class, new typeAdapters.ChessPositionAdapter())
                        .create();

                //var game = gson.fromJson(json, chess.Game.class);
                ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);

                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> WSClient.this.notify(serverMessage.getMessage());
                    case ERROR -> WSClient.this.error(serverMessage.getErrorMessage());
                    case LOAD_GAME -> WSClient.this.loadGame(serverMessage.getGame(), serverMessage.getTeamColor(),
                            serverMessage.getMessage(), serverMessage.getPosition());
                    case HIGHLIGHT_MOVES -> WSClient.this.highlight(serverMessage);
                }
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
        System.out.print(SET_TEXT_COLOR_WHITE);
    }

    public void loadGame(ChessGame game, ChessGame.TeamColor team, String message, ChessPosition piecePosition){
        ChessBoard board = game.getBoard();

        if (Objects.equals(message, "highlight")) {
            Collection<ChessMove> validMoves = game.validMoves(piecePosition);
            DrawBoard.drawValidMoves(System.out, validMoves, game.getBoard(), team);
            return;
        }

        if (team == ChessGame.TeamColor.WHITE) {
            DrawBoard.drawChessboardWhite(System.out, board);
        } else if (team == ChessGame.TeamColor.BLACK) {
            DrawBoard.drawChessboard(System.out, board);
            //means just draw a black chessboard...
        } else {
            DrawBoard.drawChessboard(System.out, board);
        }
    }

    public void highlight(ServerMessage message) {
        System.out.println("Highlight Method entered!");
        ChessGame chessGame = message.getGame();
        ChessPosition piecePosition = message.getPosition();
        ChessGame.TeamColor team = message.getTeamColor();

        Collection<ChessMove> validMoves = chessGame.validMoves(piecePosition);
        DrawBoard.drawValidMoves(System.out, validMoves, chessGame.getBoard(), team);
    }
}
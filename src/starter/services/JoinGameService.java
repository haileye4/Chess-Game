package services;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import models.User;
import request.JoinGameRequest;
import responses.JoinGameResponse;

import java.util.Objects;

/**
 * join game service
 */
public class JoinGameService extends Service {
    /**
     * join a game
     *
     * @param request
     * @param authToken
     * @return response
     */
    public JoinGameResponse joinGame(JoinGameRequest request, String authToken) {
        JoinGameResponse response = new JoinGameResponse();

        AuthDAO tokens = new AuthDAO();

        //if authToken is not in the database...
        boolean isAuthorized = isAuthorized(authToken);

        if (!isAuthorized) {
            response.setMessage("Error: unauthorized");
            return response;
        }

        //get username
        String username = "";
        for (AuthToken userToken : tokens.read()) {
            if (Objects.equals(userToken.getAuthToken(), authToken)) {
                username = userToken.getUsername();
            }
        }

        //look to make sure the game exists
        GameDAO games = new GameDAO();
        int gameID = request.getGameID();
        boolean isValidGame = false;

        for (Game game : games.read()) {
            if (game.getGameID() == gameID) {
                isValidGame = true;
            }
        }
        //if game doesn't exist...
        if (!isValidGame) {
            response.setMessage("Error: game doesn't exist");
            return response;
        }

        //make sure team isn't already taken: find the game to join
        for (Game game : games.read()) {
            if (game.getGameID() == gameID) {
            //found the game...
                if (request.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                    if (game.getWhiteUsername() != null) {
                        response.setMessage("Error: already taken");
                        return response;
                    }

                } else if (request.getPlayerColor() == ChessGame.TeamColor.BLACK) {
                    if (game.getBlackUsername() != null) {
                        response.setMessage("Error: already taken");
                        return response;
                    }
                }
            }
        }

        //okay, now we can add the person to the game then...
        for (Game game : games.read()) {
            if (game.getGameID() == gameID) {
                if (request.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                    game.setWhiteUsername(username);
                } else if (request.getPlayerColor() == ChessGame.TeamColor.BLACK) {
                    game.setBlackUsername(username);
                } else if (request.getPlayerColor() == null) {
                    User watcher = new User();
                    game.addWatcher(username);
                }
            }
        }


        return response;
    }
}

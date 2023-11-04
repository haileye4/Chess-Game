package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import request.CreateGameRequest;
import responses.CreateGameResponse;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * create game service
 */
public class CreateGameService extends Service {
    /**
     * create a game
     * @param request
     * @return response
     */
    public CreateGameResponse createGame(CreateGameRequest request, String authToken) throws SQLException, DataAccessException {
        CreateGameResponse response = new CreateGameResponse();

        //check to make sure there is a gameName
        if (request.getGameName() == null) {
            response.setMessage("Error: bad request (no game name included)");
            response.setGameID(null);
            return response;
        }

        String gameName = request.getGameName();
        AuthDAO tokens = new AuthDAO();

        //if authToken is not in the database...
        boolean isAuthorized = isAuthorized(authToken); //gets rid of duplicate code

        if (!isAuthorized) {
            response.setMessage("Error: unauthorized");
            return response;
        }

        //else, this person has an auth token and is authorized...
        //set game name
        Game newGame = new Game();
        newGame.setGameName(request.getGameName());
        //set game ID
        Random random = new Random();
        int gameID = 1000 + random.nextInt(9000);
        newGame.setGameID(gameID); //generate a random 4 digit ID
        //set a new chess game
        newGame.setGame(new chess.Game());

        //do I need to also set white and black username?
        newGame.setWhiteUsername(request.getWhiteUsername());
        newGame.setBlackUsername(request.getBlackUsername());

        GameDAO games = new GameDAO();
        games.create(newGame);

        response.setGameID(gameID);
        return response;
    }

}

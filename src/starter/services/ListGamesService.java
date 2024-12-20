package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import responses.ListGamesResponse;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * list games service
 */
public class ListGamesService extends Service {
    /**
     * list games
     * @return response
     */
    public ListGamesResponse listGames(String authToken) throws SQLException, DataAccessException {
        ListGamesResponse response = new ListGamesResponse();
        AuthDAO tokens = new AuthDAO();

        //if authToken is not in the database...
        boolean isAuthorized = isAuthorized(authToken);

        if (!isAuthorized) {
            response.setMessage("Error: unauthorized");
            return response;
        }

        //else, if they are authorized...
        GameDAO games = new GameDAO();
        ArrayList<Game> myGames;
        myGames = games.read();
        response.setGames(myGames);
        return response;
    }
}

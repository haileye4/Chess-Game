package responses;

import models.Game;

import java.util.ArrayList;

/**
 * list games service response
 */
public class ListGamesResponse extends Response {
    private ArrayList<Game> games;
    /**
     * constructor
     */
    public ListGamesResponse() {
    }
    public ListGamesResponse(String message) {
        this.message = message;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}

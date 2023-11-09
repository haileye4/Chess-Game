package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import responses.ListGamesResponse;
import services.ListGamesService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Objects;

public class ListGamesHandler {
    public Object handleRequest(Request req, Response res) throws SQLException, DataAccessException {
        Gson gson = new Gson();

        String authToken = req.headers("authorization");

        // Call the ListGamesService to perform the service
        ListGamesService service = new ListGamesService();
        ListGamesResponse result = service.listGames(authToken);

        if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
            res.status(401);
        } else {
            res.status(200);
        }

        //return response
        return gson.toJson(result);
    }
}

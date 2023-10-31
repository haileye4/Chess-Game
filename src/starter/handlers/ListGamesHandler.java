package handlers;

import com.google.gson.Gson;
import models.AuthToken;
import request.LoginRequest;
import responses.ListGamesResponse;
import responses.LoginResponse;
import services.ListGamesService;
import services.LoginService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class ListGamesHandler {
    public Object handleRequest(Request req, Response res) {
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

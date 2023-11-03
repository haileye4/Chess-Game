package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthToken;
import request.JoinGameRequest;
import request.LoginRequest;
import responses.JoinGameResponse;
import responses.LoginResponse;
import services.JoinGameService;
import services.LoginService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Objects;

public class JoinGameHandler {
    public Object handleRequest(Request req, Response res) throws SQLException, DataAccessException {
        //Get JSON data
        String reqData = req.body();

        // Use Gson to deserialize the JSON data into a JoinGameRequest object
        Gson gson = new Gson();
        JoinGameRequest request = (JoinGameRequest)gson.fromJson(reqData, JoinGameRequest.class);

        //get authToken
        String authToken = req.headers("authorization");

        // Call the JoinGameService to perform the service
        JoinGameService service = new JoinGameService();
        JoinGameResponse result = service.joinGame(request, authToken);

        if (Objects.equals(result.getMessage(), "Error: bad request (no player color included)")) {
            res.status(400);
        } else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
            res.status(401);
        } else if (Objects.equals(result.getMessage(), "Error: game doesn't exist")) {
            res.status(400);
        } else if (Objects.equals(result.getMessage(), "Error: already taken")) {
            res.status(403);
        } else {
            res.status(200);
        }

        //return response
        return gson.toJson(result);
    }
}

package handlers;

import com.google.gson.Gson;
import models.AuthToken;
import request.CreateGameRequest;
import request.LoginRequest;
import responses.CreateGameResponse;
import responses.LoginResponse;
import services.CreateGameService;
import services.LoginService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameHandler {
    public Object handleRequest(Request req, Response res) {
        //Get JSON data
        String reqData = req.body();

        // Use Gson to deserialize the JSON data into a CreateGameRequest object
        Gson gson = new Gson();
        CreateGameRequest request = (CreateGameRequest)gson.fromJson(reqData, CreateGameRequest.class);

        //get authToken
        String authToken = req.headers("authorization");

        // Call the CreateGameService to perform the service
        CreateGameService service = new CreateGameService();
        CreateGameResponse result = service.createGame(request, authToken);

        if (Objects.equals(result.getMessage(), "Error: bad request (no game name included)")) {
            res.status(400);
        } else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
            res.status(401);
        } else {
            res.status(200);
        }
        //return response
        return gson.toJson(result);
    }
}

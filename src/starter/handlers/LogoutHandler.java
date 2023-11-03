package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthToken;
import request.LoginRequest;
import request.RegisterRequest;
import responses.LogoutResponse;
import responses.RegisterResponse;
import services.LogoutService;
import services.RegisterService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Objects;

public class LogoutHandler {
    public Object handleRequest(Request req, Response res) throws SQLException, DataAccessException {
        Gson gson = new Gson();

        //get authtoken
        String authToken = req.headers("authorization");

        // Call the LogoutService to perform the service
        LogoutService service = new LogoutService();
        LogoutResponse result = service.logout(authToken);

        if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
            res.status(401);
        } else {
            res.status(200);
        }

        //return response
        return gson.toJson(result);
    }
}

package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.LoginRequest;
import responses.ClearApplicationResponse;
import responses.ListGamesResponse;
import responses.LoginResponse;
import services.ListGamesService;
import services.LoginService;
import spark.Request;
import spark.Response;
import services.ClearApplicationService;

import java.sql.SQLException;

public class ClearApplicationHandler {
    public Object handleRequest(Request req, Response res) throws SQLException, DataAccessException {
        Gson gson = new Gson();

        // Call the ClearApplicationService to perform the service
        ClearApplicationService service = new ClearApplicationService();
        ClearApplicationResponse result = service.clearApplication();

        res.status(200);
        //res.body(gson.toJson(result));
        //return response
        return gson.toJson(result);
    }
}

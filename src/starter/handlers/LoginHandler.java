package handlers;
//package request;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Objects;

public class LoginHandler {

    public Object handleRequest(Request req, Response res) throws SQLException, DataAccessException {
        //Get JSON data
        String reqData = req.body();

        // Use Gson to deserialize the JSON data into a LoginRequest object
        Gson gson = new Gson();
        LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);

        // Call the LoginService to perform the login
        LoginService service = new LoginService();
        LoginResponse result = service.login(request);

        //return response

        if (Objects.equals(result.getMessage(), "Error: user not found")) {
            res.status(401);
        } else {
            res.status(200);
        }

        return gson.toJson(result);
    }

}
